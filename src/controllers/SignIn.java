package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import application.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignIn implements Initializable {
	
		//BD variables
		Connection cnx;
		public PreparedStatement st,role_st;
		public ResultSet result,res_role;
		
		//ScreenBuilder Elements
		
		
		
	 	@FXML
	    private Button forgot_btn;

	    @FXML
	    private TextField password_fld;

	    @FXML
	    private Button signin_btn;
	    
	    @FXML
	    private VBox Vbox;

	    @FXML
	    private TextField username_fld;

	    private Parent fxml;
	    
	    //forgot password method
	    @FXML
	    void forgotAction(ActionEvent event) {
	    	try {
	       		fxml=FXMLLoader.load(getClass().getResource("/interfaces/PasswordRecovery.fxml"));
	       		Vbox.getChildren().removeAll();
	       		Vbox.getChildren().setAll(fxml);
	       	}
	       	catch(Exception e){
	       		e.printStackTrace();
	       		
	       	}
	    }
	    //signIn method
	    @FXML
	    void signinAction(ActionEvent event) {
	    	String email= username_fld.getText();
	    	String pass=password_fld.getText();
	    	if(!email.equals("") && !pass.equals("") && email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
	    		String sql="select Code_utilisateur , Login, Password , Role from utilisateur where Login='"+ email +"' and Password='"+pass +"'";
	    		try {
	    			st=cnx.prepareStatement(sql);
	    			result=st.executeQuery();
	    			if(result.next()) {
	    				if(email.equals(result.getString("Login")) && pass.equals(result.getString("Password"))) {
	    					Vbox.getScene().getWindow().hide();
	    		    		Stage home= new Stage();
	    		    		try {
	    		    			if(result.getString("Role").equals("user")) {
	    		    				fxml=FXMLLoader.load(getClass().getResource("/interfaces/Home.fxml"));
	    		    			}
	    		    			else {
		    		    			fxml=FXMLLoader.load(getClass().getResource("/interfaces/AdminFormations.fxml"));
	    		    			}
	    			    		Scene scene = new Scene(fxml);
	    			    		home.setScene(scene);
	    			    		home.show();
	    		    		}
	    		    		catch(Exception e){
	    			    		e.printStackTrace();
	    			    		
	    			    	}
	    				}
	    				else {
		    				Alert alert= new Alert(AlertType.ERROR,"username or password is not correct",javafx.scene.control.ButtonType.OK);
		    				alert.showAndWait();
		    				username_fld.setText("");
		    				password_fld.setText("");
		    			}
	    			}
	    			else {
	    				Alert alert= new Alert(AlertType.WARNING,"Wrong Email or Password",javafx.scene.control.ButtonType.OK);
	     				alert.showAndWait();
	    				username_fld.setText("");
	    				password_fld.setText("");
	    			}
	    			
		    	}
	    		catch(Exception e){
		    		e.printStackTrace();
		    		
		    	}
	    	}
	    	else {
	    		Alert alert= new Alert(AlertType.ERROR,"Please enter a valid username and password",javafx.scene.control.ButtonType.OK);
				alert.showAndWait();
	    	}
	    	
		    	
	    }

	//Initialising the page
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//connecting the db
		cnx=DataBase.connecterBase();
		
	}

}
