package com.dsv.jobportal.service;

import com.dsv.jobportal.dao.ApplicationRepository;
import com.dsv.jobportal.dao.JobRepository;
import com.dsv.jobportal.dao.UserRepository;
import com.dsv.jobportal.dto.JobApplicationRequest;
import com.dsv.jobportal.model.Application;
import com.dsv.jobportal.model.Job;
import com.dsv.jobportal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.ResourceAccessException;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    public void applyToJob(int jobId, String email, String resumeLink, String coverLetter) {
        Job job =  jobRepository.findById(jobId)
                .orElseThrow(()->new RuntimeException("Job not found"));

        User user = userRepository.findByEmail(email);

        Application application = new Application();
        application.setJob(job);
        application.setJobSeeker(user);
        application.setResumeLink(resumeLink);
        application.setCoverLetter(coverLetter);

        applicationRepository.save(application);
    }

    public List<Application> getApplicationByJobSeekerEmail(String email) {
        return applicationRepository.findByJobSeekerEmail(email);
    }

    public void deleteMyApplication(int id, String email) throws AccessDeniedException {
        Application application =  applicationRepository.findById(id).orElseThrow(()->new RuntimeException("Application not found"));
        if(!application.getJobSeeker().getEmail().equals(email)){
            throw new AccessDeniedException("Unauthorized to delete this application");
        }
        applicationRepository.delete(application);
    }

    public String updateApplication(int id, @RequestBody JobApplicationRequest updatedApplication, String email) throws AccessDeniedException {
        Application existingApplication = applicationRepository.findById(id)
                .orElseThrow(()-> new ResourceAccessException("Application not found"));

        User jobSeeker = userRepository.findByEmail(email);

        if(existingApplication.getJobSeeker().getId() != jobSeeker.getId()){
            throw new AccessDeniedException("You are not allowed to update this application");
        }
        existingApplication.setCoverLetter(updatedApplication.getCoverLetter());
        existingApplication.setResumeLink(updatedApplication.getResumeLink());

        applicationRepository.save(existingApplication);
        return "Application updated Successfully";
    }

}
