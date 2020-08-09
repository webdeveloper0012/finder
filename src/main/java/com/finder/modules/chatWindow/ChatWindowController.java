package com.finder.modules.chatWindow;

import com.finder.modules.generics.GenericRestController;
import com.finder.modules.generics.GenericService;
import com.finder.modules.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/chat_window")
public class ChatWindowController extends GenericRestController<ChatWindow> {
    @Autowired
    ChatWindowService chatWindowService;

    public ChatWindowController() {
        super(Arrays.asList(HttpMethod.GET, HttpMethod.POST));
    }

    @Override
    public GenericService<ChatWindow> getService() {
        return chatWindowService;
    }

    @Transactional
    @RequestMapping(value = "{chatId}/set_location", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ChatWindow setLocation(@PathVariable("chatId") String chatId, @RequestParam("latitude") Double latitude, @RequestParam("longitude") Double longitude) {
        return chatWindowService.setLocation(chatId, latitude, longitude);
    }

    @Transactional
    @RequestMapping(value = "{chatId}/remove_user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ChatWindow removeUser(@PathVariable("chatId") String chatId) {
        return chatWindowService.removeUser(chatId);
    }

    @Transactional
    @RequestMapping(value = "{chatId}/add_user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ChatWindow addUser(@PathVariable("chatId") String chatId) {
        return chatWindowService.addUser(chatId);
    }

    @Transactional
    @RequestMapping(value = "{chatId}/add_message", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Message addMessage(@PathVariable("chatId") String chatId, @RequestParam String content) throws Throwable {
        return chatWindowService.addMessage(chatId, content);
    }

}
