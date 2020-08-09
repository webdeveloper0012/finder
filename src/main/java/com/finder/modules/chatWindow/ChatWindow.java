package com.finder.modules.chatWindow;

import com.finder.modules.generics.GenericEntity;
import com.finder.util.MoodTypes;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "chat_window")
public class ChatWindow extends GenericEntity {
    private List<String> usersList;
    private List<String> messageList;
    private String fromUserId;
    private Double latitude;
    private Double longitude;
    //private Long dateAndTime;
    private String hookId;
    private MoodTypes chatType;
    // for duration calculation
    private Long startTime;
    private String description;

    @Transient
    private String moodType;
}
