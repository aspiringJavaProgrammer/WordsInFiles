package com.example.myapp.java;

import edu.duke.DirectoryResource;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class WordsInFiles {
    
    private HashMap<String, ArrayList<String>> store;
    
    public WordsInFiles() {
        store = new HashMap<>();
    }
    
    private void addWordsFromFile(File file) {
        
        try(Scanner scan = new Scanner(file)) {
            
            while(scan.hasNext()){
                String word = scan.next();
                if(!store.containsKey(word)) { // if map does not contain the word
                    ArrayList<String> listToMapFrom = new ArrayList<>();
                    listToMapFrom.add(file.getName());
                    store.put(word, listToMapFrom);
                }else { // if word is a key in the map
                    ArrayList<String> fileNames = store.get(word);
                    if(!fileNames.contains(file.getName())) // if file name is not in the listing of FILE NAMES
                        fileNames.add(file.getName());
                }
            }
                
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    } 
    
    private void buildWordFile(){ 
        store.clear();
        
        DirectoryResource dResource = new DirectoryResource();
        
        for(File file : dResource.selectedFiles()) {
            addWordsFromFile(file);
        }
    }
    
    // get maximum number of file that a word appeared in
    private int maxNumber() { 
        
        int maxNumber = 0;
        // compare map values' sizes
        for(Entry<String, ArrayList<String>> mappings : store.entrySet()) { // get collection view 
            
            int currentMax = mappings.getValue().size(); // get value(ArrayList<String>) size
            
            if(currentMax > maxNumber) { 
                maxNumber = currentMax;
            }
        }
        
        return maxNumber;
    }
    
    // get words that appeared in n number of files
    private ArrayList<String> wordsInNumFiles(int number) { 
        
        ArrayList<String> wordsAppeared = new ArrayList<>();
        
        for(String word : store.keySet()) { 
            if(store.get(word).size() == number) { 
                wordsAppeared.add(word);
            }
        }
        return wordsAppeared;
    }
    
    // get file names where the word appeared
    private void printFilesIn(String word) {
        System.out.println("Word " + word + " appeared in the following files: ");
        
        for(String fileNames : store.get(word)) {
                System.out.println("\t" + fileNames);
            }
    }
    
    private void printMap() { 
        
        System.out.println("MY MAP");
        
        for(String word : store.keySet()) {
            System.out.println("Key: " + word + "\tValue: " + store.get(word));
        }
    }
    public void test() {
        buildWordFile();
        
        System.out.println("Maximum number of file that a word appeared in:\t" + maxNumber());
        System.out.println("Words that appeared in 3 files: ");
        
        System.out.println("Number of each words that appeared in 5 files: " + wordsInNumFiles(5).size());
//        for(String word : wordsInNumFiles(3)) {
//            System.out.println("\t" + word);
//        }
        
        printFilesIn("sad");
//        
//        printMap();
        
    }
    
    public static void main(String args []) {
         new WordsInFiles().test();
    }
}
