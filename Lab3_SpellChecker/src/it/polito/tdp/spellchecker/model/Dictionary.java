package it.polito.tdp.spellchecker.model;

import java.io.*;
import java.util.*;

public class Dictionary {
	
	private List<String> dictionary;
	
	public Dictionary(){
		dictionary=new ArrayList<String>();
	}
	
	/**
	 * Download the correct dictionary
	 */
	public void loadDictionary(String language){
		if(language.compareTo("English")==0){
			//English dictionary
			try{
				FileReader fr=new FileReader("rsc/English.txt");
				BufferedReader br=new BufferedReader(fr);
				String word;
				while((word=br.readLine())!=null){
					//Add word to EnglishDictionary
					dictionary.add(word.toLowerCase());					
				}
				br.close();
			}
			catch(IOException e){
				System.out.println("Error reading from file");
			}
		}
		else{
			//Italian dictionary
			try{
				FileReader fr=new FileReader("rsc/Italian.txt");
				BufferedReader br=new BufferedReader(fr);
				String word;
				while((word=br.readLine())!=null){
					//Add word to ItalianDictionary
					dictionary.add(word.toLowerCase());
				}
				br.close();
			}
			catch(IOException e){
				System.out.println("Error reading from file");
			}
		}
	}
	
	public List<Word> spellCheckText(List<String> inputTextList){
		List<Word> richWord=new ArrayList<Word>();
		for(int i=0; i<inputTextList.size(); i++){
			if(dictionary.contains(inputTextList.get(i))){
				//Word is in the dictionary
				Word w=new Word(inputTextList.get(i), true);
				richWord.add(w);
			}
			else{
				//Word isn't in the dictionary
				Word w=new Word(inputTextList.get(i), false);
				richWord.add(w);
			}
		}
		return richWord;
	}

}
