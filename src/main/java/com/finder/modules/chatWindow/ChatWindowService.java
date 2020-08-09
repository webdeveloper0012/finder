package com.finder.modules.chatWindow;

import com.finder.exceptions.ErrorHolder;
import com.finder.modules.generics.GenericEntity;
import com.finder.modules.generics.GenericService;
import com.finder.modules.message.Message;
import com.finder.modules.message.MessageService;
import com.finder.modules.user.User;
import com.finder.modules.user.UserService;
import com.finder.util.CommonUtil;
import com.finder.util.MoodTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatWindowService extends GenericService<ChatWindow> {
    @Autowired
    ChatWindowRepository chatWindowRepository;
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;

    public ChatWindowService(ChatWindowRepository genericRepository) {
        super(genericRepository, "ChatWindow");
    }

    @Override
    public <T extends GenericEntity> void preValidate(T newEntity, ErrorHolder errorHolder) throws ErrorHolder {
        ChatWindow chatWindow = (ChatWindow) newEntity;
        if (chatWindow.getMoodType() == null) {
            chatWindow.setChatType(MoodTypes.PRIVATE_MESSAGE);
        } else {
            try {
                MoodTypes moodType = MoodTypes.valueOf(chatWindow.getMoodType());
                chatWindow.setChatType(moodType);
            } catch (IllegalArgumentException exception) {
                chatWindow.setChatType(MoodTypes.PRIVATE_MESSAGE);
            }
        }
        super.preValidate(newEntity, errorHolder);
    }

    @Override
    public <T extends GenericEntity> void postValidate(T newEntity, ErrorHolder errorHolder) throws ErrorHolder {
        ChatWindow chatWindow = (ChatWindow) newEntity;
        if (isNewObject(chatWindow)) {
            if (chatWindow.getStartTime() == null) {
                chatWindow.setStartTime(System.currentTimeMillis());
            }
            if (chatWindow.getFromUserId() == null) {
                chatWindow.setFromUserId(CommonUtil.getCurrentUserId());
                User currentUser = userService.getCurrentUser();
                chatWindow.setDescription(currentUser.getDisplayName() + " has opened the chat window by " + chatWindow.getChatType());
            }
            if (chatWindow.getUsersList() == null) {
                List userList = new ArrayList<>();
                userList.add(chatWindow.getFromUserId());
                chatWindow.setUsersList(userList);
            }
            if (chatWindow.getMessageList() == null) {
                chatWindow.setMessageList(new ArrayList<String>());
            }
        }
        super.postValidate(newEntity, errorHolder);
    }

    private ChatWindow getChatWindow(String chatId) {
        return getChatWindow(chatId, Boolean.FALSE);
    }

    private ChatWindow getChatWindow(String chatId, Boolean isAddUser) {
        ChatWindow chatWindow = chatWindowRepository.findById(chatId).get();
        if (chatWindow == null) {
            ErrorHolder.throwError("chat", "chat not found for chat id " + chatId);
        }
        String currentUserId = CommonUtil.getCurrentUserId();
        if (!chatWindow.getUsersList().contains(currentUserId) && !isAddUser) {
            ErrorHolder.throwError("chat", "current user does not belong to the chat " + chatId);
        }
        return chatWindow;
    }

    public ChatWindow addUser(String chatId) {
        ChatWindow chatWindow = getChatWindow(chatId, Boolean.TRUE);
        if (!chatWindow.getUsersList().contains(CommonUtil.getCurrentUserId())) {
            chatWindow.getUsersList().add(CommonUtil.getCurrentUserId());
            return super.save(chatWindow);
        }
        return chatWindow;
    }

    public Message addMessage(String chatId, String content) {
        ChatWindow chatWindow = getChatWindow(chatId);
        Message message = messageService.save(chatId, content);
        chatWindow.getMessageList().add(message.getId());
        super.save(chatWindow);
        return message;
    }

    public ChatWindow removeUser(String chatId) {
        ChatWindow chatWindow = getChatWindow(chatId);
        chatWindow.getUsersList().remove(CommonUtil.getCurrentUserId());
        return super.save(chatWindow);
    }

    public ChatWindow setLocation(String chatId, Double latitude, Double longitude) {
        ChatWindow chatWindow = getChatWindow(chatId);
        chatWindow.setLatitude(latitude);
        chatWindow.setLongitude(longitude);
        return super.save(chatWindow);
    }

    public List<String> getToUsersList(String chatId) {
        ChatWindow chatWindow = getChatWindow(chatId);
        return chatWindow.getUsersList();
    }
}
