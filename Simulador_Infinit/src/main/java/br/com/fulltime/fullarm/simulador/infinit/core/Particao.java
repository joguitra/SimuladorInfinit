package br.com.fulltime.fullarm.simulador.infinit.core;

import br.com.fulltime.fullarm.simulador.infinit.application.circle.ParticaoCircle;
import br.com.fulltime.fullarm.simulador.infinit.application.circle.ZonaCircle;
import br.com.fulltime.fullarm.simulador.infinit.infrastructura.conexao.HexTraducao;
import br.com.fulltime.fullarm.simulador.infinit.infrastructura.particao.DuplaZona;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.BitSet;

public class Particao  {


    private ArrayList<DuplaZona> listaduplazonas = new ArrayList<>();
    private String zonasstatusaberta;
    private ParticaoCircle particaocircle;
    private ImageView image;
    private Boolean statusarmada =false,statusmemoria =false,statushabilitar=true, statuspronta=true;
    private HexTraducao hextraducao= new HexTraducao();

    public Particao (Circle circle ,int numeroindetificador , ImageView image){
        this.image = image;
        particaocircle = new ParticaoCircle(circle,numeroindetificador);
    }

    public void adicionarDuplaZonas(Circle circle1 , int numerozona1 , Circle circle2 , int numerozona2, Line line1, Line line2 ){
        DuplaZona duplazona = new DuplaZona(circle1,circle2,numerozona1,numerozona2,line1,line2);
        listaduplazonas.add(duplazona);
    }


    public String statusParticao(){
        String codigo = "S";
        for (DuplaZona duplazona:listaduplazonas) {
            byte[] dado =(duplazona.statusZonaHexDecimalCompleto());
            codigo += hextraducao.formatHexString(dado);
        }
        return  codigo;
    }

    public String erroArmeDesarme (){
        String codigo = new String();
        for (DuplaZona duplazona:listaduplazonas) {
            byte[] dado =(duplazona.statusZonaHexDecimalCompleto());
            codigo += hextraducao.formatHexString(dado);
        }
        return codigo;
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
                    if(statusarmada){
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

    public String printStatus(Particao particao){
        String codigo = new String();
        if(particao.getStatusarmada()){
            codigo = "1" ;
        }else {
            codigo ="0";
        }
        if(particao.getStatushabilitar()){
            codigo += "1" ;
        }else {
            codigo +="0";
        }
        if(particao.getStatuspronta()){
            codigo += "1" ;
        }else {
            codigo +="0";
        }
        if(particao.getStatusmemoria()){
            codigo += "1" ;
        }else {
            codigo +="0";
        }

        return codigo;

    }



    public  Boolean alterarStatusParticao(){
       Boolean armado =particaocircle.armarParticao(checkoutZonaAberta());
        if(armado) {
            if (!statusarmada) {
                armarParticao();
                return statusarmada;
            }
        }
        if (statusarmada) {
            desarmaParticao();
            return statusarmada;
        }
       return  null;
    }

    public boolean armarParticao(){
        boolean armado =particaocircle.armarParticao(checkoutZonaAberta());
        if(armado) {
            image.setImage(new Image("fechado.jpg"));
            statusarmada = true;
            for (DuplaZona duplazona : listaduplazonas) {
                for (ZonaCircle zona : duplazona.getZona()) {
                    zona.zonaDesarmada();
                }
            }
            return true;
        }
        return false;
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
        image.setImage(new Image("cadeado.jpg"));
        statusarmada =false;
        statusmemoria =false;
        statushabilitar=true;
        statuspronta=true;
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

    public String mapParticao (){
        String maparticao = "";
        for (DuplaZona duplazona:listaduplazonas) {
            for (ZonaCircle zona:duplazona.getZona()) {
                maparticao+=particaocircle.getNumeroidentificador();
            }

        }
        return maparticao;
    }
    @Override
    public String toString() {
        return String.valueOf(particaocircle);
    }
}

