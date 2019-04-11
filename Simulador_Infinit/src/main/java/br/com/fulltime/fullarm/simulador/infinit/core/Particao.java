package br.com.fulltime.fullarm.simulador.infinit.core;

import br.com.fulltime.fullarm.simulador.infinit.application.circle.ParticaoCircle;
import br.com.fulltime.fullarm.simulador.infinit.application.circle.ZonaCircle;
import br.com.fulltime.fullarm.simulador.infinit.infrastructura.conexao.HexTraducao;
import br.com.fulltime.fullarm.simulador.infinit.infrastructura.particao.DuplaZona;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.BitSet;

public class Particao  {


    private ArrayList<DuplaZona> listaduplazonas = new ArrayList<>();
    private String zonasstatusaberta;
    private ParticaoCircle particaocircle;
    private ImageView image;
    private Boolean statusarmada =false,statusmemoria =false,statushabilitar=true, statuspronta=true;

    public Particao (Circle circle ,int numeroindetificador , ImageView image){
        this.image = image;
        particaocircle = new ParticaoCircle(circle,numeroindetificador);
    }

    public void adicionarDuplaZonas(Circle circle1 , int numerozona1 , Circle circle2 , int numerozona2, Line line1, Line line2 ){
        DuplaZona duplazona = new DuplaZona(circle1,circle2,numerozona1,numerozona2,line1,line2);
        listaduplazonas.add(duplazona);
    }

    public ArrayList<DuplaZona> getListaduplazonas() {
        return listaduplazonas;
    }

    public byte[] statusParticao(){
        ByteBuffer buffer = ByteBuffer.allocate(4);

        for (DuplaZona duplazona:listaduplazonas) {
            byte[] dado =(duplazona.statusZonaHexDecimalCompleto());
            buffer.put(dado);
        }
        byte[] codigo = buffer.array();
        return  codigo;
    }

    public byte[] erroArmeDesarme (){
        ByteBuffer buffer = ByteBuffer.allocate(4);
        for (DuplaZona duplazona:listaduplazonas) {
            byte[] dado =(duplazona.statusZonaHexDecimalCompleto());
            buffer.put(dado);
        }
        byte[] codigo = buffer.array();
        return  codigo;
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

    public byte[] mapParticao (){
        ByteBuffer buffer = ByteBuffer.allocate(8);
        for (DuplaZona duplazona:listaduplazonas) {
            for (ZonaCircle zona:duplazona.getZona()) {
                buffer.put(((Integer)particaocircle.getNumeroidentificador()).byteValue());
            }

        }
        byte[] resultado = buffer.array();
        return  resultado;
    }


    public ArrayList<ZonaCircle> listaInvertida() {
        ArrayList<ZonaCircle> listainvertida = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {
            int n = 0;
            for (DuplaZona duplaZona : listaduplazonas) {
                n++;
                if (i == n) {
                    for (ZonaCircle zonainvertida : duplaZona.getZonaOrdemInvertida()) {
                        listainvertida.add(zonainvertida);
                    }
                }
            }
        }
        return listainvertida;
    }

    @Override
    public String toString() {
        return String.valueOf(particaocircle);
    }

    public int getNumeroidentificador(){
        return particaocircle.getNumeroidentificador();
    }

    public void inibirzona(String zonasInibidas) {

        int digito1 = HexTraducao.hexTraducaoInteger(zonasInibidas.substring(0,1));
        int digito2 = HexTraducao.hexTraducaoInteger(zonasInibidas.substring(1,2));

        int divisor1 = 8;
        int divisor2 = 8;

        for (ZonaCircle zona: listaInvertida()) {
            if(divisor1 != 0) {
                if (digito1 / divisor1 > 0) {
                    digito1 -= divisor1;
                    zona.alterarStatusEspecificoZona(StatusZona.Inibido);
                }
                divisor1 /= 2;
            }

            else {
                if (digito2 / divisor2 > 0) {
                    digito2 -= divisor2;
                    zona.alterarStatusEspecificoZona(StatusZona.Inibido);
                }
                divisor2 /= 2;
            }
        }
    }

    public void deseinibirzona(String zonasDeseInibidas) {
        int digito1 = HexTraducao.hexTraducaoInteger(zonasDeseInibidas.substring(0,1));
        int digito2 = HexTraducao.hexTraducaoInteger(zonasDeseInibidas.substring(1,2));

        int divisor1 = 8;
        int divisor2 = 8;

        for (ZonaCircle zona: listaInvertida()) {
            if(divisor1 != 0) {
                if (digito1 / divisor1 > 0) {
                    digito1 -= divisor1;
                    zona.alterarStatusEspecificoZona(StatusZona.Fechado);
                }
                divisor1 /= 2;
            }

            else {
                if (digito2 / divisor2 > 0) {
                    digito2 -= divisor2;
                    zona.alterarStatusEspecificoZona(StatusZona.Fechado);
                }
                divisor2 /= 2;
            }
        }
    }
}


