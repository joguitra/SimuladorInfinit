package simulador.core;

import simulador.application.circle.ParticaoCircle;
import simulador.application.circle.ZonaCircle;
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

    public void adicionarDuplaZonas(Circle circle1 , int numerozona1 , Circle circle2 , int numerozona2){
        zonaCircle = new ZonaCircle(circle1, numerozona1);
        listazonas.add(zonaCircle);

        zonaCircle = new ZonaCircle(circle2, numerozona2);
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
            if (StatusZona.Aberto.equals(zona.getStatus())) {
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

