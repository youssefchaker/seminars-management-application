package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class AdminAjouterFormateur implements Initializable {
	
	//BD variables
		Connection cnx;
		public PreparedStatement st,dom_st,search_st;
		public ResultSet result,res_dom,res_search;
		public boolean result_add;
		
		//ScreenBuilder Elements
		private Parent fxml;

	    @FXML
	    private ToggleGroup acceptFormteur;

	    @FXML
	    private Button add_btn;
	    
	    
	    @FXML
	    private TextField org_fld;
	    

	    @FXML
	    private DatePicker dat_fld;

	    @FXML
	    private ComboBox<String> domaine_fld;

	    @FXML
	    private TextField email_fld;

	    @FXML
	    private TextField mat_fld;

	    @FXML
	    private TextField nom_fld;

	    @FXML
	    private TextField pass_fld;

	    @FXML
	    private TextField prenom_fld;

	    @FXML
	    private ComboBox<String> profil_fld;

	    @FXML
	    private AnchorPane root;

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
	    void demandeFormateur(MouseEvent event) {
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
	    
	    @FXML
	    //adding a formateur to the db
	    void insertFormateur(MouseEvent event) {
	    	String cod_mart=mat_fld.getText();
	    	String nom=nom_fld.getText();
	    	String prenom=prenom_fld.getText();
	    	String mail=email_fld.getText();
	    	String domaine=domaine_fld.getValue();
	    	String profil=profil_fld.getValue();
	    	String tel=tel_fld.getText();
	    	String org=org_fld.getText();
	    	String pass=pass_fld.getText(); 
	    	LocalDate date=dat_fld.getValue();
	    	String sql;
	    	sql="insert into utilisateur (Login,Password,Role) values(?,?,?)";
	    	if(!mail.equals("") && mail.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$") && !pass.equals("") )
	    	{
	    		try {
	    			st=cnx.prepareStatement(sql);
	    	 		st.setString(1, mail);
	    	 		st.setString(2, pass);
	    	 		st.setString(3, "formateur");
	    	 		st.execute();
	    		}
	    		
	    		catch(Exception e){
		    		e.printStackTrace();
		    	}
	    	}
	    	else {
	    		Alert alert= new Alert(AlertType.ERROR,"Veuillez saisir un email et un mot de passe valides",javafx.scene.control.ButtonType.OK);
				alert.showAndWait();
	    	}
	    	String sql2="insert into formateur (Matricule_formateur,Nom,Prenom,Date_naissance,N_telephone,Profil,Utilisateur,Organisme,Domaine,Etat) values(?,?,?,?,?,(select Code_profil from profil where profil.Libelle=?),(select Code_utilisateur from utilisateur where utilisateur.Login=? and utilisateur.Password=?),?,(select Code_domaine from domaine where domaine.Libelle=?),1)";;
	    	if(!domaine.equals(null) && !profil.equals(null) && !tel.equals("") && !org.equals("") && !nom.equals("") && !prenom.equals("") &&  !cod_mart.equals("") && !date.equals(null) && tel.length()==8 && cod_mart.length()==8  ) {
	    		try {
	 	 		   st=cnx.prepareStatement(sql2);
	 	 		   st.setString(1, cod_mart);
	 	 		   st.setString(2, nom);	
	 	 		   st.setString(3, prenom);
	 	 		   st.setDate(4,java.sql.Date.valueOf(date));
	 	 		   st.setString(5, tel);
	 	 		   st.setString(6, profil);
	 	 		   st.setString(7, mail);
	 	 		   st.setString(8, pass);
	 	 		   st.setString(9, org);
	 	 		   st.setString(10, domaine);
	 	 		   result_add=st.execute();
	 	 		   nom_fld.setText("");
	 	 		   tel_fld.setText("");
	 	 		   pass_fld.setText("");
	 	 		   mat_fld.setText("");
	 	 		   email_fld.setText("");
	 	 		   domaine_fld.setValue(null);
	 	 		   prenom_fld.setText("");
	 	 		   dat_fld.setValue(null);
	 	 		   org_fld.setText("");
	 	 		   profil_fld.setValue(null);
	 	 		   Alert alert= new Alert(AlertType.CONFIRMATION,"Participant insere",javafx.scene.control.ButtonType.OK);
	 				alert.showAndWait();
	 	 		   
	 	    	}
	 	    	catch(Exception e){
	 	       		e.printStackTrace();
	 	       	}
	    	}
	    	else {
	    		Alert alert= new Alert(AlertType.WARNING,"il faut donner tous le champs",javafx.scene.control.ButtonType.OK);
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
	    
	    
	    @FXML
	    void sign_out(MouseEvent event) {

	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			
			//connecting to the db
			cnx=DataBase.connecterBase();
			//displaying all formations
			
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
			
			//populating the profil combobox with domains from the db
			try {
				dom_st=cnx.prepareStatement("Select Libelle from profil");
				res_dom = dom_st.executeQuery();

		        while (res_dom.next()) {  // loop

		        	profil_fld.getItems().addAll(res_dom.getString("Libelle")); 
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
						
						// force the field prenom to start alphabetic
						mat_fld.textProperty().addListener(new ChangeListener<String>() {
						    @Override
						    public void changed(ObservableValue<? extends String> observable, String oldValue, 
						        String newValue) {
						        if (!newValue.matches("\\d*")) {
						        	mat_fld.setText(newValue.replaceAll("[^\\d]", ""));
						        }
						    }
						});
						
		}
}
