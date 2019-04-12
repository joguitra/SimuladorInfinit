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
                codigo = "$" + usuario.getText() + "3" + evento.getText() + particacao.getText() + setor.getText()+sequencia+"]";
            }
            if (qualificador.getSelectionModel().getSelectedItem().equals("R 1")) {
                codigo = "$" + usuario.getText() + "1" + evento.getText() + particacao.getText() + setor.getText()+sequencia+"]";
            }
            sequenciaCodigo();
            return codigo;
        }
        return null;
    }

    public String enviarTamper(int numeroidentificadorSetor,int numeroidentificadorParticao){

        if(numeroidentificadorSetor >=10){
            return  "$"+ "0001" +"1"+"1440"+numeroidentificadorParticao +"0"+numeroidentificadorSetor +sequencia+"]";
        }
        return  "$"+ "0001" +"1"+"1440"+numeroidentificadorParticao +"00"+numeroidentificadorSetor +sequencia+"]";
    }



    public int sequenciaCodigo(){
        sequencia++;
        if (sequencia >= 10){
            sequencia=0;
        }
        return sequencia;
    }

    public String pgmCID (){
        return  "$"+ "0001" +"1"+"422"+"01"+"001"+sequencia+"]";
    }


    public String eventoInibido (int numeroidentificadorSetor,int numeroidentificadorParticao){
        if(numeroidentificadorSetor >=10){
            return  "$"+ "0001" +"1"+"5070"+numeroidentificadorParticao +"0"+numeroidentificadorSetor +sequencia+"]";
        }
        return  "$"+ "0001" +"1"+"5070"+numeroidentificadorParticao +"00"+numeroidentificadorSetor +sequencia+"]";
    }
    public String formacodigoCompleto(Boolean estaarmado,String particao){
        try {
            if (estaarmado) {
                codigo = "$" + usuario.getText() + "3" + "4010" + particao + setor.getText() + sequencia + "]";
                sequenciaCodigo();
            }
            if (!estaarmado) {
                codigo = "$" + usuario.getText() + "1" + "4010" + particao + setor.getText() + sequencia + "]";
                sequenciaCodigo();
            }
            return codigo;
        }catch (NullPointerException ignorar){return null;}
    }

    public String armeCID(String particao) {

        String codigo= "$" + "0001" + "3" + "4010" + particao + "001" + sequencia + "]";
        return  codigo;
    }
    public String desarmeCID(String particao) {
        return "$" + "0001" + "1" + "4010" + particao + "001" + sequencia + "]";
    }
    public String stayCID(String particao){
        return  "$" + "0001" + "1" + "4410" +particao +"001" + sequencia + "]";
    }

    public String disparar(int  particao,int setor) {
        if (setor > 10) {
            return "$" + "0001" + "1" + "130"+"0" + particao + "0"+setor + sequencia + "]";
        }
        return "$" + "0001" + "1"+"130" + "0" + particao + "00"+setor + sequencia + "]";
    }

}