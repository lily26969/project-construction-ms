package tn.esprit.tpfoyer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.models.Project;
import tn.esprit.tpfoyer.repositories.ProjectRepository;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    private static final Logger LOGGER = Logger.getLogger(ProjectService.class.getName());

    /**
     * ✅ Search & Sort projects with pagination
     */
    public Page<Project> searchAndSortProjects(String search, Pageable pageable) {
        return projectRepository.findByNameContainingIgnoreCase(search, pageable);
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    /**
     * ✅ Get project by ID
     */
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    /**
     * ✅ Save or update a project
     */
    public Project saveProject(Project project) {
        LOGGER.info("Saving project: " + project.getName());
        return projectRepository.save(project);
    }

    /**
     * ✅ Update a project
     */
    public Project updateProject(Long id, Project projectDetails) {
        return projectRepository.findById(id)
                .map(existingProject -> {
                    existingProject.setName(projectDetails.getName());
                    existingProject.setLocation(projectDetails.getLocation());
                    existingProject.setStatus(projectDetails.getStatus());
                    existingProject.setStartDate(projectDetails.getStartDate());
                    existingProject.setEndDate(projectDetails.getEndDate());
                    LOGGER.info("Updating project with ID: " + id);
                    return projectRepository.save(existingProject);
                }).orElseThrow(() -> new RuntimeException("Project not found with id " + id));
    }

    /**
     * ✅ Delete a project
     */
    public void deleteProject(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            LOGGER.info("Deleted project with ID: " + id);
        } else {
            throw new RuntimeException("Project not found with id " + id);
        }
    }
}
