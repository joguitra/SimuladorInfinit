package br.com.fulltime.fullarm.simulador.infinit.core;

import br.com.fulltime.fullarm.simulador.infinit.application.circle.PGMCircle;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class PGM {
    private PGMCircle pgmcircle;
    private ArrayList<PGMCircle> listapgm = new ArrayList();
    private Boolean acionado = false;
    private String numeroidentificador;


    public void adicionarPGM(Circle circle , int numeropgm){
        pgmcircle = new PGMCircle(circle, numeropgm);
        listapgm.add(pgmcircle);
    }

    public String alterarStatus(Circle circle) {
        for (PGMCircle pgm : listapgm) {
            if (circle.equals(pgm.getCircle())) {
                acionado = pgm.alterarStatusPGM();
                numeroidentificador = String.valueOf(pgm);
                return statusPGM(String.valueOf(numeroidentificador));
            }
        }

        return null;
    }

    public String alterarStatusServidor(String numeroidentificador){
         for (PGMCircle  pgm : listapgm) {
            if (numeroidentificador.equals(pgm.getNumeroidentificadorString())) {
                pgm.alterarStatusPGM();
                return  "W"+numeroidentificador+"RP";
            }
        }
         return null;
    }

    public String statusPGM(String numeroidentificador){
        for (PGMCircle pgm :listapgm) {
            if(numeroidentificador.equals(pgm.getNumeroidentificadorString())){
                acionado = pgm.getAcionado();
                break;
            }
        }

        if(acionado) { return "W"+numeroidentificador+"OP";}

        if(!acionado){ return "W"+numeroidentificador+"CP";}

        return null;
    }

    public void  resetPGM(){
        for (PGMCircle pgm:listapgm ){
            pgm.reiniciarPGM();
        }
    }
}
