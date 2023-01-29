package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main implements Initializable {

		@FXML
		private VBox Vbox;
	
	 	@FXML
	    private Button btn_in;

	    @FXML
	    private Button btn_up;
	    
	    @FXML
	    private Pane pane;
	    private Parent fxml;

	    @FXML
	    void openSignIn() {
	    	try {
	    		fxml=FXMLLoader.load(getClass().getResource("/interfaces/SignIn.fxml"));
	    		Vbox.getChildren().removeAll();
	    		Vbox.getChildren().setAll(fxml);
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    		
	    	}
	    	Vbox.setVisible(true);
			Vbox.setPrefHeight(386);
			Vbox.setLayoutX(281);
			Vbox.setPrefWidth(281);
			
	    }

	    @FXML
	    void openSignUp() {
	    	try {
	    		fxml=FXMLLoader.load(getClass().getResource("/interfaces/SignUp.fxml"));
	    		Vbox.getChildren().removeAll();
	    		Vbox.getChildren().setAll(fxml);
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    		
	    	}
	    	Vbox.setVisible(true);
			Vbox.setPrefHeight(386);
			Vbox.setLayoutX(0);
			Vbox.setPrefWidth(281);
	    }
	    
	    @Override
	    public void initialize(URL location, ResourceBundle resources) {
	    }

}
