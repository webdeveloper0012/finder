package com.finder.modules.message;

import com.finder.exceptions.ErrorHolder;
import com.finder.modules.generics.GenericService;
import com.finder.modules.notification.NotificationService;
import com.finder.util.CommonUtil;
import com.finder.util.NotificationTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MessageService extends GenericService<Message> {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    NotificationService notificationService;

    public MessageService(MessageRepository genericRepository) {
        super(genericRepository, "Message");
    }

    /*
    Assumption: chat id is already being verified
    */
    public Message save(String chatId, String content) {
        if (StringUtils.isEmpty(content)) {
            ErrorHolder.throwError("message", "message cannot be empty");
        }
        Message message = new Message();
        message.setContent(content);
        message.setChatId(chatId);
        message.setFromUser(CommonUtil.getCurrentUserId());
        message.setTimeStamp(System.currentTimeMillis());
        super.save(message);
        // TODO: send notification to all the persons from chat id
        notificationService.sendNotification(content, chatId, NotificationTypes.CHAT_TOPIC);
        return message;
    }
}
