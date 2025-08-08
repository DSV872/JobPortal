package com.dsv.jobportal.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class JobApplicationRequest {
    private int jobId;
    private String resumeLink;
    private String coverLetter;
}