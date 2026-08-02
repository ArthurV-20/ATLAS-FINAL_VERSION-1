package ui.components;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class RightSideBar extends VBox {

    // Bloco 1: Relógio e Data
    private Text txtHoraDigital;
    private Text txtDataDigital;

    // Bloco 2: Sincronização de Rede
    private Text txtPingValor;
    private Text txtLatenciaValor;
    private Text txtUltimaSincValor;

    // Bloco 3: Indicadores (LEDs)
    private Circle ledOnline;
    private Circle ledInternet;
    private Circle ledMicrofone;
    private Circle ledIa;
    private Circle ledVoz;

    public RightSideBar() {
        setPrefWidth(30);
        setSpacing(25);
        setAlignment(Pos.TOP_CENTER);
        getStyleClass().add("sidebar-right");

        // Inicializar os blocos visuais
        inicializarBlocoRelogio();
        inicializarBlocoSincronizacao();
        inicializarBlocoIndicadores();

        // Ativar as threads de atualização constante
        iniciarCicloVidaDireito();
    }

    private void inicializarBlocoRelogio() {
        VBox blocoRelogio = new VBox(5);
        blocoRelogio.setAlignment(Pos.CENTER);
        blocoRelogio.getStyleClass().add("telemetry-card");

        txtHoraDigital = new Text("00:00:00");
        txtHoraDigital.setStyle("-fx-font-family: 'Orbitron', sans-serif; -fx-font-size: 22px; -fx-fill: #00BFFF;");

        txtDataDigital = new Text("Carregando data...");
        txtDataDigital.setStyle("-fx-font-family: 'Consolas', monospace; -fx-font-size: 11px; -fx-fill: #8BA1B0;");

        blocoRelogio.getChildren().addAll(txtHoraDigital, txtDataDigital);
        getChildren().add(blocoRelogio);
    }

    private void inicializarBlocoSincronizacao() {
        VBox blocoSinc = new VBox(6);
        blocoSinc.setAlignment(Pos.TOP_LEFT);

        Text tituloSinc = new Text("SEGURANÇA E TELEMETRIA");
        tituloSinc.getStyleClass().add("section-title");

        Text txtServidor = new Text("SERVIDOR: ATLAS-HUB-01");
        txtServidor.getStyleClass().add("telemetry-text");

        Text txtRegiao = new Text("REGIÃO: LOCAL_HOST");
        txtRegiao.getStyleClass().add("telemetry-text");

        txtPingValor = new Text("PING: -- ms");
        txtPingValor.getStyleClass().add("telemetry-text");

        txtLatenciaValor = new Text("LATÊNCIA: ESTÁVEL");
        txtLatenciaValor.getStyleClass().add("telemetry-text");

        txtUltimaSincValor = new Text("ÚLT. SINC: 00:00:00");
        txtUltimaSincValor.getStyleClass().add("telemetry-text");

        blocoSinc.getChildren().addAll(tituloSinc, txtServidor, txtRegiao, txtPingValor, txtLatenciaValor, txtUltimaSincValor);
        getChildren().add(blocoSinc);
    }

    private void inicializarBlocoIndicadores() {
        VBox blocoLeds = new VBox(8);
        blocoLeds.setAlignment(Pos.TOP_LEFT);

        Text tituloLeds = new Text("STATUS DE SINCRONIZAÇÃO");
        tituloLeds.getStyleClass().add("section-title");

        // Instanciação dos LEDs circulares (raio 5)
        ledOnline = criarLed("#32CD32"); // Verde ativo
        ledInternet = criarLed("#32CD32");
        ledMicrofone = criarLed("#00BFFF"); // Ciano escutando/pronto
        ledIa = criarLed("#32CD32");
        ledVoz = criarLed("#8BA1B0"); // Cinzento inativo por padrão

        blocoLeds.getChildren().addAll(
                tituloLeds,
                criarLinhaLed("ONLINE", ledOnline),
                criarLinhaLed("INTERNET", ledInternet),
                criarLinhaLed("MICROFONE", ledMicrofone),
                criarLinhaLed("INTEGRAÇÃO IA", ledIa),
                criarLinhaLed("SÍNTESE VOZ", ledVoz)
        );
        getChildren().add(blocoLeds);
    }

    // Método auxiliar para criar o círculo do LED com efeito de brilho inicial
    private Circle criarLed(String corHex) {
        Circle circle = new Circle(5);
        circle.setFill(Color.web(corHex));
        circle.setStyle("-fx-effect: dropshadow(three-pass-box, " + corHex + ", 5, 0, 0, 0);");
        return circle;
    }

    // Organiza o LED e o rótulo horizontalmente
    private HBox criarLinhaLed(String nomeModulo, Circle led) {
        HBox linha = new HBox(10);
        linha.setAlignment(Pos.CENTER_LEFT);

        Text txtModulo = new Text("● " + nomeModulo);
        txtModulo.getStyleClass().add("telemetry-text");

        linha.getChildren().addAll(led, txtModulo);
        return linha;
    }

    /**
     * Gerencia a alteração de cores dos LEDs de forma segura para Threads
     */
    public void alterarEstadoLed(String modulo, String corHex) {
        Platform.runLater(() -> {
            switch (modulo.toUpperCase()) {
                case "ONLINE":
                    ledOnline.setFill(Color.web(corHex));
                    break;
                case "INTERNET":
                    ledInternet.setFill(Color.web(corHex));
                    break;
                case "MICROFONE":
                    ledMicrofone.setFill(Color.web(corHex));
                    break;
                case "IA":
                    ledIa.setFill(Color.web(corHex));
                    break;
                case "VOZ":
                    ledVoz.setFill(Color.web(corHex));
                    break;
            }
        });
    }

    private void iniciarCicloVidaDireito() {
        Thread threadDireita = new Thread(() -> {
            Locale localPt = new Locale("pt", "BR");
            DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
            DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", localPt);

            Random random = new Random();

            while (true) {
                LocalDateTime agora = LocalDateTime.now();

                // Variáveis efetivamente finais (declaradas e atribuídas apenas UMA vez)
                final String horaStr = agora.format(formatoHora);

                // Criamos uma nova variável final para armazenar o resultado capitalizado
                String dataRaw = agora.format(formatoData);
                final String dataFinal = dataRaw.substring(0, 1).toUpperCase() + dataRaw.substring(1);

                int pingSimulado = random.nextInt(6) + 12;
                final String pingStr = "PING: " + pingSimulado + " ms";
                final String latenciaStr = pingSimulado > 15 ? "LATÊNCIA: INSTÁVEL" : "LATÊNCIA: EXCELENTE";

                // Agora a Lambda aceita todas as variáveis sem erro de compilação
                Platform.runLater(() -> {
                    txtHoraDigital.setText(horaStr);
                    txtDataDigital.setText(dataFinal);
                    txtPingValor.setText(pingStr);
                    txtLatenciaValor.setText(latenciaStr);
                });

                if (agora.getSecond() % 30 == 0) {
                    final String ultSinc = "ÚLT. SINC: " + horaStr;
                    Platform.runLater(() -> txtUltimaSincValor.setText(ultSinc));
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        threadDireita.setDaemon(true);
        threadDireita.start();
    }
}