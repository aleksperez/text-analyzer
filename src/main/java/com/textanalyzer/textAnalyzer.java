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

/**
 * <h1>Text Analyzer</h1>
 * <p>This program analyzes the poem, The Raven by Edgar Allen Poe, and shows the top 20 word frequencies within the poem using JavaFX for its GUI.</p>
 * @author Alexandra Perez
 */
public class textAnalyzer extends Application {
    private static Scene scene;

    /**
     * <p>Setting up the main javaFX stage by using loadFXML function and sending in the textAnalyzerGui.fxml as a parameter</p>
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("textAnalyzerGui"));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * <p>Uses String FXML file to set up the GUI for this application.</p>
     * @param fxml
     * @return fxmlLoader.load();
     * @throws IOException
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(textAnalyzer.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * <p>This function launches the JavaFX GUI.</p>
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
    /**
     * <p>This function counts the frequencies of each word within The Raven poem and puts them into a map making sure that the key is the word and the value is the number of frequency, and then sorts them into another map and returns that sorted map.</p>
     * @param str
     * @return allSortedWordFreqMp
     */
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

    /**
     * <p>This function only returns the top 20 sorted words within the map.</p>
     * @param allSortedWordFreqMp
     * @return sortedTop20Mp
     */
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

    /**
     * <p>Function that parses html with Jsoup and The Raven Poem as a String.</p>
     * @return String results
     */
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