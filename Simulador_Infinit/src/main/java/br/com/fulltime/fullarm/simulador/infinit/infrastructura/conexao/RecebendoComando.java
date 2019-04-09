package br.com.fulltime.fullarm.simulador.infinit.infrastructura.conexao;

import br.com.fulltime.fullarm.simulador.infinit.application.circle.ConectadoCircle;
import br.com.fulltime.fullarm.simulador.infinit.application.terminal.Terminal;
import br.com.fulltime.fullarm.simulador.infinit.core.PGM;
import br.com.fulltime.fullarm.simulador.infinit.core.Particao;
import br.com.fulltime.fullarm.simulador.infinit.infrastructura.particao.TodasParticao;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import br.com.fulltime.fullarm.simulador.infinit.application.controles.EsconderPane;
import br.com.fulltime.fullarm.simulador.infinit.application.controles.Mensagem;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class RecebendoComando {


    private boolean conectarservidor,reconctar = true;
    private InputStream entrada;
    private Terminal terminal;
    private EsconderPane esconderPane;
    private ConectadoCircle conectado;
    private Label labeldesconectado;
    private Button desconectar;
    private PGM pgm;
    private TodasParticao todasparticao;
    private String primeirodigito,seguendodigito;
    private String linha;
    private String numeroidentificador;
    private String pedidoservidorpgm;
    private int i;
    private HexTraducao hexTraducao = new HexTraducao();
    private ByteBuffer buffer;



    public void definirResposta (InputStream entrada, Terminal terminal, EsconderPane esconderPane,
                                 ConectadoCircle conectado, Label labeldesconectado, PGM pgm ,
                                 Button btndesconectar, TodasParticao todasparticao) {
        this.entrada = entrada;
        this.terminal = terminal;
        this.esconderPane = esconderPane;
        this.conectado = conectado;
        this.labeldesconectado = labeldesconectado;
        this.desconectar = btndesconectar;
        this.pgm = pgm;
        this.todasparticao = todasparticao;
    }

    public byte[] receberResposta(String linha) throws IOException {
        this.linha = linha;
        try {
            primeirodigito = linha.substring(0,1);
            i=0;
            switch (primeirodigito) {
                case "-":
                    desconecteServidor();
                    break;
                    case "W":
                        return diferenciadorPGMParticao();
            }
        }catch (Exception ignorarr){}
        return null;
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

    public byte[] diferenciadorPGMParticao(){
        seguendodigito = linha.substring(1,2);

        switch (seguendodigito){
            case "S":
                return pedirStatusParticao();
            case "A":
                return armaParticao();
            case "D":
                return desarmeParticao();
            case "P":
                return mapaParticaoSetor();
            default:
                return alterarPGM();
        }
    }

    public byte[] pedirStatusParticao(){
        buffer = ByteBuffer.allocate(5);
        String cabecario = "S";
        buffer.put(cabecario.getBytes());
        buffer.put(todasparticao.statusParticao());
        byte[] resultado = buffer.array();
        return  resultado;
    }


    public byte[] armaParticao(){
        separaNumeroIdetificador();
        for (Particao particao: todasparticao.getListaparticao()) {
              i++;
              if(i==Integer.valueOf(numeroidentificador)){
                    Boolean armacomsucesso = particao.armarParticao();
                    byte[] resposta = new byte[]{};
                        if(armacomsucesso) {
                            resposta = hexTraducao.hexStringToBytes("AO" + todasparticao.statusParticao());

                        }
                        if(!armacomsucesso) {
                            ByteBuffer buffer = ByteBuffer.allocate(6);
                            String cabecario = "AE";
                            buffer.put(cabecario.getBytes());
                            buffer.put(particao.erroArmeDesarme());
                            byte[] resultado = buffer.array();
                            return  resultado;

                        }
                        return resposta;
                    }

              }
        return null;
        }
    public byte[] alterarPGM(){
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
                return hexTraducao.hexStringToBytes(pgm.statusPGM(numeroidentificador));
            case "E":
                return hexTraducao.hexStringToBytes(pgm.alterarStatusServidor(numeroidentificador));

        }
        return null;
    }
    public byte [] desarmeParticao(){
        separaNumeroIdetificador();
        for (Particao particao: todasparticao.getListaparticao()) {
            i++;
            if(i==Integer.valueOf(numeroidentificador)){
                byte[] resposta = hexTraducao.hexStringToBytes("DO"+todasparticao.statusParticao());
                particao.desarmaParticao();
                return resposta;
            }
        }
        return null;
    }

    public byte[] mapaParticaoSetor(){
        ByteBuffer buffer = ByteBuffer.allocate(65);
        String cabecario = "P";
        buffer.put(cabecario.getBytes());

        for (Particao particao: todasparticao.getListaparticao() ) {
            buffer.put(particao.mapParticao());
        }

        byte[] resultado = buffer.array();
        return  resultado;
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
