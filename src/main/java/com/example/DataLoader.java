package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository repo;
    
    @Override
    public void run(String... args) throws Exception {
        if (repo.findByEmail("admin@example.com") == null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode("admin123");
            
            User admin = new User();
            admin.setEmail("admin@example.com");
            admin.setPassword(encodedPassword);
            admin.setFirstname("Admin");
            admin.setLastname("User");
            admin.setRole("ROLE_ADMIN");
            
            repo.save(admin);
        }
    }
}
