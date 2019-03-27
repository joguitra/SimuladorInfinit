package br.com.fulltime.fullarm.simulador.infinit.infrastructura.conexao;

import br.com.fulltime.fullarm.simulador.infinit.application.circle.ConectadoCircle;
import br.com.fulltime.fullarm.simulador.infinit.application.terminal.Terminal;
import javafx.application.Platform;
import javafx.scene.control.Label;
import br.com.fulltime.fullarm.simulador.infinit.application.controles.EsconderPane;
import br.com.fulltime.fullarm.simulador.infinit.application.controles.Mensagem;


import java.io.IOException;
import java.io.InputStream;

public class RecebendoComando {


    private boolean desconetado;
    private HexTraducao tradutor = new HexTraducao();
    private InputStream entrada;
    private Terminal terminal;
    private EsconderPane esconderPane;
    private ConectadoCircle conectado;
    private Label labeldesconectado;
    private String primeirodigito,seguendodigito;
    private String linha;
    private String numeroidentificador;
    private String pedidoservidorpgm;

    public void definirResposta (InputStream entrada, Terminal terminal, EsconderPane esconderPane, ConectadoCircle conectado, Label labeldesconectado) {
        this.entrada = entrada;
        this.terminal = terminal;
        this.esconderPane = esconderPane;
        this.conectado = conectado;
        this.labeldesconectado = labeldesconectado;
    }

    public void receberResposta(){
        try {
            while (!desconetado) {

                int qtdBytesDisponiveis = entrada.available();
                if (qtdBytesDisponiveis > 0) {
                    byte[] dado = new byte[qtdBytesDisponiveis];
                    entrada.read(dado);

                    linha = new String(dado);
                    linha =tradutor.traduzirCodigoHex(linha);
                    terminal.printResposta(linha);
                    primeirodigito = linha.substring(1,2);
                    switch (primeirodigito){
                        case "-":
                            desconecteServidor();
                            break;
                        case "%":
                            leitorEvento();
                            break;
                        case "W":
                            diferenciadorPGMParticao();
                            break;
                    }
                }
            }

        } catch (IOException ignorar) {
        }
    }


    public void desconecteServidor(){
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

    public void leitorEvento(){

    }

    public void diferenciadorPGMParticao(){
        seguendodigito = linha.substring(2,3);

        switch (seguendodigito){
            case "S":
                pedirStatusParticao();
                break;
            case "E":
                armaParticao();
            default:
                alterarPGM();
        }
    }

    public void pedirStatusParticao(){
        if(linha.length() == 4){
            numeroidentificador = linha.substring(2,3);

        }
        else {
            numeroidentificador = linha.substring(2,4);
        }
    }
    public void armaParticao(){
        if(linha.length() == 4){
            numeroidentificador = linha.substring(2,3);
        }
        else {
            numeroidentificador = linha.substring(2,4);
        }

    }
    public void alterarPGM(){
        if(linha.length() == 4){
            numeroidentificador = linha.substring(2,3);
            pedidoservidorpgm = linha.substring(3,4);
        }
        else {
            numeroidentificador = linha.substring(2,4);
            pedidoservidorpgm = linha.substring(4,5);
        }
    }

}
