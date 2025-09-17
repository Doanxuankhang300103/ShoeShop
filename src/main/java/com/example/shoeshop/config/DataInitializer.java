package com.example.shoeshop.config;

import com.example.shoeshop.model.User;
import com.example.shoeshop.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;

@Configuration
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder;

    public DataInitializer(UserRepository userRepo, BCryptPasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Nếu DB chứa password plaintext (ví dụ chưa mã hóa): cập nhật bằng BCrypt
        List<User> users = userRepo.findAll();
        boolean changed = false;
        for (User u : users) {
            String pw = u.getPassword();
            if (pw == null) continue;
            // simple heuristic: bcrypt starts with $2a$ or $2b$
            if (!pw.startsWith("$2a$") && !pw.startsWith("$2b$") && !pw.startsWith("$2y$")) {
                u.setPassword(encoder.encode(pw));
                changed = true;
            }
        }
        if (changed) userRepo.saveAll(users);
    }
}
