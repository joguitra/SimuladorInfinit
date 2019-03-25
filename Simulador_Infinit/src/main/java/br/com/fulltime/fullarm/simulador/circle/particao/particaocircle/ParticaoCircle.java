package br.com.fulltime.fullarm.simulador.circle.particao.particaocircle;

import br.com.fulltime.fullarm.simulador.circle.CircleStatus;
import br.com.fulltime.fullarm.simulador.circle.SetColor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.shape.Circle;
import static br.com.fulltime.fullarm.simulador.circle.particao.particaocircle.StatusParticao.*;

public class ParticaoCircle extends CircleStatus {


    private SetColor setarcor = new SetColor();
    private  String statusarmado;
    private StatusParticao statusParticao = StatusParticao.Desarmado;

    public ParticaoCircle(Circle circle, int numeroidentificador) {
        super(circle, numeroidentificador);
    }


    public String alterarStatusParticao(String zonaaberta,String zonaarmada){
            switch (statusParticao) {
                case Desarmado:
                    if (armarParticao(zonaaberta, zonaarmada)) {
                        setarcor.alterarCorVermelha(circle);
                        statusParticao = Armado;
                        return "Armado";
                    }
                    break;
                case Armado:
                    setarcor.alterarCorVerde(circle);
                    statusParticao = Desarmado;
                    return "Desarmado";
            }
            return  "Armação Cancelada";
    }

    private boolean armarParticao(String zonaaberta,String zonaarmada){

        if(condicaoArma(zonaaberta,zonaarmada) != null){
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

    public String condicaoArma(String zonaaberta ,String zonadesparada ){
        statusarmado= null;

        if(zonaaberta != null){
            statusarmado = "Zona aberta "+zonaaberta;
        }
        if(zonadesparada != null){
            if(statusarmado != null)
                statusarmado =statusarmado+ "Zona armarda "+ zonadesparada;
            else
                statusarmado = "Zona armarda "+ zonadesparada;
        }

        if(statusarmado != null ){
            statusarmado = "Armação cancelada devido a "+statusarmado;
        }
        return statusarmado;
    }

    public void reiniciarParticao(){
        statusParticao = Desarmado;
        setarcor.alterarCorVerde(circle);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
