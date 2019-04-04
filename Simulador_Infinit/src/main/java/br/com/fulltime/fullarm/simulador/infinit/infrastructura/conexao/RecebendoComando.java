package br.com.fulltime.fullarm.simulador.infinit.infrastructura.conexao;

import br.com.fulltime.fullarm.simulador.infinit.application.circle.ConectadoCircle;
import br.com.fulltime.fullarm.simulador.infinit.application.terminal.Terminal;
import br.com.fulltime.fullarm.simulador.infinit.core.PGM;
import br.com.fulltime.fullarm.simulador.infinit.core.Particao;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import br.com.fulltime.fullarm.simulador.infinit.application.controles.EsconderPane;
import br.com.fulltime.fullarm.simulador.infinit.application.controles.Mensagem;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class RecebendoComando {


    private boolean conectarservidor,reconctar = true;
    private HexTraducao tradutor = new HexTraducao();
    private InputStream entrada;
    private Terminal terminal;
    private EsconderPane esconderPane;
    private ConectadoCircle conectado;
    private Label labeldesconectado;
    private Particao particao1;
    private Particao particao2;
    private PrintStream saida;
    private Button desconectar;
    private PGM pgm;
    private String primeirodigito,seguendodigito;
    private String linha;
    private String numeroidentificador;
    private String pedidoservidorpgm;
    private ArrayList<Particao> listaparticao = new ArrayList<>();
    private int i;



    public void definirResposta (InputStream entrada, Terminal terminal, EsconderPane esconderPane,
                                 ConectadoCircle conectado, Label labeldesconectado, Particao particao1,
                                 Particao particao2, PGM pgm , PrintStream saida, Button btndesconectar) {
        this.entrada = entrada;
        this.terminal = terminal;
        this.esconderPane = esconderPane;
        this.conectado = conectado;
        this.labeldesconectado = labeldesconectado;
        this.particao1 = particao1;
        this.particao2 = particao2;
        this.saida = saida;
        this.desconectar = btndesconectar;
        listaparticao.add(particao1);
        listaparticao.add(particao2);
        this.pgm = pgm;
    }

    public void receberResposta(){
        try {
            while (!conectarservidor) {


                int qtdBytesDisponiveis = entrada.available();
                if (qtdBytesDisponiveis > 0) {

                    byte[] dado = new byte[qtdBytesDisponiveis];
                    entrada.read(dado);

                    linha = new String(dado);

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
                    break;
                }
            }

        } catch (IOException ignorar) {
        }

    }


    public void desconecteServidor(){
        conectarservidor = false;
        reconctar=true;
        terminal.limparTerminal();
        esconderPane.esconderIniciacao();
        labeldesconectado.setVisible(true);
        Platform.runLater(() -> {
            desconectar.setText("Conectar");
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
        String resposta = "";
        boolean iniciocomando = true;
        for (Particao particao: listaparticao) {
            if(numeroidentificador.equals("0")) {
                resposta += particao.statusParticao(iniciocomando);
                iniciocomando = false;
            }
            else{
                i++;
                if (i == Integer.valueOf(numeroidentificador)) {
                    resposta= particao.statusParticao(iniciocomando);
                   terminal.printTerminal(resposta);
                   saida.print(resposta);
                }
            }
        }
        terminal.printTerminal(resposta);
        saida.print(resposta);
    }


    public void armaParticao(){
        separaNumeroIdetificador();
        for (Particao particao: listaparticao) {
              i++;
              if(i==Integer.valueOf(numeroidentificador)){
                    Boolean armacomsucesso = particao.armarParticao();
                    String resposta = new String();
                        if(armacomsucesso) {
                            resposta ="AO" + particao.printStatus(particao);
                            saida.print(resposta);
                            terminal.printTerminal(resposta);
                        }
                        if(!armacomsucesso) {
                            resposta = "AE" + particao.erroArmeDesarme();
                            saida.print(resposta);
                          terminal.printTerminal(resposta);
                        }
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
                String resposta = "DO"+particao.printStatus(particao);
                saida.print(resposta);
                terminal.printTerminal(resposta);
                particao.desarmaParticao();
                break;
            }
        }
    }

    public void mapaParticaoSetor(){
        String mapaparticao= "P";
        for (Particao particao: listaparticao) {
            mapaparticao +=particao.mapParticao();
        }
        terminal.printTerminal(mapaparticao);
        saida.print(mapaparticao);
    }

    public void separaNumeroIdetificador() {
        if(linha.length() == 4){
            numeroidentificador = linha.substring(2,3);

        }
        else {
            numeroidentificador = linha.substring(2,4);
        }
    }

    public boolean getConectarservidor() {
        return conectarservidor;
    }
    public void setConectarservidor(boolean conectarservidor){
        this.conectarservidor=conectarservidor;
    }
    public boolean getReconectar() {
        return reconctar;
    }
    public void setReconctar(boolean reconctar){
        this.reconctar=reconctar;
    }

}
