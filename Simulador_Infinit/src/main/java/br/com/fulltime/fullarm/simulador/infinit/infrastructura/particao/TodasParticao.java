package br.com.fulltime.fullarm.simulador.infinit.infrastructura.particao;

import br.com.fulltime.fullarm.simulador.infinit.core.Particao;
import br.com.fulltime.fullarm.simulador.infinit.infrastructura.conexao.HexTraducao;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.BitSet;

public class TodasParticao {
    ArrayList<Particao> listaparticao = new ArrayList();


    public void adicionarParticao (Particao particaonova){
        listaparticao.add(particaonova);
    }

    public byte[] statusArmado() {
        BitSet bitSet  = BitSet.valueOf(new byte[64]);
        int i =0;
        for (Particao particao: listaparticao) {
            bitSet.set(i, particao.getStatusarmada());
            i++;
        }
        byte[] pacoteAEnviar = bitSet.toByteArray();
        if(pacoteAEnviar.length ==0){
            ByteBuffer buffer = ByteBuffer.allocate(1);
            buffer.put(((Integer)0).byteValue());
            pacoteAEnviar = buffer.array();

        }
        return pacoteAEnviar;
    }

    public byte[] statusPronta(){
        BitSet bitSet = BitSet.valueOf(new byte[64]);
        int i=0;
        for (Particao particao:listaparticao){
            bitSet.set(i,particao.getStatuspronta());
            i++;
        }
        byte[] pacoteAEnviar = bitSet.toByteArray();
        if(pacoteAEnviar.length ==0){
            ByteBuffer buffer = ByteBuffer.allocate(1);
            buffer.put(((Integer)0).byteValue());
            pacoteAEnviar = buffer.array();

        }
        return pacoteAEnviar;
    }

    public byte[] statusHabilitado(){
        BitSet bitSet = BitSet.valueOf(new byte[64]);
        int i=0;
        for (Particao particao:listaparticao){
            bitSet.set(i,particao.getStatushabilitar());
            i++;
        }
        byte[] pacoteAEnviar = bitSet.toByteArray();
        if(pacoteAEnviar.length ==0){
            ByteBuffer buffer = ByteBuffer.allocate(1);
            buffer.put(((Integer)0).byteValue());
            pacoteAEnviar = buffer.array();

        }
        return pacoteAEnviar;
    }



    public byte[] statusMemoria(){
        BitSet bitSet = BitSet.valueOf(new byte[64]);
        int i=0;
        for (Particao particao:listaparticao){
            bitSet.set(i,particao.getStatusmemoria());
            i++;
        }
        byte[] pacoteAEnviar = bitSet.toByteArray();
        if(pacoteAEnviar.length ==0){
            ByteBuffer buffer = ByteBuffer.allocate(1);
            buffer.put(((Integer)0).byteValue());
            pacoteAEnviar = buffer.array();

        }
        return pacoteAEnviar;
    }

    public ArrayList<Particao> getListaparticao() {
        return listaparticao;
    }

    public byte[] statusParticao(){
            ByteBuffer buffer = ByteBuffer.allocate(4);
            buffer.put(statusHabilitado());
            buffer.put(statusArmado());
            buffer.put(statusPronta());
            buffer.put(statusMemoria());
            byte[] resultado = buffer.array();
            return  resultado;
    }



    public byte[] statusZonas(){
        ByteBuffer buffer = ByteBuffer.allocate(32);
        for (Particao particao:listaparticao) {
            buffer.put(particao.erroArmeDesarme());
        }

        byte[] resultado = buffer.array();

        return  resultado;
    }


    public byte[] mapaZonas(){
        ByteBuffer buffer = ByteBuffer.allocate(64);
        for (Particao particao:listaparticao) {
            buffer.put(particao.mapParticao());
        }
        byte[] resultado = buffer.array();
        return  resultado;
    }
}

