package application;
	
import java.sql.Connection;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/interfaces/AdminFormateurs.fxml"));
			Scene scene = new Scene(root);
			scene.setFill(Color.TRANSPARENT);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.show();
			Connection cn = DataBase.connecterBase();
			Scanner sc = new Scanner(System.in);
			System.out.println("Le programme est terminé !");

			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
	
	}
	