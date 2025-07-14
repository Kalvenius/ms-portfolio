package org.movie.portfolioapidevelopment.controller;

import jakarta.validation.Valid;
import org.movie.portfolioapidevelopment.model.Experience;
import org.movie.portfolioapidevelopment.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/experiences")
public class ExperienceController {
    
    @Autowired
    private ExperienceService experienceService;
    
    @GetMapping
    public ResponseEntity<List<Experience>> getAllExperiences(Authentication authentication) {
        List<Experience> experiences = experienceService.getAllExperiencesByUser(authentication.getName());
        return ResponseEntity.ok(experiences);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Experience> getExperienceById(@PathVariable Long id, Authentication authentication) {
        Experience experience = experienceService.getExperienceById(id, authentication.getName());
        return ResponseEntity.ok(experience);
    }
    
    @PostMapping
    public ResponseEntity<Experience> createExperience(@Valid @RequestBody Experience experience, Authentication authentication) {
        Experience createdExperience = experienceService.createExperience(experience, authentication.getName());
        return ResponseEntity.ok(createdExperience);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Experience> updateExperience(@PathVariable Long id, 
                                                     @Valid @RequestBody Experience experience, 
                                                     Authentication authentication) {
        Experience updatedExperience = experienceService.updateExperience(id, experience, authentication.getName());
        return ResponseEntity.ok(updatedExperience);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExperience(@PathVariable Long id, Authentication authentication) {
        experienceService.deleteExperience(id, authentication.getName());
        return ResponseEntity.ok().body("Experience deleted successfully");
    }
}