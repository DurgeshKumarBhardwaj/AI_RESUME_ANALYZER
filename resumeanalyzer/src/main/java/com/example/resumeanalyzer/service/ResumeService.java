package com.example.resumeanalyzer.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;
import java.util.List;

@Service
public class ResumeService {

    // 1. These must be defined inside the CLASS brackets
    private final String GROQ_API_KEY = "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";
    private final String GROQ_URL = "https://api.groq.com/openai/v1/chat/completions";

    public String extractTextFromPDF(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    // 2. The Match Analysis Method
    public String analyzeMatch(String resumeText, String jobDescription) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + GROQ_API_KEY);

        String prompt = "Analyze this Resume against the Job Description. " +
                "Return ONLY a JSON object with: 'score' (0-100), 'summary', " +
                "'matching' (array), and 'missing' (array). " +
                "JD: " + jobDescription + " | Resume: " + resumeText;

        Map<String, Object> requestBody = Map.of(
                "model", "llama-3.3-70b-versatile",
                "messages", List.of(Map.of("role", "user", "content", prompt)),
                "response_format", Map.of("type", "json_object")
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            return restTemplate.postForObject(GROQ_URL, entity, String.class);
        } catch (Exception e) {
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }

    // 3. The general Analysis Method (optional, kept for compatibility)
    public String analyzeWithAI(String resumeText) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + GROQ_API_KEY);

        String prompt = "Analyze this resume. Return JSON with 'score', 'summary', 'strengths', 'improvements'. " +
                "Content: " + resumeText;

        Map<String, Object> requestBody = Map.of(
                "model", "llama-3.3-70b-versatile",
                "messages", List.of(Map.of("role", "user", "content", prompt)),
                "response_format", Map.of("type", "json_object")
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            return restTemplate.postForObject(GROQ_URL, entity, String.class);
        } catch (Exception e) {
            return "{\"error\": \"" + e.getMessage() + "\"}";
        }
    }
}