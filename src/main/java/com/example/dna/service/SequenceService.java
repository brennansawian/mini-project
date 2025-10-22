package com.example.dna.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class SequenceService {

    // Converts DNA → mRNA (T replaced by U)
    public String transcribeToMRNA(String dna) {
        return dna.replace('T', 'U');
    }

    // Calculates GC content
    public double calculateGCContent(String dna) {
        long gcCount = dna.chars()
                .filter(ch -> ch == 'G' || ch == 'C')
                .count();
        return (double) gcCount / dna.length() * 100;
    }

    // Converts mRNA → Amino Acid sequence using codon table
    public String translateToProtein(String mrna) {
        if (mrna == null) {
            return "";
        }
        Map<String, String> codonTable = getCodonTable();
        StringBuilder protein = new StringBuilder();

        for (int i = 0; i + 3 <= mrna.length(); i += 3) {
            String codon = mrna.substring(i, i + 3);
            String aminoAcid = codonTable.getOrDefault(codon, "");
            if (aminoAcid.equals("Stop"))
                break;
            protein.append(aminoAcid).append("-");
        }
        // Remove trailing hyphen
        if (protein.length() > 0 && protein.charAt(protein.length() - 1) == '-') {
            protein.setLength(protein.length() - 1);
        }
        var length = protein.length();
        if (length > 0 && protein.charAt(length - 1) == '-') {
            protein.deleteCharAt(length - 1);
        }
        String finalProtein = protein.toString();
        return finalProtein;
    }

    private Map<String, String> getCodonTable() {
        Map<String, String> codons = new HashMap<>();
        codons.put("AUG", "Met");
        codons.put("UUU", "Phe");
        codons.put("UUC", "Phe");
        codons.put("UUA", "Leu");
        codons.put("UUG", "Leu");
        codons.put("UAA", "Stop");
        codons.put("UAG", "Stop");
        codons.put("UGA", "Stop");
        codons.put("GCU", "Ala");
        codons.put("GCC", "Ala");
        codons.put("GCA", "Ala");
        codons.put("GCG", "Ala");
        codons.put("AAU", "Asn");
        codons.put("AAC", "Asn");
        codons.put("GAU", "Asp");
        codons.put("GAC", "Asp");
        codons.put("UGU", "Cys");
        codons.put("UGC", "Cys");
        codons.put("CAA", "Gln");
        codons.put("CAG", "Gln");
        codons.put("GAA", "Glu");
        codons.put("GAG", "Glu");
        codons.put("GGU", "Gly");
        codons.put("GGC", "Gly");
        codons.put("GGA", "Gly");
        codons.put("GGG", "Gly");
        codons.put("CAU", "His");
        codons.put("CAC", "His");
        codons.put("AUU", "Ile");
        codons.put("AUC", "Ile");
        codons.put("AUA", "Ile");
        codons.put("AAA", "Lys");
        codons.put("AAG", "Lys");
        codons.put("UCU", "Ser");
        codons.put("UCC", "Ser");
        codons.put("UCA", "Ser");
        codons.put("UCG", "Ser");
        codons.put("CCU", "Pro");
        codons.put("CCC", "Pro");
        codons.put("CCA", "Pro");
        codons.put("CCG", "Pro");
        codons.put("ACU", "Thr");
        codons.put("ACC", "Thr");
        codons.put("ACA", "Thr");
        codons.put("ACG", "Thr");
        codons.put("UGG", "Trp");
        codons.put("UAU", "Tyr");
        codons.put("UAC", "Tyr");
        codons.put("GUU", "Val");
        codons.put("GUC", "Val");
        codons.put("GUA", "Val");
        codons.put("GUG", "Val");
        return codons;
    }
}
