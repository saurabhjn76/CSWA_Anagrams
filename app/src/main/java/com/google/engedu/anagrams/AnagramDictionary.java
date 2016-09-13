package com.google.engedu.anagrams;

import android.util.Log;

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
    int wordLength=DEFAULT_WORD_LENGTH;
    private Random random = new Random();
    ArrayList<String> wordList = new ArrayList<String>();
    HashSet<String> wordSet = new HashSet<String>();
    HashMap<String,ArrayList<String>> lettersToWord= new HashMap<String, ArrayList<String>>();
    HashMap<Integer,ArrayList<String>> sizeToWords = new HashMap<Integer, ArrayList<String>>();

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
            if(sizeToWords.containsKey(word.length())) {
                sizeToWords.get(word.length()).add(word);
            }
            else {
                ArrayList<String> value = new ArrayList<String>();
                value.add(word);
                sizeToWords.put(word.length(),value);
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
        boolean flag= true;
        int counter=0;
        int index = random.nextInt(sizeToWords.get(wordLength).size());
        Log.e("index",sizeToWords.get(wordLength).size() + "  dfd  "+ index);
        Log.e("indexd:" , sizeToWords.get(wordLength).get(index)+"");
        while(flag) {
            if (lettersToWord.get(sortLetters(sizeToWords.get(wordLength).get(index))).size() + getAnagramsWithOneMoreLetter(sizeToWords.get(wordLength).get(index)).size() >= MIN_NUM_ANAGRAMS) {
                flag = false;
                if(wordLength < MAX_WORD_LENGTH) {
                    wordLength++;
                    return sizeToWords.get(wordLength - 1).get(index);
                }
                return sizeToWords.get(wordLength).get(index);
            }
            index=(index+1)%sizeToWords.get(wordLength).size();
            Log.e("HELLO",sizeToWords.get(wordLength).get(index)+"");

        }
        return  "";
    }
}
