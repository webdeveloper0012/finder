package com.finder.modules.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finder.modules.generics.GenericEntity;
import com.finder.util.NotificationTypes;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "notification")
public class Notification extends GenericEntity {
    private NotificationTypes notificationType;
    private String fromUserId;
    private String content;
    @JsonIgnore
    private List<String> toUserList;
    // chat id or mood message id
    @JsonIgnore
    private String hookId;
    private Long timestamp;
    @JsonIgnore
    private Boolean isNotificationSent;
}
