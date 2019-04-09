package br.com.fulltime.fullarm.simulador.infinit.infrastructura.conexao;

import br.com.fulltime.fullarm.simulador.infinit.core.PGM;
import br.com.fulltime.fullarm.simulador.infinit.core.Particao;
import br.com.fulltime.fullarm.simulador.infinit.infrastructura.particao.TodasParticao;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import br.com.fulltime.fullarm.simulador.infinit.application.circle.ConectadoCircle;
import br.com.fulltime.fullarm.simulador.infinit.application.controles.EsconderPane;
import br.com.fulltime.fullarm.simulador.infinit.application.terminal.Terminal;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Conexao {

    private TextField ip;
    private TextField porta;
    private TextField usuario;
    private TextField keeplive;
    private Terminal terminal;
    private EsconderPane esconderPane;
    private Label labeldesconectado;
    private ConectadoCircle conectado;
    private TextField imei;
    private TodasParticao todasparticao;
    private PGM pgm;
    private Button btndesconectar;
    private ChoiceBox<String> tipodconexao;
    private  int portanumerica;
    private  String ipnumerico;
    private PrintStream saida;
    private  String usuariocompleto;
    private Socket socket;
    private boolean desconetado;
    private InputStream entrada;
    private HexTraducao tradutor = new HexTraducao();
    private  RecebendoComando recebendoComando = new RecebendoComando();


    public void setConexa(TextField ip , TextField porta , TextField usuario, TextField keeplive, Terminal terminal,
                          EsconderPane esconderPane, Label labeldesconectado, ConectadoCircle conectado,
                          TextField imei , ChoiceBox<String> tipodeconexao, PGM pgm,
                          Button btndesconectar, TodasParticao todasparticao){
        this.tipodconexao = tipodeconexao;
        this.ip = ip;
        this.porta = porta;
        this.usuario = usuario;
        this.keeplive = keeplive;
        this.terminal = terminal;
        this.esconderPane = esconderPane;
        this.labeldesconectado = labeldesconectado;
        this.conectado = conectado;
        this.imei = imei;
        this.todasparticao = todasparticao;
        this.pgm = pgm;
        this.btndesconectar = btndesconectar;

    }


    public   boolean conectarServidor() throws IOException {
        portanumerica =Integer.valueOf(porta.getText());
        ipnumerico=String.valueOf(ip.getText());
        socket = new Socket(ipnumerico,portanumerica);

        desconetado = false;
        saida=new PrintStream(socket.getOutputStream());
        entrada= socket.getInputStream();
        labeldesconectado.setVisible(false);
        return true;
    }

    public boolean auntetificarConta(){
        try {
            if (tipodconexao.getSelectionModel().getSelectedItem().equals("E - Ethernet"))
                usuariocompleto = "#" + usuario.getText() + "E";
            if (tipodconexao.getSelectionModel().getSelectedItem().equals("G - GPRS"))
                usuariocompleto = "#" + usuario.getText() + "G";
        }catch (NullPointerException e) {return false;}
        saida.print(usuariocompleto);
        return true;
    }

    public void printSaida(String saidacodigo){
        printHexDecimal(saidacodigo);
    }
    public  void mandaByts(byte[] resposta){
        try {
            saida.write(resposta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] textReposta(String comando) throws IOException {
        return recebendoComando.receberResposta(comando);
    }


    public void recebendoResposta ()  {
        Thread recebercomando = new Thread( ()-> {
            recebendoComando.definirResposta(entrada,terminal,esconderPane,conectado,labeldesconectado,pgm,btndesconectar,todasparticao);
            while (true) {
                try {
                    int qtdBytesDisponiveis = entrada.available();
                    if (qtdBytesDisponiveis > 0) {
                        byte[] dado = new byte[qtdBytesDisponiveis];
                        entrada.read(dado);
                        String linha = new String(dado);
                        terminal.printResposta(linha);
                        byte[] resposta = recebendoComando.receberResposta(linha);
                        saida.write(resposta);
                        terminal.printTerminalBits(resposta);
                    }
                }catch (Exception ignorar) {}
            }
    });
        recebercomando.start();

    }
    public void enviarIMEI(){
        imei.getText();
        saida.print("I"+imei.getText());
    }



    public void ativarKeepAlive ()  {

        long millis = TimeUnit.MINUTES.toMillis(Integer.valueOf(keeplive.getText()));
        Thread keeplive = new Thread(()->{
            try {
                Thread.sleep(millis);
                while (!desconetado) {
                    saida.print("@");
                    terminal.printTerminal("@");
                    Thread.sleep(millis);

                }
            } catch (Exception ignorar) {
            }
        });
        if(!keeplive.isAlive()) {
            keeplive.start();
        }
    }
    public boolean desconectarServidor() throws IOException {
        esconderPane.esconderIniciacao();
        conectado.alterarStatusDesconectado();
        socket.close();
        setConectaservidor(false);
        return false;
    }

    public boolean getConectarservidor(){
        return recebendoComando.getConectarservidor();
    }

    public void setConectaservidor(boolean conectaservidor){
        recebendoComando.setConectarservidor(conectaservidor);
    }
    public boolean getReconectar(){
        return recebendoComando.getReconectar();
    }

    public void setReconectar(boolean reconectar){
        recebendoComando.setReconctar(reconectar);
    }

    public void printHexDecimal(String codigodecimal){
        try {
            saida.write(tradutor.hexStringToBytes(codigodecimal));
        } catch (IOException ignorar) { }
    }
}
