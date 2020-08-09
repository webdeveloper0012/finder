package com.finder.modules.successRate;

import com.finder.modules.generics.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuccessRateService extends GenericService<SuccessRate> {
    @Autowired
    SuccessRateRepository successRateRepository;

    public SuccessRateService(SuccessRateRepository genericRepository) {
        super(genericRepository, "SuccessRate");
    }
}
