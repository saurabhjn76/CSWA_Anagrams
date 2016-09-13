package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    ArrayList<String> wordList = new ArrayList<String>();
    HashSet<String> wordSet = new HashSet<String>();
    HashMap<String,ArrayList<String>> lettersToWord= new HashMap<String, ArrayList<String>>();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            String sorted =sortLetters(word);
            if(lettersToWord.containsKey(sorted))
                lettersToWord.get(sorted).add(word);
            else {
                ArrayList<String> values = new ArrayList<String>();
                values.add(word);
                lettersToWord.put(sorted,values);
            }
        }

    }


    public boolean isGoodWord(String word, String base) {
        if(wordSet.contains(word) && !word.toLowerCase().contains(base.toLowerCase()) )
            return true;
        else
            return false;
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        for(int i=0;i<wordList.size();i++) {
            if(targetWord.length()==wordList.get(i).length() && sortLetters(targetWord).equalsIgnoreCase(sortLetters(wordList.get(i)))){
                result.add(wordList.get(i));
            }
        }
        return result;
    }

    public String sortLetters(String string) {
        char[] chars = string.toCharArray();
        Arrays.sort(chars);
        String sorted = new String(chars);
        return  sorted;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for(char c='a';c<='z';c++)
        {
            if(lettersToWord.containsKey(sortLetters(word + c))) {
                result.addAll(lettersToWord.get(sortLetters(word+c)));
            }
        }

        return result;
    }

    public String pickGoodStarterWord() {
        random.nextInt()
        return "stop";
    }
}
