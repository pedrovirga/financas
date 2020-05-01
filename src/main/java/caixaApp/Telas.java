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


public class Telas {
    Scene login, principal, compromissos;
    Calculations calcFluxo;
    Stage primaryStage;

    //construtor das telas até agora feitas
    public Telas(Stage primaryStage)
    {
        calcFluxo = new Calculations();
        login = new Scene(telaLogin(primaryStage), 753, 377);
        principal = new Scene(basicConf(calcFluxo), 753, 377);
        principal.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());
        compromissos = new Scene(compScreen() , 753 , 377);
        compromissos.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());

        this.primaryStage = primaryStage;
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
              primaryStage.setScene(principal);
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

    public HBox SceneTransition(){
  		HBox hbox = new HBox();
      hbox.setPadding(new Insets(15, 12, 15, 12));
      hbox.setSpacing(10);
      hbox.setStyle("-fx-background-color: #336699;");
  		hbox.setAlignment(Pos.TOP_RIGHT);

      Button buttonCurrent = new Button("Caixa");
      buttonCurrent.setPrefSize(100, 20);

      buttonCurrent.setOnAction(new EventHandler<ActionEvent>(){
          @Override
          public void handle(ActionEvent event){
            primaryStage.setScene(principal);
          }
      });

      Button buttonProjected = new Button("Compromissos");
      buttonProjected.setPrefSize(100, 20);

      buttonProjected.setOnAction(new EventHandler<ActionEvent>(){
          @Override
          public void handle(ActionEvent event){
            primaryStage.setScene(compromissos);
          }
      });

      hbox.getChildren().addAll(buttonCurrent, buttonProjected);

      return hbox;
	 }

    //Tela de transacoes
    public GridPane basicConf(Calculations calculoFluxo) {

        /* Configura a tela */
        GridPane layout = new GridPane();

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

        layout.add(SceneTransition(), 0, 0, 6, 1); /* column ,  line , column to grow , line to grow */

        /* Configura o texto */
        Text title = new Text("Caixa");
        title.setId("title-text");
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
        layout.add(addValueField , 1 , 3);
        layout.add(subValueField , 1 , 4);
        layout.add(balanceValueField , 1 , 5);
        layout.add(descriptionValueField , 4 , 4 , 5 , 3);
    /*****************************************/

    /*               Buttons                 */
        layout.add(buttonAdd ,2,3);
        layout.add(buttonSub ,2,4);
    /*****************************************/

        return layout;
    }

    VBox sideToolbar(){
      VBox vbox = new VBox(20); /* spacing 20*/

      Button addButton = new Button();
      addButton.setShape(new Circle(30));
      addButton.setId("button-text-add");
      Button searchButton = new Button();
      searchButton.setShape(new Circle(30));
      searchButton.setId("side-search-button");

      vbox.getChildren().addAll(addButton, searchButton);

      return vbox;
    }


    GridPane listId(){
      GridPane listview = new GridPane();

      listview.setId("set-border");

      ColumnConstraints colOne = new ColumnConstraints(100, 100, 100);
      ColumnConstraints colTwo = new ColumnConstraints(200 , 200 , 200);
      ColumnConstraints colThree = new ColumnConstraints(100, 100, 100);
      ColumnConstraints colFour = new ColumnConstraints(100, 100, 100);

      /* set Priority to maintain grip size`s config */
      colOne.setHgrow(Priority.ALWAYS);
      colTwo.setHgrow(Priority.ALWAYS);
      colThree.setHgrow(Priority.ALWAYS);
      colFour.setHgrow(Priority.ALWAYS);

      listview.getColumnConstraints().addAll(colOne, colTwo, colThree, colFour);

      Label id = new Label("Id") , title = new Label("Titulo") ,
      date = new Label("Data") , value = new Label("Valor");

      id.setId("label-text");
      title.setId("label-text");
      date.setId("label-text");
      value.setId("label-text");

      listview.add(id, 0, 0);
      listview.add(title, 1, 0);
      listview.add(date, 2, 0);
      listview.add(value, 3, 0);

      listview.setHalignment(id, HPos.CENTER);
      listview.setHalignment(title, HPos.CENTER);
      listview.setHalignment(date, HPos.CENTER);
      listview.setHalignment(value, HPos.CENTER);

      Label id1 = new Label("Teste") , id2 = new Label() , id3 = new Label() ,
      title1 = new Label() , title2 = new Label() , title3 = new Label() ,
      date1 = new Label() , date2 = new Label() , date3 = new Label() ,
      value1 = new Label() , value2 = new Label() , value3 = new Label() ;

      listview.add(id1 , 0 , 1 );
      listview.add(id2 , 0 , 2 );
      listview.add(id3 , 0 , 3 );
      listview.add(title1 , 1 , 1 );
      listview.add(title2 , 1 , 2 );
      listview.add(title3 , 1 , 3 );
      listview.add(date1 , 2 , 1 );
      listview.add(date2 , 2 , 2 );
      listview.add(date3 , 2 , 3 );
      listview.add(value1 , 3 , 1 );
      listview.add(value2 , 3 , 2 );
      listview.add(value3 , 3 , 3 );

      return listview;
    }

    GridPane compScreen(){
      GridPane layout = new GridPane();
      layout.setPadding(new Insets(20, 20, 20, 20));
      //layout.setId("set-border");
      layout.add(SceneTransition(), 0, 0, 6, 1);

      ColumnConstraints colOne = new ColumnConstraints(30 , 30 , 30);
      ColumnConstraints colTwo = new ColumnConstraints(50 , 50 , 50);
      ColumnConstraints colThree = new ColumnConstraints(100, 100, 100); //id
      ColumnConstraints colFour = new ColumnConstraints(200 , 200 , 200); //tit
      ColumnConstraints colFive = new ColumnConstraints(100, 100, 100); //date
      ColumnConstraints colSix = new ColumnConstraints(100, 100, 100); //value

      /* set Priority to maintain grip size`s config */
      colOne.setHgrow(Priority.ALWAYS);
      colTwo.setHgrow(Priority.ALWAYS);
      colThree.setHgrow(Priority.ALWAYS);
      colFour.setHgrow(Priority.ALWAYS);
      colFive.setHgrow(Priority.ALWAYS);
      colSix.setHgrow(Priority.ALWAYS);

      layout.getColumnConstraints().addAll(colOne, colTwo, colThree , colFour ,
      colFive , colSix);

      Text title = new Text("Compromissos");
      title.setId("title-text");

      layout.add(title, 0, 1, 3, 1);
      layout.add(sideToolbar() , 1 , 3);
      layout.add(listId() , 2 , 3 , 4 , 3 );

      return layout;
    }

}
