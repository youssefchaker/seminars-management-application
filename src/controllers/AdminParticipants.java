package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import application.DataBase;
import application.Participant;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class AdminParticipants implements Initializable {
	
	//BD variables
		Connection cnx;
		public PreparedStatement st,pr_st,user_st,search_st;
		public ResultSet result,res_pr,res_user,res_search;
		public boolean result_add;
		
		//ScreenBuilder Elements
		private Parent fxml;

		@FXML
	    private Button add_btn;

	    @FXML
	    private TextField code_del_fld;

	    @FXML
	    private TextField code_mod_fld;

	    @FXML
	    private DatePicker date_fld;

	    @FXML
	    private DatePicker date_mod_fld;

	    @FXML
	    private Button del_btn;

	    @FXML
	    private TextField mart_fld;

	    @FXML
	    private Button mod_btn;

	    @FXML
	    private TextField nom_fld;

	    @FXML
	    private TextField nom_mod_fld;

	    @FXML
	    private ComboBox<String> pr_fld;

	    @FXML
	    private ComboBox<String> pr_mod_fld;

	    @FXML
	    private TextField prenom_fld;

	    @FXML
	    private TextField prenom_mod_fld;

	    @FXML
	    private AnchorPane root;

	    @FXML
	    private Button search_btn;

	    @FXML
	    private TableColumn<Participant, Integer> tab_code;

	    @FXML
	    private TableColumn<Participant, Date> tab_nais;

	    @FXML
	    private TableColumn<Participant, Integer> tab_pr;

	    @FXML
	    private TableColumn<Participant, Integer> tab_user;

	    @FXML
	    private ComboBox<String> user_fld;

	    @FXML
	    private ComboBox<String> user_mod_fld;

	    

	  //list to store each db entry
	    public ObservableList<Participant> data=FXCollections.observableArrayList();
	    
	  //the whole table element

	    @FXML
	    private TableView<Participant> table;

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
	    
	 // display all participants in db
	    
	    void displayParticipants() {
	 	   table.getItems().clear();
	 	   String sql="select Matricule_participant ,Date_naissance,Profil ,Utilisateur from participant";
	 	   try {
	 		   st=cnx.prepareStatement(sql);
	 		   result=st.executeQuery();
	 		   while(result.next()) {
	 				   data.add(new Participant(result.getInt("Matricule_participant"),result.getDate("Date_naissance"),result.getInt("Profil"),result.getInt("Utilisateur")));
	 		   }
	 	   }
	 	   
	 	   catch(Exception e){
	    		e.printStackTrace();
	    	}
	 	   //setting the table values with each formation element from db
	 	   
	 	   tab_code.setCellValueFactory(new PropertyValueFactory<Participant, Integer>("Matricule_participant"));
	 	   tab_nais.setCellValueFactory(new PropertyValueFactory<Participant, Date>("Date_naissance"));
	 	   tab_user.setCellValueFactory(new PropertyValueFactory<Participant, Integer>("user_code"));
	 	   tab_pr.setCellValueFactory(new PropertyValueFactory<Participant, Integer>("profil_code"));
	 	   table.setItems(data);
	    }
	    
	    
	    @FXML
	    //adding a participant to the db
	    void addParticipant(MouseEvent event) {
	    	String cod_mart=mart_fld.getText();
	    	String nom=nom_fld.getText();
	    	String prenom=prenom_fld.getText();
	    	String user=user_fld.getValue();
	    	String profil=pr_fld.getValue();
	    	LocalDate date=date_fld.getValue();
	    	
	    	String sql="insert into participant(Matricule_participant ,Nom,Prenom,Date_naissance,Profil,Utilisateur ) values(?,?,?,?,(select Code_profil  from profil where profil.Libelle=?),(select Code_utilisateur from utilisateur where utilisateur.Login=?))";
	    	if(!nom.equals("") && !user.equals(null) && !prenom.equals("") && !profil.equals(null) && !cod_mart.equals("") && !date.equals(null) && cod_mart.length()==8 ) {
	    		try {
	 	 		   st=cnx.prepareStatement(sql);
	 	 		   st.setString(1, cod_mart);
	 	 		   st.setString(2, nom);	
	 	 		   st.setString(3, prenom);
	 	 		   st.setDate(4,java.sql.Date.valueOf(date));
	 	 		   st.setString(5, profil);
	 	 		   st.setString(6, user);
	 	 		   result_add=st.execute();
	 	 		   nom_fld.setText("");
	 	 		   mart_fld.setText("");
	 	 		   prenom_fld.setText("");
	 	 		   user_fld.setValue(null);
	 	 		   pr_fld.setValue(null);
	 	 		   date_fld.setValue(null);
	 	 		   Alert alert= new Alert(AlertType.CONFIRMATION,"Participant Inserted with Success",javafx.scene.control.ButtonType.OK);
	 				alert.showAndWait();
	 				displayParticipants();
	 	 		   
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
	    
	    @FXML
	    //update an existing participant in the db
	    void updateParticipant(MouseEvent event) {
	    	String cod_mart=code_mod_fld.getText();
	    	String nom=nom_mod_fld.getText();
	    	String prenom=prenom_mod_fld.getText();
	    	String user=user_mod_fld.getValue();
	    	String profil=pr_mod_fld.getValue();
	    	LocalDate date=date_mod_fld.getValue();
	    	String sql="update participant set Nom=?,Prenom=?, Date_naissance=?,Profil=(select Code_profil from profil where profil.Libelle=?),Utilisateur=(select Code_utilisateur from utilisateur where utilisateur.Login=?) where Matricule_participant='"+cod_mart+"'";
	    	if(!nom.equals("") && !user.equals(null) && !prenom.equals("") && !profil.equals(null) && !cod_mart.equals("") && !date.equals(null) ) {
	    		try {
	    			st=cnx.prepareStatement(sql);
		 	 		   st.setString(1, nom);	
		 	 		   st.setString(2, prenom);
		 	 		   st.setDate(3,java.sql.Date.valueOf(date));
		 	 		   st.setString(4, profil);
		 	 		   st.setString(5, user);
		 	 		   result_add=st.execute();
		 	 		   nom_mod_fld.setText("");
		 	 		   code_mod_fld.setText("");
		 	 		   prenom_mod_fld.setText("");
		 	 		   user_mod_fld.setValue(null);
		 	 		   pr_mod_fld.setValue(null);
		 	 		   date_mod_fld.setValue(null);
	 	 		   Alert alert= new Alert(AlertType.CONFIRMATION,"Participant Updated with Success",javafx.scene.control.ButtonType.OK);
	 				alert.showAndWait();
	 	 		   	displayParticipants();
	 	 		   
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
	    
	    //search for a participant using the Matricule_participant 
	    
	    @FXML
	    void searchParticipant(MouseEvent event) {
	    	String cod_mart=code_mod_fld.getText();
	    	if(!cod_mart.equals("")) {
	    		String sql="select Nom,Prenom,Date_naissance,Profil ,Utilisateur from participant where Matricule_participant='"+cod_mart+"'";
		    	try {
		 		   search_st=cnx.prepareStatement(sql);
		 		   res_search=search_st.executeQuery();
		 		   if(res_search.next()) {
					   String sql_pr="select Libelle from profil, participant where participant.Profil=Profil.Code_profil  AND participant.Profil='"+ res_search.getString("Profil")+"'";
					   String sql_user="select Login from utilisateur, participant where participant.Utilisateur=utilisateur.Code_utilisateur AND participant.Utilisateur='"+ res_search.getString("Utilisateur")+"'";
					   	pr_st=cnx.prepareStatement(sql_pr);
					   	res_pr=pr_st.executeQuery();
						user_st=cnx.prepareStatement(sql_user);
					   	res_user=user_st.executeQuery();
						res_user.next();
						res_pr.next();
						nom_mod_fld.setText(res_search.getString("Nom"));
			 	 		prenom_mod_fld.setText(res_search.getString("Prenom"));
			 	 		user_mod_fld.setValue(res_user.getString("Login"));
			 	 		pr_mod_fld.setValue(res_pr.getString("Libelle"));
			 	 		date_mod_fld.setValue(res_search.getDate("Date_naissance").toLocalDate());
		 		   }
		 		   
		 		  else {
			    		Alert alert= new Alert(AlertType.ERROR,"No Particpants found with this code",javafx.scene.control.ButtonType.OK);
		 				alert.showAndWait();
			    	}
		 	   }
		 	   
		 	   catch(Exception e){
		    		e.printStackTrace();
		    	}
	    	}
	    	else {
	    		Alert alert= new Alert(AlertType.WARNING,"Please Type In a Valid Marticule",javafx.scene.control.ButtonType.OK);
 				alert.showAndWait();
	    	}
	    	
	    }
	    
	    //delete a participant using the Matricule_participant 

	    @FXML
	    void deleteParticipant(MouseEvent event) {
	    	String cod_mart=code_del_fld.getText();
	    	if(!cod_mart.equals("")) {
	    		String sql="delete from participant where Matricule_participant ='"+cod_mart+"'";
		    	try {
		    		st=cnx.prepareStatement(sql);
		    		st.executeUpdate();
		    		Alert alert= new Alert(AlertType.CONFIRMATION,"Participant Deleted",javafx.scene.control.ButtonType.OK);
	 				alert.showAndWait();
	 				displayParticipants();
	 				
		    	}
		    	catch(Exception e){
		    		e.printStackTrace();
		    	}
	    	}
	    	else {
	    		Alert alert= new Alert(AlertType.WARNING,"Please Type In a Valid Marticule",javafx.scene.control.ButtonType.OK);
 				alert.showAndWait();
	    	}
	    	
	    	
	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			String user="user";
			
			//connecting to the db
			cnx=DataBase.connecterBase();
			//displaying all formations
			displayParticipants();
			
			//configuring the add fields
			
			//populating the profil combobox with profils from the db
			try {
				pr_st=cnx.prepareStatement("Select Libelle from Profil");
				res_pr = pr_st.executeQuery();

		        while (res_pr.next()) {  // loop

		        	pr_fld.getItems().addAll(res_pr.getString("Libelle")); 
		       }
			}
			
			catch(Exception e){
 	       		e.printStackTrace();
 	       	}
			
			//populating the user combobox with users from the db
			try {
				user_st=cnx.prepareStatement("Select Login from utilisateur where utilisateur.Role='"+user+"'");
				res_user = user_st.executeQuery();

		        while (res_user.next()) {  // loop

		        	user_fld.getItems().addAll(res_user.getString("Login")); 
		       }
			}
			
			catch(Exception e){
 	       		e.printStackTrace();
 	       	}
			
			
			
			
			// force the field Code_mart to be numeric only
			mart_fld.textProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, 
			        String newValue) {
			        if (!newValue.matches("\\d*")) {
			        	mart_fld.setText(newValue.replaceAll("[^\\d]", ""));
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
			
			//configuring the update fields

						//populating the profil combobox with profils from the db
						try {
							pr_st=cnx.prepareStatement("Select Libelle from profil");
							res_pr = pr_st.executeQuery();

					        while (res_pr.next()) {  // loop

					        	pr_mod_fld.getItems().addAll(res_pr.getString("Libelle")); 
					       }
						}
						
						catch(Exception e){
			 	       		e.printStackTrace();
			 	       	}
						
						//populating the user combobox with users from the db
						try {		
							user_st=cnx.prepareStatement("Select Login from utilisateur where utilisateur.Role='"+user+"'");
							res_user = user_st.executeQuery();

					        while (res_user.next()) {  // loop

					        	user_mod_fld.getItems().addAll(res_user.getString("Login")); 
					       }
						}
						
						catch(Exception e){
			 	       		e.printStackTrace();
			 	       	}
						
						// force the field nom to start alphabetic
						nom_mod_fld.textProperty().addListener(new ChangeListener<String>() {
							@Override
							public void changed(ObservableValue<? extends String> observable, String oldValue, 
								String newValue) {
								if (newValue.matches("^[0-9]*$")) {
									 nom_mod_fld.setText(newValue.replaceAll("^[0-9]*$", ""));
								}
							}
						});
						
						// force the field prenom to start alphabetic
									prenom_mod_fld.textProperty().addListener(new ChangeListener<String>() {
										@Override
										public void changed(ObservableValue<? extends String> observable, String oldValue, 
											String newValue) {
											if (newValue.matches("^[0-9]*$")) {
												 prenom_mod_fld.setText(newValue.replaceAll("^[0-9]*$", ""));
											}
										}
									});
		}
}
