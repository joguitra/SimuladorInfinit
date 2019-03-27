package br.com.fulltime.fullarm.simulador.infinit.core;

import br.com.fulltime.fullarm.simulador.infinit.application.circle.PGMCircle;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class PGM {
    private PGMCircle pgmcircle;
    private ArrayList<PGMCircle> listapgm = new ArrayList();
    private Boolean acionado;
    private String numeroidentificador;

    public void adicionarPGM(Circle circle , int numeropgm){
        pgmcircle = new PGMCircle(circle, numeropgm);
        listapgm.add(pgmcircle);
    }

    public String alterarStatus(Circle circle){
        for (PGMCircle pgm:listapgm) {
            if(circle.equals(pgm.getCircle())) {
                acionado = pgm.alterarStatusPGM(circle);
                numeroidentificador = String.valueOf(pgm);
                break;
            }
        }
        if(acionado) return "W"+numeroidentificador+"OR";

        if(!acionado) return  "W"+numeroidentificador+"CR";
                return null;
    }

    public void  resetPGM(){
        for (PGMCircle pgm:listapgm ){
            pgm.reiniciarPGM();
        }
    }
}