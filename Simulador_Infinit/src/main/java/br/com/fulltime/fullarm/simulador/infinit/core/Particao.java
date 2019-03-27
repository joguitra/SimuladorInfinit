package br.com.fulltime.fullarm.simulador.infinit.core;

import br.com.fulltime.fullarm.simulador.infinit.application.circle.ParticaoCircle;
import br.com.fulltime.fullarm.simulador.infinit.application.circle.ZonaCircle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Particao  {


    private ArrayList<DuplaZona> listaduplazonas = new ArrayList<>();
    private String zonasstatusfechada;
    private ParticaoCircle particaocircle;


    public Particao (Circle circle ,int numeroindetificador){
        particaocircle = new ParticaoCircle(circle,numeroindetificador);
    }

    public void adicionarDuplaZonas(Circle circle1 , int numerozona1 , Circle circle2 , int numerozona2, Line line1, Line line2){
        DuplaZona duplazona = new DuplaZona(circle1,circle2,numerozona1,numerozona2,line1,line2);
        listaduplazonas.add(duplazona);
    }


    public void statusParticao(){
        for (DuplaZona duplazona:listaduplazonas) {
            System.out.println(duplazona.statusZonaHexDecimalCompleto());
        }
    }

    public void alterarZonaInibida(Circle circle){
        for (DuplaZona duplaZona:listaduplazonas) {
            for (ZonaCircle zona :duplaZona.getZona()) {
                if(zona.getCircle().equals(circle))
                    zona.alterarStatusEspecificoZona(StatusZona.Inibido);
            }
        }
    }

    public void alterarZona(Circle circle){
        for (DuplaZona duplazona:listaduplazonas) {
            for (ZonaCircle zona:duplazona.getZona()) {
                if(zona.getCircle().equals(circle))
                    zona.alterarOrdemStatusZona();
            }
        }
    }

    public void alterarTamper(Circle circle) {
        for (DuplaZona duplazona : listaduplazonas) {
            for (ZonaCircle zona : duplazona.getZona()) {
                if (zona.getCircle().equals(circle))
                    zona.alterarTamper();
            }
        }
    }

    public  void alterarTodasZonas(StatusZona statuszona){
        for (DuplaZona duplazona :listaduplazonas) {
            for (ZonaCircle zona:duplazona.getZona()){
              zona.alterarStatusEspecificoZona(statuszona);
            }
        }
    }

    public String checkoutZonaAberta() {
        zonasstatusfechada = null;
        for (DuplaZona duplazona : listaduplazonas) {
            for (ZonaCircle zona : duplazona.getZona()) {
                if (StatusZona.Aberto.equals(zona.getStatus())) {

                    if(!zona.getStatusinibido())
                        if (zonasstatusfechada != null)
                            zonasstatusfechada += zona + " ";
                        else
                            zonasstatusfechada = zona + " ";
                }
            }
        }
        return zonasstatusfechada;
    }



    public  String alterarStatusParticao(){
        String armado =particaocircle.alterarStatusParticao(checkoutZonaAberta());
        if(armado.equals("Desarmado")){
            for (DuplaZona duplazona:listaduplazonas) {
                for (ZonaCircle zona:duplazona.getZona()) {
                    if(zona.getStatusinibido()){
                        zona.alterarStatusEspecificoZona(StatusZona.Aberto);
                    }
                }
            }
        }
        return armado ;
    }

    public  void reiniciarParticao(){

        particaocircle.reiniciarParticao();
        for (DuplaZona duplazona:listaduplazonas)
            for (ZonaCircle zona:duplazona.getZona()) {
                zona.reiniciarzona();
            }
    }

    public  void ativarping(){
        for (DuplaZona duplazona:listaduplazonas)
            for (ZonaCircle zona:duplazona.getZona()) {
                zona.ativarPing();
            }
    }

    @Override
    public String toString() {
        return String.valueOf(particaocircle);
    }
}

