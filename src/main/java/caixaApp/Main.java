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

	public HBox SceneTransition(){
		HBox hbox = new HBox();
    hbox.setPadding(new Insets(15, 12, 15, 12));
    hbox.setSpacing(10);
    hbox.setStyle("-fx-background-color: #336699;");

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
		layout.setPadding(new Insets(20 , 20 , 20 , 20)); /*Mudar a posicao*/

		ColumnConstraints colOne = new ColumnConstraints();
		ColumnConstraints colTwo = new ColumnConstraints();
		ColumnConstraints colThree = new ColumnConstraints(50 , 50 , 50);
		/* was necessary to separate one column to adust*/
		colThree.setHgrow(Priority.ALWAYS);
		layout.getColumnConstraints().addAll(colOne , colTwo , colThree);

		layout.add(hbox , 0 , 0 , 4 , 1); /* coluna ,  linha , linhas , colunas */

		/* Configura o texto */
		Text title = new Text("Caixa");
		title.setId("caixa-text");
		layout.add(title , 0 , 1 , 2 , 1);

		Label addValueText = new Label("Adicionar o valor");
		addValueText.setId("label-text");
		layout.add(addValueText , 0 , 3); /* coluna 0 e linha 2*/

		TextField addValueField = new TextField();

		Label subValueText = new Label("Retirar o valor");
		subValueText.setId("label-text");
		layout.add(subValueText , 0 , 4);
		layout.setHalignment(subValueText, HPos.RIGHT);

		TextField subValueField = new TextField();

		Label balanceValueText = new Label("Saldo");
		balanceValueText.setId("label-text");
		layout.add(balanceValueText , 0 , 5);
		layout.setHalignment(balanceValueText, HPos.RIGHT);

		TextField balanceValueField = new TextField();
		balanceValueField.setEditable(false);
		balanceValueField.setText(calculoFluxo.getSaldo());

		Label descriptionValueText = new Label("Descricao");
		descriptionValueText.setId("label-text");
		layout.add(descriptionValueText , 3 , 3);

		TextField descriptionValueField = new TextField();
		descriptionValueField.setMinSize(250,100);


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

		layout.add(addValueField , 1 , 3);
		layout.add(subValueField , 1 , 4);
		layout.add(balanceValueField , 1 , 5);
		layout.add(descriptionValueField, 3 , 4 , 2 , 2);

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
