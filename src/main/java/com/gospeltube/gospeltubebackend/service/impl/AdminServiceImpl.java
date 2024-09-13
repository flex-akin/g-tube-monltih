package com.gospeltube.gospeltubebackend.service.impl;


import com.gospeltube.gospeltubebackend.dto.AdminNotificationDto;
import com.gospeltube.gospeltubebackend.dto.ChurchEnableOFreeze;
import com.gospeltube.gospeltubebackend.dto.ChurchFAdmin;
import com.gospeltube.gospeltubebackend.entity.Admin;
import com.gospeltube.gospeltubebackend.entity.AppUser;
import com.gospeltube.gospeltubebackend.entity.Notification;
import com.gospeltube.gospeltubebackend.exception.ResourceNotFoundException;
import com.gospeltube.gospeltubebackend.repository.AdminRepository;
import com.gospeltube.gospeltubebackend.repository.NotificationRepository;
import com.gospeltube.gospeltubebackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.gospeltube.gospeltubebackend.service.AdminService;

import java.util.Set;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final NotificationRepository notificationRepository;

    public AdminServiceImpl(UserRepository userRepository, AdminRepository adminRepository, NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public String activateUser(Long id) {
        String message = null;
        AppUser user =  this.userRepository.findByUserId(id).orElseThrow(
                () -> new ResourceNotFoundException("user not found")
        );
        if (!user.isEnabled()){
            user.setEnabled(true);
            message = "User activated";
        }

        else{
            user.setEnabled(false);
            message = "User deactivated";
            user.setEnabled(false);
        }

        userRepository.save(user);
        return message;
    }

    @Override
    public ChurchFAdmin singleChurch(Long id) {
        return this.adminRepository.getChurchForAdmin(id);
    }

    public Page<ChurchEnableOFreeze> churchesEnable2(int page, int size ){
        Pageable pageable = PageRequest.of(page, size);
        return this.adminRepository.getChurchFEnable2(pageable);
    }

    public Set<ChurchEnableOFreeze> churchesEnable(){
        return this.adminRepository.getChurchFEnable();
    }

    @Override
    public Notification postNotification(AdminNotificationDto notificationDto) {
        return this.notificationRepository.save(
                new Notification(
                        notificationDto.getMessage(),
                        notificationDto.getRecipient(),
                        notificationDto.getTitle(),
                        null,
                        null,
                        null
                )
        );
    }

    @Override
    public Admin getStatistics() {
       return this.adminRepository.findById(1).orElseThrow(
                () -> new ResourceNotFoundException("no data found")
        );
    }




}
