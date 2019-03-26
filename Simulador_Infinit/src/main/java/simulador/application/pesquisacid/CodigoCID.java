package simulador.application.pesquisacid;

public class CodigoCID {
    private String qualificador ,evento;
    private  String  descricao;
    private String codigo;


    public void criarCodigoCID (String qualficador , String evento,String descricao){
        this.qualificador = qualficador;
        this.evento = evento;
        this.descricao = descricao;
    }


    public String impremirCID() {

        codigo =qualificador+" " + evento+" "+ descricao;
        return codigo;
    }

}
