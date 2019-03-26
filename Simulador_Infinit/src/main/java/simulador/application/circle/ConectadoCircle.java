package simulador.application.circle;

import javafx.scene.control.Label;
import javafx.scene.shape.Circle;


public class ConectadoCircle extends CircleStatus {
    private SetColor setarcor = new SetColor();
    private Label lconectado;

    public ConectadoCircle(Circle circle, int numeroidentificador) {
        super(circle, numeroidentificador);
    }
    public void definirLabel(Label lconectado){
        this.lconectado = lconectado;
    }

    public void alterarStatusConectado(){
        setarcor.alterarCorVerde(circle);
        lconectado.setText("Conectado");
    }

    public void alterarStatusDesconectado(){
        setarcor.alterarCorVermelha(circle);
        lconectado.setText("Desconectado");
    }
}
