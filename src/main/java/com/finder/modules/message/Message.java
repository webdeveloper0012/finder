package com.finder.modules.message;

import com.finder.modules.generics.GenericEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "message")
public class Message extends GenericEntity {
    private String content;
    private String chatId;
    private String fromUser;
    private Long timeStamp;
}
