package br.com.fulltime.fullarm.simulador.infinit.application.circle;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.shape.Circle;

public class ParticaoCircle extends CircleStatus {


    private  String statusarmado;

    public ParticaoCircle(Circle circle, int numeroidentificador) {
        super(circle, numeroidentificador);
    }

    public boolean armarParticao(String zonaaberta){

        if(condicaoArma(zonaaberta) != null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Armação de partição cancelado");
            alert.setHeaderText("Armação foi cancelada devido "+statusarmado);
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    alert.close();
                }
            });

        }
        else {
            return true;
    }
        return  false;
    }

    public String condicaoArma(String zonaaberta  ){
        statusarmado= null;

        if(zonaaberta != null){
            statusarmado = "Zona aberta "+zonaaberta;
        }
        if(statusarmado != null ){
            statusarmado = "Armação cancelada devido a "+statusarmado;
        }
        return statusarmado;
    }


    @Override
    public String toString() {
        return super.toString();
    }

}
