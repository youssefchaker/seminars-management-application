package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import application.DataBase;
import application.Domaine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class AdminDomaines implements Initializable {
	
	//BD variables
		Connection cnx;
		public PreparedStatement st,search_st;
		public ResultSet result,res_search;
		public boolean result_add;
		
		//ScreenBuilder Elements
		private Parent fxml;
	    
	    @FXML
	    private TextField code_mod_fld;
	    
	    @FXML
	    private TextField code_del_fld;

	    @FXML
	    private TextField lib_fld;

	    @FXML
	    private TextField lib_mod_fld;

	    @FXML
	    private Button del_btn;

	    @FXML
	    private Button mod_btn;

	    @FXML
	    private AnchorPane root;
	    
	  //table columns

	    @FXML
	    private TableColumn<Domaine, String> tab_code;

	    @FXML
	    private TableColumn<Domaine, String> tab_lib;
	    
	  //list to store each db entry
	    public ObservableList<Domaine> data=FXCollections.observableArrayList();
	    
	  //the whole table element

	    @FXML
	    private TableView<Domaine> table;

	  //Go to admin domaines page
	    
	    @FXML
	    void adminDomaines(MouseEvent event) {
	    	try {
	       		fxml=FXMLLoader.load(getClass().getResource("/interfaces/AdminDomaines.fxml"));
	       		root.getChildren().removeAll();
	       		root.getChildren().setAll(fxml);
	       	}
	       	catch(Exception e){
	       		e.printStackTrace();
	       		
	       	}
	    }
	    
	    //Go to admin formations page

	    @FXML
	    void adminFormations(MouseEvent event) {
	    	try {
	       		fxml=FXMLLoader.load(getClass().getResource("/interfaces/AdminFormations.fxml"));
	       		root.getChildren().removeAll();
	       		root.getChildren().setAll(fxml);
	       	}
	       	catch(Exception e){
	       		e.printStackTrace();
	       		
	       	}
	    }
	    
	    //Go to admin instructors page

	    @FXML
	    void adminFormateurs(MouseEvent event) {
	    	try {
	       		fxml=FXMLLoader.load(getClass().getResource("/interfaces/AdminFormateurs.fxml"));
	       		root.getChildren().removeAll();
	       		root.getChildren().setAll(fxml);
	       	}
	       	catch(Exception e){
	       		e.printStackTrace();
	       		
	       	}
	    }
	    
	  //Go to admin participants page

	    @FXML
	    void adminParticipants(MouseEvent event) {
	    	try {
	       		fxml=FXMLLoader.load(getClass().getResource("/interfaces/AdminParticipants.fxml"));
	       		root.getChildren().removeAll();
	       		root.getChildren().setAll(fxml);
	       	}
	       	catch(Exception e){
	       		e.printStackTrace();
	       		
	       	}
	    }
	    
	 // display all domaines in db
	    
	    void displayDomaines() {
	 	   table.getItems().clear();
	 	   String sql="select Code_domaine,Libelle from domaine";
	 	   try {
	 		   st=cnx.prepareStatement(sql);
	 		   result=st.executeQuery();
	 		   while(result.next()) {
	 				   data.add(new Domaine(result.getInt("Code_domaine"),result.getString("Libelle")));
	 		   }
	 	   }
	 	   
	 	   catch(Exception e){
	    		e.printStackTrace();
	    	}
	 	   //setting the table values with each formation element from db
	 	   
	 	   tab_code.setCellValueFactory(new PropertyValueFactory<Domaine, String>("Code"));
	 	   tab_lib.setCellValueFactory(new PropertyValueFactory<Domaine, String>("Libelle"));
	 	   table.setItems(data);
	    }
	    
	    
	    @FXML
	    //adding a domaine to the db
	    void addDomaine(MouseEvent event) {
	    	String lib=lib_fld.getText();
	    	
	    	String sql="insert into domaine(Libelle) values(?)";
	    	if(!lib.equals("")) {
	    		try {
	 	 		   st=cnx.prepareStatement(sql);
	 	 		   st.setString(1, lib); 
	 	 		   result_add=st.execute();
	 	 		   lib_fld.setText("");
	 	 		   Alert alert= new Alert(AlertType.CONFIRMATION,"Domaine Inserted with Success",javafx.scene.control.ButtonType.OK);
	 				alert.showAndWait();
	 	 		   	displayDomaines();
	 	 		   
	 	    	}
	 	    	catch(Exception e){
	 	       		e.printStackTrace();
	 	       	}
	    	}
	    	else {
	    		Alert alert= new Alert(AlertType.WARNING,"Please fill in the Libelle with Valid information",javafx.scene.control.ButtonType.OK);
 				alert.showAndWait();
	    	}
	    	
	    }
	    
	    @FXML
	    //update an existing domaine in the db
	    void updateDomaine(MouseEvent event) {
	    	String code=code_mod_fld.getText();
	    	String lib=lib_mod_fld.getText();
	    	String sql="update domaine set Libelle=? where Code_domaine='"+code+"'";
	    	if(!code.equals("") &&  !lib.equals("")  ) {
	    		try {
	 	 		   st=cnx.prepareStatement(sql);
	 	 		   st.setString(1, lib);
	 	 		   st.execute();
	 	 		   code_mod_fld.setText("");
	 	 		   lib_mod_fld.setText("");
	 	 		   Alert alert= new Alert(AlertType.CONFIRMATION,"Domaine Updated with Success",javafx.scene.control.ButtonType.OK);
	 				alert.showAndWait();
	 	 		   	displayDomaines();
	 	 		   
	 	    	}
	 	    	catch(Exception e){
	 	       		e.printStackTrace();
	 	       	}
	    	}
	    	else {
	    		Alert alert= new Alert(AlertType.WARNING,"Please fill in all fields with Valid information",javafx.scene.control.ButtonType.OK);
 				alert.showAndWait();
	    	}
	    	
	    }
	    
	    //search for a domaine using the domaine_code
	    
	    @FXML
	    void searchDomaine(MouseEvent event) {
	    	String code=code_mod_fld.getText();
	    	if(!code.equals("")) {
	    		String sql="select Libelle from domaine where Code_domaine='"+code+"'";
		    	try {
		 		   search_st=cnx.prepareStatement(sql);
		 		   res_search=search_st.executeQuery();
		 		   if(res_search.next()) {
		 			   	lib_mod_fld.setText(res_search.getString("Libelle"));
		 		   }
		 		   
		 		  else {
			    		Alert alert= new Alert(AlertType.ERROR,"No Domaines found with this code",javafx.scene.control.ButtonType.OK);
		 				alert.showAndWait();
			    	}
		 	   }
		 	   
		 	   catch(Exception e){
		    		e.printStackTrace();
		    	}
	    	}
	    	else {
	    		Alert alert= new Alert(AlertType.WARNING,"Please Type In a Valid Code",javafx.scene.control.ButtonType.OK);
 				alert.showAndWait();
	    	}
	    	
	    }
	    
	    //delete a domaine using the domaine_code

	    @FXML
	    void deleteDomaine(MouseEvent event) {
	    	String code=code_del_fld.getText();
	    	if(!code.equals("")) {
	    		String sql="delete from domaine where Code_domaine='"+code+"'";
		    	try {
		    		st=cnx.prepareStatement(sql);
		    		st.executeUpdate();
		    		Alert alert= new Alert(AlertType.CONFIRMATION,"Domaine Deleted",javafx.scene.control.ButtonType.OK);
	 				alert.showAndWait();
	 				displayDomaines();
	 				
		    	}
		    	catch(Exception e){
		    		e.printStackTrace();
		    	}
	    	}
	    	else {
	    		Alert alert= new Alert(AlertType.WARNING,"Please Type In a Valid Code",javafx.scene.control.ButtonType.OK);
 				alert.showAndWait();
	    	}
	    	
	    	
	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			
			//connecting to the db
			cnx=DataBase.connecterBase();
			//displaying all 
			displayDomaines();
		}
}
