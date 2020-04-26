package caixaApp;
/*
 Objetivo: testar o javafx
 Main.java
*/

import manipulate.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.text.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.io.*;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage){

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
