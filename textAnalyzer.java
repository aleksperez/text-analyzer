/*
 * Alexandra Perez
 * Software Dev 1 - 2023
 * Input: HTML website / File
 * Output: Set of pairs containing the word and how many times it occurred in the file. 
 * Description: Program is a text analyzer that reads a file/url and outputs the word frequencies of all words in file and is sorted by the most used word.
 * Extra: Create a method or somewhere in your code so it ONLY outputs the top 20 words from the poem and not all of the words.
 */

import org.jsoup.nodes.Document;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.jsoup.*;

public class textAnalyzer {

    public static void countFreq(String str){
        Map<String,Integer> unsortedWordFreqMp=new TreeMap<>();

        String arr[]=str.split(" ");
        //counting frequencies of word and storing into unsortedWordFreqMp
        for(int i=0;i<arr.length;i++){
            if(arr[i]==" "){
                continue;
            }
            if(unsortedWordFreqMp.containsKey(arr[i])){
                unsortedWordFreqMp.put(arr[i], unsortedWordFreqMp.get(arr[i])+1);
            }
            else{
                unsortedWordFreqMp.put(arr[i],1);
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
       
        //printing all sorted words and their frequencies
        System.out.println("\nAll Words and Their Frequencies: --------------");
        allSortedWordFreqMp.forEach((key, value) -> System.out.println(key+ " - "+ value));
        
        //creating top 20 mp
        Map<String, Integer> unsortedTop20Mp = allSortedWordFreqMp.entrySet().stream()
        .limit(20)
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
        
        //sorting top 20 mp
        LinkedHashMap<String, Integer> sortedTop20Mp = unsortedTop20Mp.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .collect(Collectors.toMap(
        Map.Entry::getKey,
        Map.Entry::getValue,
        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        //Printing top 20 most used words and their frequencies
        System.out.println("\nTop 20 Most Used Words: --------------");
        sortedTop20Mp.forEach((key, value) -> System.out.println(key+ " - "+ value));
     
    }

    public static void main(String[] args) {
        final String url = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";

        try{
            final Document doc = Jsoup.connect(url).get();

                String file = doc.select("div.chapter").text();    
                file = file.replaceAll("[^A-Za-z0-9]", " ");
                file = file.trim().replaceAll(" +"," ").toLowerCase();
                countFreq(file);
                
                
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

    }
}