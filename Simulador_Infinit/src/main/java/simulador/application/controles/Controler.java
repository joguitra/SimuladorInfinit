package simulador.application.controles;

;
import simulador.core.StatusZona;
import simulador.core.PGM;
import simulador.application.circle.ConectadoCircle;
import simulador.infrastructura.conexao.Conexao;
import simulador.infrastructura.enviocontactid.FormarContactID;
import simulador.application.pesquisacid.ConfirmaPesquisa;
import simulador.application.pesquisacid.ListaCodigo;
import simulador.core.Particao;
import simulador.application.terminal.Terminal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controler implements Initializable {
    private  ListaCodigo listacodigo = new ListaCodigo();
    private int trocarzona =1;
    private Particao particao1;
    private Particao particao2;
    private ConfirmaPesquisa confirmapesquisa = new ConfirmaPesquisa();
    private EsconderPane esconderpane = new EsconderPane();
    private FormarContactID formarcontactid = new FormarContactID();
    private Terminal terminal = new Terminal();
    private Conexao conexao = new Conexao();
    private boolean conectado;
    private PGM pgm1 = new PGM();
    private ConectadoCircle statusconectar;
    private Mensagem mensagem = new Mensagem();



    @FXML
    private AnchorPane btnenviacID;


    @FXML
    private Button btnconectar;

    @FXML
    private Label llminuto;

    @FXML
    private TextField tfporta;


    @FXML
    private TextField tfip;

    @FXML
    private Pane paneprincipal;

    @FXML
    private Pane panepgn;

    @FXML
    private Pane panezona;

    @FXML
    private Circle efz1;

    @FXML
    private Circle efz2;

    @FXML
    private Circle efz3;

    @FXML
    private Circle efz4;

    @FXML
    private Circle efz5;

    @FXML
    private Circle efz6;

    @FXML
    private Circle efz7;

    @FXML
    private Circle efz8;


    @FXML
    private Circle efzp1;

    @FXML
    private Circle efzp2;

    @FXML
    private Circle efzp3;

    @FXML
    private Circle efzp4;

    @FXML
    private Circle efzp5;

    @FXML
    private Circle efzp6;

    @FXML
    private Circle efzp7;

    @FXML
    private Circle efzp8;

    @FXML
    private Pane paneenviarcomando;

    @FXML
    private TextField tfmac;
    @FXML
    private TextField tfconta;

    @FXML
    private ListView<String> lvqualificador;

    @FXML
    private TextField tfevento;

    @FXML
    private TextField tfevento1;

    @FXML
    private TextField tfevento2;

    @FXML
    private Button btnbuscaCID;

    @FXML
    private Button btnenviarCID;

    @FXML
    private Pane panesetarzonas;

    @FXML
    private Button btnconfirmarpesquisa;

    @FXML
    private Pane panepesquisa;

    @FXML
    private Button btnconfirmar;

    @FXML
    private Label lconfirmar;

    @FXML
    private ListView<String> listpcid;

    @FXML
    private Circle efparticao2;

    @FXML
    private Button btnretornarparticao;

    @FXML
    private Button btnavnacarparticao;

    @FXML
    private Label lparticao;

    @FXML
    private ImageView imagemview1;
    @FXML
    private ImageView imagemview2;


    @FXML
    private Button btnarmar;

    @FXML
    private Circle efparticao;

    @FXML
    private Label ldesconectado;

    @FXML
    private Label lconctado;

    @FXML
    private Button btnescoderlog;

    @FXML
    private Pane panezona2;

    @FXML
    private Circle efz9;

    @FXML
    private Circle efz10;

    @FXML
    private Circle efz11;

    @FXML
    private Circle efz12;

    @FXML
    private Circle efz13;

    @FXML
    private Circle efz14;

    @FXML
    private Circle efz15;

    @FXML
    private Circle efz16;

    @FXML
    private Pane panelog;

    @FXML
    private TextArea taterminal;

    @FXML
    private TextField tfenviarcomando;

    @FXML
    private Button btnenviar;

    @FXML
    private Button btnlimpar;

    @FXML
    private Circle efzconectado;


    @FXML
    private Circle efzping;

    @FXML
    private TextField tftimekeepalive;

    @FXML
    void digitaporta(KeyEvent event) {
        String regexNumero = "\\d+";
        tfporta.textProperty().addListener( (observableList, valorAntigo, novoValor) -> {
            boolean ehNumero = novoValor.matches(regexNumero);
            if( !ehNumero ){
               tfporta.setText(valorAntigo);
            }
        });
    }

    @FXML
    void digitarevento(KeyEvent event) {
        String regexNumero = "\\d+";
        tfevento.textProperty().addListener( (observableList, valorAntigo, novoValor) -> {
            boolean ehNumero = novoValor.matches(regexNumero);
            if( !ehNumero ){
                tfevento.setText(valorAntigo);
            }
        });
    }

    @FXML
    void digitarkeepalive(KeyEvent event) {
        String regexNumero = "\\d+";
        tftimekeepalive.textProperty().addListener( (observableList, valorAntigo, novoValor) -> {
            boolean ehNumero = novoValor.matches(regexNumero);
            if( !ehNumero ){
                tftimekeepalive.setText(valorAntigo);
            }
        });
    }

    @FXML
    void digitarparticao(KeyEvent event) {
        String regexNumero = "\\d+";
        tfevento1.textProperty().addListener( (observableList, valorAntigo, novoValor) -> {
            boolean ehNumero = novoValor.matches(regexNumero);
            if( !ehNumero ){
                tfevento1.setText(valorAntigo);
            }
        });
    }

    @FXML
    void digitarsetor(KeyEvent event) {
        String regexNumero = "\\d+";
        tfevento2.textProperty().addListener( (observableList, valorAntigo, novoValor) -> {
            boolean ehNumero = novoValor.matches(regexNumero);
            if( !ehNumero ){
                tfevento2.setText(valorAntigo);
            }
        });
    }


    @FXML
    void abrirtodaszonas(MouseEvent event) throws InterruptedException {
        particao1.alterarTodasZonas(StatusZona.Aberto);
        particao2.alterarTodasZonas(StatusZona.Aberto);



    }


    @FXML
    void fechartodaszona(MouseEvent event) throws InterruptedException {
        particao1.alterarTodasZonas(StatusZona.Fechado);
        particao2.alterarTodasZonas(StatusZona.Fechado);
    }

    @FXML
    void inibirtodaszonas(MouseEvent event) throws InterruptedException {
        particao1.alterarTodasZonas(StatusZona.Inibido);
        particao2.alterarTodasZonas(StatusZona.Inibido);
    }

    @FXML
    void enviarcomando(ActionEvent event) throws InterruptedException {
        String codigo =tfenviarcomando.getText();
        Thread.sleep(1000);
        conexao.printSaida(codigo);
        terminal.printTerminal(codigo);
    }
    @FXML
    void limparterminal(ActionEvent event) {
        terminal.limparTerminal();
    }

    @FXML
    void btnconfirmarpesquisa(ActionEvent event) {
        esconderpane.escoderPesquisa();
        confirmapesquisa.confirmaPesquisa(listpcid,tfevento, lvqualificador);
    }

    @FXML
    void itemselecionado(MouseEvent event) {
        confirmapesquisa.mostraDescriacao(listpcid,lconfirmar);
    }

    @FXML
    void alterarzona1(MouseEvent event) {
            if( event.getButton() == MouseButton.PRIMARY ){
                particao1.alterarZona(efz1);

            }else{
                particao1.alterarZonaInibida(efz1);
            }

    }

    @FXML
    void alterarzona2(MouseEvent event) {
        if( event.getButton() == MouseButton.PRIMARY ){
            particao1.alterarZona(efz2);

        }else{
            particao1.alterarZonaInibida(efz2);
        }

    }

    @FXML
    void alterarzona3(MouseEvent event) {
        if( event.getButton() == MouseButton.PRIMARY ){
            particao1.alterarZona(efz3);

        }else{
            particao1.alterarZonaInibida(efz3);
        }
    }

    @FXML
    void alterarzona4(MouseEvent event) {
        if( event.getButton() == MouseButton.PRIMARY ){
            particao1.alterarZona(efz4);

        }else{
            particao1.alterarZonaInibida(efz4);
        }
    }

    @FXML
    void alterarzona5(MouseEvent event) {
        if( event.getButton() == MouseButton.PRIMARY ){
            particao1.alterarZona(efz5);

        }else{
            particao1.alterarZonaInibida(efz5);
        }
    }

    @FXML
    void alterarzona6(MouseEvent event) {
        if( event.getButton() == MouseButton.PRIMARY ){
            particao1.alterarZona(efz6);

        }else{
            particao1.alterarZonaInibida(efz6);
        }
    }

    @FXML
    void alterarzona7(MouseEvent event) {
        if( event.getButton() == MouseButton.PRIMARY ){
            particao1.alterarZona(efz7);

        }else{
            particao1.alterarZonaInibida(efz7);
        }
    }

    @FXML
    void alterzona8(MouseEvent event) {
        if( event.getButton() == MouseButton.PRIMARY ){
            particao1.alterarZona(efz8);

        }else{
            particao1.alterarZonaInibida(efz8);
        }
    }

    @FXML
    void alterarzona9(MouseEvent event) {
        if( event.getButton() == MouseButton.PRIMARY ){
            particao2.alterarZona(efz9);

        }else{
            particao2.alterarZonaInibida(efz9);
        }
    }

    @FXML
    void alterarzona10(MouseEvent event) {
        if( event.getButton() == MouseButton.PRIMARY ){
            particao2.alterarZona(efz10);

        }else{
            particao2.alterarZonaInibida(efz10);
        }
    }

    @FXML
    void alterarzona11(MouseEvent event) {
        if( event.getButton() == MouseButton.PRIMARY ){
            particao2.alterarZona(efz11);

        }else{
            particao2.alterarZonaInibida(efz11);
        }
    }

    @FXML
    void alterarzona12(MouseEvent event) {
        if( event.getButton() == MouseButton.PRIMARY ){
            particao2.alterarZona(efz12);

        }else{
            particao2.alterarZonaInibida(efz12);
        }
    }

    @FXML
    void alterarzona13(MouseEvent event) {
        if( event.getButton() == MouseButton.PRIMARY ){
            particao2.alterarZona(efz13);

        }else{
            particao2.alterarZonaInibida(efz13);
        }
    }

    @FXML
    void alterarzona14(MouseEvent event) {
        if( event.getButton() == MouseButton.PRIMARY ){
            particao2.alterarZona(efz14);

        }else{
            particao2.alterarZonaInibida(efz14);
        }
    }

    @FXML
    void alterarzona15(MouseEvent event) {
        if( event.getButton() == MouseButton.PRIMARY ){
            particao2.alterarZona(efz15);

        }else{
            particao2.alterarZonaInibida(efz15);
        }
    }

    @FXML
    void alterarzona16(MouseEvent event) {
       if( event.getButton() == MouseButton.PRIMARY ){
            particao2.alterarZona(efz16);

        }else{
            particao2.alterarZonaInibida(efz16);
        }
    }


    @FXML
    void avancarparticao(ActionEvent event) {
        trocarzona= esconderpane.alterarZona(trocarzona+1);
    }



    @FXML
    void alterarpgm1(MouseEvent event) {
      String codigo =pgm1.alterarStatus(efzp1);
      conexao.printSaida(codigo);
      terminal.printTerminal(codigo);
    }

    @FXML
    void alterarpgm2(MouseEvent event) {
        String codigo =pgm1.alterarStatus(efzp2);
        conexao.printSaida(codigo);
        terminal.printTerminal(codigo);
    }

    @FXML
    void alterarpgm3(MouseEvent event) {
        String codigo =pgm1.alterarStatus(efzp3);
        conexao.printSaida(codigo);
        terminal.printTerminal(codigo);
    }

    @FXML
    void alterarpgm4(MouseEvent event) {
        String codigo =pgm1.alterarStatus(efzp4);
        conexao.printSaida(codigo);
        terminal.printTerminal(codigo);
    }

    @FXML
    void alterarpgm5(MouseEvent event) {
        String codigo =pgm1.alterarStatus(efzp5);
        conexao.printSaida(codigo);
        terminal.printTerminal(codigo);
    }

    @FXML
    void alterarpgm6(MouseEvent event) {
        String codigo=pgm1.alterarStatus(efzp6);
        conexao.printSaida(codigo);
        terminal.printTerminal(codigo);
    }
    @FXML
    void alterarpgm7(MouseEvent event) {
        String codigo =pgm1.alterarStatus(efzp7);
        conexao.printSaida(codigo);
        terminal.printTerminal(codigo);
    }

    @FXML
    void alterarpgm8(MouseEvent event) {
        String codigo =pgm1.alterarStatus(efzp8);
        conexao.printSaida(codigo);
        terminal.printTerminal(codigo);
    }

    @FXML
    void btconectar(ActionEvent event)  {
        try {

            if(!conectado) {

                conectado = conexao.conectarServidor();
                conexao.recebendoResposta();
                conectado = conexao.auntetificarConta();

                if(conectado){
                    esconderpane.mostarPaneConexao();
                    conexao.ativarKeepAlive();
                    particao1.reiniciarParticao();
                    particao2.reiniciarParticao();
                    imagemview1.setImage(new Image("cadeado.jpg"));
                    imagemview2.setImage(new Image("cadeado.jpg"));
                    pgm1.resetPGM();
                    statusconectar.alterarStatusConectado();
                    terminal.limparTerminal();
                    btnconectar.setText("Desconectar");

            }else {
                        mensagem.messagemErroAuntetificar();
                        conectado =conexao.desconectarServidor();
                        btnconectar.setText("Conectar");
                    }
            }else {
               mensagem.mensagemDesconectado();
               conectado = conexao.desconectarServidor();
               btnconectar.setText("Conectar");

            }
        } catch (IOException erroservidor) {
            mensagem.mensagemErroConectar();
        }
}

    @FXML
    void btnarmartodos(ActionEvent event) {
        if(trocarzona == 1){
            String codigo =formarcontactid.formacodigoCompleto(particao1.alterarStatusParticao(),particao1.toString());
            if(codigo.length()>3) {

                if(codigo.substring(4,5).equals("3")) { imagemview1.setImage(new Image("fechado.jpg"));}
                if(codigo.substring(4,5).equals("1")) {imagemview1.setImage(new Image("cadeado.jpg"));}
                conexao.printSaida(codigo);
                terminal.printTerminal(codigo);
            }

        }
        if(trocarzona == 2){
            String codigo =formarcontactid.formacodigoCompleto(particao2.alterarStatusParticao(),particao2.toString());
            if(codigo.length()>3) {
                if(codigo.substring(4,5).equals("3")) { imagemview2.setImage(new Image("fechado.jpg"));}
                if(codigo.substring(4,5).equals("1")) {imagemview2.setImage(new Image("cadeado.jpg"));}
                conexao.printSaida(codigo);
                terminal.printTerminal(codigo);
            }
        }
    }

    @FXML
    void btnbuscarCID(ActionEvent event) {
        esconderpane.mostrarPesquisa();
    }


    @FXML
    void enviarCID(ActionEvent event) throws IOException {
       String codigo =(formarcontactid.formarcodigo());
       terminal.printTerminal(codigo);
       conexao.printSaida(codigo);
    }


    @FXML
    void esconderlog(ActionEvent event) {
        esconderpane.escoderLog(panelog,btnescoderlog);
    }

    @FXML
    void retornarparticao(ActionEvent event) {
         trocarzona =esconderpane.alterarZona(trocarzona-1);
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lvqualificador.getItems().addAll("E 3","R 1");
        statusconectar = new ConectadoCircle(efzconectado,1);
        statusconectar.definirLabel(lconctado);

        particao1 = new Particao(efparticao,1);
        particao2= new Particao(efparticao2,2);

        pgm1.adicionarPGM(efzp1,1);
        pgm1.adicionarPGM(efzp2,2);
        pgm1.adicionarPGM(efzp3,3);
        pgm1.adicionarPGM(efzp4,4);
        pgm1.adicionarPGM(efzp5,5);
        pgm1.adicionarPGM(efzp6,6);
        pgm1.adicionarPGM(efzp7,7);
        pgm1.adicionarPGM(efzp8,8);


        particao1.adicionarZonas(efz1,1);
        particao1.adicionarZonas(efz2,2);
        particao1.adicionarZonas(efz3,3);
        particao1.adicionarZonas(efz4,4);
        particao1.adicionarZonas(efz5,5);
        particao1.adicionarZonas(efz6,6);
        particao1.adicionarZonas(efz7,7);
        particao1.adicionarZonas(efz8,8);

        particao2.adicionarZonas(efz9,9);
        particao2.adicionarZonas(efz10,10);
        particao2.adicionarZonas(efz11,11);
        particao2.adicionarZonas(efz12,12);
        particao2.adicionarZonas(efz13,13);
        particao2.adicionarZonas(efz14,14);
        particao2.adicionarZonas(efz15,15);
        particao2.adicionarZonas(efz16,16);

        terminal.definirTerminal(taterminal);

        conexao.setConexa(tfip,tfporta,tfmac,tftimekeepalive,terminal,esconderpane,ldesconectado, statusconectar);

        esconderpane.definirPane(paneprincipal,panelog,panepesquisa);
        esconderpane.definirParticao(panezona,panezona2,lparticao,imagemview1,imagemview2,efzping);
        esconderpane.esconderIniciacao();

        formarcontactid.definirContatctID(tfconta, lvqualificador,tfevento,tfevento1,tfevento2);

        listacodigo.montarLista();
        listacodigo.imprimirLista(listpcid);

    }
}

