package ui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.function.Consumer;

public class BottomInput extends HBox {

    private final TextField campoComando;
    private final Button btnEnviar;

    private Consumer<String> aoExecutarComando;

    public BottomInput() {

        getStyleClass().add("bottom-input-container");

        setAlignment(Pos.CENTER);
        setSpacing(50);

        campoComando = new TextField();
        campoComando.setPromptText("Inserir diretriz operacional...");
        campoComando.getStyleClass().add("comando-input");
        HBox.setHgrow(campoComando, Priority.ALWAYS);

        btnEnviar = new Button("ENVIAR");
        btnEnviar.getStyleClass().add("botao-enviar");

        btnEnviar.setFocusTraversable(false);

        btnEnviar.setOnAction(e -> despacharComando());
        campoComando.setOnAction(e -> despacharComando());

        getChildren().addAll(
                campoComando,
                btnEnviar
        );
    }

    private void despacharComando() {

        String comando = campoComando.getText().trim();

        if (comando.isEmpty()) {
            return;
        }

        if (aoExecutarComando != null) {
            aoExecutarComando.accept(comando);
        }

        campoComando.clear();
        campoComando.requestFocus();
    }

    public void setAoExecutarComando(Consumer<String> callback) {
        this.aoExecutarComando = callback;
    }

    public TextField getCampoComando() {
        return campoComando;
    }

    public Button getBtnEnviar() {
        return btnEnviar;
    }
}