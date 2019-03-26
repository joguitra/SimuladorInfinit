package br.com.fulltime.fullarm.simulador.infrastructura.conexao;

import br.com.fulltime.fullarm.simulador.application.controles.Mensagem;
import br.com.fulltime.fullarm.simulador.application.circle.ConectadoCircle;
import br.com.fulltime.fullarm.simulador.application.controles.EsconderPane;
import br.com.fulltime.fullarm.simulador.application.terminal.Terminal;
import javafx.application.Platform;
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
    private  int portanumerica;
    private  String ipnumerico;
    private PrintStream saida;
    private  int time;
    private Socket socket;
    private boolean desconetado;
    private InputStream entrada;


    public void setConexa(TextField ip , TextField porta , TextField usuario, TextField keeplive, Terminal terminal, EsconderPane esconderPane, Label labeldesconectado, ConectadoCircle conectado){
        this.ip = ip;
        this.porta = porta;
        this.usuario = usuario;
        this.keeplive = keeplive;
        this.terminal = terminal;
        this.esconderPane = esconderPane;
        this.labeldesconectado = labeldesconectado;

        this.conectado = conectado;
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
        saida.print(usuario.getText());
        return true;
    }

    public void printSaida(String saidacodigo){
        saida.print(saidacodigo);
    }

    public void recebendoResposta ()  {


        Thread recebercomando = new Thread( ()-> {
            try {
            while (!desconetado) {

                int qtdBytesDisponiveis = entrada.available();
                if (qtdBytesDisponiveis > 0) {
                    byte[] dado = new byte[qtdBytesDisponiveis];
                    entrada.read(dado);

                    String linha = new String(dado);
                    terminal.printResposta(linha);

                    if (linha.equals("-")) {
                        desconetado = true;
                        terminal.limparTerminal();
                        esconderPane.esconderIniciacao();
                        labeldesconectado.setVisible(true);
                        Platform.runLater(() -> {
                            conectado.alterarStatusDesconectado();
                            Mensagem mensagem = new Mensagem();
                            mensagem.mensagemDesconectado();
                        });

                    }
                }
            }

            } catch (IOException ignorar) {
            }
        });
        recebercomando.start();


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
        return false;
    }

}
