package tn.esprit.tpfoyer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.models.Project;
import tn.esprit.tpfoyer.services.ProjectService;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    private static final Logger LOGGER = Logger.getLogger(ProjectController.class.getName());

    /**
     * ✅ Create a new project
     */
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Project savedProject = projectService.createProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }


    /**
     * ✅ Get all projects (with optional pagination)
     */
    @GetMapping
    public ResponseEntity<Page<Project>> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Project> projects = projectService.searchAndSortProjects("", PageRequest.of(page, size));
        return ResponseEntity.ok(projects);
    }

    /**
     * ✅ Get project by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProjectById(@PathVariable Long id) {
        Optional<Project> project = projectService.getProjectById(id);
        return project.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found"));
    }


    /**
     * ✅ Update an existing project
     */
    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody Project project) {
        try {
            Project updatedProject = projectService.updateProject(id, project);
            return ResponseEntity.ok(updatedProject);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * ✅ Delete a project by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.ok("Project deleted successfully");
        } catch (Exception e) {
            LOGGER.severe("Error deleting project: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Error deleting project: " + e.getMessage());
        }
    }

    /**
     * ✅ Search and sort projects with pagination
     */
    @GetMapping("/search")
    public ResponseEntity<Page<Project>> searchProjects(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Project> projects = projectService.searchAndSortProjects(query, PageRequest.of(page, size));
        return ResponseEntity.ok(projects);
    }
    @GetMapping("/check-token-status")
    public ResponseEntity<String> checkToken(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("🚫 Missing or malformed Authorization header");
        }

        System.out.println("✅ Token received: " + authHeader);
        return ResponseEntity.ok("✅ Token received on backend");
    }

    @GetMapping("/check-token-details")
    public ResponseEntity<String> checkTokenDetails(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        String username = jwt.getClaimAsString("preferred_username");
        return ResponseEntity.ok("👤 User: " + username + " | 📧 Email: " + email);
    }


    @GetMapping("/user")
    public ResponseEntity<String> getCurrentUserEmail(Authentication authentication) {
        if (authentication.getPrincipal() instanceof Jwt jwt) {
            String email = jwt.getClaimAsString("email");
            return ResponseEntity.ok("✅ Authenticated user email: " + email);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ User not authenticated");
    }
    @GetMapping("/test-user-fetch/{username}")
    public ResponseEntity<?> fetchUserFromUserService(@PathVariable String username) {
        try {
            var userDTO = projectService.fetchUserByUsername(username);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Failed to fetch user: " + e.getMessage());
        }
    }

}
