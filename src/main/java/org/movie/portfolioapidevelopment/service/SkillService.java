package org.movie.portfolioapidevelopment.service;

import org.movie.portfolioapidevelopment.model.Skill;
import org.movie.portfolioapidevelopment.repository.SkillRepository;
import org.movie.portfolioapidevelopment.model.User;
import org.movie.portfolioapidevelopment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SkillService {
    
    @Autowired
    private SkillRepository skillRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public List<Skill> getAllSkillsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return skillRepository.findByUserId(user.getId());
    }
    
    public Skill getSkillById(Long id, String username) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));
        
        if (!skill.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You don't have permission to access this skill");
        }
        
        return skill;
    }
    
    public Skill createSkill(Skill skill, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        skill.setUser(user);
        return skillRepository.save(skill);
    }
    
    public Skill updateSkill(Long id, Skill skillDetails, String username) {
        Skill skill = getSkillById(id, username);
        
        skill.setName(skillDetails.getName());
        skill.setLevel(skillDetails.getLevel());
        skill.setCategory(skillDetails.getCategory());
        
        return skillRepository.save(skill);
    }
    
    public void deleteSkill(Long id, String username) {
        Skill skill = getSkillById(id, username);
        skillRepository.delete(skill);
    }
}