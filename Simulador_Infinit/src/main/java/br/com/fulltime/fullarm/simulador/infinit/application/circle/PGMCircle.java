package br.com.fulltime.fullarm.simulador.infinit.application.circle;

import br.com.fulltime.fullarm.simulador.infinit.core.StatusPGM;
import javafx.scene.shape.Circle;

import static br.com.fulltime.fullarm.simulador.infinit.core.StatusPGM.*;


public class PGMCircle extends CircleStatus {
    private StatusPGM status = Desacionar;
    private SetColor setcolor = new SetColor();
    private Boolean acionado = false;

    public PGMCircle(Circle circle, int numeroidentificador) {
        super(circle, numeroidentificador);
    }

    public Boolean alterarStatusPGM() {
        switch (status) {
            case Acionar:
                setcolor.alterarCorVermelha(circle);
                status = Desacionar;
                acionado = false;
                return false;

            case Desacionar:
                setcolor.alterarCorVerde(circle);
                status = Acionar;
                acionado = true;
                return true;

        }
        return null;
    }
    public void reiniciarPGM(){
        status = Desacionar;
        setcolor.alterarCorVermelha(circle);
    }

    public StatusPGM getStatus() {
        return status;
    }

    public Boolean getAcionado() {
        return acionado;
    }

    @Override
    public String toString() {
        return String.valueOf(numeroidentificador);
    }
}


