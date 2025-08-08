package com.dsv.jobportal.dao;

import com.dsv.jobportal.model.Application;
import com.dsv.jobportal.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application,Integer> {

    ArrayList<Application> findByJob(Job job);

    List<Application> findByJobSeekerEmail(String email);
}
