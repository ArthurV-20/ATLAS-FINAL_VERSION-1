package ui.components;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LeftSideBar extends VBox {

    // Bloco 1: Navegação
    private Button btnDashboard;
    private Button btnConversas;
    private Button btnProjetos;
    private Button btnAutomacoes;
    private Button btnConfig;
    private Button btnSistema;

    // Bloco 2: ATLAS CORE
    private Text txtStatusValor;
    private Text txtUptimeValor;
    private Text txtModoValor;

    private long segundosAtivo = 0;

    public LeftSideBar() {

        setPrefWidth(220);
        setSpacing(25);
        setAlignment(Pos.TOP_LEFT);
        getStyleClass().add("left-sidebar");

        inicializarBlocoNavegacao();
        inicializarBlocoAtlasCore();

        iniciarUptime();
    }

    private void inicializarBlocoNavegacao() {

        VBox blocoNav = new VBox(8);
        blocoNav.setAlignment(Pos.TOP_LEFT);

        Text tituloNav = new Text("NAVEGAÇÃO");
        tituloNav.getStyleClass().add("section-title");

        btnDashboard = new Button("// Visão Geral");
        btnConversas = new Button("// Conversas");
        btnProjetos = new Button("// Projetos");
        btnAutomacoes = new Button("// Automações");
        btnConfig = new Button("// Configurações");
        btnSistema = new Button("// Sistema");

        btnDashboard.getStyleClass().add("nav-button");
        btnConversas.getStyleClass().add("nav-button");
        btnProjetos.getStyleClass().add("nav-button");
        btnAutomacoes.getStyleClass().add("nav-button");
        btnConfig.getStyleClass().add("nav-button");
        btnSistema.getStyleClass().add("nav-button");

        Platform.runLater(() -> btnDashboard.requestFocus());

        blocoNav.getChildren().addAll(
                tituloNav,
                btnDashboard,
                btnConversas,
                btnProjetos,
                btnAutomacoes,
                btnConfig,
                btnSistema
        );

        getChildren().add(blocoNav);
    }

    private void inicializarBlocoAtlasCore() {

        VBox blocoCore = new VBox(6);
        blocoCore.setAlignment(Pos.TOP_LEFT);

        Text tituloCore = new Text("ATLAS CORE");
        tituloCore.getStyleClass().add("section-title");

        Text txtVersao = new Text("VERSÃO: v1.0");
        txtVersao.getStyleClass().add("telemetry-text");

        txtModoValor = new Text("MODO: CORE");
        txtModoValor.getStyleClass().add("telemetry-text");

        HBox statusBox = new HBox(5);

        Text txtStatusRotulo = new Text("STATUS:");
        txtStatusRotulo.getStyleClass().add("telemetry-text");

        txtStatusValor = new Text("ONLINE");
        txtStatusValor.setStyle("-fx-fill: #32CD32; -fx-font-weight: bold;");

        statusBox.getChildren().addAll(
                txtStatusRotulo,
                txtStatusValor
        );

        txtUptimeValor = new Text("UPTIME: 00:00:00");
        txtUptimeValor.getStyleClass().add("telemetry-text");

        blocoCore.getChildren().addAll(
                tituloCore,
                txtVersao,
                txtModoValor,
                statusBox,
                txtUptimeValor
        );

        getChildren().add(blocoCore);
    }

    public void setStatus(String status, String corHex) {

        Platform.runLater(() -> {
            txtStatusValor.setText(status);
            txtStatusValor.setStyle("-fx-fill: " + corHex + "; -fx-font-weight: bold;");
        });

    }

    public void setModo(String modo) {

        Platform.runLater(() ->
                txtModoValor.setText("MODO: " + modo)
        );

    }

    private void iniciarUptime() {

        Thread threadUptime = new Thread(() -> {

            while (true) {

                segundosAtivo++;

                long horas = segundosAtivo / 3600;
                long minutos = (segundosAtivo % 3600) / 60;
                long segundos = segundosAtivo % 60;

                String uptime = String.format(
                        "UPTIME: %02d:%02d:%02d",
                        horas,
                        minutos,
                        segundos
                );

                Platform.runLater(() ->
                        txtUptimeValor.setText(uptime)
                );

                try {

                    Thread.sleep(1000);

                } catch (InterruptedException e) {

                    break;

                }

            }

        });

        threadUptime.setDaemon(true);
        threadUptime.start();
    }

    public Button getBtnDashboard() {
        return btnDashboard;
    }

    public Button getBtnConversas() {
        return btnConversas;
    }

    public Button getBtnProjetos() {
        return btnProjetos;
    }

    public Button getBtnAutomacoes() {
        return btnAutomacoes;
    }

    public Button getBtnConfig() {
        return btnConfig;
    }

    public Button getBtnSistema() {
        return btnSistema;
    }
}