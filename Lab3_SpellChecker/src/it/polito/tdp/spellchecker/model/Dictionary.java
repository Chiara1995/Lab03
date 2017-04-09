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
	 * @param language language of the dictionary to load (English or Italian)
	 */
	public void loadDictionary(String language){
		if(language.compareTo("English")==0){
			//English dictionary
			try{
				FileReader fr=new FileReader("rsc/English.txt");
				BufferedReader br=new BufferedReader(fr);
				String word;
				while((word=br.readLine())!=null){
					//Add words from English Dictionary
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
					//Add words from Italian Dictionary
					dictionary.add(word.toLowerCase());
				}
				br.close();
			}
			catch(IOException e){
				System.out.println("Error reading from file");
			}
		}
	}
	
	/**
	 * Spellcheck of text
	 * @param inputTextList list<String> of words in the input sentence
	 * @return list<Word> of words checked in the input sentence
	 */
	public List<Word> spellCheckText(List<String> inputTextList){
		
		/*
		
		//Ricerca normale
		 
		List<Word> richWord=new ArrayList<Word>();
		for(int i=0; i<inputTextList.size(); i++){
			if(dictionary.contains(inputTextList.get(i))){
				//Word is in the dictionary
				Word w=new Word(inputTextList.get(i));
				w.setCorrect(true);
				richWord.add(w);
			}
			else{
				//Word isn't in the dictionary
				Word w=new Word(inputTextList.get(i));
				w.setCorrect(false);
				richWord.add(w);
			}
		}
		return richWord;
		
		*/ 

		//Ricerca dicotomica
				
		Collections.sort(dictionary, new Comparator<String>(){
			public int compare(String s1, String s2){
				return s1.compareTo(s2);
			}
		});
		List<Word> richWord=new ArrayList<Word>();
		
		for(int i=0; i<inputTextList.size(); i++){
			
			Word w=ricercaDicotomica(dictionary, inputTextList.get(i));
			if(w==null){
				//Word isn't in the dictionary
				Word wtemp=new Word(inputTextList.get(i));
				wtemp.setCorrect(false);
				richWord.add(wtemp);
			}
			else{
				//Word is in the dictionary
				Word wtemp=new Word(inputTextList.get(i));
				wtemp.setCorrect(true);
				richWord.add(wtemp);					
			}
		}
		return richWord;
	
	}
	
	public Word ricercaDicotomica(List<String> listD, String word){
		int low=0;
		int high=listD.size();
		
		while (low<=high) {
			int mid=(low+high)/2;
			if(listD.get(mid).compareTo(word)==0){
				Word w=new Word(word);
				return w;
		    }
			else {
				if (listD.get(mid).compareTo(word)<0){
					//word is lexicographically less than the string argument
					low=mid+1;
				}
				else {
					//word is lexicographically greater than the string argument
					high=mid-1;
				}
			}	
		}
		return null;
	}
	
}
