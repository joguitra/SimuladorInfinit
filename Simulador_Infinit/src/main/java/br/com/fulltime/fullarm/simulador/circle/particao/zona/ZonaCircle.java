package br.com.fulltime.fullarm.simulador.circle.particao.zona;

import br.com.fulltime.fullarm.simulador.circle.CircleStatus;
import br.com.fulltime.fullarm.simulador.circle.SetColor;
import javafx.scene.shape.Circle;

import static br.com.fulltime.fullarm.simulador.circle.particao.zona.StatusZona.*;

public class ZonaCircle extends CircleStatus {

     private StatusZona status = Fechado;
     private SetColor setarcor = new SetColor();

    public ZonaCircle(Circle circle, int numeroidentificador) {
        super(circle, numeroidentificador);
    }


    public void AlterarOrdemStatusZona(){
        switch (status) {

            case Fechado:
                setarcor.alterarCorVermelha(circle);
                status= Aberto;
                break;
            case Aberto:
                setarcor.alterarCorAmarela(circle);
                status = Disparado;
                break;
            case Disparado:
                setarcor.alterarCorBranca(circle);
                status = Inibido;
                break;
            case Inibido:
                setarcor.alterarCorVerde(circle);
                status = Fechado;
                break;
        }
    }

    public void AlterarStatusEspecificoZona(StatusZona statusespecifico){
        switch (statusespecifico){
            case Aberto:
                setarcor.alterarCorVermelha(circle);
                status= Aberto;
                break;
            case Disparado:
                setarcor.alterarCorAmarela(circle);
                status = Disparado;
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
