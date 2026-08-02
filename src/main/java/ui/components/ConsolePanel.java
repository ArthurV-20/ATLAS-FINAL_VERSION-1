package ui.components;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ConsolePanel extends VBox {

    private ScrollPane scrollPane;
    private TextFlow areaTextoFlow;
    private Button botaoLimpar;

    public ConsolePanel() {

        getStyleClass().add("console-container");

        HBox cabecalho = new HBox();
        cabecalho.setAlignment(Pos.CENTER_LEFT);
        cabecalho.getStyleClass().add("console-header");

        Label titulo = new Label("FLUXO DE ATIVIDADES");
        titulo.getStyleClass().add("console-title");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        botaoLimpar = new Button("// LIMPAR");
        botaoLimpar.getStyleClass().add("console-clear-btn");
        botaoLimpar.setOnAction(e -> limparConsole());

        cabecalho.getChildren().addAll(
                titulo,
                spacer,
                botaoLimpar
        );

        areaTextoFlow = new TextFlow();
        areaTextoFlow.setPadding(new Insets(5));
        areaTextoFlow.setLineSpacing(85);

        scrollPane = new ScrollPane(areaTextoFlow);
        scrollPane.getStyleClass().add("console-text-area");
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        scrollPane.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-background: #01080f;" +
                        "-fx-border-color: transparent;"
        );

        scrollPane.vvalueProperty().bind(areaTextoFlow.heightProperty());

        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        getChildren().addAll(
                cabecalho,
                scrollPane
        );
    }

    public void escreverLog(String mensagem, LogType tipo) {

        String horario = LocalTime.now().format(
                DateTimeFormatter.ofPattern("HH:mm:ss")
        );

        Text timestamp = new Text("[" + horario + "] ");
        timestamp.setFill(Color.web("#7BA7B6"));
        timestamp.setStyle(
                "-fx-font-family:'Consolas';" +
                        "-fx-font-size:12px;"
        );

        Text prefixo = new Text(getPrefixo(tipo));
        prefixo.setFill(getCor(tipo));
        prefixo.setStyle(
                "-fx-font-family:'Consolas';" +
                        "-fx-font-size:13px;" +
                        "-fx-font-weight:bold;"
        );

        Text conteudo = new Text("  " + mensagem + "\n");
        conteudo.setFill(Color.web("#C9D8E2"));
        conteudo.setStyle(
                "-fx-font-family:'Consolas';" +
                        "-fx-font-size:13px;"
        );

        Platform.runLater(() ->
                areaTextoFlow.getChildren().addAll(
                        timestamp,
                        prefixo,
                        conteudo
                )
        );
    }

    private String getPrefixo(LogType tipo) {

        return switch (tipo) {

            case SUCCESS -> "✔ EXECUTADO";
            case INFO -> "ℹ SISTEMA";
            case WARNING -> "⚠ ALERTA";
            case ERROR -> "✖ ERRO";
            case AI_RESPONSE -> "🧠 ATLAS";

        };

    }

    private Color getCor(LogType tipo) {

        return switch (tipo) {

            case SUCCESS -> Color.web("#32CD32");
            case INFO -> Color.web("#00D2FF");
            case WARNING -> Color.web("#FFD700");
            case ERROR -> Color.web("#FF4A4A");
            case AI_RESPONSE -> Color.web("#B36CFF");

        };

    }

    public void limparConsole() {

        Platform.runLater(() ->
                areaTextoFlow.getChildren().clear()
        );

    }

    public enum LogType {

        SUCCESS,
        INFO,
        WARNING,
        ERROR,
        AI_RESPONSE

    }
}