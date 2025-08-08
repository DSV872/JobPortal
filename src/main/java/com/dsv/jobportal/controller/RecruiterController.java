package com.dsv.jobportal.controller;

import com.dsv.jobportal.model.Application;
import com.dsv.jobportal.model.Job;
import com.dsv.jobportal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recruiter")
@PreAuthorize("hasAuthority('ROLE_RECRUITER')")
public class RecruiterController {

    @Autowired
    private JobService jobService;

    @PostMapping("/jobs")
    public Job add(@RequestBody Job job){
        return jobService.addJob(job);
    }

    @GetMapping("/jobs")
    public List<Job> getAllJobs(){
        return jobService.getAllJobs();
    }

    @PutMapping("/jobs/{id}")
    public Job updateJob(@PathVariable int id,@RequestBody Job job){
        return jobService.updateJob(id,job);
    }

    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable int id){
        jobService.deleteJob(id);
        return ResponseEntity.ok("job deleted");
    }

    @GetMapping("/applications/{jobId}")
    public List<Application> getApplications(@PathVariable int jobId){
        return jobService.getApplicationsForJob(jobId);
    }
}
