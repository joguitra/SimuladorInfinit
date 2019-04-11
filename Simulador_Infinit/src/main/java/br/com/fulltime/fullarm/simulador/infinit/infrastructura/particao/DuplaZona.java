package br.com.fulltime.fullarm.simulador.infinit.infrastructura.particao;

import br.com.fulltime.fullarm.simulador.infinit.application.circle.ZonaCircle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.BitSet;

public class DuplaZona {

    private ArrayList<ZonaCircle> zona = new ArrayList<>();
    private ZonaCircle zona1;
    private ZonaCircle zona2;
    private String statuszonaparaservidor;

    public DuplaZona(Circle circle1, Circle circle2, int numeroidentificador1, int numeroidentificador2, Line linha1, Line linha2) {
        zona1 = new ZonaCircle(circle1, numeroidentificador1);
        zona2 = new ZonaCircle(circle2, numeroidentificador2);
        zona1.adicinarTamper(linha1);
        zona2.adicinarTamper(linha2);
        zona.add(zona1);
        zona.add(zona2);
    }

    public ArrayList<ZonaCircle> getZona() {
        return zona;
    }

    public ArrayList<ZonaCircle> getZonaOrdemInvertida(){
        ArrayList<ZonaCircle> listainvertida= new ArrayList<>();
        listainvertida.add(zona2);
        listainvertida.add(zona1);
        return  listainvertida;
    }



    public byte[] statusZonaHexDecimalCompleto() {

        BitSet bitSet = BitSet.valueOf(new byte[32]);
        bitSet.set(0, zona1.getStatusmemoria());
        bitSet.set(1, zona1.getStatusinibido());
        bitSet.set(2, zona1.getStatustamper());
        bitSet.set(3, zona1.getStatusaberto());
        bitSet.set(4, zona2.getStatusmemoria());
        bitSet.set(5, zona2.getStatusinibido());
        bitSet.set(6, zona2.getStatustamper());
        bitSet.set(7, zona2.getStatusaberto());

         byte[] pacoteAEnviar = bitSet.toByteArray();
         if(pacoteAEnviar.length ==0){
                 ByteBuffer buffer = ByteBuffer.allocate(1);
                 buffer.put(((Integer)0).byteValue());
                 pacoteAEnviar = buffer.array();

         }
         return  pacoteAEnviar;

    }
}
