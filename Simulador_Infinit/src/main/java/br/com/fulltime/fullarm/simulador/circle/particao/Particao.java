package br.com.fulltime.fullarm.simulador.circle.particao;

import br.com.fulltime.fullarm.simulador.circle.particao.particaocircle.ParticaoCircle;
import br.com.fulltime.fullarm.simulador.circle.particao.zona.StatusZona;
import br.com.fulltime.fullarm.simulador.circle.particao.zona.ZonaCircle;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Particao  {


    private ArrayList<ZonaCircle> listazonas = new ArrayList<>();
    private ZonaCircle zonaCircle;
    private String zonasstatusabertos,zonasstatusdesparada;
    private ParticaoCircle particaocircle;


    public Particao (Circle circle ,int numeroindetificador){
        particaocircle = new ParticaoCircle(circle,numeroindetificador);
    }

    public void adicionarZonas(Circle circle , int numerozona){
        zonaCircle = new ZonaCircle(circle, numerozona);
        listazonas.add(zonaCircle);
    }


    public void alterarZona(Circle circle){
        for (ZonaCircle zona:listazonas) {
            if(zona.getCircle().equals(circle))
            zona.AlterarOrdemStatusZona();
        }
    }

    public  void alterarTodasZonas(StatusZona statuszona){
        for (ZonaCircle zona :listazonas) {
            zona.AlterarStatusEspecificoZona(statuszona);
        }
    }

    public String checkoutZonaAberta() {
        zonasstatusabertos = null;
        for (ZonaCircle zona : listazonas) {
            if (StatusZona.Aberto.equals(zona.getStatus())) {
                if(zonasstatusabertos != null)
                zonasstatusabertos += zona + " ";
                else
                    zonasstatusabertos = zona + " ";
            }
        }
        return zonasstatusabertos;
    }

    public String checkoutZonaDesparada(){
        zonasstatusdesparada =  null;
        for (ZonaCircle zona: listazonas) {
            if(StatusZona.Disparado.equals(zona.getStatus())){
                if (zonasstatusdesparada != null)
                    zonasstatusdesparada += zona + " ";
                else
                    zonasstatusdesparada = zona + " ";
            }



        }
        return zonasstatusdesparada;
    }


    public  String alterarStatusParticao(){

        String armado =particaocircle.alterarStatusParticao(checkoutZonaAberta(),checkoutZonaDesparada());
        if(armado.equals("Desarmado")){
            for (ZonaCircle zona:listazonas) {
                if(StatusZona.Inibido.equals(zona.getStatus())){
                    zona.AlterarStatusEspecificoZona(StatusZona.Aberto);
                }
            }
        }
        return armado ;
    }

    public  void reiniciarParticao(){

        particaocircle.reiniciarParticao();
        for (ZonaCircle zona:listazonas) {
            zona.AlterarStatusEspecificoZona(StatusZona.Fechado);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(particaocircle);
    }
}

