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

		Calculations c = new Calculations();
		Scene scene = new Scene(basicConf(c) , 753 , 377);
		primaryStage.setTitle("Caixa");
		primaryStage.setScene(scene);
		scene.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());

		primaryStage.show();
	}

	public GridPane basicConf( Calculations c){

		/* Configura a tela */
		GridPane g = new GridPane();
		g.setHalignment(g , HPos.CENTER);
		g.setHgap(10);
		g.setVgap(10);
		g.setPadding(new Insets(20 , 20 , 20 , 20)); /*Mudar a posicao*/

		/* Configura o texto */
		Text t = new Text("Caixa");
		t.setId("caixa-text");
		g.add(t , 0 , 0 , 2 , 1);

		Label l = new Label("Adicionar o valor:");
		l.setId("label-text");
		g.add(l , 0 , 1); /* coluna 0 e linha 1*/

		TextField tf = new TextField();

		Label ll = new Label("Retirar o valor:");
		ll.setId("label-text");
		g.add(ll , 0 , 2);

		TextField tff = new TextField();

		Label lll = new Label("Saldo:");
		lll.setId("label-text");
		g.add(lll , 0 , 3);

		TextField tfff = new TextField();
		tfff.setEditable(false);
		tfff.setText(c.getSaldo());

		tf.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				System.out.println("O valor adicionado " + tf.getText());
				c.addValor(tf.getText());
				tfff.setText(c.getSaldo());
				tf.clear();
			}
		});

		tff.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				System.out.println("O valor retirado " + tff.getText());
				c.removeValor(tff.getText());
				tfff.setText(c.getSaldo());
				tff.clear();
			}
		});

		g.add(tf , 1 , 1);
		g.add(tff , 1 , 2);
		g.add(tfff , 1 , 3);

		return g;
	}

	private class Calculations{

		BigDecimal valor, saldo;
		File log = new File("." , "log.txt");
		App manLog = new App(log);

		String debtexto = "O valor Debitado foi: ";
		String credtexto = "O valor Creditado foi: ";
		String saldotexto = "O Saldo e: ";

		Calculations(){
			String texto = "O Saldo e: ";
			String saldoTexto = manLog.readLineSaldo();
			if(saldoTexto == "0"){ /*o arquivo de log nao existe */
				this.saldo = new BigDecimal("0");
				manLog.writeTextoLog(saldotexto + "0" + "\n");
			}
			else{
				String tirarTexto = saldoTexto.substring(saldoTexto.indexOf(texto) + texto.length());
				String[] valorSaldo = tirarTexto.split(":");
				this.saldo = new BigDecimal(valorSaldo[0].replaceAll("(\\r|\\n)" , ""));
			}
		}

		public void addValor(String valor){
			this.valor = new BigDecimal(valor);
			this.saldo = this.saldo.add(this.valor);
			System.out.println(credtexto + this.valor);
			System.out.println(saldotexto + this.saldo);
			if(manLog.writeTextoLog(credtexto + this.valor + "\n"))
				System.out.println("Credito foi para o log.");
			if(manLog.writeTextoLog(saldotexto + this.saldo + "\n"))
				System.out.println("Atualizou Saldo log.");
		}

		public void removeValor(String valor){
			this.valor = new BigDecimal(valor);
			this.saldo = this.saldo.subtract(this.valor);
			System.out.println(debtexto + this.valor);
			System.out.println(saldotexto + this.saldo);
			if(manLog.writeTextoLog(debtexto + this.valor + "\n"))
				System.out.println("Debito foi para o log.");
			if(manLog.writeTextoLog(saldotexto + this.saldo + "\n"))
				System.out.println("Atualizou Saldo log.");
		}

		public String getSaldo(){
			return this.saldo.toString();
		}
	}

	public static void main(String[] args){
		launch(args);
	}
}
