package org.movie.portfolioapidevelopment.service;

import org.movie.portfolioapidevelopment.model.Project;
import org.movie.portfolioapidevelopment.repository.ProjectRepository;
import org.movie.portfolioapidevelopment.model.User;
import org.movie.portfolioapidevelopment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectService {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public List<Project> getAllProjectsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return projectRepository.findByUserId(user.getId());
    }
    
    public Project getProjectById(Long id, String username) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        
        if (!project.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You don't have permission to access this project");
        }
        
        return project;
    }
    
    public Project createProject(Project project, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        project.setUser(user);
        return projectRepository.save(project);
    }
    
    public Project updateProject(Long id, Project projectDetails, String username) {
        Project project = getProjectById(id, username);
        
        project.setName(projectDetails.getName());
        project.setDescription(projectDetails.getDescription());
        project.setTechnologies(projectDetails.getTechnologies());
        project.setGithubUrl(projectDetails.getGithubUrl());
        project.setLiveUrl(projectDetails.getLiveUrl());
        
        return projectRepository.save(project);
    }
    
    public void deleteProject(Long id, String username) {
        Project project = getProjectById(id, username);
        projectRepository.delete(project);
    }
}