package com.finder.modules.successRate;

import com.finder.modules.generics.GenericEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Data
@Document(collection = "success_rate")
public class SuccessRate extends GenericEntity {
    private String userId;
    private String chatId;
    private Timestamp timestamp;
}
