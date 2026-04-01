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

    // YOUR NEW GROQ API KEY
    private final String GROQ_API_KEY = "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";
    private final String GROQ_URL = "https://api.groq.com/openai/v1/chat/completions";

    public String extractTextFromPDF(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    public String analyzeWithAI(String resumeText) {
        RestTemplate restTemplate = new RestTemplate();

        // 1. Set Headers (Groq requires Authorization Bearer)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + GROQ_API_KEY);

        // 2. Create the Prompt
        String prompt = "You are an HR expert. Analyze this resume for a Java Backend role: " +
                "1. Score out of 100. 2. Three strengths. 3. Three improvements. " +
                "Resume Content: " + resumeText;

        // 3. Create Request Body (Groq/OpenAI Format)
        Map<String, Object> requestBody = Map.of(
                "model", "llama-3.3-70b-versatile",
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                )
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            // 4. Send Request
            return restTemplate.postForObject(GROQ_URL, entity, String.class);
        } catch (Exception e) {
            return "Analysis failed: " + e.getMessage();
        }
    }
}