package br.com.fulltime.fullarm.simulador.circle;

import javafx.scene.shape.Circle;
import jdk.internal.util.EnvUtils;

public abstract class CircleStatus {
    protected Circle circle;
    protected int numeroidentificador;

    public CircleStatus(Circle circle, int numeroidentificador){
        this.circle = circle;
        this.numeroidentificador = numeroidentificador;

    }

    @Override
    public String toString() {
        return String.valueOf(numeroidentificador);
    }

    public Circle getCircle() {
        return circle;
    }

    public int getNumeroidentificador() {
        return numeroidentificador;
    }

}
