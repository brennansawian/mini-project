package com.example.dna.controller;

import com.example.dna.model.Sequence;
import com.example.dna.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SequenceController {

    @Autowired
    private SequenceService sequenceService;

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("sequence", new Sequence());
        return "index";
    }

    @PostMapping("/analyze")
    public String analyzeSequence(@ModelAttribute Sequence sequence, Model model) {

        String dna = sequence.getSequence().toUpperCase().trim();
        String mrna = sequenceService.transcribeToMRNA(dna);
        String protein = sequenceService.translateToProtein(mrna);
        double gcContent = sequenceService.calculateGCContent(dna);

        // Pass all data to Thymeleaf
        model.addAttribute("dnaSequence", dna);
        model.addAttribute("mrnaSequence", mrna);
        model.addAttribute("aminoAcidSequence", protein);
        model.addAttribute("gcContent", gcContent);

        return "result";
    }
}