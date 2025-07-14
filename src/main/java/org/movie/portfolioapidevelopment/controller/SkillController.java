package org.movie.portfolioapidevelopment.controller;

import jakarta.validation.Valid;
import org.movie.portfolioapidevelopment.model.Skill;
import org.movie.portfolioapidevelopment.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {
    
    @Autowired
    private SkillService skillService;
    
    @GetMapping
    public ResponseEntity<List<Skill>> getAllSkills(Authentication authentication) {
        List<Skill> skills = skillService.getAllSkillsByUser(authentication.getName());
        return ResponseEntity.ok(skills);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable Long id, Authentication authentication) {
        Skill skill = skillService.getSkillById(id, authentication.getName());
        return ResponseEntity.ok(skill);
    }
    
    @PostMapping
    public ResponseEntity<Skill> createSkill(@Valid @RequestBody Skill skill, Authentication authentication) {
        Skill createdSkill = skillService.createSkill(skill, authentication.getName());
        return ResponseEntity.ok(createdSkill);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long id, 
                                           @Valid @RequestBody Skill skill, 
                                           Authentication authentication) {
        Skill updatedSkill = skillService.updateSkill(id, skill, authentication.getName());
        return ResponseEntity.ok(updatedSkill);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSkill(@PathVariable Long id, Authentication authentication) {
        skillService.deleteSkill(id, authentication.getName());
        return ResponseEntity.ok().body("Skill deleted successfully");
    }
}