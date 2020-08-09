package com.finder.modules.MoodMessage;

import com.finder.modules.generics.GenericEntity;
import com.finder.util.MoodTypes;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "mood_message")
public class MoodMessage extends GenericEntity {
    private String messageText;
    private String fromUser;
    private List<String> usersList;
    private MoodTypes messageType;
}
