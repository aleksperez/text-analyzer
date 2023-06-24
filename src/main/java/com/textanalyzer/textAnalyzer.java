package com.textanalyzer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class textAnalyzer extends Application {
    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("textAnalyzerGui"));
        stage.setScene(scene);
        stage.show();
    }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(textAnalyzer.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public static void main(String[] args) {
        launch();
    }
    public static LinkedHashMap allResults(String str) {
        Map<String, Integer> unsortedWordFreqMp = new TreeMap<>();

        String arr[] = str.split(" ");
        //counting frequencies of word and storing into unsortedWordFreqMp
        for (int i = 0; i < arr.length; i++) {

            if (unsortedWordFreqMp.containsKey(arr[i])) {
                unsortedWordFreqMp.put(arr[i], unsortedWordFreqMp.get(arr[i]) + 1);
            } else {
                unsortedWordFreqMp.put(arr[i], 1);
            }
        }
        //creating new sorted word frequency map
        LinkedHashMap<String, Integer> allSortedWordFreqMp = unsortedWordFreqMp.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return allSortedWordFreqMp;
    }
    public static LinkedHashMap<String,Integer> top20results(LinkedHashMap<String,Integer> allSortedWordFreqMp) {
        Map<String, Integer> unsortedTop20Mp = allSortedWordFreqMp.entrySet().stream()
                .limit(20)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        //sorting top 20 mp
        LinkedHashMap<String, Integer> sortedTop20Mp = unsortedTop20Mp.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        return sortedTop20Mp;
    }
    public static LinkedHashMap<String,Integer> getResults() {
        final String url = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";

        try{
            final Document doc = Jsoup.connect(url).get();

            String file = doc.select("div.chapter").text();
            file = file.replaceAll("[^A-Za-z0-9\s]", "").toLowerCase();
            LinkedHashMap<String,Integer> results = top20results((allResults(file)));
            return results;
        }
        catch(Exception ex){
            ex.printStackTrace();
            return null;
        }

    }
}