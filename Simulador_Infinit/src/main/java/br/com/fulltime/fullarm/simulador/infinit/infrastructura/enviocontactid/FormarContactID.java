package br.com.fulltime.fullarm.simulador.infinit.infrastructura.enviocontactid;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


public class FormarContactID {

    private TextField usuario;
    private ListView<String> qualificador;
    private TextField evento;
    private TextField setor;
    private TextField particacao;
    private  String codigo;
    private  int sequencia =0;

    public void definirContatctID(TextField usuario, ListView<String> qualificador, TextField evento, TextField particao , TextField setor){
        this.usuario = usuario;
        this.qualificador = qualificador;
        this.evento = evento;
        this.particacao = particao;
        this.setor = setor;
    }

    private boolean condicao(){
        if (usuario.getText().length() ==4 &&
            evento.getText().length() ==3 &&
            setor.getText().length() == 3 &&
            particacao.getText().length() == 2) {
            return true;
        }
        return false;
    }

    public String formarcodigo(){
        if(condicao()){

            if (qualificador.getSelectionModel().getSelectedItem().equals("E 3")) {
                codigo = "$" + usuario.getText() + "3" + evento.getText() + particacao.getText() + setor.getText()+sequencia+"¶";
            }
            if (qualificador.getSelectionModel().getSelectedItem().equals("R 1")) {
                codigo = "$" + usuario.getText() + "1" + evento.getText() + particacao.getText() + setor.getText()+sequencia+"¶";
            }
            sequenciaCodigo();
            return codigo;
        }
        return null;
    }

    public int sequenciaCodigo(){
        sequencia++;
        if (sequencia >= 10){
            sequencia=0;
        }
        return sequencia;
    }
    public String formacodigoCompleto(Boolean estaarmado,String particao){
        try {
            if (estaarmado) {
                codigo = usuario.getText() + "3" + "4010" + particao + setor.getText() + sequencia;
                sequenciaCodigo();
            }
            if (!estaarmado) {
                codigo = usuario.getText() + "1" + "4010" + particao + setor.getText() + sequencia;
                sequenciaCodigo();
            }
            return codigo;
        }catch (NullPointerException ignorar){return null;}
    }

}


