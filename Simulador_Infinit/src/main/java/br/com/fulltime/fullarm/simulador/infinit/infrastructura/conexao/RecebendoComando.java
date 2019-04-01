package br.com.fulltime.fullarm.simulador.infinit.infrastructura.conexao;

import br.com.fulltime.fullarm.simulador.infinit.application.circle.ConectadoCircle;
import br.com.fulltime.fullarm.simulador.infinit.application.terminal.Terminal;
import br.com.fulltime.fullarm.simulador.infinit.core.PGM;
import br.com.fulltime.fullarm.simulador.infinit.core.Particao;
import javafx.application.Platform;
import javafx.scene.control.Label;
import br.com.fulltime.fullarm.simulador.infinit.application.controles.EsconderPane;
import br.com.fulltime.fullarm.simulador.infinit.application.controles.Mensagem;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class RecebendoComando {


    private boolean desconetado;
    private HexTraducao tradutor = new HexTraducao();
    private InputStream entrada;
    private Terminal terminal;
    private EsconderPane esconderPane;
    private ConectadoCircle conectado;
    private Label labeldesconectado;
    private Particao particao1;
    private Particao particao2;
    private PrintStream saida;
    private PGM pgm;
    private String primeirodigito,seguendodigito;
    private String linha;
    private String numeroidentificador;
    private String pedidoservidorpgm;
    private ArrayList<Particao> listaparticao = new ArrayList<>();
    private int i;


    public void definirResposta (InputStream entrada, Terminal terminal, EsconderPane esconderPane,
                                 ConectadoCircle conectado, Label labeldesconectado, Particao particao1,
                                 Particao particao2, PGM pgm , PrintStream saida) {
        this.entrada = entrada;
        this.terminal = terminal;
        this.esconderPane = esconderPane;
        this.conectado = conectado;
        this.labeldesconectado = labeldesconectado;
        this.particao1 = particao1;
        this.particao2 = particao2;
        this.saida = saida;
        listaparticao.add(particao1);
        listaparticao.add(particao2);
        this.pgm = pgm;
    }

    public void receberResposta(){
        try {
            while (!desconetado) {


                int qtdBytesDisponiveis = entrada.available();
                if (qtdBytesDisponiveis > 0) {

                    byte[] dado = new byte[qtdBytesDisponiveis];
                    entrada.read(dado);

                    linha = new String(dado);
                    linha = tradutor.traduzirCodigoHex(linha);

                    terminal.printResposta(linha);

                    try {
                        primeirodigito = linha.substring(0,1);
                        i=0;
                        switch (primeirodigito) {
                            case "-":
                                desconecteServidor();
                                break;
                            case "W":
                                diferenciadorPGMParticao();
                                break;
                            }
                    }catch (Exception ignorarr){}

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

    public void diferenciadorPGMParticao(){
        seguendodigito = linha.substring(1,2);

        switch (seguendodigito){
            case "S":
                pedirStatusParticao();
                break;
            case "A":
                armaParticao();
                break;
            case "D":
                desarmeParticao();
                break;
            case "P":
                mapaParticaoSetor();
                break;
            default:
                alterarPGM();
        }
    }

    public void pedirStatusParticao(){
        separaNumeroIdetificador();
        for (Particao particao: listaparticao) {
            i++;
            if(i==Integer.valueOf(numeroidentificador)){
                particao.desarmaParticao();
            }
        }
    }
    public void armaParticao(){
        separaNumeroIdetificador();
        for (Particao particao: listaparticao) {
            i++;
            if(i==Integer.valueOf(numeroidentificador)){
                particao.armarParticao();
                break;
            }
        }

    }
    public void alterarPGM(){
        if(linha.length() == 4){
            numeroidentificador = linha.substring(1,2);
            pedidoservidorpgm = linha.substring(2,3);
        }
        else {
            numeroidentificador = linha.substring (1,3);
            pedidoservidorpgm = linha.substring(3,4);
        }
        switch (pedidoservidorpgm){
            case "S":
                String text =(pgm.statusPGM(numeroidentificador));
                terminal.printTerminal(text);
                saida.print(text);
                break;
            case "E":
                String text1 =(pgm.alterarStatusServidor(numeroidentificador));
                terminal.printTerminal(text1);
                saida.print(text1);
                break;
        }
    }
    public void desarmeParticao(){
        separaNumeroIdetificador();
        for (Particao particao: listaparticao) {
            i++;
            if(i==Integer.valueOf(numeroidentificador)){
                particao.desarmaParticao();
                break;
            }
        }
    }

    public void mapaParticaoSetor(){
        for (Particao particao: listaparticao) {
            particao.statusParticao();
        }
    }

    public void separaNumeroIdetificador() {
        if(linha.length() == 4){
            numeroidentificador = linha.substring(2,3);

        }
        else {
            numeroidentificador = linha.substring(2,4);
        }
    }

}
