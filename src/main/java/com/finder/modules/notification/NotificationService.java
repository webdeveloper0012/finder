package com.finder.modules.notification;

import com.finder.exceptions.ErrorHolder;
import com.finder.modules.chatWindow.ChatWindowService;
import com.finder.modules.generics.GenericService;
import com.finder.modules.user.notification.UserNotification;
import com.finder.modules.user.notification.UserNotificationService;
import com.finder.modules.user.profile.UserProfile;
import com.finder.modules.user.profile.UserProfileService;
import com.finder.util.CommonUtil;
import com.finder.util.NotificationTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService extends GenericService<Notification> {
    @Autowired
    ChatWindowService chatWindowService;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserNotificationService userNotificationService;
    @Autowired
    UserProfileService userProfileService;

    public NotificationService(NotificationRepository genericRepository) {
        super(genericRepository, "Notification");
    }

    public void sendNotification(String content, String hookId, NotificationTypes notificationType) {
        ErrorHolder errorHolder = new ErrorHolder();
        Notification notification = new Notification();
        notification.setContent(content);
        notification.setFromUserId(CommonUtil.getCurrentUserId());
        notification.setHookId(hookId);
        notification.setIsNotificationSent(Boolean.FALSE);
        notification.setTimestamp(System.currentTimeMillis());
        List<String> toUsersList = chatWindowService.getToUsersList(hookId);
        notification.setToUserList(toUsersList);
        super.save(notification);
        if (notificationType.equals(NotificationTypes.CHAT_TOPIC)) {
            for (String toUser : toUsersList) {
                // save the notification in UserNotification
                UserNotification userNotification = new UserNotification();
                userNotification.setNotificationId(notification.getId());
                userNotification.setTimestamp(System.currentTimeMillis());
                userNotification.setUserId(toUser);
                userNotificationService.save(userNotification);
                UserProfile toUserProfile = userProfileService.findByUserId(toUser);
                if (toUserProfile != null) {
                    String deviceId = toUserProfile.getDeviceId();
                    //TODO: send notification using FCM
                } else {
                    errorHolder.addError("User", "User id : " + toUser + " doesnot exist");
                }
            }
        }
        if (errorHolder.hasErrors()) {
            throw errorHolder;
        }
    }

    public List<Notification> getRecentNotifications(Integer offset) {
        if (offset == null) {
            offset = 0;
        }
        List<String> notificationIdList = userNotificationService.getRecentNotifications(offset);
        List<Notification> notificationList = new ArrayList<>();
        notificationRepository.findAllById(notificationIdList, new Sort(Sort.Direction.ASC, "timeStamp")).forEach(notification -> notificationList.add(notification));
        return notificationList;
    }
}
