package br.com.fulltime.fullarm.simulador.infinit.core;

import br.com.fulltime.fullarm.simulador.infinit.application.circle.ZonaCircle;
import br.com.fulltime.fullarm.simulador.infinit.infrastructura.conexao.HexTraducao;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class DuplaZona {

    private ArrayList<ZonaCircle> zona = new ArrayList<>();
    private  ZonaCircle zona1;
    private  ZonaCircle zona2;
    private  int statuszona1;
    private  int statuszona2;
    private  String statuszonahexdecimal1;
    private  String statuszonahexdecimal2;
    private HexTraducao hexTraducao = new HexTraducao();
    private  String statuszonaparaservidor;

    public DuplaZona(Circle circle1, Circle circle2 , int numeroidentificador1, int numeroidentificador2, Line linha1, Line linha2){
        zona1 = new ZonaCircle(circle1,numeroidentificador1);
        zona2 = new ZonaCircle(circle2,numeroidentificador2);
        zona1.adicinarTamper(linha1);
        zona2.adicinarTamper(linha2);
        zona.add(zona1);
        zona.add(zona2);
    }

    public ArrayList<ZonaCircle> getZona() {
        return zona;
    }




    public String statusZonaHexDecimalCompleto(){
       statuszona1 = 0;
       statuszona2 = 0;

       if(StatusZona.Aberto.equals(zona1.getStatus())) {statuszona1 +=8;}
       if(StatusZona.Aberto.equals(zona2.getStatus())) {statuszona2 +=8;}

       if(zona1.getStatustamper()) {statuszona1 += 4;}
       if(zona2.getStatustamper()) {statuszona2 += 4;}

       if(zona1.getStatusinibido()) {statuszona1 +=2;}
       if(zona2.getStatusinibido()) {statuszona2 +=2;}

       if(zona1.getStatus().equals(StatusZona.Disparado)) {statuszona1 +=1;}
       if(zona2.getStatus().equals(StatusZona.Disparado)) {statuszona2 +=1;}

       statuszonahexdecimal1 =hexTraducao.convertorHexDecimal(statuszona1);
       statuszonahexdecimal2 =hexTraducao.convertorHexDecimal(statuszona2);

       statuszonaparaservidor = statuszonahexdecimal1 +statuszonahexdecimal2;
       return statuszonaparaservidor;
    }
}
