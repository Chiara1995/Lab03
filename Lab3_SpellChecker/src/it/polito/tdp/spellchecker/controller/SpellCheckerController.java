/**
 * Sample Skeleton for 'SpellChecker.fxml' Controller Class
 */

package it.polito.tdp.spellchecker.controller;

import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class SpellCheckerController {
	
	Dictionary dictionary; 
	int numError;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbLanguage"
    private ComboBox<String> cmbLanguage; // Value injected by FXMLLoader

    @FXML // fx:id="txtInput"
    private TextArea txtInput; // Value injected by FXMLLoader

    @FXML // fx:id="btnSpellCheck"
    private Button btnSpellCheck; // Value injected by FXMLLoader

    @FXML // fx:id="txtOutput"
    private TextArea txtOutput; // Value injected by FXMLLoader

    @FXML // fx:id="txtErrors"
    private Label txtErrors; // Value injected by FXMLLoader

    @FXML // fx:id="btnClear"
    private Button btnClear; // Value injected by FXMLLoader

    @FXML // fx:id="txtSeconds"
    private Label txtSeconds; // Value injected by FXMLLoader

    @FXML
    void doClearText(ActionEvent event) {
    	txtInput.clear();
    	txtOutput.clear();
    	inizializeGUI();
    }
    
    private void inizializeGUI() {
    	txtSeconds.setVisible(false);
    	btnClear.setVisible(true);
    	txtErrors.setVisible(false);
    	btnSpellCheck.setDisable(false);
    	txtInput.setEditable(true);
    	cmbLanguage.setDisable(false);
	}
    
        
    @FXML
    void doSpellCheck(ActionEvent event) {
    	//Download data from view
    	String text=txtInput.getText().replaceAll("[\\p{Punct}]", "").toLowerCase().trim();
    	text=text.replaceAll("( )+", " ");
    	String[] a=text.split(" ");
    	List<String> textWords=new LinkedList<String>();
    	for(int k=0; k<a.length; k++){
    		textWords.add(a[k]);
    	}
    	    	
    	//Ask dictionary to do spellcheck
    	dictionary.loadDictionary(cmbLanguage.getValue());
    	double t1=System.nanoTime();
    	List<Word> richWords=dictionary.spellCheckText(textWords);
    	double t2=System.nanoTime();
    	
    	//Update view
    	numError=0;
    	for(int i=0; i<richWords.size(); i++){
    		if(richWords.get(i).isCorrect()==false){
    			//incorrect word
    			txtOutput.appendText(richWords.get(i)+"\n");
    			numError++;
    		}
    	}
    	txtErrors.setText("The text contains "+numError+" errors");
    	txtErrors.setVisible(true);
    	txtSeconds.setText("Spell check completed in "+(t2-t1)/1e9+" seconds");
    	txtSeconds.setVisible(true);
    	
    	btnSpellCheck.setDisable(true);
    	cmbLanguage.setDisable(true);
    	txtInput.setEditable(false);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbLanguage != null : "fx:id=\"cmbLanguage\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnSpellCheck != null : "fx:id=\"btnSpellCheck\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtOutput != null : "fx:id=\"txtOutput\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtErrors != null : "fx:id=\"txtErrors\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtSeconds != null : "fx:id=\"txtSeconds\" was not injected: check your FXML file 'SpellChecker.fxml'.";

        //add elements to the comboBox
        cmbLanguage.getItems().addAll("English","Italian");
        
        //display default element in the comboBox
        if(cmbLanguage.getItems().size()>0)
        	cmbLanguage.setValue(cmbLanguage.getItems().get(0));
        
    }

	/**
	 * Set model to the controller 
	 * @param dictionary 
	 */
    public void setModel(Dictionary dictionary) {
		this.dictionary=dictionary;		
	}
}
