package br.com.fulltime.fullarm.simulador.application.circle;

import br.com.fulltime.fullarm.simulador.core.StatusZona;
import javafx.scene.shape.Circle;

import static br.com.fulltime.fullarm.simulador.core.StatusZona.*;

public class ZonaCircle extends CircleStatus {

     private StatusZona status = Fechado;
     private SetColor setarcor = new SetColor();

    public ZonaCircle(Circle circle, int numeroidentificador) {
        super(circle, numeroidentificador);
    }


    public void alterarOrdemStatusZona(){
        switch (status) {
            case Fechado:
                setarcor.alterarCorVerde(circle);
                status= Aberto;
                break;
            case Aberto:
                setarcor.alterarCorVermelha(circle);
                status = Fechado;
                break;
            case Inibido:
                setarcor.alterarCorBranca(circle);
                status = Inibido;
        }
    }

    public void alterarStatusEspecificoZona(StatusZona statusespecifico){
        switch (statusespecifico){
            case Aberto:
                setarcor.alterarCorVermelha(circle);
                status= Aberto;
                break;
            case Inibido:
                setarcor.alterarCorBranca(circle);
                status = Inibido;
                break;
            case Fechado:
                setarcor.alterarCorVerde(circle);
                status = Fechado;
                break;
            }
    }



    public StatusZona getStatus() {
        return status;
    }

}
