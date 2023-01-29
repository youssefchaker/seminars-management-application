package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.DataBase;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Formations implements Initializable {
	//BD variables
	Connection cnx;
	public PreparedStatement st,form,dom,btn,up;
	public ResultSet result,res_form,res_dom,res_btn,res_up;
	int nbpart;
	
	//ScreenBuilder Elements
	private Parent fxml;
	 @FXML
	private AnchorPane root;
	 
	//search text field
	 
	@FXML
    private TextField search_fld;
	
	//table columns

    @FXML
    private TableColumn<Formation, Integer> tab_annee;


    @FXML
    private TableColumn<Formation, String> tab_domaine;

    @FXML
    private TableColumn<Formation, Integer> tab_duree;

    @FXML
    private TableColumn<Formation, String> tab_form;

    @FXML
    private TableColumn<Formation, Integer> tab_mois;
    
    @FXML
    private TableColumn<Formation, Integer> tab_nbpart;
    
    @FXML
    private TableColumn<Formation, String> tab_inti;
    
    @FXML
    private TableColumn<Formation, Button> tab_part;
    
    //the whole table element
    @FXML
    private TableView<Formation> table;
    
    //list to store each db entry
    public ObservableList<Formation> data=FXCollections.observableArrayList();
    
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
   
   // display all formations
   void displayFormations() {
	   table.getItems().clear();
	   String sql="select Code_formation,Intitulé,Domaine,Nombre_jours,Annee,Mois,Formateur,Nombre_participants from formation";
	   try {
		   st=cnx.prepareStatement(sql);
		   result=st.executeQuery();
		   while(result.next()) {
			   String init=result.getString("Intitulé");
			   String sql_form="select Nom,Prenom from formation,formateur where formation.Formateur=formateur.Code_formateur AND formation.Formateur='"+ result.getString("Formateur")+"'";
			   String sql_dom="select Libelle from formation, domaine where formation.Domaine=domaine.Code_domaine AND formation.Domaine='"+ result.getString("Domaine")+"'";
			   String sql_btn="select Nombre_participants from formation where Intitulé='"+ result.getString("Intitulé")+"'";
			   form=cnx.prepareStatement(sql_form);
			   res_form=form.executeQuery();
			   dom=cnx.prepareStatement(sql_dom);
			   res_dom=dom.executeQuery();
			   btn=cnx.prepareStatement(sql_btn);
			   res_btn=btn.executeQuery(); 
			   res_form.next();
			   res_dom.next();
			   res_btn.next();
			   Button button = new Button();
					   button.setText("Participate");
					   button.setOnAction(new EventHandler<ActionEvent>() {
						    @Override
						    public void handle(ActionEvent event) {
						    	System.out.print("aaa");
						 	   try {
						 		  String sql="UPDATE formation SET Nombre_participants = Nombre_participants+1 WHERE Intitulé='"+ init +"'";
						 		up=cnx.prepareStatement(sql);
						 		up.executeUpdate();
						 		up.close();
						 		button.setText("Registered");
								button.setDisable(true);
								Alert alert= new Alert(AlertType.INFORMATION,"Registered!!",javafx.scene.control.ButtonType.OK);
								alert.showAndWait();
						 	} catch (SQLException e) {
						 		e.printStackTrace();
						 	}
						    }
						});
				   data.add(new Formation(result.getString("Intitulé"),res_dom.getString("Libelle"),result.getInt("Nombre_jours"),result.getInt("Annee"),result.getInt("Mois"),res_form.getString("Prenom")+" "+res_form.getString("Nom"),result.getInt("Nombre_participants"),button));
		   }
	   }
	   
	   catch(Exception e){
   		e.printStackTrace();
   	}
	   setTableValues();
   }
   
 //Search formations by Intitulé
   @FXML
   void search(MouseEvent event) {
	   if(search_fld.getText()=="") {
		   Alert alert= new Alert(AlertType.INFORMATION,"Please enter a valid search",javafx.scene.control.ButtonType.OK);
			alert.showAndWait();
			table.getItems().clear();
			displayFormations();
	   }
	   else {
	   table.getItems().clear();
	   String inti="%"+ search_fld.getText() +"%";
	   String sql="select * from formation where Intitulé LIKE'"+ inti+"'";  
	   try {
		   st=cnx.prepareStatement(sql);
		   result=st.executeQuery();
		   while(result.next()) {
			   String init=result.getString("Intitulé");
			   String sql_form="select Nom,Prenom from formation,formateur where formation.Formateur=formateur.Code_formateur AND formation.Formateur='"+ result.getString("Formateur")+"'";
			   String sql_dom="select Libelle from formation, domaine where formation.Domaine=domaine.Code_domaine AND formation.Domaine='"+ result.getString("Domaine")+"'";
			   String sql_btn="select Nombre_participants from formation where Intitulé='"+ result.getString("Intitulé")+"'";
			   form=cnx.prepareStatement(sql_form);
			   res_form=form.executeQuery();
			   dom=cnx.prepareStatement(sql_dom);
			   res_dom=dom.executeQuery();
			   btn=cnx.prepareStatement(sql_btn);
			   res_btn=btn.executeQuery(); 
			   res_form.next();
			   res_dom.next();
			   res_btn.next();
			   Button button = new Button();
				   if(Integer.parseInt(result.getString("Nombre_participants")) >=4) {
					   button.setText("Full");
					   button.setDisable(true);
				   }
				   else {
					   button.setText("Participate");
					   button.setOnAction(new EventHandler<ActionEvent>() {
						    @Override
						    public void handle(ActionEvent event) {
						    	System.out.print("aaa");
						 	   try {
						 		  String sql="UPDATE formation SET Nombre_participants = Nombre_participants+1 WHERE Intitulé='"+ init +"'";
						 		up=cnx.prepareStatement(sql);
						 		up.executeUpdate();
						 		up.close();
						 		button.setText("Registered");
								button.setDisable(true);
								Alert alert= new Alert(AlertType.INFORMATION,"Registered!!",javafx.scene.control.ButtonType.OK);
								alert.showAndWait();
						 	} catch (SQLException e) {
						 		e.printStackTrace();
						 	}
						    }
						});
				   }
				   data.add(new Formation(result.getString("Intitulé"),res_dom.getString("Libelle"),result.getInt("Nombre_jours"),result.getInt("Annee"),result.getInt("Mois"),res_form.getString("Prenom")+" "+res_form.getString("Nom"),result.getInt("Nombre_participants"),button));
		   }
	   }
	   
	   catch(Exception e){
   		e.printStackTrace();
   	}
	   }
	   setTableValues();   
   
   }
   
   //reset table to all values
   @FXML
   void resetTable() {
	   table.getItems().clear();
	   displayFormations();
   }
   
   //setting the table values
   void setTableValues() {
	   tab_inti.setCellValueFactory(new PropertyValueFactory<Formation, String>("Intitulé"));
	   tab_domaine.setCellValueFactory(new PropertyValueFactory<Formation, String>("Domaine_name"));
	   tab_duree.setCellValueFactory(new PropertyValueFactory<Formation, Integer>("Nombre_jours"));
	   tab_annee.setCellValueFactory(new PropertyValueFactory<Formation, Integer>("Annee"));
	   tab_mois.setCellValueFactory(new PropertyValueFactory<Formation, Integer>("Mois"));
	   tab_form.setCellValueFactory(new PropertyValueFactory<Formation, String>("Formateur_name"));
	   tab_nbpart.setCellValueFactory(new PropertyValueFactory<Formation, Integer>("Nombre_participants"));
	   tab_part.setCellValueFactory(new PropertyValueFactory<Formation, Button>("Button"));
	   table.setItems(data);
   }
   
   
//Initialising the page
@Override
public void initialize(URL arg0, ResourceBundle arg1) {
	//connecting to the db
	cnx=DataBase.connecterBase();
	//displaying all formations
	displayFormations();
	
	
}

}
