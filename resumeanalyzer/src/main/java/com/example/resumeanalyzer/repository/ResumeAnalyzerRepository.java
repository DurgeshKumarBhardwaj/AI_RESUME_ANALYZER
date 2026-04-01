package com.example.resumeanalyzer.repository;

import com.example.resumeanalyzer.entity.ResumeAnalyzer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeAnalyzerRepository extends JpaRepository<ResumeAnalyzer, Long> {
    // By extending JpaRepository, Spring automatically writes all the SQL
    // for saving and finding resumes for us!
}