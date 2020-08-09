package com.finder.modules.message;

import com.finder.modules.generics.GenericRestController;
import com.finder.modules.generics.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/messages")
public class MessageController extends GenericRestController<Message> {
    @Autowired
    MessageService messageService;

    public MessageController() {
        super(Arrays.asList(HttpMethod.GET, HttpMethod.POST));
    }

    @Override
    public GenericService<Message> getService() {
        return messageService;
    }
}
