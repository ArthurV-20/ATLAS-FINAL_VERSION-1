package ui;

import br.com.atlas.ai.AIManager;
import br.com.atlas.ai.AIResponse;
import br.com.atlas.ai.services.IntroMusicCatalog;
import br.com.atlas.ai.services.IntroMusicService;
import br.com.atlas.ai.voice.*;
import br.com.atlas.core.AtlasCore;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.components.*;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;

public class AtlasApplication extends Application {

    private AtlasCore atlasCore;
    CenterPanel centerPanel =
            new CenterPanel();

    ConsolePanel consolePanel =
            new ConsolePanel();

    BottomInput bottomInput =
            new BottomInput();
    private VoiceService voiceService;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        try {

            AIManager aiManager =
                    new AIManager();


            VoiceManager voiceManager =
                    VoiceFactory.create();

            VoiceController voiceController =
                    VoiceFactory.createController(
                            aiManager
                    );

            voiceService =
                    new VoiceService(
                            voiceController,
                            voiceManager
                    );

            atlasCore =
                    new AtlasCore(
                            aiManager,
                            voiceManager,
                            voiceController
                    );


            VBox root =
                    new VBox();

            TopBar topBar =
                    new TopBar();
            //AQUI É A LATERAL ESQUERDA
            /*LeftSideBar leftSideBar =
                    new LeftSideBar();*/
            //==========================
            //AQUI É A LATERAL DIREITA
            /*RightSideBar rightSideBar =
                    new RightSideBar();*/
            //============================


            configurarVoz(
                    topBar,
                    centerPanel
            );


            configurarInteracoes(
                    bottomInput,
                    centerPanel
            );


            HBox areaCentral = new HBox();

            HBox.setHgrow(centerPanel, Priority.ALWAYS);

            areaCentral.getChildren().addAll(
                    //lateral esquerda(só tirar a "//")
                    //leftSideBar,
                    centerPanel
                    //lateral direita(só tirar a "//")
                    //rightSideBar
            );

            VBox.setVgrow(areaCentral, Priority.ALWAYS);

            consolePanel.setMaxWidth(Double.MAX_VALUE);
            consolePanel.setPrefHeight(180);
            consolePanel.setMinHeight(180);

            root.getChildren().addAll(
                    topBar,
                    areaCentral,
                    consolePanel,
                    bottomInput
            );

            Rectangle2D screen =
                    Screen.getPrimary().getVisualBounds();

            Scene scene =
                    new Scene(
                            root,
                            screen.getWidth(),
                            screen.getHeight()
                    );
            String css =
                    getClass()
                            .getResource("/style.css")
                            .toExternalForm();


            scene.getStylesheets()
                    .add(css);



            primaryStage.setTitle(
                    "ATLAS NEXT // SYSTEM ACTIVE"
            );


            primaryStage.setScene(scene);

            primaryStage.show();

            IntroMusicCatalog catalog =
                    new IntroMusicCatalog();

            IntroMusicService introMusicService =
                    new IntroMusicService();

            introMusicService.play(
                    catalog.getTodayMusic()
            );

            consolePanel.escreverLog(
                    "Interface gráfica ATLAS iniciada.",
                    ConsolePanel.LogType.SUCCESS
            );

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void configurarInteracoes(
            BottomInput bottomInput,
            CenterPanel centerPanel
    ) {


        bottomInput.setAoExecutarComando(comando -> {


            centerPanel.getAtlasHud()
                    .iniciarProcessamento();



            consolePanel.escreverLog(
                    "Processando comando: " + comando,
                    ConsolePanel.LogType.INFO
            );


            Task<AIResponse> tarefa =
                    new Task<>() {


                        @Override
                        protected AIResponse call()
                                throws Exception {


                            return atlasCore.executarComando(
                                    comando
                            );

                        }

                    };



            tarefa.setOnSucceeded(e -> {


                AIResponse resposta =
                        tarefa.getValue();



                consolePanel
                        .escreverLog(
                                resposta.getMessage(),
                                ConsolePanel.LogType.SUCCESS
                        );



                centerPanel.getAtlasHud()
                        .mostrarSucesso();


            });



            tarefa.setOnFailed(e -> {


                consolePanel
                        .escreverLog(
                                "Falha ao executar comando.",
                                ConsolePanel.LogType.ERROR
                        );



                centerPanel.getAtlasHud()
                        .mostrarErro();


                tarefa.getException()
                        .printStackTrace();


            });



            new Thread(tarefa)
                    .start();


        });

    }



    private void configurarVoz(
            TopBar topBar,
            CenterPanel centerPanel
    ) {

        topBar.getVoiceButton().setOnAction(event -> {

            System.out.println(
                    "[DEBUG] BOTÃO VOZ CLICADO"
            );

            if (!voiceService.isAlive()) {

                voiceService.start();

                consolePanel.escreverLog(
                        "ATLAS Voice Service iniciado.",
                        ConsolePanel.LogType.SUCCESS
                );

            } else {

                consolePanel.escreverLog(
                        "ATLAS já está ouvindo.",
                        ConsolePanel.LogType.INFO
                );

            }

        });

    }

    public static void main(String[] args) {

        launch(args);

    }

}