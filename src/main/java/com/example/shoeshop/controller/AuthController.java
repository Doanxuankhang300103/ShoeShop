package com.example.shoeshop.controller;

import com.example.shoeshop.model.Role;
import com.example.shoeshop.model.User;
import com.example.shoeshop.repository.RoleRepository;
import com.example.shoeshop.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ===== REGISTER =====
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") User user) {
        Role customerRole = roleRepository.findByRoleName("CUSTOMER");
        user.setRole(customerRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }

    // ===== LOGIN =====
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    // Xóa phương thức POST /login vì Spring Security xử lý

    // ===== WELCOME =====
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    // ===== LOGOUT =====
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout=true";
    }
}