package ui.components;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.scene.CacheHint;
import javafx.animation.ScaleTransition;
import javafx.animation.FadeTransition;

public class AtlasHud extends StackPane {

    private Text status;

    private Circle anelInterno;
    private Circle anelMedio;
    private Circle anelContencao;
    private Circle anelExterno;

    private Circle anelCentroAzul;
    private Circle anelBranco;
    private Circle anelPontilhado;
    private Circle anelSegmentado;
    private Circle anelFino;
    private Circle anelGlow;
    private Circle anelFinal;
    private Group grupoSatelites;
    private Group grupoLinhas;
    private DropShadow glowContencao;

    private Group grupoInterno;
    private Group grupoExterno;
    private Group grupoMarcadores;
    private Group grupoArcos;
    private Group grupoParticulas;
    private Group grupoArcosAssimetricos;
    private Group grupoMicroSegmentos;
    private Group grupoConectores;
    private Group grupoDados;
    private ScaleTransition pulsoCentro;

    private FadeTransition brilhoPulso;

    private RotateTransition scannerRotation;
    private Group grupoScannerInterno;

    private Arc scanner;
    private Text titulo;

    public AtlasHud() {

        setAlignment(Pos.CENTER);

        criarEstrutura();

        criarMarcadores();

        criarArcos();

        criarCrosshair();

        criarTexto();

        criarSatelites();

        criarLinhasTecnicas();

        criarMicroSegmentos();

        criarConectores();

        criarDadosOrbitais();

        iniciarAnimacoes();
    }

    private void criarEstrutura(){

        Circle fundoCentral = new Circle(82);
        fundoCentral.setFill(Color.web("#02070d"));
        fundoCentral.setStroke(Color.web("#004a66"));
        fundoCentral.setStrokeWidth(2);

        anelCentroAzul = criarAnel(
                150,
                5,
                "#00d2ff",
                false,
                0,
                0
        );

        anelCentroAzul.setOpacity(.65);
        anelCentroAzul.setEffect(
                new DropShadow(
                        18,
                        Color.web("#00d2ff")
                )
        );

        anelBranco = criarAnel(
                103,
                2.5,
                "#dff8ff",
                true,
                28,
                8
        );

        anelPontilhado = criarAnel(
                116,
                2,
                "#00d2ff",
                true,
                2,
                10
        );

        anelPontilhado.setOpacity(.85);

        anelInterno = criarAnel(
                126,
                5,
                "#00d2ff",
                true,
                18,
                12
        );

        anelSegmentado = criarAnel(
                138,
                4,
                "#00d2ff",
                true,
                42,
                20
        );

        anelMedio = criarAnel(
                150,
                1.4,
                "#007da8",
                false,
                0,
                0
        );

        anelGlow = criarAnel(
                162,
                2,
                "#00d2ff",
                false,
                0,
                0
        );

        DropShadow glow1 =
                new DropShadow(
                        18,
                        Color.web("#00d2ff")
                );

        DropShadow glow2 =
                new DropShadow(
                        40,
                        Color.web("#00d2ff")
                );

        glow1.setInput(glow2);

        anelGlow.setEffect(glow1);

        anelContencao = criarAnel(
                174,
                2,
                "#005a7a",
                false,
                0,
                0
        );

        glowContencao =
                new DropShadow(
                        14,
                        Color.web("#00d2ff")
                );

        anelContencao.setEffect(glowContencao);

        anelExterno = criarAnel(
                188,
                5,
                "#00d2ff",
                true,
                46,
                24
        );

        anelFinal = criarAnel(
                198,
                1,
                "#00384f",
                false,
                0,
                0
        );

        grupoInterno = new Group(

                anelCentroAzul,

                anelBranco,

                anelPontilhado,

                anelInterno

        );
        /*grupoInterno.setCache(true);
        grupoInterno.setCacheHint(CacheHint.SPEED);*/
        //AQUI teoricamente é otimização.

        grupoExterno = new Group(

                anelSegmentado,

                anelMedio,

                anelGlow,

                anelContencao,

                anelExterno,

                anelFinal
        );
        /*grupoExterno.setCache(true);
        grupoExterno.setCacheHint(CacheHint.SPEED);*/
        //OTMIZAÇÃO
        scanner = criarArco(

                145,

                0,

                18,

                6,

                "#66f2ff"

        );

        scanner.setOpacity(.35);

        scanner.setStrokeLineCap(
                StrokeLineCap.ROUND
        );

        getChildren().add(scanner);
        getChildren().addAll(

                grupoExterno,

                grupoInterno,

                fundoCentral

        );
        DropShadow ambiente =

                new DropShadow(

                        25,

                        Color.web("#0088aa")

                );

        ambiente.setSpread(.15);

        //this.setEffect(ambiente); causador núnero 2 de travamento
    }

    private void criarMarcadores(){

        grupoMarcadores = new Group();
        /*grupoMarcadores.setCache(true);
        grupoMarcadores.setCacheHint(CacheHint.SPEED);*/
        //OTIMIZAÇÃO

        for(int i=0;i<8;i++){

            Rectangle marcador =
                    new Rectangle(3,10);

            marcador.setFill(
                    Color.web("#00d2ff")
            );

            marcador.setTranslateY(-205);

            marcador.setRotate(i*10);

            Group g = new Group(marcador);

            g.setRotate(i*45);

            grupoMarcadores.getChildren().add(g);

        }

        for(int i=0;i<4;i++){

            Rectangle marcador =
                    new Rectangle(4,18);

            marcador.setFill(
                    Color.web("#dff8ff")
            );

            marcador.setTranslateY(-185);

            Group g = new Group(marcador);

            g.setRotate(i*90);

            grupoMarcadores.getChildren().add(g);

        }

        getChildren().add(grupoMarcadores);

    }
    //START here the AI addition
    private void criarArcos(){

        grupoArcos = new Group();
        /*grupoArcos.setCache(true);
        grupoArcos.setCacheHint(CacheHint.SPEED);*/

        //OTIMIZAÇÃO


        //--------------------------
        // Arcos externos
        //--------------------------

        for(int i=0;i<12;i++){

            Arc arco = criarArco(
                    172,
                    i*30,
                    18,
                    3,
                    "#00d2ff"
            );

            grupoArcos.getChildren().add(arco);
            criarScannerInterno();
        }

        //--------------------------
        // Arcos médios
        //--------------------------

        for(int i=0;i<24;i++){

            Arc arco = criarArco(
                    138,
                    i*15,
                    6,
                    2,
                    "#dff8ff"
            );

            grupoArcos.getChildren().add(arco);

        }

        //--------------------------
        // Arcos internos
        //--------------------------

        for(int i=0;i<18;i++){

            Arc arco = criarArco(
                    118,
                    i*20,
                    10,
                    2,
                    "#00d2ff"
            );

            grupoArcos.getChildren().add(arco);

        }

        //--------------------------
        // Segmentos pequenos
        //--------------------------

        for(int i=0;i<48;i++){

            Rectangle r =
                    new Rectangle(2,10);

            r.setFill(Color.web("#00d2ff"));

            r.setTranslateY(-182);

            Group g = new Group(r);

            g.setRotate(i*5);

            grupoArcos.getChildren().add(g);

        }

        getChildren().add(grupoArcos);
        //NOVOS ARCOS
        criarArcosAssimetricos();

        criarParticulas();
        //ENDS here.
    }
    //ends here the AI addtion
    private void criarCrosshair(){

        Group cross = new Group();
        Line horizontal =
                new Line(-120,0,120,0);

        horizontal.setStroke(Color.web("#00d2ff"));
        horizontal.setStrokeWidth(1);

        Line vertical =
                new Line(0,-120,0,120);

        vertical.setStroke(Color.web("#00d2ff"));
        vertical.setStrokeWidth(1);
        Line norte =
                new Line(0,-235,0,-205);

        Line sul =
                new Line(0,205,0,235);

        Line leste =
                new Line(205,0,235,0);

        Line oeste =
                new Line(-205,0,-235,0);

        norte.setStroke(Color.web("#00d2ff"));
        sul.setStroke(Color.web("#00d2ff"));
        leste.setStroke(Color.web("#00d2ff"));
        oeste.setStroke(Color.web("#00d2ff"));

        norte.setStrokeWidth(2);
        sul.setStrokeWidth(2);
        leste.setStrokeWidth(2);
        oeste.setStrokeWidth(2);

        Circle n =
                new Circle(4);

        n.setFill(Color.web("#00d2ff"));
        n.setTranslateY(-202);

        Circle s =
                new Circle(4);

        s.setFill(Color.web("#00d2ff"));
        s.setTranslateY(202);

        Circle l =
                new Circle(4);

        l.setFill(Color.web("#00d2ff"));
        l.setTranslateX(202);

        Circle o =
                new Circle(4);

        o.setFill(Color.web("#00d2ff"));
        o.setTranslateX(-202);

        cross.getChildren().addAll(

                horizontal,
                vertical,

                norte,
                sul,
                leste,
                oeste,

                n,
                s,
                l,
                o

        );

        getChildren().add(cross);

    }

    private void criarTexto(){

        VBox centro =
                new VBox(4);

        centro.setAlignment(Pos.CENTER);

        titulo =
                new Text("ATLAS");

        titulo.setStyle(
                "-fx-font-size:24;" +
                        "-fx-font-weight:bold;" +
                        "-fx-fill:#EAFBFF;" +
                        "-fx-font-family:'Consolas';"
        );
        titulo.setEffect(

                new DropShadow(

                        10,

                        Color.web("#88ffff")

                )

        );
        status =
                new Text("ONLINE");

        status.setStyle(
                "-fx-font-size:20;" +
                        "-fx-font-weight:bold;" +
                        "-fx-fill:#32CD32;" +
                        "-fx-font-family:'Consolas';"
        );

        status.setEffect(
                new DropShadow(
                        15,
                        Color.web("#32CD32")
                )
        );

        Text versao =
                new Text("v1.0");

        versao.setStyle(
                "-fx-fill:#00d2ff;" +
                        "-fx-font-size:15;" +
                        "-fx-font-family:'Consolas';"
        );

        centro.getChildren().addAll(

                titulo,

                status,

                versao

        );
        Circle brilhoCentro =
                new Circle(18);

        brilhoCentro.setFill(
                Color.web("#00d2ff")
        );

        brilhoCentro.setOpacity(.18);

        brilhoCentro.setEffect(

                new DropShadow(
                        18,
                        Color.web("#00d2ff")
                )

        );

        getChildren().add(brilhoCentro);
        Circle halo =
                new Circle(72);

        halo.setFill(Color.TRANSPARENT);

        halo.setStroke(Color.web("#00d2ff"));

        halo.setOpacity(.15);

        halo.setStrokeWidth(2);

        getChildren().add(halo);
        getChildren().add(centro);

    }

    private void iniciarAnimacoes(){

        animarRotacao(
                grupoInterno,
                18,
                true
        );

        animarRotacao(
                grupoExterno,
                40,
                false
        );

        animarRotacao(
                grupoMarcadores,
                65,
                true
        );

        animarRotacao(
                anelPontilhado,
                55,
                false
        );

        animarRotacao(
                anelExterno,
                120,
                true
        );
        animarRotacao(
                grupoArcos,
                90,
                true
        );
        animarRotacao(
                grupoSatelites,
                140,
                false
        );
        animarRotacao(
                grupoArcosAssimetricos,
                55,
                false
        );

        animarRotacao(
                grupoParticulas,
                220,
                true
        );
        animarRotacao(
                grupoMicroSegmentos,
                300,
                true
        );

        animarRotacao(
                grupoDados,
                260,
                false
        );
        iniciarPulso();

        iniciarRespiracao();

        iniciarScanner();
        animarRotacao(
                grupoScannerInterno,
                8,
                false
        );
    }

    private Circle criarAnel(
            double raio,
            double espessura,
            String cor,
            boolean tracejado,
            double dash,
            double gap){

        Circle c =
                new Circle(raio);

        c.setFill(Color.TRANSPARENT);

        c.setStroke(Color.web(cor));

        c.setStrokeWidth(espessura);

        c.setStrokeType(
                StrokeType.CENTERED
        );

        c.setStrokeLineCap(
                StrokeLineCap.BUTT
        );

        if(tracejado){

            c.getStrokeDashArray().addAll(
                    dash,
                    gap
            );

        }

        return c;

    }
    //ONE more AI addition
    private Arc criarArco(
            double raio,
            double inicio,
            double comprimento,
            double largura,
            String cor){

        Arc arco = new Arc();

        arco.setRadiusX(raio);
        arco.setRadiusY(raio);

        arco.setStartAngle(inicio);

        arco.setLength(comprimento);

        arco.setType(ArcType.OPEN);

        arco.setFill(Color.TRANSPARENT);

        arco.setStroke(Color.web(cor));

        arco.setStrokeWidth(largura);

        arco.setStrokeLineCap(StrokeLineCap.ROUND);

        return arco;

    }
    //ENDS here(again)
    private void animarRotacao(
            javafx.scene.Node n,
            double segundos,
            boolean horario){

        RotateTransition rt =
                new RotateTransition(
                        Duration.seconds(segundos),
                        n
                );

        rt.setInterpolator(
                Interpolator.LINEAR
        );

        rt.setDelay(
                Duration.millis(
                        Math.random()*250
                )
        );

        rt.setCycleCount(
                Animation.INDEFINITE
        );

        rt.setByAngle(
                horario ? 360 : -360
        );

        rt.play();

    }

    public void iniciarProcessamento(){

        status.setText("PROCESSANDO");

        status.setFill(
                Color.web("#ffd700")
        );

        status.setEffect(
                new DropShadow(
                        16,
                        Color.web("#ffd700")
                )
        );

        anelCentroAzul.setStroke(Color.web("#ffd700"));
        anelBranco.setStroke(Color.web("#fff3aa"));
        anelPontilhado.setStroke(Color.web("#ffd700"));
        anelInterno.setStroke(Color.web("#ffd700"));
        anelSegmentado.setStroke(Color.web("#ffd700"));
        anelMedio.setStroke(Color.web("#ffd700"));
        anelGlow.setStroke(Color.web("#ffd700"));
        anelContencao.setStroke(Color.web("#ffd700"));
        anelExterno.setStroke(Color.web("#ffd700"));

        scannerRotation.setRate(2.5);

        pulsoCentro.setRate(2.2);

        brilhoPulso.setRate(2.5);

        glowContencao.setColor(
                Color.web("#ffd700")
        );
        titulo.setScaleX(1.03);
        titulo.setScaleY(1.03);
    }

    public void mostrarSucesso(){

        status.setText("ONLINE");

        status.setFill(
                Color.web("#32CD32")
        );

        status.setEffect(
                new DropShadow(
                        15,
                        Color.web("#32CD32")
                )
        );

        restaurarAzul();
        //NÃO tenho certeza que está correto, vou verificar.
        scannerRotation.setRate(1);

        pulsoCentro.setRate(1);

        brilhoPulso.setRate(1);
        titulo.setScaleX(1);
        titulo.setScaleY(1);
    }

    public void mostrarErro(){

        status.setText("ERRO");

        status.setFill(
                Color.web("#ff3333")
        );

        status.setEffect(
                new DropShadow(
                        15,
                        Color.web("#ff3333")
                )
        );

        anelCentroAzul.setStroke(Color.web("#ff3333"));
        anelBranco.setStroke(Color.web("#ff8888"));
        anelPontilhado.setStroke(Color.web("#ff3333"));
        anelInterno.setStroke(Color.web("#ff3333"));
        anelSegmentado.setStroke(Color.web("#ff3333"));
        anelMedio.setStroke(Color.web("#ff3333"));
        anelGlow.setStroke(Color.web("#ff3333"));
        anelContencao.setStroke(Color.web("#ff3333"));
        anelExterno.setStroke(Color.web("#ff3333"));
        anelFinal.setStroke(Color.web("#662222"));

        glowContencao.setColor(
                Color.web("#ff3333")
        );
        scannerRotation.setRate(.25);

        pulsoCentro.setRate(.45);

        brilhoPulso.setRate(.35);
        titulo.setScaleX(.98);
        titulo.setScaleY(.98);
    }

    private void restaurarAzul(){

        anelCentroAzul.setStroke(
                Color.web("#00d2ff")
        );

        anelBranco.setStroke(
                Color.web("#dff8ff")
        );

        anelPontilhado.setStroke(
                Color.web("#00d2ff")
        );

        anelInterno.setStroke(
                Color.web("#00d2ff")
        );

        anelSegmentado.setStroke(
                Color.web("#00d2ff")
        );

        anelMedio.setStroke(
                Color.web("#007da8")
        );

        anelGlow.setStroke(
                Color.web("#00d2ff")
        );

        anelContencao.setStroke(
                Color.web("#005a7a")
        );

        anelExterno.setStroke(
                Color.web("#00d2ff")
        );

        anelFinal.setStroke(
                Color.web("#00384f")
        );

        glowContencao.setColor(
                Color.web("#00d2ff")
        );

    }
    private Arc criarArcos(
            double raio,
            double inicio,
            double comprimento,
            double largura,
            String cor){

        Arc arco = new Arc();

        arco.setRadiusX(raio);
        arco.setRadiusY(raio);

        arco.setStartAngle(inicio);

        arco.setLength(comprimento);

        arco.setType(ArcType.OPEN);

        arco.setFill(Color.TRANSPARENT);

        arco.setStroke(Color.web(cor));

        arco.setStrokeWidth(largura);

        arco.setStrokeLineCap(StrokeLineCap.ROUND);

        return arco;

    }
    private void criarArcosv2() {
//Só pra deixar claro, que eu tive que alterar o nome desse método porque estava igual ao método anterior.
        Group arcosExternos = new Group();

        for(int i=0;i<12;i++){

            Arc arco = criarArco(
                    170,
                    i * 30,
                    18,
                    3,
                    "#00d2ff"
            );

            arcosExternos.getChildren().add(arco);
        }

        Group arcosInternos = new Group();

        for(int i=0;i<24;i++){

            Arc arco = criarArco(
                    122,
                    i * 15,
                    6,
                    2,
                    "#dff8ff"
            );

            arcosInternos.getChildren().add(arco);
        }

        Group tracos = new Group();

        for(int i=0;i<90;i++){

            Rectangle r = new Rectangle(2,8);

            r.setFill(Color.web("#00d2ff"));

            r.setTranslateY(-185);

            Group g = new Group(r);

            g.setRotate(i * 2);

            tracos.getChildren().add(g);
        }

        getChildren().addAll(
                arcosExternos,
                arcosInternos,
                tracos
        );
    }
    //ends here
    private void criarSatelites(){

        grupoSatelites = new Group();
        /*grupoSatelites.setCache(true);
        grupoSatelites.setCacheHint(CacheHint.SPEED);*/
        //OTIMIZAÇÃO

        double raio = 225;

        for(int i=0;i<4;i++){

            double angulo = Math.toRadians(i*90);

            Group satelite = new Group();

            Circle externo = new Circle(8);
            externo.setEffect(

                    new DropShadow(
                            10,
                            Color.web("#00d2ff")
                    )

            );
            externo.setFill(Color.TRANSPARENT);
            externo.setStroke(Color.web("#00d2ff"));
            externo.setStrokeWidth(2);

            Circle interno = new Circle(2.5);
            interno.setEffect(

                    new DropShadow(
                            8,
                            Color.web("#00d2ff")
                    )

            );
            interno.setFill(Color.web("#00d2ff"));

            satelite.getChildren().addAll(
                    externo,
                    interno
            );

            satelite.setTranslateX(
                    Math.cos(angulo)*raio
            );

            satelite.setTranslateY(
                    Math.sin(angulo)*raio
            );

            grupoSatelites.getChildren().add(satelite);

        }

        getChildren().add(grupoSatelites);

    }
    private void criarLinhasTecnicas(){

        grupoLinhas = new Group();
        /*grupoLinhas.setCache(true);
        grupoLinhas.setCacheHint(CacheHint.SPEED);*/
        //OTIMIZAÇÃO


        //-------------------------
        // Horizontal Superior
        //-------------------------

        Line l1 = new Line(-260,-55,-180,-55);
        Line l2 = new Line(180,-55,260,-55);

        //-------------------------
        // Horizontal Inferior
        //-------------------------

        Line l3 = new Line(-260,55,-180,55);
        Line l4 = new Line(180,55,260,55);

        //-------------------------
        // Vertical Esquerda
        //-------------------------

        Line l5 = new Line(-55,-260,-55,-180);
        Line l6 = new Line(-55,180,-55,260);

        //-------------------------
        // Vertical Direita
        //-------------------------

        Line l7 = new Line(55,-260,55,-180);
        Line l8 = new Line(55,180,55,260);

        for(Line l : new Line[]{
                l1,l2,l3,l4,l5,l6,l7,l8
        }){

            l.setStroke(Color.web("#007da8"));
            l.setStrokeWidth(1.3);

        }

        grupoLinhas.getChildren().addAll(

                l1,l2,l3,l4,
                l5,l6,l7,l8

        );

        getChildren().add(grupoLinhas);

    }
    private void criarArcosAssimetricos(){

        grupoArcosAssimetricos = new Group();
        /*grupoArcosAssimetricos.setCache(true);
        grupoArcosAssimetricos.setCacheHint(CacheHint.SPEED);*/
        //OTIMIZAÇÃO


        double[] angulos = {

                5,
                32,
                61,
                104,
                132,
                177,
                214,
                247,
                289,
                318

        };

        double[] comprimentos = {

                22,
                11,
                28,
                14,
                18,
                30,
                16,
                24,
                13,
                26

        };

        for(int i=0;i<angulos.length;i++){

            Arc arco = criarArco(

                    154,

                    angulos[i],

                    comprimentos[i],

                    3,

                    "#00d2ff"

            );

            arco.setOpacity(.95);

            grupoArcosAssimetricos.getChildren().add(arco);

        }

        //--------------------------------------

        for(int i=0;i<18;i++){

            Arc arco = criarArco(

                    96,

                    i*20+7,

                    9,

                    2,

                    "#ffffff"

            );

            arco.setOpacity(.8);

            grupoArcosAssimetricos.getChildren().add(arco);

        }

        getChildren().add(grupoArcosAssimetricos);

    }
    private void criarParticulas(){

        grupoParticulas = new Group();
        /*grupoParticulas.setCache(true);
        grupoParticulas.setCacheHint(CacheHint.SPEED);*/
        //OTIMIZAÇÃO


        java.util.Random random =
                new java.util.Random();

        for(int i=0;i<180;i++){ //causador 1 dos travamentos

            Circle ponto =
                    new Circle(

                            random.nextDouble()*1.6+0.3

                    );

            ponto.setFill(

                    Color.web("#00d2ff")

            );

            double angulo =
                    random.nextDouble()*360;

            double raio =
                    115+
                            random.nextDouble()*125;

            ponto.setTranslateX(

                    Math.cos(
                            Math.toRadians(angulo)
                    )*raio

            );

            ponto.setTranslateY(

                    Math.sin(
                            Math.toRadians(angulo)
                    )*raio

            );

            ponto.setOpacity(

                    random.nextDouble()

            );

            grupoParticulas.getChildren().add(

                    ponto

            );
            /*criarMicroSegmentos();

            criarConectores(); é esses os causadores do travamento.(08/07/2026)

            criarDadosOrbitais();*/
        }

        getChildren().add(grupoParticulas);

    }
    private void criarMicroSegmentos(){

        grupoMicroSegmentos = new Group();
        /*grupoMicroSegmentos.setCache(true);
        grupoMicroSegmentos.setCacheHint(CacheHint.SPEED);*/
        //OTIMIZAÇÃO

        for(int i=0;i<360;i+=5){

            Rectangle r =
                    new Rectangle(1.2,4);

            r.setFill(Color.web("#00d2ff"));

            r.setOpacity(.45);

            r.setTranslateY(-194);

            Group g = new Group(r);

            g.setRotate(i);

            grupoMicroSegmentos.getChildren().add(g);

        }

        getChildren().add(grupoMicroSegmentos);

    }
    private void criarConectores(){

        grupoConectores = new Group();
        /*grupoConectores.setCache(true);
        grupoConectores.setCacheHint(CacheHint.SPEED);*/
        //OTIMIZAÇÃO

        double raioInterno = 190;
        double raioExterno = 218;

        for(int i=0;i<4;i++){

            double ang = Math.toRadians(i*90);

            Line l = new Line();

            l.setStartX(
                    Math.cos(ang)*raioInterno
            );

            l.setStartY(
                    Math.sin(ang)*raioInterno
            );

            l.setEndX(
                    Math.cos(ang)*raioExterno
            );

            l.setEndY(
                    Math.sin(ang)*raioExterno
            );

            l.setStroke(Color.web("#00d2ff"));

            l.setStrokeWidth(2);

            grupoConectores.getChildren().add(l);

        }

        getChildren().add(grupoConectores);

    }
    private void criarDadosOrbitais(){

        grupoDados = new Group();
        /*grupoDados.setCache(true);
        grupoDados.setCacheHint(CacheHint.SPEED);*/
        //OTIMIZAÇÃO

        for(int i=0;i<18;i++){

            Group bloco = new Group();

            Rectangle r1 =
                    new Rectangle(10,2);

            Rectangle r2 =
                    new Rectangle(6,2);

            Rectangle r3 =
                    new Rectangle(3,2);

            r1.setFill(Color.web("#00d2ff"));
            r2.setFill(Color.web("#00d2ff"));
            r3.setFill(Color.web("#00d2ff"));

            r2.setTranslateY(5);

            r3.setTranslateY(10);

            bloco.getChildren().addAll(
                    r1,
                    r2,
                    r3
            );

            double ang =
                    Math.toRadians(i*20);

            bloco.setTranslateX(
                    Math.cos(ang)*250
            );

            bloco.setTranslateY(
                    Math.sin(ang)*250
            );

            bloco.setRotate(i*20);

            grupoDados.getChildren().add(bloco);

        }

        getChildren().add(grupoDados);

    }
    private void iniciarPulso(){

        pulsoCentro =

                new ScaleTransition(

                        Duration.seconds(2.8),

                        anelCentroAzul

                );

        pulsoCentro.setFromX(1);

        pulsoCentro.setFromY(1);

        pulsoCentro.setToX(1.03);

        pulsoCentro.setToY(1.03);

        pulsoCentro.setAutoReverse(true);

        pulsoCentro.setCycleCount(
                Animation.INDEFINITE
        );

        pulsoCentro.play();

    }
    private void iniciarRespiracao(){

        brilhoPulso =

                new FadeTransition(

                        Duration.seconds(2.2),

                        anelGlow

                );

        brilhoPulso.setFromValue(.35);

        brilhoPulso.setToValue(.9);

        brilhoPulso.setAutoReverse(true);

        brilhoPulso.setCycleCount(
                Animation.INDEFINITE
        );

        brilhoPulso.play();

    }
    private void iniciarScanner(){

        scannerRotation =

                new RotateTransition(

                        Duration.seconds(6),

                        scanner

                );

        scannerRotation.setInterpolator(
                Interpolator.LINEAR
        );

        scannerRotation.setCycleCount(
                Animation.INDEFINITE
        );

        scannerRotation.setByAngle(360);

        scannerRotation.play();

    }
    private void criarScannerInterno(){

        grupoScannerInterno = new Group();

        for(int i=0;i<6;i++){

            Arc arco = criarArco(

                    72,

                    i*60,

                    20,

                    2,

                    "#66ffff"

            );

            arco.setOpacity(.25);

            grupoScannerInterno.getChildren().add(arco);

        }

        getChildren().add(grupoScannerInterno);

    }
}