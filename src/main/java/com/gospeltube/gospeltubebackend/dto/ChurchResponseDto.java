package com.gospeltube.gospeltubebackend.dto;

public class ChurchResponseDto {
    private String churchName;
    private Long churchId;
    private String profilePicture;
    private Long totalStreams;
    private Long totalLikes;
    private Long totalSuscribers;
    private Long totalPost;
    private boolean following;
    private String description;

    public ChurchResponseDto(String churchName, Long churchId, String profilePicture, Long totalStreams, Long totalLikes, Long totalSuscribers, Long totalPost, boolean following, String description) {
        this.churchName = churchName;
        this.churchId = churchId;
        this.profilePicture = profilePicture;
        this.totalStreams = totalStreams;
        this.totalLikes = totalLikes;
        this.totalSuscribers = totalSuscribers;
        this.totalPost = totalPost;
        this.following = following;
        this.description = description;
    }

    public String getChurchName() {
        return churchName;
    }

    public void setChurchName(String churchName) {
        this.churchName = churchName;
    }

    public Long getChurchId() {
        return churchId;
    }

    public void setChurchId(Long churchId) {
        this.churchId = churchId;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Long getTotalStreams() {
        if (totalStreams == null) return 0L;
        return totalStreams;
    }

    public void setTotalStreams(Long totalStreams) {
        this.totalStreams = totalStreams;
    }

    public Long getTotalLikes() {
        if (totalLikes == null) return 0L;
        return totalLikes;
    }

    public void setTotalLikes(Long totalLikes) {
        this.totalLikes = totalLikes;
    }

    public Long getTotalSuscribers() {
        if (totalSuscribers == null) return 0L;
        return totalSuscribers;
    }

    public void setTotalSuscribers(Long totalSuscribers) {
        this.totalSuscribers = totalSuscribers;
    }

    public Long getTotalPost() {
        return totalPost;
    }

    public void setTotalPost(Long totalPost) {
        this.totalPost = totalPost;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
