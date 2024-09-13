package com.gospeltube.gospeltubebackend.service;

import com.gospeltube.gospeltubebackend.dto.AdminNotificationDto;
import com.gospeltube.gospeltubebackend.dto.ChurchEnableOFreeze;
import com.gospeltube.gospeltubebackend.dto.ChurchFAdmin;
import com.gospeltube.gospeltubebackend.dto.NotificationDto;
import com.gospeltube.gospeltubebackend.entity.Admin;
import com.gospeltube.gospeltubebackend.entity.Notification;

import java.util.Set;

public interface AdminService {
    String activateUser(Long id);
    ChurchFAdmin singleChurch(Long id);
    Set<ChurchEnableOFreeze> churchesEnable();
    Notification postNotification(AdminNotificationDto ddminNotificationDto);
    Admin getStatistics();
}
