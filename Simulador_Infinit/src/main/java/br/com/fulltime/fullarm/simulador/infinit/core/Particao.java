package br.com.fulltime.fullarm.simulador.infinit.core;

import br.com.fulltime.fullarm.simulador.infinit.application.circle.ParticaoCircle;
import br.com.fulltime.fullarm.simulador.infinit.application.circle.ZonaCircle;
import br.com.fulltime.fullarm.simulador.infinit.infrastructura.particao.DuplaZona;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Particao  {


    private ArrayList<DuplaZona> listaduplazonas = new ArrayList<>();
    private String zonasstatusaberta;
    private ParticaoCircle particaocircle;
    private ImageView image;
    private Boolean statusarmada,statusmemoria,statushabilitar, statuspronta;


    public Particao (Circle circle ,int numeroindetificador , ImageView image){
        this.image = image;
        particaocircle = new ParticaoCircle(circle,numeroindetificador);
    }

    public void adicionarDuplaZonas(Circle circle1 , int numerozona1 , Circle circle2 , int numerozona2, Line line1, Line line2 ){
        DuplaZona duplazona = new DuplaZona(circle1,circle2,numerozona1,numerozona2,line1,line2);
        listaduplazonas.add(duplazona);
    }


    public void statusParticao(){
        int i =0;
        for (DuplaZona duplazona:listaduplazonas) {
            i++;
            System.out.print(i);

//            String text =duplazona.statusZonaHexDecimalCompleto();
//            text =Integer.toHexString(Integer.valueOf(text));
//            System.out.print(text);

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
                if(zona.getCircle().equals(circle)) {
                    zona.alterarOrdemStatusZona();
                    if(StatusParticao.Armado.equals(particaocircle.getStatusParticao())){
                        zona.zonaArmada();
                        statusmemoria=true;
                        image.setImage(new Image("megafone.jpg"));
                    }
                }

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
        zonasstatusaberta = null;
        for (DuplaZona duplazona : listaduplazonas) {
            for (ZonaCircle zona : duplazona.getZona()) {
                if (StatusZona.Aberto.equals(zona.getStatus())) {

                    if(!zona.getStatusinibido())
                        if (zonasstatusaberta != null)
                            zonasstatusaberta += zona + " ";
                        else
                            zonasstatusaberta = zona + " ";
                }
            }
        }
        return zonasstatusaberta;
    }



    public  String alterarStatusParticao(){
        String armado =particaocircle.alterarStatusParticao(checkoutZonaAberta());
        if(armado.equals("Desarmado")){
            armarParticao();
        }
        if(armado.equals("Armado")){
            desarmaParticao();
        }
        return armado ;
    }

    public String armarParticao(){
        boolean armado =particaocircle.armarParticao(checkoutZonaAberta());
        if(armado) {
            image.setImage(new Image("fechado.jpg"));
            statusarmada = true;
            for (DuplaZona duplazona : listaduplazonas) {
                for (ZonaCircle zona : duplazona.getZona()) {
                    zona.zonaDesarmada();
                }
            }
            return "Armado";
        }
        return null;

    }

    public String desarmaParticao(){
        image.setImage(new Image("cadeado.jpg"));
        for (DuplaZona duplazona:listaduplazonas) {
            for (ZonaCircle zona: duplazona.getZona()) {
                zona.zonaDesarmada();
                statusarmada = false;
                if(zona.getStatusinibido()){
                    zona.alterarStatusEspecificoZona(StatusZona.Aberto);
                }
            }
        }
        return  null;
    }

    public Boolean getStatusarmada(){
        return statusarmada;
    }

    public Boolean getStatusmemoria(){
        return statusmemoria;
    }

    public Boolean getStatushabilitar(){
        return statushabilitar = true;
    }

    public Boolean getStatuspronta() {
        statuspronta = true;
        for (DuplaZona duplazona:listaduplazonas) {
            for (ZonaCircle zona :duplazona.getZona()) {
                if(StatusZona.Aberto.equals(zona.getStatus())){
                    if(!zona.getStatusinibido()) {
                        statuspronta = false;
                    }
                }
            }

        }
        return statuspronta;
    }



    public  void reiniciarParticao(){
        particaocircle.reiniciarParticao();
        for (DuplaZona duplazona:listaduplazonas) {
            for (ZonaCircle zona : duplazona.getZona()) {
                zona.reiniciarzona();
            }
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

