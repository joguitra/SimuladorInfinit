package br.com.fulltime.fullarm.simulador.application.pesquisacid;

import javafx.scene.control.ListView;

import java.util.ArrayList;

public class ListaCodigo {
    private  ArrayList<CodigoCID> listacodigoid = new ArrayList<>();
    private CodigoCID codigoid = new CodigoCID();

    public void montarLista(){

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("1","120","Pânico com acionamento de sirene");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("1" ,"122","Pãnico silencioso");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("1","130","Disparo de circle");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("1","133","Disparo de circle 24 horas");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("1","146","Disparo de circle silenciosa");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("1","147","Falha de comunicação com o sensor");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("1","301","Falha de AC");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("1","302","Bateria principal baixa");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("1","305","Reset da central");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("1","309","Falha no teste de bateria principal");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("1","311","Bateria principal ausente");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("1","384","Bateria baixa de sensor sem fio");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("1","401","Desarme");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("1","403","Arme automático");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("1","407","Desarme remoto");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("1","456","Arme parcial");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("1","602","Teste periódico");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("1","840","Disparo de circle aberta Shox");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("3","120","Restauração de pânico com acionamento de sirene");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("3","122","Restauração de pânico silencioso");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("3","130","Restauração de disparo de circle");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("3","133","Restauração de disparo de circle 24 horas");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("3","146","Restauração de disparo de circle silenciosa");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("3","147","Restauração de falha de comunicação com o sensor");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("3","301","Restauração de falha de AC");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("3","302","Restauração de bateria principal baixa");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("3","309","Teste de bateria principal OK");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("3","311","Restauração de bateria principal ausente");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("3","384","Restauração de bateria baixa de sensor sem fio");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("3","401","Arme");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("3","407","Arme remoto");
        listacodigoid.add(codigoid);

        codigoid = new CodigoCID();
        codigoid.criarCodigoCID("3","840","Restauração de disparo de circle abertura Shox");
        listacodigoid.add(codigoid);



    }

    public  void  imprimirLista(ListView<String> lista){
        for (CodigoCID a : listacodigoid) {
            lista.getItems().addAll(a.impremirCID());
        }

    }


    public ArrayList<CodigoCID> getListacodigoid() {
        return listacodigoid;
    }
}
