package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.DataBase;
import application.Formateur;
import application.Formation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Formateurs implements Initializable {
	//BD variables
	Connection cnx;
	public PreparedStatement st,dom;
	public ResultSet result,res_dom;
	//ScreenBuilder Elements
	
	private Parent fxml;
	 @FXML
	    private AnchorPane root;
	 
	//search text field
	 
		@FXML
	    private TextField search_fld;
	 

	    @FXML
	    private TableColumn<Formateur, String> tab_dom;

	    @FXML
	    private TableColumn<Formateur, String> tab_email;

	    @FXML
	    private TableColumn<Formateur, String> tab_nom;

	    @FXML
	    private TableColumn<Formateur, String> tab_ntel;

	    @FXML
	    private TableColumn<Formateur, String> tab_prenom;

	    @FXML
	    private TableView<Formateur> table;
	    
	    @FXML
	  //list to store each db entry
	    public ObservableList<Formateur> data=FXCollections.observableArrayList();
	 
	//Go to formations page
	@FXML
   void formations(MouseEvent event) {
		try {
   		fxml=FXMLLoader.load(getClass().getResource("/interfaces/formations.fxml"));
   		root.getChildren().removeAll();
   		root.getChildren().setAll(fxml);
   	}
   	catch(Exception e){
   		e.printStackTrace();
   		
   	}
   }
	//Go to home page
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
   
 //Go to instructors page
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
   
   @FXML
   void resetTable(MouseEvent event) {
	   table.getItems().clear();
	   displayFormateurs();
   }

   @FXML
   void search(MouseEvent event) {
	   if(search_fld.getText()=="") {
		   Alert alert= new Alert(AlertType.INFORMATION,"Please enter a valid search",javafx.scene.control.ButtonType.OK);
			alert.showAndWait();
			table.getItems().clear();
			displayFormateurs();
	   }
	   else {
	   table.getItems().clear();
	   String name="%"+ search_fld.getText() +"%";
	   String sql="select * from formateur where Nom LIKE'"+ name+"'";  
	   try {
		   st=cnx.prepareStatement(sql);
		   result=st.executeQuery();
		   while(result.next()) {
			   String code_form=result.getString("Code_formateur");
			   if(code_form==null) {
				   Alert alert= new Alert(AlertType.INFORMATION,"No formateurs found",javafx.scene.control.ButtonType.OK);
					alert.showAndWait();
					table.getItems().clear();
					displayFormateurs();
			   }
			   else {
				   String sql_dom="select Libelle from formateur, domaine where formateur.Domaine=domaine.Code_domaine AND formateur.Code_formateur='"+ code_form+"'";
				   dom=cnx.prepareStatement(sql_dom);
				   res_dom=dom.executeQuery();
				   res_dom.next();
				   data.add(new Formateur(result.getString("Nom"),result.getString("Prenom"),res_dom.getString("Libelle"),result.getString("Email"),result.getString("N_telephone")));
				   setTableValues();
			   }
			   
		   }
	   }
	   
	   catch(Exception e){
   		e.printStackTrace();
   	}
	   }
	   
   }
   
// display all formations
   @FXML
   void displayFormateurs() {
	   table.getItems().clear();
	   String sql="select Code_formateur , Nom,Prenom,Domaine ,Email,N_telephone from formateur";
	   try {
		   st=cnx.prepareStatement(sql);
		   result=st.executeQuery();
		   while(result.next()) {
			   String code_form=result.getString("Code_formateur");
			   String sql_dom="select Libelle from formateur, domaine where formateur.Domaine=domaine.Code_domaine AND formateur.Code_formateur='"+ code_form +"'";
			   dom=cnx.prepareStatement(sql_dom);
			   res_dom=dom.executeQuery();
			   res_dom.next();
				   data.add(new Formateur(result.getString("Nom"),result.getString("Prenom"),res_dom.getString("Libelle"),result.getString("Email"),result.getString("N_telephone")));
		   }
	   }
	   
	   catch(Exception e){
   		e.printStackTrace();
   	}
	   setTableValues();
   }
   
 //setting the table values
   
   void setTableValues() {
	   tab_nom.setCellValueFactory(new PropertyValueFactory<Formateur, String>("Nom"));
	   tab_prenom.setCellValueFactory(new PropertyValueFactory<Formateur, String>("Prenom"));
	   tab_dom.setCellValueFactory(new PropertyValueFactory<Formateur, String>("Domaine_name"));
	   tab_email.setCellValueFactory(new PropertyValueFactory<Formateur, String>("Email"));
	   tab_ntel.setCellValueFactory(new PropertyValueFactory<Formateur, String>("N_telephone"));
	   table.setItems(data);
   }
   
 //Initialising the page
@Override
public void initialize(URL arg0, ResourceBundle arg1) {
	//connecting to the db
		cnx=DataBase.connecterBase();
		//displaying all formations
		displayFormateurs();
	
}

}
