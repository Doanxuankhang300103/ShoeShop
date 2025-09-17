package com.example.shoeshop.controller;

import com.example.shoeshop.model.Role;
import com.example.shoeshop.model.User;
import com.example.shoeshop.repository.RoleRepository;
import com.example.shoeshop.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    public AuthController(UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
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

        // Mã hóa password trước khi lưu
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

    @PostMapping("/login")
    public String processLogin(@ModelAttribute("user") User formUser,
                               HttpSession session,
                               Model model) throws Exception {
        Optional<User> userOpt = userRepository.findByUsername(formUser.getUsername());

        if (userOpt.isPresent() && passwordEncoder.matches(formUser.getPassword(), userOpt.get().getPassword())) {
            User user = userOpt.get();
            session.setAttribute("loggedInUser", user);

            // JSON string
            String userJson = objectMapper.writeValueAsString(user);
            model.addAttribute("userJson", userJson);

            return "index";
        }

        model.addAttribute("error", "Sai username hoặc password!");
        return "login";
    }


    // ===== WELCOME =====
    @GetMapping("/welcome")
    public String welcome(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", loggedInUser);
        return "welcome";
    }

    // ===== LOGOUT =====
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
