package com.textanalyzer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedHashMap;

//set main page which shows button to analyze the raven
//when button is clicked it shows all word occurrences from most to least
// another button only shows top 20 occurrences

public class guiController {
    @FXML
    private ListView<String> words;
    @FXML
    private ListView<Integer> occurrences;
    @FXML
    protected void showResults() {
        LinkedHashMap<String,Integer> results = textAnalyzer.getResults();
        ArrayList<String> wordResults = new ArrayList<String>(results.keySet());
        ArrayList<Integer> occurrenceResults = new ArrayList<Integer>(results.values());
        words.getItems().addAll(wordResults);
        occurrences.getItems().addAll(occurrenceResults);
    }

}