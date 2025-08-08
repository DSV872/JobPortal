package com.dsv.jobportal.controller;

import com.dsv.jobportal.dto.JobApplicationRequest;
import com.dsv.jobportal.model.Application;
import com.dsv.jobportal.model.UserPrincipal;
import com.dsv.jobportal.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/application")
@PreAuthorize("hasAuthority('ROLE_JOBSEEKER')")
public class JobSeekerController {

    @Autowired
    ApplicationService applicationService;

    @PostMapping("/apply")
    public ResponseEntity<String> apply(@RequestBody JobApplicationRequest jobApplication,
                                        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        String email = userPrincipal.getUsername(); // logged in job seeker
        applicationService.applyToJob(jobApplication.getJobId(), email, jobApplication.getResumeLink(), jobApplication.getCoverLetter());
        return ResponseEntity.ok("Application submitted successfully");
    }

    @GetMapping("/my-applications")
    public List<Application> getMyApplication(@AuthenticationPrincipal UserPrincipal userPrincipal){
        String email = userPrincipal.getUsername();
        return applicationService.getApplicationByJobSeekerEmail(email);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplication(@PathVariable int id,
                                                   @AuthenticationPrincipal UserPrincipal userPrincipal) {
        String email = userPrincipal.getUsername();
        try {
            applicationService.deleteMyApplication(id, email);
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("Application deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateApplication(@PathVariable int id, @RequestBody JobApplicationRequest updatedApplication, @AuthenticationPrincipal UserPrincipal principal){
        String reponse = null;
        try {
            reponse = applicationService.updateApplication(id,updatedApplication,principal.getUser().getEmail());
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(reponse);
    }
}
