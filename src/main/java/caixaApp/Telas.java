package caixaApp;

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
import javafx.scene.shape.Circle;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.io.*;


public class Telas extends UpperButtons{
    Scene login, principal;
    Calculations calcFluxo;

    //construtor das telas até agora feitas
    public Telas(Stage primaryStage)
    {
        calcFluxo = new Calculations();
        this.login = new Scene(telaLogin(primaryStage), 753, 377);
        this.principal = new Scene(basicConf(calcFluxo), 753, 377);
    }


    //Tela de login
    public GridPane telaLogin(Stage primaryStage)
    {
        //Variaveis da tela de login
        GridPane layout = new GridPane();
        Text title = new Text("Bem vindo!");
        Label username = new Label("Login:");
        TextField userTextField = new TextField();
        Label passwrd = new Label("Senha:");
        PasswordField passwrdField = new PasswordField();
        Button btnSign = new Button("Sign in");
        HBox hbBtnSign = new HBox(10);
        Button btnLogin = new Button("Login");
        HBox hbBtnLogin = new HBox(10);

        //ajustes de tela
        layout.setAlignment(Pos.CENTER);
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(15, 15, 15, 15));
        layout.add(title, 0, 0, 2, 1);
        layout.add(username, 0, 1);
        layout.add(userTextField, 1, 1);
        layout.add(passwrd, 0, 2);
        layout.add(passwrdField, 1, 2);
        hbBtnSign.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtnLogin.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtnSign.getChildren().add(btnSign);
        hbBtnSign.getChildren().add(btnLogin);
        layout.add(hbBtnSign, 1, 4);
        layout.add(hbBtnLogin, 0, 4);

        //Acionamento dos botoes
        btnLogin.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event)
            {
                /*Ainda falta configurar a parte de acesso ao arquivo para login*/
                Stage telaTransacao = new Stage();
                telaTransacao.setTitle("Caixa");
                Scene principal = new Scene(basicConf(calcFluxo), 753, 377);
                telaTransacao.setScene(principal);
                principal.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());
                telaTransacao.show();
                primaryStage.hide();
            }
        });

        /*btnSign.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event)
            {

            }
        }*/

        return layout;
    }

    //Tela de transacoes
    public GridPane basicConf(Calculations calculoFluxo) {

        /* Configura a tela */
        GridPane layout = new GridPane();
        UpperButtons upperBtn = new UpperButtons();
        upperBtn.sceneTransition();

        layout.setHalignment(layout, HPos.CENTER);
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(20, 20, 20, 20));

        /* Ajust size of grids */
        ColumnConstraints colOne = new ColumnConstraints();
        ColumnConstraints colTwo = new ColumnConstraints();
        ColumnConstraints colThree = new ColumnConstraints();
        ColumnConstraints colFour = new ColumnConstraints(50, 50, 50);
        ColumnConstraints colFive = new ColumnConstraints(200, 200, 200);

        /* set Priority to maintain grip size`s config */
        colFour.setHgrow(Priority.ALWAYS);
        colFive.setHgrow(Priority.ALWAYS);

        layout.getColumnConstraints().addAll(colOne, colTwo, colThree, colFour, colFive);

        layout.add(upperBtn.hbox, 0, 0, 6, 1); /* column ,  line , line to grow , column to grow */

        /* Configura o texto */
        Text title = new Text("Caixa");
        title.setId("caixa-text");
        layout.add(title, 0, 1, 2, 1);

        /******************************************/
        /*Adicionar valor*/
        Label addValueText = new Label("Adicionar o valor");
        addValueText.setId("label-text");
        layout.add(addValueText, 0, 3); /* coluna 0 e linha 2*/

        TextField addValueField = new TextField();
        addValueField.setText("0");

        Button buttonAdd = new Button();
        buttonAdd.setShape(new Circle(30));
        buttonAdd.setId("button-text-add");
        /******************************************/

        /********************************************/
        /*Retirar valor*/
        Label subValueText = new Label("Retirar o valor");
        subValueText.setId("label-text");
        layout.add(subValueText, 0, 4);
        layout.setHalignment(subValueText, HPos.RIGHT);

        TextField subValueField = new TextField();
        subValueField.setText("0");

        Button buttonSub = new Button();
        buttonSub.setShape(new Circle(30));
        buttonSub.setId("button-text-sub");
        /*******************************************/

        /****************************************/
        /*Saldo*/
        Label balanceValueText = new Label("Saldo");
        balanceValueText.setId("label-text");
        layout.add(balanceValueText, 0, 5);
        layout.setHalignment(balanceValueText, HPos.RIGHT);

        TextField balanceValueField = new TextField();
        balanceValueField.setEditable(false);
        balanceValueField.setText(calculoFluxo.getSaldo());
        /******************************************/

        /***************************************/
        /*Descricao*/
        Label descriptionValueText = new Label("Descricao");
        descriptionValueText.setId("label-text");
        layout.add(descriptionValueText, 4, 3);
        layout.setHalignment(descriptionValueText, HPos.CENTER);

        TextField descriptionValueField = new TextField();
        descriptionValueField.setMinSize(100, 100);
        descriptionValueField.setPrefSize(150, 150);
        descriptionValueField.setMaxSize(200, 200);
        /*******************************************/

        /*******************************************/
        /*Acionamento dos botões*/
        buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //converte o texto para numerico
                if (Integer.parseInt(addValueField.getText()) != 0) {
                    System.out.println("O valor adicionado " + addValueField.getText());
                    calculoFluxo.addValor(addValueField.getText());
                    balanceValueField.setText(calculoFluxo.getSaldo());
                    addValueField.clear();
                } else {
                    //Dialog
                    Alert errorAdd = new Alert(Alert.AlertType.ERROR);
                    errorAdd.setTitle("ERRO");
                    errorAdd.setHeaderText("Ocorreu um erro na transação");
                    errorAdd.setContentText("Valor não mencionado. Por favor, informe um valor");

                    errorAdd.showAndWait();
                }
                //valor 0 para o campo
                addValueField.setText("0");
            }
        });

        buttonSub.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //converte o texto para numerico
                if (Integer.parseInt(subValueField.getText()) != 0) {
                    System.out.println("O valor retirado " + subValueField.getText());
                    calculoFluxo.removeValor(subValueField.getText());
                    balanceValueField.setText(calculoFluxo.getSaldo());
                    subValueField.clear();
                } else {
                    //dialog
                    Alert errorSub = new Alert(Alert.AlertType.ERROR);
                    errorSub.setTitle("ERRO");
                    errorSub.setHeaderText("Ocorreu um erro na transação");
                    errorSub.setContentText("Valor não mencionado. Por favor, informe um valor");

                    errorSub.showAndWait();

                }
                //valor 0 para o campo
                subValueField.setText("0");
            }
        });
        /************************************************/
    /*              TextFields               */
        layout.add(addValueField ,1,3);
        layout.add(subValueField ,1,4);
        layout.add(balanceValueField ,1,5);
        layout.add(descriptionValueField,4,4,5,3);
    /*****************************************/

    /*               Buttons                 */
        layout.add(buttonAdd ,2,3);
        layout.add(buttonSub ,2,4);
    /*****************************************/

        return layout;
    }


}
