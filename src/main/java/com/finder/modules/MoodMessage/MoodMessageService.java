package com.finder.modules.MoodMessage;

import com.finder.modules.generics.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoodMessageService extends GenericService<MoodMessage> {
    @Autowired
    MoodMessageRepository moodMessageRepository;

    public MoodMessageService(MoodMessageRepository genericRepository) {
        super(genericRepository, "MoodMessage");
    }
}
