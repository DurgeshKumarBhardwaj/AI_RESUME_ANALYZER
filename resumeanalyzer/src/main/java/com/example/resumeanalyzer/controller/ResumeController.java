package com.example.resumeanalyzer.controller;

// THESE ARE THE MISSING IMPORTS CAUSING THE ERRORS
import com.example.resumeanalyzer.service.ResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resume")
@CrossOrigin(origins = "*")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    // ACTION 1: The "Quick Audit" Button
    @PostMapping("/upload")
    public ResponseEntity<String> quickAudit(@RequestParam("file") MultipartFile file) {
        try {
            String text = resumeService.extractTextFromPDF(file);
            return ResponseEntity.ok(resumeService.analyzeWithAI(text));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    // ACTION 2: The "Match with Job" Button
    @PostMapping("/compare")
    public ResponseEntity<String> targetedMatch(
            @RequestParam("file") MultipartFile file,
            @RequestParam("jd") String jd) {
        try {
            String text = resumeService.extractTextFromPDF(file);
            return ResponseEntity.ok(resumeService.analyzeMatch(text, jd));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}