package com.textanalyzer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class textAnalyzerTest {
    LinkedHashMap<String, Integer> correctTop20Results;
    final String url = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
    @BeforeEach
    public void setUp() {
        correctTop20Results = new LinkedHashMap<String,Integer>();
        correctTop20Results.put("the",56);
        correctTop20Results.put("and",38);
        correctTop20Results.put("i",32);
        correctTop20Results.put("my",24);
        correctTop20Results.put("of",22);
        correctTop20Results.put("that",18);
        correctTop20Results.put("this",16);
        correctTop20Results.put("a",15);
        correctTop20Results.put("door",14);
        correctTop20Results.put("chamber",11);
        correctTop20Results.put("raven",10);
        correctTop20Results.put("is",10);
        correctTop20Results.put("bird",10);
        correctTop20Results.put("on",10);
        correctTop20Results.put("nevermore",9);
        correctTop20Results.put("lenore",8);
        correctTop20Results.put("or",8);
        correctTop20Results.put("more",8);
        correctTop20Results.put("at",8);
        correctTop20Results.put("from",8);

    }
    @Test
    @DisplayName("LinkedHashMap should be size 20")
    public void checkForSize20(){
        LinkedHashMap<String,Integer> results = textAnalyzer.getResults();
        Integer size = results.size();
        assertEquals(20,size);
    }

    @Test
    @DisplayName("Results should be the same as the correct results")
    public void checkForCorrectResults(){
        LinkedHashMap<String,Integer> results = textAnalyzer.getResults();
        assertEquals(results,correctTop20Results);
    }

    @Test
    @DisplayName("Making sure that all the adjustments to the file did not remove or create any new words to the file.")
    public void checkIfFileIsCorrect() throws IOException {
        final Document doc = Jsoup.connect(url).get();

        String originalFile = doc.select("div.chapter").text();
        String noSymbolsFile = originalFile.replaceAll("[^A-Za-z0-9\s]", "");

        String[] originalWords = originalFile.split("\\s+");
        String[] noSymbolsWords = noSymbolsFile.split("\\s+");


        assertEquals(originalWords.length,noSymbolsWords.length);

    }

}