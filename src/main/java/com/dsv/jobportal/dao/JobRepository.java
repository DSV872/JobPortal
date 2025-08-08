package com.dsv.jobportal.dao;

import com.dsv.jobportal.model.Job;
import com.dsv.jobportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job,Integer> {
    List<Job> findByRecruiter(User recruiter);
}
