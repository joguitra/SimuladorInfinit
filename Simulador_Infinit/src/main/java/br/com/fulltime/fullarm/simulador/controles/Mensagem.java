package br.com.fulltime.fullarm.simulador.controles;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Mensagem {


    public void mensagemDesconectado(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Desconexão");
        alert.setHeaderText("Você foi desconectado");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                alert.close();
            }
        });
    }
    public void mensagemErroConectar(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Desconexao");
        alert.setHeaderText("Erro ao tenta conectado ao servidor verifique se foi digitado corretamente  o IP e a PORTA ");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                alert.close();
            }
        });

    }
    public void  messagemErroAuntetificar(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Desconexao");
        alert.setHeaderText("Erro ao logar ao servidor verifique MAC foi digitado corretamente");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                alert.close();
            }
        });
    }
}
