package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Home implements Initializable {
		private Parent fxml;
		 @FXML
		    private AnchorPane root;
	 	@FXML
	    void formations(MouseEvent event) {
	 		try {
	    		fxml=FXMLLoader.load(getClass().getResource("/interfaces/Formations.fxml"));
	    		root.getChildren().removeAll();
	    		root.getChildren().setAll(fxml);
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    		
	    	}
	    }

	    @FXML
	    void home(MouseEvent event) {
	    	try {
	    		fxml=FXMLLoader.load(getClass().getResource("/interfaces/Home.fxml"));
	    		root.getChildren().removeAll();
	    		root.getChildren().setAll(fxml);
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    		
	    	}
	    }

	    @FXML
	    void instructors(MouseEvent event) {
	    	try {
	    		fxml=FXMLLoader.load(getClass().getResource("/interfaces/Formateurs.fxml"));
	    		root.getChildren().removeAll();
	    		root.getChildren().setAll(fxml);
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    		
	    	}
	    }

	    @FXML
	    void participants(MouseEvent event) {
	    	
	    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
