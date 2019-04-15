package br.com.fulltime.fullarm.simulador.infinit.infrastructura.conexao;

import br.com.fulltime.fullarm.simulador.infinit.application.circle.ZonaCircle;
import br.com.fulltime.fullarm.simulador.infinit.core.PGM;
import br.com.fulltime.fullarm.simulador.infinit.core.Particao;
import br.com.fulltime.fullarm.simulador.infinit.infrastructura.enviocontactid.FormarContactID;
import br.com.fulltime.fullarm.simulador.infinit.infrastructura.particao.DuplaZona;
import br.com.fulltime.fullarm.simulador.infinit.infrastructura.particao.TodasParticao;
import javafx.scene.control.*;
import br.com.fulltime.fullarm.simulador.infinit.application.circle.ConectadoCircle;
import br.com.fulltime.fullarm.simulador.infinit.application.controles.EsconderPane;
import br.com.fulltime.fullarm.simulador.infinit.application.terminal.Terminal;

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
    private CheckBox chbCID;
    private ChoiceBox<String> tipodconexao;
    private  int portanumerica;
    private  String ipnumerico;
    private PrintStream saida;
    private  String usuariocompleto;
    private Socket socket;
    private boolean desconetado;
    private boolean enviarcid;
    private InputStream entrada;
    private  RecebendoComando recebendoComando = new RecebendoComando();
    private  FormarContactID formarcontactid = new FormarContactID();


    public void setConexa(TextField ip , TextField porta , TextField usuario, TextField keeplive, Terminal terminal,
                          EsconderPane esconderPane, Label labeldesconectado, ConectadoCircle conectado,
                          TextField imei , ChoiceBox<String> tipodeconexao, PGM pgm,
                          Button btndesconectar, TodasParticao todasparticao, CheckBox chbCID){
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
        this.chbCID = chbCID;
    }


    public   boolean conectarServidor() throws IOException {
        portanumerica =Integer.valueOf(porta.getText());
        ipnumerico=String.valueOf(ip.getText());
        socket = new Socket(ipnumerico,portanumerica);

        desconetado = false;
        saida =new PrintStream(socket.getOutputStream());
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
        } catch (IOException ignorar) {
            reconectar();
        }
    }

    public boolean enviarCID () {
        if (chbCID.isSelected()) {
            enviarcid = true;
        } else {
            enviarcid = false;
        }
        return enviarcid;
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

                        String linha = HexTraducao.formatHexString(dado);
                        terminal.printResposta(HexTraducao.formatHexString(dado));
                        byte[] resposta = recebendoComando.receberResposta(linha);
                        saida.write(resposta);
                        terminal.printTerminalBits(resposta);
                        String reposta = HexTraducao.formatHexString(resposta);

                        if ("42 ".equals(reposta) || "49".equals(linha.substring(3, 5))) {
                            for (Particao particao : todasparticao.getListaparticao()) {
                                for (DuplaZona duplazona : particao.getListaduplazonas()) {
                                    for (ZonaCircle zona : duplazona.getZona()) {
                                        if (zona.getStatusinibido()) {
                                            String respostainibida = (formarcontactid.eventoInibido(zona.getNumeroidentificador(), particao.getNumeroidentificador()));
                                            saida.print(respostainibida);
                                            terminal.printTerminal(respostainibida);
                                        }
                                    }
                                }
                            }
                        }
                        if (enviarCID()) {
                            if ("52".equals(reposta.substring(6, 8))) {
                                saida.print(formarcontactid.pgmCID());
                                terminal.printTerminal(formarcontactid.pgmCID());
                            }
                        }
                        if ("4F".equals(reposta.substring(3, 5))) {
                            if ("41".equals(linha.substring(3, 5))) {
                                saida.print(formarcontactid.armeCID(linha.substring(7, 8)));
                                terminal.printTerminal(formarcontactid.armeCID(linha.substring(7, 8)));
                            }

                            if ("44".equals(linha.substring(3, 5))) {
                                saida.print(formarcontactid.desarmeCID(linha.substring(7, 8)));
                                terminal.printTerminal(formarcontactid.desarmeCID(linha.substring(7, 8)));
                            }
                            if ("49".equals(linha.substring(3, 5))) {
                                saida.print(formarcontactid.stayCID(linha.substring(7, 8)));
                                terminal.printTerminal(formarcontactid.stayCID(linha.substring(7, 8)));
                            }
                        }

                    }
                }catch (IOException reconectar){
                    reconectar.printStackTrace();
                    reconectar();
                }catch (Exception reconectar) {
                }
            }
    });
        recebercomando.start();

    }

    private void reconectar() {
        try {
            desconectarServidor();
            Thread.sleep(1000);
            conectarServidor();
            enviarIMEI();
            auntetificarConta();
            esconderPane.mostarPaneConexao();
        } catch (Exception ignorar) {
        }
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
                    printHexDecimal("@");
                    terminal.printTerminal("@");
                    Thread.sleep(millis);
                }
            } catch (Exception ignorar) {
                ignorar.printStackTrace();
            }
        });
        if(!keeplive.isAlive()) {
            keeplive.start();
        }
    }
    public boolean desconectarServidor() throws IOException {
        esconderPane.esconderIniciacao();
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

    public void printHexDecimal(String ascii){

        try {
            saida.write(HexTraducao.hexStringToBytes(ascii));
        } catch (IOException reconectar) {
            reconectar.printStackTrace();
            reconectar();
        }

    }
}
