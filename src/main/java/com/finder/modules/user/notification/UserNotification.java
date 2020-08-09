package com.finder.modules.user.notification;

import com.finder.modules.generics.GenericEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user_notification")
public class UserNotification extends GenericEntity {
    private String userId;
    private String notificationId;
    private Long timestamp;
}
