package com.gospeltube.gospeltubebackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    @Column(nullable = false, unique = true)
    private String email;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    @Column(length = 10)
    private String code;
    @Column(columnDefinition = "boolean default true")
    private boolean isVerified;
    @Column(nullable = true)
    private String gender;
    @Column(name = "profile_picture", length = 500)
    private String profilePicture;
    @Column(columnDefinition = "boolean default true")
    private boolean isEnabled;
    @Column(name = "total_likes")
    private Long totalLikes;
    @Column(name = "total_comments")
    private Long totalComments;
    @Column(name = "total_streams")
    private Long totalStreams;
    @Column(name = "total_subs")
    private Long totalSubscribers;
    @Column(name = "total_posts")
    private Long totalPosts;
    @Column(name = "stream_id")
    private String streamId;
    @Column(name = "stream_id_ext")
    private String StreamIdExt;
    @Column(columnDefinition = "boolean default false")
    private boolean isOauth2;
    @OneToOne
    @JoinColumn(name = "church_id")
    private Church church;

    @ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name="user_role_junction",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="role_id")}

    )
    private Set<Role> authorities;

    public AppUser() {
        super();
        this.authorities = new HashSet<Role>();
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "userId=" + userId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", code='" + code + '\'' +
                ", isVerified=" + isVerified +
                ", gender=" + gender +
                ", isEnabled=" + isEnabled +
                ", authorities=" + authorities +
                '}';
    }




    public AppUser(String phoneNumber, String email, String password, String firstName, String lastName, String gender,
                   String code, Set<Role> authorities, Church church, String streamId, String StreamIdExt, boolean isEnabled,
                   String profilePicture, boolean isOauth2) {
        super();
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.authorities = authorities;
        this.code = code;
        this.church = church;
        this.streamId = streamId;
        this.StreamIdExt = StreamIdExt;
        this.isEnabled = isEnabled;
        this.profilePicture = profilePicture;
        this.isOauth2 = isOauth2;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public Church getChurch() {
        return church;
    }

    public void setChurch(Church church) {
        this.church = church;
    }

    public void setAuthorities(Set<Role> authorities){
        this.authorities = authorities;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getPhoneNumber() {
        return null;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public Long getTotalLikes() {
        if(this.totalLikes == null) return 0L;
        return totalLikes;
    }

    public void setTotalLikes(Long totalLikes) {
        this.totalLikes = totalLikes;
    }

    public Long getTotalStreams() {
        if(this.totalStreams == null) return 0L;
        return totalStreams;
    }

    public void setTotalStreams(Long totalStreams) {
        this.totalStreams = totalStreams;
    }

    public Long getTotalSubscribers() {
        if(this.totalSubscribers == null) return 0L;
        return totalSubscribers;
    }

    public boolean isOauth2() {
        return isOauth2;
    }

    public void setOauth2(boolean oauth2) {
        isOauth2 = oauth2;
    }

    public void setTotalSubscribers(Long totalSubscribers) {
        this.totalSubscribers = totalSubscribers;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public String getStreamIdExt() {
        return StreamIdExt;
    }

    public void setStreamIdExt(String streamIdExt) {
        StreamIdExt = streamIdExt;
    }

    public Long getTotalPosts() {
        if(this.totalPosts == null) return 0L;
        return totalPosts;
    }

    public void setTotalPosts(Long totalPosts) {
        this.totalPosts = totalPosts;
    }

    public Long getTotalComments() {
        if (this.totalComments == null) return 0L;
        return totalComments;
    }

    public void setTotalComments(Long totalComments) {
        this.totalComments = totalComments;
    }
}
