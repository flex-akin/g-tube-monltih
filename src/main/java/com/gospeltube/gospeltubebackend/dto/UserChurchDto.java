package com.gospeltube.gospeltubebackend.dto;

import com.gospeltube.gospeltubebackend.entity.Leaders;

import java.util.Set;

public class UserChurchDto {
    private String churchName;
    private Long churchId;
    private String profilePicture;
    private String description;
    private Long totalPost;
    private Long totalSubscribers;
    private boolean following;
    private String vision;
    private String mission;
    private String about;
    private Set<Leaders> leaders;
    private String instagram;
    private String twitter;
    private String facebook;
    private String address;
    private String phone;
    private String email;

    public UserChurchDto(String churchName, Long churchId, String profilePicture, String description, Long totalPost, Long totalSubscribers, boolean following, String vision, String mission, String about, Set<Leaders> leaders, String instagram, String twitter, String facebook, String address, String phone, String email) {
        this.churchName = churchName;
        this.churchId = churchId;
        this.profilePicture = profilePicture;
        this.description = description;
        this.totalPost = totalPost;
        this.totalSubscribers = totalSubscribers;
        this.following = following;
        this.vision = vision;
        this.mission = mission;
        this.about = about;
        this.leaders = leaders;
        this.instagram = instagram;
        this.twitter = twitter;
        this.facebook = facebook;
        this.address = address;
        this.phone = phone;
        this.email = email;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTotalPost() {
        return totalPost;
    }

    public void setTotalPost(Long totalPost) {
        this.totalPost = totalPost;
    }

    public Long getTotalSubscribers() {
        return totalSubscribers;
    }

    public void setTotalSubscribers(Long totalSubscribers) {
        this.totalSubscribers = totalSubscribers;
    }

    public boolean isFollowingg() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Set<Leaders> getLeaders() {
        return leaders;
    }

    public void setLeaders(Set<Leaders> leaders) {
        this.leaders = leaders;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
