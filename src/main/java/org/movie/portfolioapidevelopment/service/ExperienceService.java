package org.movie.portfolioapidevelopment.service;

import org.movie.portfolioapidevelopment.model.Experience;
import org.movie.portfolioapidevelopment.model.User;
import org.movie.portfolioapidevelopment.repository.UserRepository;
import org.movie.portfolioapidevelopment.repository.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExperienceService {
    
    @Autowired
    private ExperienceRepository experienceRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public List<Experience> getAllExperiencesByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return experienceRepository.findByUserId(user.getId());
    }
    
    public Experience getExperienceById(Long id, String username) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Experience not found"));
        
        if (!experience.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You don't have permission to access this experience");
        }
        
        return experience;
    }
    
    public Experience createExperience(Experience experience, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        experience.setUser(user);
        return experienceRepository.save(experience);
    }
    
    public Experience updateExperience(Long id, Experience experienceDetails, String username) {
        Experience experience = getExperienceById(id, username);
        
        experience.setCompany(experienceDetails.getCompany());
        experience.setPosition(experienceDetails.getPosition());
        experience.setDescription(experienceDetails.getDescription());
        experience.setStartDate(experienceDetails.getStartDate());
        experience.setEndDate(experienceDetails.getEndDate());
        
        return experienceRepository.save(experience);
    }
    
    public void deleteExperience(Long id, String username) {
        Experience experience = getExperienceById(id, username);
        experienceRepository.delete(experience);
    }
}
