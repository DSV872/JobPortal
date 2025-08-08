package com.dsv.jobportal.service;

import com.dsv.jobportal.dao.ApplicationRepository;
import com.dsv.jobportal.dao.JobRepository;
import com.dsv.jobportal.dao.UserRepository;
import com.dsv.jobportal.model.Application;
import com.dsv.jobportal.model.Job;
import com.dsv.jobportal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepo;

    @Autowired
    private ApplicationRepository applicationRepo;

    @Autowired
    private UserRepository userRepo;

    public User getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public Job addJob(Job job) {
        User recruiter = getCurrentUser();
        job.setRecruiter(recruiter);
        return jobRepo.save(job);
    }

    public List<Job> getAllJobs() {
        User recruiter = getCurrentUser();
        return jobRepo.findByRecruiter(recruiter);

    }

    public Job updateJob(int id, Job updatedJob) {
       Optional<Job> optionalJob = jobRepo.findById(id);
       Job job = null;
       if(optionalJob.isPresent()){
           job = optionalJob.get();
       }
       User recruiter = getCurrentUser();
       if(job.getRecruiter() == null || job.getRecruiter().getId() != recruiter.getId()){
           try {
               throw new AccessDeniedException("Not authorize to update the job");
           } catch (AccessDeniedException e) {
               throw new RuntimeException(e);
           }
       }
       job.setTitle(updatedJob.getTitle());
       job.setDescription(updatedJob.getDescription());
       job.setTechStack(updatedJob.getTechStack());

       return jobRepo.save(job);
    }

    public void deleteJob(int id) {
        Job job;
        Optional<Job> optionalJob = jobRepo.findById(id);
       if(optionalJob.isPresent()){
            job = optionalJob.get();
       }else {
           throw new RuntimeException("job not found");
       }
        User recruiter = getCurrentUser();

       if(job.getRecruiter() == null || job.getRecruiter().getId() != recruiter.getId()){
            try {
                throw new AccessDeniedException("User not found");
            } catch (AccessDeniedException e) {
                throw new RuntimeException(e);
            }
        }
        jobRepo.delete(job);
    }

    public List<Application> getApplicationsForJob(int jobId) {
        Optional<Job> optionalJob = jobRepo.findById(jobId);
        Job job = null;
        if(optionalJob.isPresent()){
            job = optionalJob.get();
        }
        User recruiter = getCurrentUser();
        if (job.getRecruiter() == null || job.getRecruiter().getId() != recruiter.getId()) {
            try {
                throw new AccessDeniedException("Not authorized to view applications");
            } catch (AccessDeniedException e) {
                throw new RuntimeException(e);
            }
        }
        return applicationRepo.findByJob(job);
    }
}
