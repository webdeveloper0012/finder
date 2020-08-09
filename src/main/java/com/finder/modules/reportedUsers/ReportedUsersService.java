package com.finder.modules.reportedUsers;

import com.finder.modules.generics.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportedUsersService extends GenericService<ReportedUsers> {
    @Autowired
    ReportedUsersRepository reportedUsersRepository;

    public ReportedUsersService(ReportedUsersRepository genericRepository) {
        super(genericRepository, "ReportedUsers");
    }
}
