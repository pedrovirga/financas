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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.io.*;

/*Botoẽs "Caixa" e "Compromissos"*/

public class UpperButtons {
    //Atributos
    HBox hbox;
    Button buttonCaixa;
    Button buttonCompromissos;

    public UpperButtons()
    {
        this.hbox = new HBox();

        this.buttonCaixa = new Button("Caixa");
        this.buttonCaixa.setPrefSize(100, 20);

        this.buttonCompromissos = new Button("Compromissos");
        this.buttonCompromissos.setPrefSize(100, 200);
    }

    /* @Description: It has buttons to perfom a scene transition */
    public void sceneTransition() {
        this.hbox.setPadding(new Insets(15, 12, 15, 12));
        this.hbox.setSpacing(10);
        this.hbox.setStyle("-fx-background-color: #336699;");
        this.hbox.setAlignment(Pos.TOP_RIGHT);

        this.buttonCaixa.setPrefSize(100, 20);
        this.buttonCaixa.setId("upperButton-text");

        this.buttonCompromissos.setPrefSize(100, 20);
        this.buttonCompromissos.setId("upperButton-text");

        this.hbox.getChildren().addAll(buttonCaixa, buttonCompromissos);

    }
}