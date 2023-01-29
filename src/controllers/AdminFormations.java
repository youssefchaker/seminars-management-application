package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import application.DataBase;
import application.Formation;
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

public class AdminFormations implements Initializable {
	
	//BD variables
		Connection cnx;
		public PreparedStatement st,dom_st,form_st,search_st;
		public ResultSet result,res_dom,res_form,res_search;
		public boolean result_add;
		
		//ScreenBuilder Elements
		private Parent fxml;
	
	 	@FXML
	    private Button add_btn;

	    @FXML
	    private TextField annee_fld;

	    @FXML
	    private TextField annee_mod_fld;

	    @FXML
	    private TextField code_del_fld;

	    @FXML
	    private TextField code_mod_fld;

	    @FXML
	    private Button del_btn;

	    @FXML
	    private ComboBox<String> dom_fld;

	    @FXML
	    private ComboBox<String> dom_mod_fld;

	    @FXML
	    private ComboBox<String> form_fld;

	    @FXML
	    private ComboBox<String> form_mod_fld;

	    @FXML
	    private TextField inti_fld;

	    @FXML
	    private TextField inti_mod_fld;

	    @FXML
	    private Button mod_btn;

	    @FXML
	    private TextField mois_fld;

	    @FXML
	    private TextField mois_mod_fld;

	    @FXML
	    private TextField nbj_fld;

	    @FXML
	    private TextField nbj_mod_fld;

	    @FXML
	    private TextField nbpart_fld;

	    @FXML
	    private TextField nbpart_mod_fld;

	    @FXML
	    private AnchorPane root;
	    
	  //table columns

	    @FXML
	    private TableColumn<Formation, Integer> tab_form;
	    
	    @FXML
	    private TableColumn<Formation, Integer> tab_code;

	    @FXML
	    private TableColumn<Formation, String> tab_inti;

	    @FXML
	    private TableColumn<Formation, Integer> tab_nbpart;
	    
	    @FXML
	    private TableColumn<Formation, Integer> tab_duree;
	    
	  //list to store each db entry
	    public ObservableList<Formation> data=FXCollections.observableArrayList();
	    
	  //the whole table element

	    @FXML
	    private TableView<Formation> table;

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
	    
	 // display all formations in db
	    
	    void displayFormations() {
	 	   table.getItems().clear();
	 	   String sql="select Code_formation,Intitulé,Nombre_jours,Formateur,Nombre_participants from formation";
	 	   try {
	 		   st=cnx.prepareStatement(sql);
	 		   result=st.executeQuery();
	 		   while(result.next()) {
	 				   data.add(new Formation(result.getInt("Code_formation"),result.getString("Intitulé"),result.getInt("Nombre_jours"),result.getInt("Formateur"),result.getInt("Nombre_participants")));
	 		   }
	 	   }
	 	   
	 	   catch(Exception e){
	    		e.printStackTrace();
	    	}
	 	   //setting the table values with each formation element from db
	 	   
	 	   tab_code.setCellValueFactory(new PropertyValueFactory<Formation, Integer>("Code"));
	 	   tab_inti.setCellValueFactory(new PropertyValueFactory<Formation, String>("Intitulé"));
	 	   tab_form.setCellValueFactory(new PropertyValueFactory<Formation, Integer>("code_form"));
	 	   tab_nbpart.setCellValueFactory(new PropertyValueFactory<Formation, Integer>("Nombre_participants"));
	 	   tab_duree.setCellValueFactory(new PropertyValueFactory<Formation, Integer>("Nombre_jours"));
	 	   table.setItems(data);
	    }
	    
	    
	    @FXML
	    //adding a formation to the db
	    void addFormation(MouseEvent event) {
	    	String inti=inti_fld.getText();
	    	String dom=dom_fld.getValue();
	    	String nbj=nbj_fld.getText();
	    	String mois=mois_fld.getText();
	    	String annee=annee_fld.getText();
	    	String nbpart=nbpart_fld.getText();
	    	String form=form_fld.getValue();
	    	
	    	String sql="insert into formation(Intitulé,Domaine,Nombre_jours,Annee,Mois,Formateur,Nombre_participants) values(?,(select Code_domaine from domaine where domaine.Libelle=?),?,?,?,(select Code_formateur from formateur where formateur.Nom=?),?)";
	    	if(!inti.equals("") && !dom.equals(null) && !nbj.equals("") && !mois.equals("") && !annee.equals("") && !nbpart.equals("") && !form.equals(null) ) {
	    		try {
	 	 		   st=cnx.prepareStatement(sql);
	 	 		   st.setString(1, inti);
	 	 		   st.setString(2, dom);
	 	 		   st.setString(3, nbj);
	 	 		   st.setString(4, annee);
	 	 		   st.setString(5, mois);
	 	 		   st.setString(6, form);
	 	 		   st.setString(7, nbpart);
	 	 		   result_add=st.execute();
	 	 		   inti_fld.setText("");
	 	 		   dom_fld.setValue(null);
	 	 		   nbj_fld.setText("");
	 	 		   mois_fld.setText("");
	 	 		   annee_fld.setText("");
	 	 		   nbpart_fld.setText("");
	 	 		   form_fld.setValue(null);
	 	 		   Alert alert= new Alert(AlertType.CONFIRMATION,"Formation Inserted with Success",javafx.scene.control.ButtonType.OK);
	 				alert.showAndWait();
	 	 		   	displayFormations();
	 	 		   
	 	    	}
	 	    	catch(Exception e){
	 	       		e.printStackTrace();
	 	       	}
	    	}
	    	else {
	    		Alert alert= new Alert(AlertType.WARNING,"Please fill in all fields",javafx.scene.control.ButtonType.OK);
 				alert.showAndWait();
	    	}
	    	
	    }
	    
	    @FXML
	    //update an existing formation in the db
	    void updateFormation(MouseEvent event) {
	    	String code=code_mod_fld.getText();
	    	String inti=inti_mod_fld.getText();
	    	String dom=dom_mod_fld.getValue();
	    	String nbj=nbj_mod_fld.getText();
	    	String mois=mois_mod_fld.getText();
	    	String annee=annee_mod_fld.getText();
	    	String nbpart=nbpart_mod_fld.getText();
	    	String form=form_mod_fld.getValue();
	    	String sql="update formation set Intitulé=?,Domaine=(select Code_domaine from domaine where domaine.Libelle=?),Nombre_jours=?,Annee=?,Mois=?,Formateur=(select Code_formateur from formateur where formateur.Nom=?),Nombre_participants=? where Code_formation='"+code+"'";
	    	if(!code.equals("") && !inti.equals("") && !dom.equals(null) && !nbj.equals("") && !mois.equals("") && !annee.equals("") && !nbpart.equals("") && !form.equals(null) ) {
	    		try {
	 	 		   st=cnx.prepareStatement(sql);
	 	 		   st.setString(1, inti);
	 	 		   st.setString(2, dom);
	 	 		   st.setString(3, nbj);
	 	 		   st.setString(4, annee);
	 	 		   st.setString(5, mois);
	 	 		   st.setString(6, form);
	 	 		   st.setString(7, nbpart);
	 	 		   st.execute();
	 	 		   inti_mod_fld.setText("");
	 	 		   dom_mod_fld.setValue(null);
	 	 		   nbj_mod_fld.setText("");
	 	 		   mois_mod_fld.setText("");
	 	 		   annee_mod_fld.setText("");
	 	 		   nbpart_mod_fld.setText("");
	 	 		   form_mod_fld.setValue(null);
	 	 		   Alert alert= new Alert(AlertType.CONFIRMATION,"Formation Updated with Success",javafx.scene.control.ButtonType.OK);
	 				alert.showAndWait();
	 	 		   	displayFormations();
	 	 		   
	 	    	}
	 	    	catch(Exception e){
	 	       		e.printStackTrace();
	 	       	}
	    	}
	    	else {
	    		Alert alert= new Alert(AlertType.WARNING,"Please fill in all fields",javafx.scene.control.ButtonType.OK);
 				alert.showAndWait();
	    	}
	    	
	    }
	    
	    //search for a formation using the formation_code
	    
	    @FXML
	    void searchFormation(MouseEvent event) {
	    	String code=code_mod_fld.getText();
	    	if(!code.equals("")) {
	    		String sql="select Code_formation,Intitulé,Domaine,Nombre_jours,Annee,Mois,Formateur,Nombre_participants from formation where Code_formation='"+code+"'";
		    	try {
		 		   search_st=cnx.prepareStatement(sql);
		 		   res_search=search_st.executeQuery();
		 		   if(res_search.next()) {
		 			   String sql_form="select Nom from formation,formateur where formation.Formateur=formateur.Code_formateur AND formation.Formateur='"+ res_search.getString("Formateur")+"'";
					   String sql_dom="select Libelle from formation, domaine where formation.Domaine=domaine.Code_domaine AND formation.Domaine='"+ res_search.getString("Domaine")+"'";
					   	form_st=cnx.prepareStatement(sql_form);
					   	res_form=form_st.executeQuery();
					   	dom_st=cnx.prepareStatement(sql_dom);
					   	res_dom=dom_st.executeQuery();
					   	res_form.next();
						res_dom.next();
		 			   	inti_mod_fld.setText(res_search.getString("Intitulé"));
		 		    	dom_mod_fld.setValue(res_dom.getString("Libelle"));
		 		    	nbj_mod_fld.setText(res_search.getString("Nombre_jours"));
		 		    	mois_mod_fld.setText(res_search.getString("Mois"));
		 		    	annee_mod_fld.setText(res_search.getString("Annee"));
		 		    	nbpart_mod_fld.setText(res_search.getString("Nombre_participants"));
		 		    	form_mod_fld.setValue(res_form.getString("Nom")); 
		 		   }
		 		   
		 		  else {
			    		Alert alert= new Alert(AlertType.ERROR,"No Formations found with this code",javafx.scene.control.ButtonType.OK);
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
	    
	    //delete a formation using the formation_code

	    @FXML
	    void deleteFormation(MouseEvent event) {
	    	String code=code_del_fld.getText();
	    	if(!code.equals("")) {
	    		String sql="delete from formation where Code_formation='"+code+"'";
		    	try {
		    		st=cnx.prepareStatement(sql);
		    		st.executeUpdate();
		    		Alert alert= new Alert(AlertType.CONFIRMATION,"Formation Deleted",javafx.scene.control.ButtonType.OK);
	 				alert.showAndWait();
	 				displayFormations();
	 				
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
			//displaying all formations
			displayFormations();
			LocalDateTime date = LocalDateTime.now();  
			
			//configuring the add fields
			
			//populating the domaine combobox with domains from the db
			try {
				dom_st=cnx.prepareStatement("Select Libelle from Domaine");
				res_dom = dom_st.executeQuery();

		        while (res_dom.next()) {  // loop

		        	dom_fld.getItems().addAll(res_dom.getString("Libelle")); 
		       }
			}
			
			catch(Exception e){
 	       		e.printStackTrace();
 	       	}
			
			//populating the formateur combobox with formateurs from the db
			try {
				form_st=cnx.prepareStatement("Select Nom from Formateur");
				res_form = form_st.executeQuery();

		        while (res_form.next()) {  // loop

		        	form_fld.getItems().addAll(res_form.getString("Nom")); 
		       }
			}
			
			catch(Exception e){
 	       		e.printStackTrace();
 	       	}
			
			// force the field nbjours to be numeric only
			nbj_fld.textProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, 
			        String newValue) {
			        if (!newValue.matches("\\d*")) {
			        	nbj_fld.setText(newValue.replaceAll("[^\\d]", ""));
			        }
			    }
			});
			
			// force the field mois to be numeric only and between 1 and 12
			mois_fld.textProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, 
			        String newValue) {
			        if (!newValue.matches("\\d*")) {
			        	mois_fld.setText(newValue.replaceAll("[^\\d]", ""));
			        }
			        if (Integer.parseInt(newValue)>12 ) {
			        	mois_fld.setText(newValue.substring(0,1));
			        }
			        if (Integer.parseInt(newValue)<1 ) {
			        	mois_fld.setText("1");
			        }
			    }
			});
			
			// force the field annee to be numeric only and only accept years < current year
			annee_fld.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) {
					if (!newValue.matches("\\d*")) {
			        	mois_fld.setText(newValue.replaceAll("[^\\d]", ""));
			        }
					if (Integer.parseInt(newValue)>date.getYear() ) {
						annee_fld.setText(Integer.toString(date.getYear()));
					}
					if (Integer.parseInt(newValue)<1 ) {
			        	mois_fld.setText("1");
			        }
				}
			});
			
			// force the field nb_participants to be numeric only and only accept numbers < 4 and >=0
			nbpart_fld.textProperty().addListener(new ChangeListener<String>() {
							@Override
							public void changed(ObservableValue<? extends String> observable, String oldValue, 
								String newValue) {
								if (!newValue.matches("\\d*")) {
									nbpart_fld.setText(newValue.replaceAll("[^\\d]", ""));
						        }
								if (Integer.parseInt(newValue)<4 ) {
									nbpart_fld.setText("4");
								}
							}
						});
			
			//configuring the update fields
			
			//populating the domaine combobox with domains from the db
			try {
				dom_st=cnx.prepareStatement("Select Libelle from Domaine");
				res_dom = dom_st.executeQuery();

		        while (res_dom.next()) {  // loop

		        	dom_mod_fld.getItems().addAll(res_dom.getString("Libelle")); 
		       }
			}
			
			catch(Exception e){
 	       		e.printStackTrace();
 	       	}
			
			//populating the formateur combobox with formateurs from the db
			try {
				form_st=cnx.prepareStatement("Select Nom from Formateur");
				res_form = form_st.executeQuery();

		        while (res_form.next()) {  // loop

		        	form_mod_fld.getItems().addAll(res_form.getString("Nom")); 
		       }
			}
			
			catch(Exception e){
 	       		e.printStackTrace();
 	       	}
			
			// force the field nbjours to be numeric only
			nbj_mod_fld.textProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, 
			        String newValue) {
			        if (!newValue.matches("\\d*")) {
			        	nbj_mod_fld.setText(newValue.replaceAll("[^\\d]", ""));
			        }
			    }
			});
			
			// force the field mois to be numeric only and between 1 and 12
			mois_mod_fld.textProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, 
			        String newValue) {
			        if (!newValue.matches("\\d*")) {
			        	mois_mod_fld.setText(newValue.replaceAll("[^\\d]", ""));
			        }
			        if (Integer.parseInt(newValue)>12 ) {
			        	mois_mod_fld.setText(newValue.substring(0,1));
			        }
			        if (Integer.parseInt(newValue)<1 ) {
			        	mois_mod_fld.setText("1");
			        }
			    }
			});
			
			// force the field annee to be numeric only and only accept years < current year
			annee_mod_fld.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) {
					if (!newValue.matches("\\d*")) {
						annee_mod_fld.setText(newValue.replaceAll("[^\\d]", ""));
			        }
					if (Integer.parseInt(newValue)>date.getYear() ) {
						annee_mod_fld.setText(Integer.toString(date.getYear()));
					}
					if (Integer.parseInt(newValue)<1 ) {
			        	annee_mod_fld.setText("1");
			        }
				}
			});
			
			// force the field nb_participants to be numeric only and only accept numbers < 4 and >=0
			nbpart_mod_fld.textProperty().addListener(new ChangeListener<String>() {
							@Override
							public void changed(ObservableValue<? extends String> observable, String oldValue, 
								String newValue) {
								if (!newValue.matches("\\d*")) {
									nbpart_mod_fld.setText(newValue.replaceAll("[^\\d]", ""));
						        }
								if (Integer.parseInt(newValue)>4 ) {
									nbpart_mod_fld.setText("4");
								}
								if (Integer.parseInt(newValue)<0 ) {
									nbpart_mod_fld.setText("0");
						        }
							}
						});
			
			
			
			
			
		}
}
