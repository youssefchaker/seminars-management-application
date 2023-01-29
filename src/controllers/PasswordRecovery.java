package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import application.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

public class PasswordRecovery implements Initializable {
	
		//BD variables
		Connection cnx;
		public PreparedStatement st;
		public ResultSet result;
		
		//ScreenBuilder Elements
		
		
		private Parent fxml;
		@FXML
	    private VBox Vbox;

	    @FXML
	    private Button mail_btn;

	    @FXML
	    private TextField email_fld;
	    
	    
	    @FXML
	    void sendMail() {
	    	//get user mail adresse
	    	final String email=email_fld.getText();
	    	if(!email.equals("") && email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
	    		String sql="select Password from utilisateur where Login='"+email +"'";
	    		try {
	    			st=cnx.prepareStatement(sql);
	    			result=st.executeQuery();
	    			if(result.next()) {
	    				final String pass=result.getString("Password");
	    				prepareMail(email,pass);
	    			}
	    			else {
	    				Alert alert= new Alert(AlertType.WARNING,"No account found with this email",javafx.scene.control.ButtonType.OK);
	     				alert.showAndWait();
	     				email_fld.setText("");
	    			}
	    		}
	    		catch(Exception e){
		    		e.printStackTrace();
		    		
		    	}
	    	}
	    	else {
	    		Alert alert= new Alert(AlertType.WARNING,"Please fill in a valid email",javafx.scene.control.ButtonType.OK);
 				alert.showAndWait();
	    	}
	    	
	    }


	    void prepareMail(String mail,String pass) {
	    	Properties prop=new Properties();
	    	
	    	//enable authentication
	    	prop.put("mail.smtp.auth", "true");
	    	//set TLS encryption enabled
	    	prop.put("mail.smtp.starttls.enable", "true");
	    	//set SMTP host
	    	prop.put("mail.smtp.host", "smtp.gmail.com");
	    	//set SMTP port
	    	prop.put("mail.smtp.port", "567");
	    	
	    	//my gmail @ 
	    	final String myEmail="thebestgamer352@gmail.com";
	    	//my gmail password
	    	final String myPassword="egg852123789";
	    	
	    				// create a session with account information
	    				Session session =Session.getInstance(prop,new Authenticator() {
	    					protected javax.mail.PasswordAuthentication getPasswordAuthentication(){
	    						return new javax.mail.PasswordAuthentication(myEmail,myPassword);
	    						
	    					}
	    				});
		    			Message message=preparedMessage(session,myEmail,mail,pass);
		    			//send mail
		    			try {
		    				Transport.send(message);
		    				Alert alert= new Alert(AlertType.CONFIRMATION,"Email has been sent",javafx.scene.control.ButtonType.OK);
		    				alert.showAndWait();
		    			}
		    			catch(Exception e){
				    		e.printStackTrace();
				    		
				    	}
		    			try {
		    	       		fxml=FXMLLoader.load(getClass().getResource("/interfaces/SignIn.fxml"));
		    	       		Vbox.getChildren().removeAll();
		    	       		Vbox.getChildren().setAll(fxml);
		    	       	}
		    	       	catch(Exception e){
		    	       		e.printStackTrace();
		    	       		
		    	       	}

	    			
	    			
	    		}
	    		    		
	    //prepare email message
	    
		private Message preparedMessage(Session session,String email,String recep,String pass) {
			String text="your password is : "+pass+"";
			String objet="Password Recovery";
			Message message=new MimeMessage(session);
			try {
				message.setFrom(new InternetAddress(email));
				message.setRecipient(Message.RecipientType.TO,new InternetAddress(recep));
				message.setSubject(objet);
				String htmlcode="<h1>"+text+"</h1> <h2><b></b></h2>";
				message.setContent(htmlcode,"text/html");
			}
			catch(Exception e){
	    		e.printStackTrace();
	    		
	    	}
			return message;
		}

	   


	//Initialising the page
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//connecting the db
		cnx=DataBase.connecterBase();
		
	}

}
