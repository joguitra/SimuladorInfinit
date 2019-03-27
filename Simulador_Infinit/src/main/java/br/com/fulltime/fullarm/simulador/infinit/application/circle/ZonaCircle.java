package br.com.fulltime.fullarm.simulador.infinit.application.circle;

import javafx.animation.ScaleTransition;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import br.com.fulltime.fullarm.simulador.infinit.core.StatusZona;
import javafx.scene.shape.Circle;

import static br.com.fulltime.fullarm.simulador.infinit.core.StatusZona.*;

public class ZonaCircle extends CircleStatus {

     private StatusZona status = Fechado;
     private SetColor setarcor = new SetColor();
     private Line tamper;
     private Boolean statustamper = false;
     private Boolean statusinibido = false;

    public ZonaCircle(Circle circle, int numeroidentificador) {
        super(circle, numeroidentificador);
    }

    public void reiniciarzona(){
        tamper.setVisible(false);
        statustamper=false;
        statusinibido = false;
        alterarStatusEspecificoZona(Fechado);

    }

    public void adicinarTamper(Line tamper){
        this.tamper = tamper;
        tamper.setVisible(false);
    }

    public void alterarTamper (){
        if(!statustamper) {
            tamper.setVisible(true);
            statustamper = true;
        }
        else if(statustamper){
            tamper.setVisible(false);
            statustamper = false;
        }
    }

    public void alterarOrdemStatusZona(){
        switch (status) {
            case Fechado:
                setarcor.alterarCorVermelha(circle);
                status= Aberto;
                statusinibido =false;
                break;
            case Aberto:
                setarcor.alterarCorVerde(circle);
                statusinibido =false;
                status = Fechado;
                break;
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
                statusinibido=true;
                break;
            case Fechado:
                setarcor.alterarCorVerde(circle);
                status = Fechado;
                break;
            }
    }


    public void ativarPing(){
        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(500));
        scaleTransition.setNode(circle);
        scaleTransition.setByY(0.5);
        scaleTransition.setByX(0.5);
        scaleTransition.setCycleCount(2);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
    }

    public StatusZona getStatus() {
        return status;
    }

    public Boolean getStatustamper() {
        return statustamper;
    }


    public Boolean getStatusinibido() {
        return statusinibido;
    }
}
