package com.finder.modules.user.notification;

import com.finder.modules.generics.GenericService;
import com.finder.util.CommonUtil;
import com.finder.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserNotificationService extends GenericService<UserNotification> {

    @Autowired
    UserNotificationRepository userNotificationRepository;

    public UserNotificationService(MongoRepository userNotificationRepository) {
        super(userNotificationRepository, "UserNotification");
    }

    public List<String> getRecentNotifications(Integer offset) {
        List<String> notificationIdList = new ArrayList<>();
        userNotificationRepository.findByUserId(CommonUtil.getCurrentUserId(),
                PageRequest.of(offset, Constants.PAGINATION_SIZE,
                        new Sort(Sort.Direction.DESC, "timeStamp")))
                .forEach(userNotification -> notificationIdList.add(userNotification.getNotificationId()));
        return notificationIdList;
    }
}
