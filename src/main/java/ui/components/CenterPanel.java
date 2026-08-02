package ui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CenterPanel extends VBox {

    private AtlasHud AtlasHud;


    public CenterPanel() {
        this.getStyleClass().add("center-panel");
        this.setAlignment(Pos.TOP_CENTER);
        this.setPadding(new Insets(20));
        this.setSpacing(2);
// Dentro de CenterPanel.java
        this.setAlignment(Pos.CENTER);
        // Inicializa o HUD (Coração da ATLAS)
        this.AtlasHud = new AtlasHud();
        VBox.setVgrow(this.AtlasHud, Priority.ALWAYS); // Força o HUD a usar todo o espaço vertical disponível

        // Inicializa a Console (Logs)
        this.getChildren().add(this.AtlasHud);
    }

    public AtlasHud getAtlasHud() {
        return this.AtlasHud;
    }

}