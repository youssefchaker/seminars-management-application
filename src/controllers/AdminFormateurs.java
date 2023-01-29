package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import application.DataBase;
import application.Formateur;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class AdminFormateurs implements Initializable {
	
	//BD variables
		Connection cnx;
		public PreparedStatement st,dom_st,search_st;
		public ResultSet result,res_dom,res_search;
		public boolean result_add;
		
		//ScreenBuilder Elements
		private Parent fxml;

		@FXML
		 private ComboBox<String> domaine_fld;

	    @FXML
	    private TextField mat_search;

	    @FXML
	    private Button mod_btn;

	    @FXML
	    private TextField nom_fld;

	    @FXML
	    private TextField prenom_fld;

	    @FXML
	    private AnchorPane root;

	    @FXML
	    private TableColumn<Formateur, String> tab_domaine;

	    @FXML
	    private TableColumn<Formateur, String> tab_email;

	    @FXML
	    private TableColumn<Formateur, Integer> tab_mat;

	    @FXML
	    private TableColumn<Formateur, String> tab_nom;

	    @FXML
	    private TableColumn<Formateur, String> tab_prenom;

	    @FXML
	    private TableColumn<Formateur, Integer> tab_tel;


	    @FXML
	    private TextField tel_fld;
	    
	  //list to store each db entry
	    public ObservableList<Formateur> data=FXCollections.observableArrayList();
	    
	  //the whole table element

	    @FXML
	    private TableView<Formateur> table;

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
	    
	    @FXML
	    void adminDemandes(MouseEvent event) {
	    	try {
	       		fxml=FXMLLoader.load(getClass().getResource("/interfaces/AdminDemandes.fxml"));
	       		root.getChildren().removeAll();
	       		root.getChildren().setAll(fxml);
	       	}
	       	catch(Exception e){
	       		e.printStackTrace();
	       		
	       	}
	    }
	    
	    
	    @FXML
	    void demandeFormateurs(MouseEvent event) {
	    	/*try {
	       		fxml=FXMLLoader.load(getClass().getResource("/interfaces/DemandeFormateur.fxml"));
	       		root.getChildren().removeAll();
	       		root.getChildren().setAll(fxml);
	       	}
	       	catch(Exception e){
	       		e.printStackTrace();
	       		
	       	}*/
	    }
	    
	    @FXML
	    void demandeFormations(MouseEvent event) {
	    	/*
	    	try {
	       		fxml=FXMLLoader.load(getClass().getResource("/interfaces/DemandeFormation.fxml"));
	       		root.getChildren().removeAll();
	       		root.getChildren().setAll(fxml);
	       	}
	       	catch(Exception e){
	       		e.printStackTrace();
	       		
	       	}*/
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
	    
	 // display all formateurs in db
	    
	    void displayFormateurs() {
	 	   table.getItems().clear();
	 	   String sql="select Matricule_formateur,Nom,Prenom,Utilisateur.Login,Domaine.Libelle,N_telephone from formateur, utilisateur,domaine where Utilisateur =utilisateur.Code_utilisateur and Domaine =domaine.Code_domaine   ";
	 	   try {
	 		   st=cnx.prepareStatement(sql);
	 		   result=st.executeQuery();
	 		   while(result.next()) {
	 				   data.add(new Formateur(result.getInt("Matricule_formateur"),result.getString("Nom"),result.getString("Prenom"),result.getString("Login"),result.getString("Libelle"),result.getString("N_telephone")));
	 		   }
	 	   }
	 	   
	 	   catch(Exception e){
	    		e.printStackTrace();
	    	}
	 	   //setting the table values with each formation element from db
	 	   
	 	   tab_mat.setCellValueFactory(new PropertyValueFactory<Formateur, Integer>("Code"));
	 	   tab_nom.setCellValueFactory(new PropertyValueFactory<Formateur, String>("Nom"));
	 	   tab_prenom.setCellValueFactory(new PropertyValueFactory<Formateur, String>("Prenom"));
	 	   tab_email.setCellValueFactory(new PropertyValueFactory<Formateur, String>("Login"));
	 	   tab_domaine.setCellValueFactory(new PropertyValueFactory<Formateur, String>("Libelle"));
	 	   tab_tel.setCellValueFactory(new PropertyValueFactory<Formateur, Integer>("N_telephone"));
	 	   
	 	   table.setItems(data);
	    }
	    
	    /*
	    @FXML
	    //adding a formateur to the db
	    void addFormateur(MouseEvent event) {
	    	String dom=dom_fld.getValue();
	    	String nom=nom_fld.getText();
	    	String prenom=prenom_fld.getText();
	    	String tel=tel_fld.getText();
	    	String email=email_fld.getText();
	    	
	    	String sql="insert into formateur(Nom,Prenom,Domaine,Email,N_telephone) values(?,?,(select Code_domaine from domaine where domaine.Libelle=?),?,?)";
	    	if(!nom.equals("") && !dom.equals(null) && !prenom.equals("") && !tel.equals("") && tel.length()==8 && !email.equals("") && email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$") ) {
	    		try {
	 	 		   st=cnx.prepareStatement(sql);
	 	 		   st.setString(1, nom);
	 	 		   st.setString(2, prenom);	
	 	 		   st.setString(3, dom);
	 	 		   st.setString(4, email);
	 	 		   st.setString(5, tel);   
	 	 		   result_add=st.execute();
	 	 		   nom_fld.setText("");
	 	 		   dom_fld.setValue(null);
	 	 		   prenom_fld.setText("");
	 	 		   tel_fld.setText("");
	 	 		   email_fld.setText("");
	 	 		   Alert alert= new Alert(AlertType.CONFIRMATION,"Formateur Inserted with Success",javafx.scene.control.ButtonType.OK);
	 				alert.showAndWait();
	 	 		   	displayFormateurs();
	 	 		   
	 	    	}
	 	    	catch(Exception e){
	 	       		e.printStackTrace();
	 	       	}
	    	}
	    	else {
	    		Alert alert= new Alert(AlertType.WARNING,"Please fill in all fields with Valid information",javafx.scene.control.ButtonType.OK);
 				alert.showAndWait();
	    	}
	    	
	    }*/
	    
	    @FXML
	    //update an existing formateur in the db
	    void updateFormateur(MouseEvent event) {
	    	String code=mat_search.getText();
	    	String nom=nom_fld.getText();
	    	String prenom=prenom_fld.getText();
	    	String profil=domaine_fld.getValue();
	    	
	    	String tel=tel_fld.getText();
	    	String sql="update formateur set Nom=?,Prenom=?, Domaine=(select Code_domaine  from domaine where domaine.Libelle=?),N_telephone=? where Matricule_formateur ='"+code+"'";
	    	if(!code.equals("")  && !nom.equals("") && !prenom.equals("") && !tel.equals("") && tel.length()==8 ) {
	    		try {
	 	 		   st=cnx.prepareStatement(sql);
	 	 		   st.setString(1, nom);
	 	 		   st.setString(2, prenom);
	 	 		   st.setString(3, profil);
	 	 		   st.setString(4, tel);
	 	 		   st.execute();
	 	 		   mat_search.setText("");
	 	 		   nom_fld.setText("");
	 	 		   prenom_fld.setText("");
	 	 		   tel_fld.setText("");
	 	 		   domaine_fld.setValue(null);
	 	 		   Alert alert= new Alert(AlertType.CONFIRMATION,"Formateur Mise a jour",javafx.scene.control.ButtonType.OK);
	 				alert.showAndWait();
	 	 		   	displayFormateurs();
	 	 		   
	 	    	}
	 	    	catch(Exception e){
	 	       		e.printStackTrace();
	 	       	}
	    	}
	    	else {
	    		Alert alert= new Alert(AlertType.WARNING,"Si il vous plais tapez tous le informations",javafx.scene.control.ButtonType.OK);
 				alert.showAndWait();
	    	}
	    	
	    }
	    
	    //search for a formateur using the formateur_code
	    
	    @FXML
	    void searchFormateur(MouseEvent event) {
	    	String code=mat_search.getText();
	    	if(!code.equals("")) {
	    		String sql="select Matricule_formateur ,Nom,Prenom,Domaine.Libelle,N_telephone from formateur,domaine,utilisateur where Matricule_formateur  ='"+code+"' and Utilisateur =utilisateur.Code_utilisateur and Domaine =domaine.Code_domaine ";
		    	try {
		 		   search_st=cnx.prepareStatement(sql);
		 		   res_search=search_st.executeQuery();
		 		   if(res_search.next()) {
		 			   	nom_fld.setText(res_search.getString("Nom"));
		 		    	prenom_fld.setText(res_search.getString("Prenom"));
		 		    	domaine_fld.setValue(res_search.getString("Libelle"));
		 		    	tel_fld.setText(res_search.getString("N_telephone"));
		 		   }
		 		   
		 		  else {
			    		Alert alert= new Alert(AlertType.ERROR,"Aucun formateur trouvé",javafx.scene.control.ButtonType.OK);
		 				alert.showAndWait();
			    	}
		 	   }
		 	   
		 	   catch(Exception e){
		    		e.printStackTrace();
		    	}
	    	}
	    	else {
	    		Alert alert= new Alert(AlertType.WARNING,"donner un code formateur valide",javafx.scene.control.ButtonType.OK);
 				alert.showAndWait();
	    	}
	    	
	    }
	    
	    @FXML
	    void addFormateur(MouseEvent event) {
	    	try {
	       		fxml=FXMLLoader.load(getClass().getResource("/interfaces/AdminAjouterFormateur.fxml"));
	       		root.getChildren().removeAll();
	       		root.getChildren().setAll(fxml);
	       	}
	       	catch(Exception e){
	       		e.printStackTrace();
	       		
	       	}
	    	
	    }
	    
	    
	    //delete a formateur using the formateur_code

	    @FXML
	    void deleteFormateur(MouseEvent event) {
	    	String code=mat_search.getText();
	    	if(!code.equals("")) {
	    		String sql="delete from formateur where Matricule_formateur ='"+code+"'";
		    	try {
		    		st=cnx.prepareStatement(sql);
		    		st.executeUpdate();
		    		Alert alert= new Alert(AlertType.CONFIRMATION,"Formateur supprimer",javafx.scene.control.ButtonType.OK);
	 				alert.showAndWait();
	 				displayFormateurs();
	 				
		    	}
		    	catch(Exception e){
		    		e.printStackTrace();
		    	}
	    	}
	    	else {
	    		Alert alert= new Alert(AlertType.WARNING,"donner un code formateur valide",javafx.scene.control.ButtonType.OK);
 				alert.showAndWait();
	    	}
	    	
	    	
	    }
	    
	    @FXML
	    void sign_out(MouseEvent event) {

	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			
			//connecting to the db
			cnx=DataBase.connecterBase();
			//displaying all formations
			displayFormateurs();
			
			//configuring the add fields
			
			//populating the profil combobox with domains from the db
			try {
				dom_st=cnx.prepareStatement("Select Libelle from domaine");
				res_dom = dom_st.executeQuery();

		        while (res_dom.next()) {  // loop

		        	domaine_fld.getItems().addAll(res_dom.getString("Libelle")); 
		       }
			}
			
			catch(Exception e){
 	       		e.printStackTrace();
 	       	}
			
			
			
			// force the field N_tel to be numeric only
			tel_fld.textProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, 
			        String newValue) {
			        if (!newValue.matches("\\d*")) {
			        	tel_fld.setText(newValue.replaceAll("[^\\d]", ""));
			        }
			    }
			});
			
			// force the field nom to start alphabetic
			nom_fld.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) {
					if (newValue.matches("^[0-9]*$")) {
						 nom_fld.setText(newValue.replaceAll("^[0-9]*$", ""));
					}
				}
			});
			
			// force the field prenom to start alphabetic
						prenom_fld.textProperty().addListener(new ChangeListener<String>() {
							@Override
							public void changed(ObservableValue<? extends String> observable, String oldValue, 
								String newValue) {
								if (newValue.matches("^[0-9]*$")) {
									 prenom_fld.setText(newValue.replaceAll("^[0-9]*$", ""));
								}
							}
						});
						
		}
}
