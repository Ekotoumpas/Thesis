package com.example;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroolsService {

    private final KieSession kieSession;

    @Autowired
    public DroolsService(KieSession kieSession) {
        this.kieSession = kieSession;
    }

    public void evaluateApplication(Application application ,Program program) {
    	kieSession.setGlobal("program", program);
        kieSession.insert(application);
        kieSession.fireAllRules();
    }
}
