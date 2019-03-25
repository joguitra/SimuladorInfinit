package br.com.fulltime.fullarm.simulador.circle.pgm;

import br.com.fulltime.fullarm.simulador.circle.CircleStatus;
import br.com.fulltime.fullarm.simulador.circle.SetColor;
import javafx.scene.shape.Circle;

import static br.com.fulltime.fullarm.simulador.circle.pgm.StatusPGM.*;


public class PGMCircle extends CircleStatus {
    private StatusPGM status = Desacionar;
    private SetColor setcolor = new SetColor();

    public PGMCircle(Circle circle, int numeroidentificador) {
        super(circle, numeroidentificador);
    }

    public Boolean alterarStatusPGM(Circle circle) {
        this.circle = circle;
        switch (status) {
            case Acionar:
                setcolor.alterarCorVermelha(circle);
                status = Desacionar;
                return false;

            case Desacionar:
                setcolor.alterarCorVerde(circle);
                status = Acionar;
                return true;

        }
        return null;
    }
    public void reiniciarPGM(){
        status = Desacionar;
        setcolor.alterarCorVermelha(circle);
    }

    @Override
    public String toString() {
        return String.valueOf(numeroidentificador);
    }
}


