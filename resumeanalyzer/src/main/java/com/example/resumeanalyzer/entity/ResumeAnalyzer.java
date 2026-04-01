package com.example.resumeanalyzer.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "resume_results")
public class ResumeAnalyzer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String candidateName;
    private String fileName;

    @Column(columnDefinition = "TEXT")
    private String extractedText;

    private Integer aiScore;

    @Column(columnDefinition = "TEXT")
    private String aiFeedback;
}