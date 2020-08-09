package com.finder.modules.notification;

import com.finder.modules.generics.GenericRestController;
import com.finder.modules.generics.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController extends GenericRestController<Notification> {
    @Autowired
    NotificationService notificationService;

    public NotificationController() {
        super(Arrays.asList(HttpMethod.GET, HttpMethod.POST));
    }

    @RequestMapping(value = "/get_notifications", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Notification> getNotifications(@RequestParam(name = "offset", required = false) Integer offset) {
        return notificationService.getRecentNotifications(offset);
    }

    @Override
    public GenericService<Notification> getService() {
        return notificationService;
    }
}
