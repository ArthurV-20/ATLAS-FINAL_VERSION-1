package ui.components;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TopBar extends HBox {

    private Button voiceButton;
    private Button modeButton;

    private Text modelText;
    private Text clockText;

    public TopBar() {

        getStyleClass().add("top-bar");

        setAlignment(Pos.CENTER_LEFT);
        setSpacing(10);

        //========================================
        // ESQUERDA
        //========================================

        HBox leftBox = new HBox(8);
        leftBox.setAlignment(Pos.CENTER_LEFT);

        modeButton = new Button("MODO: NEXUS");
        modeButton.getStyleClass().add("mode-button");

        modelText = new Text("MODELO: Qwen 2.5 7B");
        modelText.getStyleClass().add("telemetry-text");

        leftBox.getChildren().addAll(
                modeButton,
                modelText
        );

        //========================================
        // CENTRO
        //========================================

        HBox centerBox = new HBox();
        centerBox.setAlignment(Pos.CENTER);

        Text titulo = new Text("ATLAS // TACTICAL INTERFACE");
        titulo.getStyleClass().add("title-main");

        centerBox.getChildren().add(titulo);

        //========================================
        // DIREITA
        //========================================

        HBox rightBox = new HBox(10);
        rightBox.setAlignment(Pos.CENTER_RIGHT);

        voiceButton = new Button("🎙 VOZ");
        voiceButton.getStyleClass().add("voice-button");

        clockText = new Text();
        clockText.getStyleClass().add("hud-clock");

        iniciarRelogio(clockText);

        rightBox.getChildren().addAll(
                voiceButton,
                clockText
        );

        //========================================
        // SPACERS
        //========================================

        Region spacerLeft = new Region();
        Region spacerRight = new Region();

        HBox.setHgrow(spacerLeft, Priority.ALWAYS);
        HBox.setHgrow(spacerRight, Priority.ALWAYS);

        getChildren().addAll(
                leftBox,
                spacerLeft,
                centerBox,
                spacerRight,
                rightBox
        );
    }

    public Button getVoiceButton() {
        return voiceButton;
    }

    private void iniciarRelogio(Text textoRelogio) {

        DateTimeFormatter formatador =
                DateTimeFormatter.ofPattern("HH:mm:ss");

        textoRelogio.setText(
                LocalTime.now().format(formatador)
        );

        Timeline clockTimeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        e -> textoRelogio.setText(
                                LocalTime.now().format(formatador)
                        )
                )
        );

        clockTimeline.setCycleCount(Animation.INDEFINITE);
        clockTimeline.play();
    }

    public void setMode(String mode) {
        modeButton.setText("MODO: " + mode);
    }

    public void setModel(String model) {
        modelText.setText("MODELO: " + model);
    }
}