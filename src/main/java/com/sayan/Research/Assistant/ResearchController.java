package com.sayan.Research.Assistant;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/research")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ResearchController {
    private final ResearchService researchService;
    @PostMapping("/process")
    public ResponseEntity<String> processContent(@RequestBody ResearchRequest req){
        String result = researchService.processContent(req);
        return ResponseEntity.ok(result);
    }
}
