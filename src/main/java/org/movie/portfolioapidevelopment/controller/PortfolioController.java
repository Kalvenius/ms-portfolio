package org.movie.portfolioapidevelopment.controller;

import org.movie.portfolioapidevelopment.model.User;
import org.movie.portfolioapidevelopment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getCompletePortfolio(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Map<String, Object> portfolio = new HashMap<>();
        portfolio.put("user", Map.of(
            "id", user.getId(),
            "username", user.getUsername(),
            "email", user.getEmail()
        ));
        portfolio.put("projects", user.getProjects());
        portfolio.put("skills", user.getSkills());
        portfolio.put("experiences", user.getExperiences());
        portfolio.put("educations", user.getEducations());
        
        return ResponseEntity.ok(portfolio);
    }
}