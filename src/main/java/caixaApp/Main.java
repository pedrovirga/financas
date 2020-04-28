package caixaApp;
/*
 Objetivo: testar o javafx
 Main.java
*/

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.text.*;


public class Main extends Application{

	@Override
	public void start(Stage primaryStage){

		Font.loadFont(getClass().getResourceAsStream("/Acme-Regular.ttf"), 30);

		//Calculations calculoFluxo = new Calculations();
		Telas telas = new Telas(primaryStage);
		//Scene login = new Scene(telas.login() , 753 , 377);
		primaryStage.setTitle("Finanças Master");
		primaryStage.setScene(telas.login);
		//scene1.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());

		primaryStage.show();

		//Scene scene = new Scene(  ,753 , 377);

	}

	public static void main(String[] args){
		launch(args);
	}
}
