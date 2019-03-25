package br.com.fulltime.fullarm.simulador.controles;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;



public class EsconderPane {
    private boolean logescondido = false;
    private Pane zona1;
    private Pane zona2;
    private Label labelparticao;
    private ImageView imagem1;
    private ImageView imagem2;
    private Circle efzping;
    private Pane principal;
    private Pane log;
    private Pane pesquisa;

    public void escoderLog(Pane log , Button btnescoderlog ){


        if (!logescondido) {
            btnescoderlog.setText("Escoder Log");
            log.setVisible(false);
            logescondido = true;
        } else {
            btnescoderlog.setText("Mostrar Log");
            logescondido = false;
            log.setVisible(true);
        }
    }


    public  void mostarPaneConexao(){
        principal.setVisible(true);
        log.setVisible(true);
        zona2.setVisible(false);
        imagem2.setVisible(false);
        efzping.setVisible(false);
        imagem1.setVisible(true);
    }
    public  void definirPane(Pane principal,Pane log ,Pane pesquisa){

        this.principal = principal;
        this.log = log;
        this.pesquisa = pesquisa;
    }

    public void definirParticao(Pane zona1 , Pane zona2, Label labelparticao,  ImageView imagem1, ImageView imagem2,Circle efzping){
        this.zona1 = zona1;
        this.zona2 = zona2;
        this.labelparticao = labelparticao;

        this.imagem1 = imagem1;
        this.imagem2 = imagem2;
        this.efzping = efzping;
    }

    public int alterarZona(int zona){
        if (zona <1) {
            zona =2;
        }
        if (zona > 2) {
            zona =1;
        }
        if (zona == 1) {
            zona1.setVisible(true);
            labelparticao.setText("1");
            zona2.setVisible(false);
            imagem1.setVisible(true);
            imagem2.setVisible(false);
            return  1;
        }
        else if (zona == 2) {
            zona2.setVisible(true);
            labelparticao.setText("2");
            zona1.setVisible(false);
            imagem1.setVisible(false);
            imagem2.setVisible(true);
            return  2;
        }
        return  1;
    }

    public void esconderIniciacao(){
        principal.setVisible(false);
        log.setVisible(false);
        pesquisa.setVisible(false);

    }

//    public void mostrarPing(){ efzping.setVisible(true);}
//    public void escondePing(){ efzping.setVisible(false);}

    public void mostrarPesquisa(){
        pesquisa.setVisible(true);
    }

    public void escoderPesquisa(){
        pesquisa.setVisible(false);
    }
}
