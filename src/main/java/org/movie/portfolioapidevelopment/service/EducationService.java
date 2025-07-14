package org.movie.portfolioapidevelopment.service;

import org.movie.portfolioapidevelopment.model.Education;
import org.movie.portfolioapidevelopment.repository.EducationRepository;
import org.movie.portfolioapidevelopment.model.User;
import org.movie.portfolioapidevelopment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EducationService {
    
    @Autowired
    private EducationRepository educationRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public List<Education> getAllEducationsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return educationRepository.findByUserId(user.getId());
    }
    
    public Education getEducationById(Long id, String username) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Education not found"));
        
        if (!education.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You don't have permission to access this education");
        }
        
        return education;
    }
    
    public Education createEducation(Education education, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        education.setUser(user);
        return educationRepository.save(education);
    }
    
    public Education updateEducation(Long id, Education educationDetails, String username) {
        Education education = getEducationById(id, username);
        
        education.setInstitution(educationDetails.getInstitution());
        education.setDegree(educationDetails.getDegree());
        education.setFieldOfStudy(educationDetails.getFieldOfStudy());
        education.setStartDate(educationDetails.getStartDate());
        education.setEndDate(educationDetails.getEndDate());
        education.setGrade(educationDetails.getGrade());
        
        return educationRepository.save(education);
    }
    
    public void deleteEducation(Long id, String username) {
        Education education = getEducationById(id, username);
        educationRepository.delete(education);
    }
}