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

		Calculations calculoFluxo = new Calculations();
		Scene scene = new Scene(basicConf(calculoFluxo) , 753 , 377);
		primaryStage.setTitle("Caixa");
		primaryStage.setScene(scene);
		scene.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());

		primaryStage.show();

		//Scene scene = new Scene(  ,753 , 377);

	}
	/* @Description: It has buttons to perfom a scene transition */
	public HBox SceneTransition(){
		HBox hbox = new HBox();
    hbox.setPadding(new Insets(15, 12, 15, 12));
    hbox.setSpacing(10);
    hbox.setStyle("-fx-background-color: #336699;");
		hbox.setAlignment(Pos.TOP_RIGHT);

    Button buttonCurrent = new Button("Caixa");
    buttonCurrent.setPrefSize(100, 20);

    Button buttonProjected = new Button("Compromissos");
    buttonProjected.setPrefSize(100, 20);
    hbox.getChildren().addAll(buttonCurrent, buttonProjected);

    return hbox;
	}

	public GridPane basicConf(Calculations calculoFluxo){

		/* Configura a tela */
		GridPane layout = new GridPane();
		HBox hbox = SceneTransition();

		layout.setHalignment(layout , HPos.CENTER);
		layout.setHgap(10);
		layout.setVgap(10);
		layout.setPadding(new Insets(20 , 20 , 20 , 20));

		/* Ajust size of grids */
		ColumnConstraints colOne = new ColumnConstraints();
		ColumnConstraints colTwo = new ColumnConstraints();
		ColumnConstraints colThree = new ColumnConstraints();
		ColumnConstraints colFour = new ColumnConstraints(50 , 50 ,50);
		ColumnConstraints colFive = new ColumnConstraints(200 , 200 , 200);

		/* set Priority to maintain grip size`s config */
		colFour.setHgrow(Priority.ALWAYS);
		colFive.setHgrow(Priority.ALWAYS);

		layout.getColumnConstraints().addAll(colOne , colTwo , colThree , colFour , colFive);

		layout.add(hbox , 0 , 0 , 6 , 1); /* column ,  line , line to grow , column to grow */

		/* Configura o texto */
		Text title = new Text("Caixa");
		title.setId("caixa-text");
		layout.add(title , 0 , 1 , 2 , 1);

		Label addValueText = new Label("Adicionar o valor");
		addValueText.setId("label-text");
		layout.add(addValueText , 0 , 3); /* coluna 0 e linha 2*/

		TextField addValueField = new TextField();

		Button buttonAdd = new Button("Adicionar");
    buttonAdd.setId("button-text");

		Label subValueText = new Label("Retirar o valor");
		subValueText.setId("label-text");
		layout.add(subValueText , 0 , 4);
		layout.setHalignment(subValueText, HPos.RIGHT);

		TextField subValueField = new TextField();

		Button buttonSub = new Button("Retirar");
    buttonSub.setId("button-text");

		Label balanceValueText = new Label("Saldo");
		balanceValueText.setId("label-text");
		layout.add(balanceValueText , 0 , 5);
		layout.setHalignment(balanceValueText, HPos.RIGHT);

		TextField balanceValueField = new TextField();
		balanceValueField.setEditable(false);
		balanceValueField.setText(calculoFluxo.getSaldo());

		Label descriptionValueText = new Label("Descricao");
		descriptionValueText.setId("label-text");
		layout.add(descriptionValueText , 4 , 3);
		layout.setHalignment(descriptionValueText, HPos.CENTER);

		TextField descriptionValueField = new TextField();
		descriptionValueField.setMinSize(100 , 100);
		descriptionValueField.setPrefSize(150 , 150);
		descriptionValueField.setMaxSize(200 , 200);

		addValueField.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				System.out.println("O valor adicionado " + addValueField.getText());
				calculoFluxo.addValor(addValueField.getText());
				balanceValueField.setText(calculoFluxo.getSaldo());
				addValueField.clear();
			}
		});

		subValueField.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event){
				System.out.println("O valor retirado " + subValueField.getText());
				calculoFluxo.removeValor(subValueField.getText());
				balanceValueField.setText(calculoFluxo.getSaldo());
				subValueField.clear();
			}
		});

		/*              TextFields               */
		layout.add(addValueField , 1 , 3);
		layout.add(subValueField , 1 , 4);
		layout.add(balanceValueField , 1 , 5);
		layout.add(descriptionValueField, 4 , 4 , 5 , 3);
		/*****************************************/

		/*               Buttons                 */
		layout.add(buttonAdd , 2 , 3);
		layout.add(buttonSub , 2 , 4);
		/*****************************************/

		return layout;
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
