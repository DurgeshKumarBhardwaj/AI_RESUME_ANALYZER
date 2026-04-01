package com.example.resumeanalyzer.controller;

import com.example.resumeanalyzer.service.ResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resume")
@CrossOrigin(origins = "*") // 1. INTEGRATION: Allows Frontend to talk to Backend
public class ResumeController {

    private final ResumeService resumeService;

    // Constructor Injection
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadResume(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload.");
        }

        try {
            // Extract text from PDF
            String extractedText = resumeService.extractTextFromPDF(file);

            // Get AI Analysis from Gemini
            String aiResponse = resumeService.analyzeWithAI(extractedText);

            return ResponseEntity.ok(aiResponse);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}