package com.finder.modules.reportedUsers;

import com.finder.modules.generics.GenericEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Data
@Document(collection = "reported_users")
public class ReportedUsers extends GenericEntity {
    private String fromUser;
    private String toUser;
    private String reportReason;
    private Timestamp timestamp;
}
