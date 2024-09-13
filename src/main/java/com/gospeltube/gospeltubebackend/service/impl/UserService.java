package com.gospeltube.gospeltubebackend.service.impl;

import com.amazonaws.services.mq.model.NotFoundException;
import com.gospeltube.gospeltubebackend.dto.*;
import com.gospeltube.gospeltubebackend.entity.*;
import com.gospeltube.gospeltubebackend.exception.BadRequestException;
import com.gospeltube.gospeltubebackend.exception.ResourceNotFoundException;
import com.gospeltube.gospeltubebackend.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService {


    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final  FileServiceImpl fileService;
    private final ChurchRepository churchRepository;
    private final ChurchFollowersRepository churchFollowersRepository;
    private final DevotionalRepository devotionalRepository;
    private final BlogRepository blogRepository;
    private final NotificationRepository notificationRepository;
    private final LeadersRepository leadersRepository;
    private final VideoCommentsRepository videoCommentsRepository;
    private final BlogCommentRepository blogCommentRepository;
    private final ContinueWatchingRepository continueWatchingRepository;
    private final VideoRepository videoRepository;
    private final AudioRepository audioRepository;
    private final ReportRepository reportRepository;
    private final FeedbackRepository feedbackRepository;

    public UserService(PasswordEncoder encoder, UserRepository userRepository, FileServiceImpl fileService, ChurchRepository churchRepository, ChurchFollowersRepository churchFollowersRepository,
                       DevotionalRepository devotionalRepository, BlogRepository blogRepository, NotificationRepository notificationRepository, LeadersRepository leadersRepository,
                       VideoCommentsRepository videoCommentsRepository, BlogCommentRepository blogCommentRepository, ContinueWatchingRepository continueWatchingRepository,
                       VideoRepository videoRepository, AudioRepository audioRepository, ReportRepository reportRepository, FeedbackRepository feedbackRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.fileService = fileService;
        this.churchRepository = churchRepository;
        this.churchFollowersRepository = churchFollowersRepository;
        this.devotionalRepository = devotionalRepository;
        this.blogRepository = blogRepository;
        this.notificationRepository = notificationRepository;
        this.leadersRepository = leadersRepository;
        this.videoCommentsRepository = videoCommentsRepository;
        this.blogCommentRepository = blogCommentRepository;
        this.continueWatchingRepository = continueWatchingRepository;
        this.videoRepository = videoRepository;
        this.audioRepository = audioRepository;
        this.reportRepository = reportRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public boolean followChurch(String email, Long church_id ){
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );

        AppUser churchUser = this.userRepository.findById(church_id).orElseThrow(
                () -> new ResourceNotFoundException("church doesn't exit")
        );

        Church church = churchUser.getChurch();
        if (church == null) throw new ResourceNotFoundException("church doesn't exist");

        Optional<ChurchFollowers> churchFollowers = this.churchFollowersRepository.getIfFollow(user.getUserId(), churchUser.getChurch().getId());
        if (churchFollowers.isPresent()){
            churchUser.setTotalSubscribers(churchUser.getTotalSubscribers() - 1);
            this.userRepository.save(churchUser);
            churchFollowersRepository.delete(churchFollowers.get());
            return false;
        }
        else{
            churchUser.setTotalSubscribers(churchUser.getTotalSubscribers() + 1);
            this.userRepository.save(churchUser);
            churchFollowersRepository.save(new ChurchFollowers(
                    church,
                    user
            ));
            NotificationDto notificationDto = new NotificationDto(
                    user.getUserId(),
                    null,
                    church.getId(),
                    "FOLLOW",
                    "followed you"

            );
            this.notification(notificationDto);
            return true;
        }
    }

    public Set<ChurchResponseDto> getChurches(String email){
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new BadRequestException("user not found")
        );
        // TODO: optimize this repository layer from O(n)
        Set<AppUser> church = this.userRepository.findByChurchIdIsNotNull().orElseThrow(
                () -> new ResourceNotFoundException("no data found")
        );

        return church.stream().map(

                iChurch -> new ChurchResponseDto(
                        iChurch.getFirstName(),
                        iChurch.getUserId(),
                        iChurch.getProfilePicture(),
                        iChurch.getTotalStreams(),
                        iChurch.getTotalLikes(),
                        iChurch.getTotalSubscribers(),
                        iChurch.getTotalPosts(),
                        checkFollow(iChurch.getChurch().getId(), user.getUserId()),
                        iChurch.getChurch().getDescription()
                )
        ).collect(Collectors.toSet());
    }

    public Set<ChurchResponseDto> searchChurch(String name){
        Set<AppUser> church = this.userRepository.findByFirstNameContainingAndChurchIdIsNotNull(name).orElseThrow(
                () -> new ResourceNotFoundException("no data")
        );
        return church.stream().map(
                iChurch -> new ChurchResponseDto(
                        iChurch.getFirstName(),
                        iChurch.getChurch().getId(),
                        iChurch.getProfilePicture(),
                        iChurch.getTotalStreams(),
                        iChurch.getTotalLikes(),
                        iChurch.getTotalSubscribers(),
                        iChurch.getTotalPosts(),
                        true,
                        iChurch.getChurch().getDescription()
                )
        ).collect(Collectors.toSet());
    }

    public Set<Devotional> getBookmarkedDevotionals(String email){
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        return this.devotionalRepository.findByBookmarked(user.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("you have no bookmark")
        );
    }

    public Set<Blog> getLiked(String email){
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        return this.blogRepository.findLiked(user.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("you have no bookmark")
        );
    }

    public boolean notification(NotificationDto notificationDto) {
        AppUser user = this.userRepository.findById(notificationDto.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );

        Church church = this.churchRepository.findById(notificationDto.getChurchId()).orElseThrow(
                () -> new ResourceNotFoundException("church not found")
        );

        String message = MessageFormat.format("{0} {1} {2}", user.getFirstName(), user.getLastName(),
                notificationDto.getNotificationType());

        this.notificationRepository.save(
                new Notification(
                        message,
                        notificationDto.getContentType(),
                        notificationDto.getNotificationType(),
                        notificationDto.getUserId(),
                        notificationDto.getItemId(),
                        church
                )
        );
        return true;
    }

    public UserChurchDto userGetSingleChurch(String email, Long churchId){


        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        AppUser churchUser = this.userRepository.findById(churchId).orElseThrow(
                () -> new NotFoundException("church does not exist")
        );

        Church church = this.churchRepository.findById(churchUser.getChurch().getId()).orElseThrow(
                () -> new ResourceNotFoundException("church doesn't exit")
        );

        boolean following =  this.churchFollowersRepository.existsByChurchAndUser(church, user);

        Set<Leaders> churchLeaders = leadersRepository.findByChurchId(church.getId()).get();
        return new UserChurchDto(
                churchUser.getFirstName(),
                churchUser.getUserId(),
                church.getLogo(),
                church.getDescription(),
                churchUser.getTotalPosts(),
                churchUser.getTotalSubscribers(),
                following,
                church.getVision(),
                church.getMission(),
                church.getBio(),
                churchLeaders,
                church.getInstagram(),
                church.getTwitter(),
                church.getFacebook(),
                church.getAddress(),
                churchUser.getPhoneNumber(),
                churchUser.getEmail()
        );
    }

    public boolean updateChurch(UpdateChurchDto updateChurchDto, String email){
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        Church church = this.churchRepository.findById(user.getChurch().getId()).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        if (updateChurchDto.getLogo() != null) {
            church.setLogo(updateChurchDto.getLogo());
            user.setProfilePicture(updateChurchDto.getLogo());
        }
        if (updateChurchDto.getChurchName() != null) {
            church.setChurchName(updateChurchDto.getChurchName());
        }
        if (updateChurchDto.getWebsite() != null) {
            church.setWebsite(updateChurchDto.getWebsite());
        }
        if (updateChurchDto.getBio() != null) {
            church.setBio(updateChurchDto.getBio());
        }
        if (updateChurchDto.getMission() != null) {
            church.setMission(updateChurchDto.getMission());
        }
        if (updateChurchDto.getVision() != null) {
            church.setVision(updateChurchDto.getVision());
        }
        if (updateChurchDto.getInstagram() != null) {
            church.setInstagram(updateChurchDto.getInstagram());
        }
        if (updateChurchDto.getFacebook() != null) {
            church.setFacebook(updateChurchDto.getFacebook());
        }
        if (updateChurchDto.getTwitter() != null) {
            church.setTwitter(updateChurchDto.getTwitter());
        }
        this.userRepository.save(user);
        this.churchRepository.save(church);
        return true;
    }

    public boolean deleteAccount(String email){
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        user.setEnabled(false);
        this.userRepository.save(user);
        return true;
    }

    public boolean changePassword(String email, String password){
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        String encodedPassword = encoder.encode(password);
        encodedPassword = "{bcrypt}" + encodedPassword;
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return true;
    }

    public boolean addLeaders(List<LeadersDto> leaders, String email){
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        Church church = this.churchRepository.findById(user.getChurch().getId()).orElseThrow(
                () -> new NotFoundException("user not found")
        );

        Set<Leaders> leadersSet = new HashSet<>();

        for(int i=0; i <= leaders.size() - 1; i++ ){
            Leaders leader = new Leaders(
                    leaders.get(i).getImage(),
                    leaders.get(i).getName(),
                    leaders.get(i).getBio(),
                    church
            );
            leadersSet.add(leader);
        }
        this.leadersRepository.saveAll(leadersSet);
        return true;
    }

    public AnalyticsResDto getAnalytics(String email){
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
       return new AnalyticsResDto(user.getTotalLikes(), user.getTotalSubscribers(), user.getTotalStreams());
    }

    public CommentLikes likeComment(String email, Long itemId, String type){
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        if (type == "VIDEO"){
            VideoComment videoComment = this.videoCommentsRepository.findById(itemId).orElseThrow(
                    () -> new NotFoundException("comment not found")
            );
            videoComment.setTotalLikes(videoComment.getTotalLikes() + 1);
            this.videoCommentsRepository.save(videoComment);
        }
        else if (type == "BLOG"){
            BlogComment blogComment = this.blogCommentRepository.findById(itemId).orElseThrow(
                    () -> new NotFoundException("comment not found")
            );
            blogComment.setTotalLikes(blogComment.getTotalLikes() + 1);
            this.blogCommentRepository.save(blogComment);
        }
        return new CommentLikes(
                itemId,
                user.getUserId(),
                type
        );
    }
    public boolean checkFollow(Long churchId, Long userId){
        boolean isFollowing = false;
        Optional<ChurchFollowers> churchFollowers = this.churchFollowersRepository.getIfFollow(userId, churchId);
        if (churchFollowers.isPresent()){
            isFollowing = true;
        }
        return isFollowing;
    }

    public boolean continueWatching( ContinueWatchingDto continueWatchingDto, String email){
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        Videos video = this.videoRepository.findById(continueWatchingDto.getVideoId()).orElseThrow(
                () -> new NotFoundException("video not found")
        );
        Optional<ContinueWatching> optionalContinueWatching = this.continueWatchingRepository.findByVideoAndUser(video, user);

        if (optionalContinueWatching.isPresent()) {
            ContinueWatching continueWatching = optionalContinueWatching.get();
            continueWatching.setCurrentTime(continueWatchingDto.getCurrentTime());
            continueWatchingRepository.save(continueWatching);
        } else {
            this.continueWatchingRepository.save(
                    new ContinueWatching(
                            continueWatchingDto.getCurrentTime(),
                            video,
                            user
                    )
            );
        }
        return true;
    }

    public boolean deleteContinueWatching(Long videoId, String email){
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        Videos video = this.videoRepository.findById(videoId).orElseThrow(
                () -> new NotFoundException("video not found")
        );
        Optional<ContinueWatching> continueWatching = this.continueWatchingRepository.findByVideoAndUser(video, user);

        this.continueWatchingRepository.deleteById(continueWatching.get().getId());
        return true;
    }

    public Set<VideoResponseExt> getContinueWatching(String email){
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        return this.videoRepository.getContinueWatching(user.getUserId());
    }

    public boolean deleteItems(String type, Long id){
        if (type.equals("VIDEO")){
            this.videoRepository.deleteById(id);
        }
        else if (type.equals("BLOG")){
            this.blogRepository.deleteById(id);
        }
        else if (type.equals("AUDIO")){
            this.audioRepository.deleteById(id);
        }
        else if (type.equals("DEVOTIONAL")){
            this.devotionalRepository.deleteById(id); 
        }

        return true;
    }

    public Set<UserFollowed> getFollowing(String email){
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException("user not found")
        );

        return this.churchFollowersRepository.getFollowedChurches(user.getUserId()).orElseThrow(
                ()-> new BadRequestException("something went wrong")
        );
    }

    public boolean updateUser(UpdateUserDto updateUserDto, String email){
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );

        if (updateUserDto.getFirstName() != null) {
            user.setFirstName(updateUserDto.getFirstName());
        }
        if (updateUserDto.getLastName() != null) {
            user.setLastName(updateUserDto.getLastName());
        }
        if (updateUserDto.getProfilePicture() != null) {
            user.setProfilePicture(updateUserDto.getProfilePicture());
        }
        if (updateUserDto.getGender() != null) {
            user.setGender(updateUserDto.getGender());
        }
        if (updateUserDto.getPhoneNumber() != null) {
            user.setPhoneNumber(updateUserDto.getPhoneNumber());
        }

        this.userRepository.save(user);
        return true;
    }

    public String reportContent(ReportDto reportDto, String email){
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user doesn't exist")
        );
        this.reportRepository.save(
                new Report(
                        reportDto.getItem_Id(),
                        reportDto.getMessage(),
                        reportDto.getItemType(),
                        user
                )
        );
        return "submitted";
    }
    public String feedback(FeedbackDto feedbackDto, String email){
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user doesn't exist")
        );
        this.feedbackRepository.save(
                new Feedback(
                        feedbackDto.getComment(),
                        user
                )
        );
        return "successful";
    }

}
