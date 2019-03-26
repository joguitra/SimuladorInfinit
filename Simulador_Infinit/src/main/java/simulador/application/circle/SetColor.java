package simulador.application.circle;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SetColor {

    public void alterarCorVermelha(Circle circlezona){
        circlezona.setFill(Color.rgb(255, 31, 31));
    }

    public void alterarCorVerde(Circle circlezona){ circlezona.setFill(Color.rgb(27, 235, 0));
    }
    public void alterarCorBranca(Circle circlezona){
        circlezona.setFill(Color.rgb(255,255,255));
    }

}
