package org.movie.portfolioapidevelopment.controller;

import jakarta.validation.Valid;
import org.movie.portfolioapidevelopment.model.Education;
import org.movie.portfolioapidevelopment.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/educations")
public class EducationController {
    
    @Autowired
    private EducationService educationService;
    
    @GetMapping
    public ResponseEntity<List<Education>> getAllEducations(Authentication authentication) {
        List<Education> educations = educationService.getAllEducationsByUser(authentication.getName());
        return ResponseEntity.ok(educations);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Education> getEducationById(@PathVariable Long id, Authentication authentication) {
        Education education = educationService.getEducationById(id, authentication.getName());
        return ResponseEntity.ok(education);
    }
    
    @PostMapping
    public ResponseEntity<Education> createEducation(@Valid @RequestBody Education education, Authentication authentication) {
        Education createdEducation = educationService.createEducation(education, authentication.getName());
        return ResponseEntity.ok(createdEducation);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Education> updateEducation(@PathVariable Long id, 
                                                   @Valid @RequestBody Education education, 
                                                   Authentication authentication) {
        Education updatedEducation = educationService.updateEducation(id, education, authentication.getName());
        return ResponseEntity.ok(updatedEducation);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEducation(@PathVariable Long id, Authentication authentication) {
        educationService.deleteEducation(id, authentication.getName());
        return ResponseEntity.ok().body("Education deleted successfully");
    }
}
