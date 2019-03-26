package br.com.fulltime.fullarm.simulador.core;

import br.com.fulltime.fullarm.simulador.application.circle.ParticaoCircle;
import br.com.fulltime.fullarm.simulador.application.circle.ZonaCircle;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Particao  {


    private ArrayList<ZonaCircle> listazonas = new ArrayList<>();
    private ZonaCircle zonaCircle;
    private String zonasstatusfechada;
    private ParticaoCircle particaocircle;


    public Particao (Circle circle ,int numeroindetificador){
        particaocircle = new ParticaoCircle(circle,numeroindetificador);
    }

    public void adicionarZonas(Circle circle , int numerozona){
        zonaCircle = new ZonaCircle(circle, numerozona);
        listazonas.add(zonaCircle);
    }

    public void alterarZonaInibida(Circle circle){
        for (ZonaCircle zona:listazonas) {
            if(zona.getCircle().equals(circle))
                zona.alterarStatusEspecificoZona(StatusZona.Inibido);
        }
    }

    public void alterarZona(Circle circle){
        for (ZonaCircle zona:listazonas) {
            if(zona.getCircle().equals(circle))
            zona.alterarOrdemStatusZona();
        }
    }

    public  void alterarTodasZonas(StatusZona statuszona){
        for (ZonaCircle zona :listazonas) {
            zona.alterarStatusEspecificoZona(statuszona);
        }
    }

    public String checkoutZonaAberta() {
        zonasstatusfechada = null;
        for (ZonaCircle zona : listazonas) {
            if (StatusZona.Fechado.equals(zona.getStatus())) {
                if(zonasstatusfechada != null)
                zonasstatusfechada += zona + " ";
                else
                    zonasstatusfechada = zona + " ";
            }
        }
        return zonasstatusfechada;
    }



    public  String alterarStatusParticao(){

        String armado =particaocircle.alterarStatusParticao(checkoutZonaAberta());
        if(armado.equals("Desarmado")){
            for (ZonaCircle zona:listazonas) {
                if(StatusZona.Inibido.equals(zona.getStatus())){
                    zona.alterarStatusEspecificoZona(StatusZona.Aberto);
                }
            }
        }
        return armado ;
    }

    public  void reiniciarParticao(){

        particaocircle.reiniciarParticao();
        for (ZonaCircle zona:listazonas) {
            zona.alterarStatusEspecificoZona(StatusZona.Fechado);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(particaocircle);
    }
}

