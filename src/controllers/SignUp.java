package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import application.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class SignUp implements Initializable {
	
	//BD variables
			Connection cnx;
			public PreparedStatement st;
			public ResultSet result;
			
	//ScreenBuilder Elements
			
	private Parent fxml;
	
	@FXML
    private VBox Vbox;
	
	@FXML
    private TextField email_fld;

    @FXML
    private TextField pass_fld;

    @FXML
    private Button sign_btn;
    
    @FXML
    private ComboBox<String> role_fld;

    @FXML
    void signUp(MouseEvent event) {
    	String email= email_fld.getText();
    	String pass=pass_fld.getText();
    	String role=role_fld.getValue();
    	String sql="insert into utilisateur (Login,Password,Role) values(?,?,?)";
    	if(!email.equals("") && email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$") && !pass.equals("") && !role.equals("")) {
    		try {
    			st=cnx.prepareStatement(sql);
    	 		st.setString(1, email);
    	 		st.setString(2, pass);
    	 		st.setString(3, role);
    	 		st.execute();
    	 		Alert alert= new Alert(AlertType.CONFIRMATION,"Account Created Successfully",javafx.scene.control.ButtonType.OK);
 				alert.showAndWait();
 				Vbox.getScene().getWindow().hide();
	    		Stage home= new Stage();
 				if(role.equals("user")) {
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
    		Alert alert= new Alert(AlertType.ERROR,"Please enter a valid username and password",javafx.scene.control.ButtonType.OK);
			alert.showAndWait();
    	}

    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx=DataBase.connecterBase();
		
		//configure role filed with proper values
		role_fld.getItems().addAll("user", "admin");
		
		
		
		
	}

}
