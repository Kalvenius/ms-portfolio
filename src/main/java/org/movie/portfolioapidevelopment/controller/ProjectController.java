package org.movie.portfolioapidevelopment.controller;

import jakarta.validation.Valid;
import org.movie.portfolioapidevelopment.model.Project;
import org.movie.portfolioapidevelopment.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;
    
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects(Authentication authentication) {
        List<Project> projects = projectService.getAllProjectsByUser(authentication.getName());
        return ResponseEntity.ok(projects);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id, Authentication authentication) {
        Project project = projectService.getProjectById(id, authentication.getName());
        return ResponseEntity.ok(project);
    }
    
    @PostMapping
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project, Authentication authentication) {
        Project createdProject = projectService.createProject(project, authentication.getName());
        return ResponseEntity.ok(createdProject);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, 
                                               @Valid @RequestBody Project project, 
                                               Authentication authentication) {
        Project updatedProject = projectService.updateProject(id, project, authentication.getName());
        return ResponseEntity.ok(updatedProject);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id, Authentication authentication) {
        projectService.deleteProject(id, authentication.getName());
        return ResponseEntity.ok().body("Project deleted successfully");
    }
}