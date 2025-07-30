package com.onuryilmazer.tradereconsystemdb.security;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    private void loadUserData() {
        if (userRepository.count() == 0){
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("12345"))
                    .role(Role.ADMIN)
                    .build();

            userRepository.save(admin);
        }
    }


    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }
}
