package com.example;

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfig {

    @Bean
    public KieServices kieServices() {
        return KieServices.Factory.get();
    }

    @Bean
    public KieFileSystem kieFileSystem() {
        KieFileSystem kieFileSystem = kieServices().newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource("rules.drl"));
        return kieFileSystem;
    }

    @Bean
    public KieContainer kieContainer() {
        final KieRepository kieRepository = kieServices().getRepository();
        kieRepository.addKieModule(() -> kieServices().getRepository().getDefaultReleaseId());
        kieServices().newKieBuilder(kieFileSystem()).buildAll();
        return kieServices().newKieContainer(kieRepository.getDefaultReleaseId());
    }

    @Bean
    public KieSession kieSession() {
        return kieContainer().newKieSession();
    }
}
