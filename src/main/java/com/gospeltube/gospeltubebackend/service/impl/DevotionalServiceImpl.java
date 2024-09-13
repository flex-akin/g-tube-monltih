package com.gospeltube.gospeltubebackend.service.impl;

import com.gospeltube.gospeltubebackend.dto.DevotionalDto;
import com.gospeltube.gospeltubebackend.dto.DevotionalRes;
import com.gospeltube.gospeltubebackend.entity.AppUser;
import com.gospeltube.gospeltubebackend.entity.BookmarkDevotional;
import com.gospeltube.gospeltubebackend.entity.Church;
import com.gospeltube.gospeltubebackend.entity.Devotional;
import com.gospeltube.gospeltubebackend.exception.BadRequestException;
import com.gospeltube.gospeltubebackend.exception.ResourceNotFoundException;
import com.gospeltube.gospeltubebackend.repository.BookmarkDevotionalRepository;
import com.gospeltube.gospeltubebackend.repository.ChurchRepository;
import com.gospeltube.gospeltubebackend.repository.DevotionalRepository;
import com.gospeltube.gospeltubebackend.repository.UserRepository;
import com.gospeltube.gospeltubebackend.service.DevotionalService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class DevotionalServiceImpl implements DevotionalService {
    private final UserRepository userRepository;
    private final ChurchRepository churchRepository;
    private final DevotionalRepository devotionalRepository;
    private final BookmarkDevotionalRepository bookmarkDevotionalRepository;
    private static final String USER_NF= "user not found";

    public DevotionalServiceImpl(UserRepository userRepository, ChurchRepository churchRepository, DevotionalRepository devotionalRepository, BookmarkDevotionalRepository bookmarkDevotionalRepository) {
        this.userRepository = userRepository;
        this.churchRepository = churchRepository;
        this.devotionalRepository = devotionalRepository;
        this.bookmarkDevotionalRepository = bookmarkDevotionalRepository;
    }

    @Override
    public boolean createDevotional(DevotionalDto devotionalDto, String email) {
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(USER_NF)
        );
        Church church = this.churchRepository.findById(user.getChurch().getId()).orElseThrow(
                () -> new ResourceNotFoundException("you can't create a blog")
        );
        user.setTotalPosts(user.getTotalPosts() + 1L);
        this.userRepository.save(user);
        this.devotionalRepository.save(new Devotional(
                devotionalDto.getHeader(),
                devotionalDto.getContent(),
                devotionalDto.getDate(),
                church,
                devotionalDto.getBibleRef()
        ));
        return false;
    }

    @Override
    public DevotionalDto getDevotional (Long devotionalId, String email){
        Devotional devotional = this.devotionalRepository.findById(devotionalId).orElseThrow(
                () -> new ResourceNotFoundException("devotional doesn't exist")
        );
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(USER_NF)
        );
        boolean isBookmarked = this.bookmarkDevotionalRepository.existsByDevotionalAndUser(devotional, user);
        DevotionalDto devotionalDto = new DevotionalDto();
        devotionalDto.setId(devotionalId);
        devotionalDto.setBookmarked(isBookmarked);
        devotionalDto.setContent(devotional.getContent());
        devotionalDto.setHeader(devotional.getHeader());
        devotionalDto.setBibleRef(devotional.getBibleRef());
        devotionalDto.setDate(devotional.getTime());
        devotionalDto.setChurchId(devotional.getChurch().getId());
        return devotionalDto;
    }

    @Override
    public Set<DevotionalRes> getDevotionals(Long id, String email) {
        AppUser churchUser = this.userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(USER_NF)
        );
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(USER_NF)
        );

       return this.devotionalRepository.findAllDevotionalByChurch(churchUser.getChurch().getId(), user.getUserId()).orElseThrow(
               () -> new ResourceNotFoundException("data not found")
       );
    }

    @Override
    public Set<DevotionalRes> allDevotionals(String email) {
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(USER_NF)
        );
        Set<DevotionalRes> devotionalList = this.devotionalRepository.findAllDevotional(user.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("no data found")
        );
        return new HashSet<>(devotionalList);
    }

    @Override
    public String bookmark(String email, Long devotionalId) {
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(USER_NF)
        );
        Devotional devotional = this.devotionalRepository.findById(devotionalId).orElseThrow(
                ()-> new ResourceNotFoundException("devotional doesn't exist")
        );
        Optional<BookmarkDevotional> bookmarkDevotional = this.bookmarkDevotionalRepository.findByUserAndDevotional(
                user, devotional
        );
       if(bookmarkDevotional.isPresent()){
           this.bookmarkDevotionalRepository.delete(bookmarkDevotional.get());
           return "bookmark removed";
       }
        this.bookmarkDevotionalRepository.save(
                new BookmarkDevotional(
                        devotional, user
                )
        );
        return "bookmarked";
    }

    public Set<BookmarkDevotional> getBookmarked(String email){
        AppUser user = this.userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(USER_NF)
        );

        return this.bookmarkDevotionalRepository.findByUser(user).orElseThrow(
                () -> new ResourceNotFoundException("devotional not found")
        );
    }

}


