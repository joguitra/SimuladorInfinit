package br.com.fulltime.fullarm.simulador.infinit.infrastructura.conexao;

import br.com.fulltime.fullarm.simulador.infinit.application.circle.ConectadoCircle;
import br.com.fulltime.fullarm.simulador.infinit.application.terminal.Terminal;
import br.com.fulltime.fullarm.simulador.infinit.core.PGM;
import br.com.fulltime.fullarm.simulador.infinit.core.Particao;
import br.com.fulltime.fullarm.simulador.infinit.infrastructura.particao.DuplaZona;
import br.com.fulltime.fullarm.simulador.infinit.infrastructura.particao.TodasParticao;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import br.com.fulltime.fullarm.simulador.infinit.application.controles.EsconderPane;
import br.com.fulltime.fullarm.simulador.infinit.application.controles.Mensagem;


import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

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
            primeirodigito = linha.substring(0,2);
            i=0;
            switch (primeirodigito) {
                case "2D":
                    desconecteServidor();
                    break;
                    case "57":
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
        seguendodigito = linha.substring(3,5);

        switch (seguendodigito){
            case "53":
                return pedirStatusParticao();
            case "41":
                return armaParticaoComando();
            case "44":
                return desarmeParticaoComando();
            case "45":
                return armedesarmeParticao();
            case "42":
                return inibidoZona();
            default:
                return alterarPGM();
        }
    }

    public byte[] inibidoZona(){
        int I = 6;
        int F = 8;
        for (Particao particao: todasparticao.getListaparticao()) {

            particao.inibirzona(linha.substring(I,F));
            I+=3;
            F+=3;

        }

        buffer =ByteBuffer.allocate(1);
        buffer.put("B".getBytes());
        byte[] resultado = buffer.array();
        return resultado;
    }



    public byte[] armedesarmeParticao(){
        separaNumeroIdetificador();
        for (Particao particao: todasparticao.getListaparticao()){
            i++;
            if (numeroidentificador.equals("0")) {
                armedesarmeParticaoUnica(particao);
            }
            if(numeroidentificador.equals(String.valueOf(i))){
                armedesarmeParticaoUnica(particao);
            }
        }
        buffer = ByteBuffer.allocate(6);
        String cabecario="WS";
        buffer.put(cabecario.getBytes());
        buffer.put(todasparticao.statusParticao());
        byte[] resultado =buffer.array();
        return  resultado;
    }

    public byte[] armedesarmeParticaoUnica(Particao particao){
        if(!particao.getStatusarmada()){
            byte[] resultado =armeParticao(particao);

            if(resultado.length == 4){
                buffer = ByteBuffer.allocate(6);
                String cabecario ="WS";
                buffer.put(cabecario.getBytes());
                buffer.put(resultado);
                byte[] resposta = buffer.array();
                return  resposta;
            }
            if(resultado.length == 64){
                buffer = ByteBuffer.allocate(66);
                String cabecario = "AE";
                buffer.put(cabecario.getBytes());
                buffer.put(resultado);
                byte[] resposta = buffer.array();
                return  resposta;
            }
        }
        if(particao.getStatusarmada()){
            buffer = ByteBuffer.allocate(6);
            byte[] resultado =desarmeParticao(particao);
            String cabecario ="WS";
            buffer.put(cabecario.getBytes());
            buffer.put(resultado);
            byte[] resposta = buffer.array();
            return  resposta;

        }
        return null;
    }
    public byte[] pedirStatusParticao(){
        buffer = ByteBuffer.allocate(33);
        String cabecario = "S";
        buffer.put(cabecario.getBytes());
        buffer.put(todasparticao.statusZonas());
        byte[] resultado = buffer.array();
        return  resultado;
    }
    public byte[] desarmeParticao(Particao particao){
        particao.desarmaParticao();
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(todasparticao.statusParticao());
        byte[] resultado = buffer.array();
        return  resultado;
    }

    public byte[] armeParticao(Particao particao){
        Boolean armacomsucesso = particao.armarParticao();

        if(armacomsucesso) {
            ByteBuffer buffer = ByteBuffer.allocate(4);
            buffer.put(todasparticao.statusParticao());
            byte[] resultado = buffer.array();
            return  resultado;
        }
        if(!armacomsucesso) {
            ByteBuffer buffer = ByteBuffer.allocate(32);
            buffer.put(todasparticao.statusZonas());
            byte[] resultado = buffer.array();
            return  resultado;

        }
        return  null;
    }


    public byte[] armaParticaoComando(){
        separaNumeroIdetificador();
        for (Particao particao: todasparticao.getListaparticao()) {
              i++;
              if(i==Integer.valueOf(numeroidentificador)){

                       byte[] resultado = armeParticao(particao);
                       if(resultado.length ==4){
                           buffer = ByteBuffer.allocate(6);
                           String cabecario = "AO";
                           buffer.put(cabecario.getBytes());
                           buffer.put(resultado);
                           byte[] resposta = buffer.array();
                           return  resposta;
                       }
                       if(resultado.length == 32){
                           buffer = ByteBuffer.allocate(34);
                           String cabecario = "AE";
                           buffer.put(cabecario.getBytes());
                           buffer.put(resultado);
                           byte[] resposta = buffer.array();
                           return  resposta;
                       }
                    }

              }
        return null;
        }
    public byte[] alterarPGM(){
        if(linha.length() == 12){
            numeroidentificador = linha.substring(4,5);
            pedidoservidorpgm = linha.substring(6,8);
        }
        else {


            numeroidentificador = linha.substring (4,5) + linha.substring(7,8);
            pedidoservidorpgm = linha.substring(9,11);
        }
        switch (pedidoservidorpgm){
            case "53":
                return HexTraducao.hexStringToBytes(pgm.statusPGM(numeroidentificador));
            case "45":
                return HexTraducao.hexStringToBytes(pgm.alterarStatusServidor(numeroidentificador));

        }
        return null;
    }
    public byte [] desarmeParticaoComando(){
        separaNumeroIdetificador();
        for (Particao particao: todasparticao.getListaparticao()) {
            i++;
            if(i==Integer.valueOf(numeroidentificador)){
               byte[] resultado = desarmeParticao(particao);
               buffer =  ByteBuffer.allocate(6);
               String cabecario = "DO";
               buffer.put(cabecario.getBytes());
               buffer.put(resultado);
               byte[]resposta = buffer.array();
               return  resposta;
            }
        }
        return null;
    }


    public void separaNumeroIdetificador() {
        if(linha.length() == 12){
            numeroidentificador = linha.substring(7,8);
        }
        else {
            numeroidentificador = linha.substring(7,8) + linha.substring(10,11);
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
