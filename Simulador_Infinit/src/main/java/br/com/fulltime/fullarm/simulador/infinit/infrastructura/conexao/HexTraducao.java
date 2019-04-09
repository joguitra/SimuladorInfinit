package br.com.fulltime.fullarm.simulador.infinit.infrastructura.conexao;

import java.nio.ByteBuffer;

import static javax.xml.bind.DatatypeConverter.printHexBinary;

public class HexTraducao {


    public  String formatHexString(final byte[] data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : data) {
            stringBuilder
                    .append(printHexBinary(new byte[]{b}))
                    .append(" ");

        }
        return stringBuilder.toString();
    }


    public  byte[] hexStringToBytes(String rawData) {
        int len = rawData.length();
        ByteBuffer buffer = ByteBuffer.allocate(len);
        for (int i = 0; i < len; i += 1) {
            String bytestring =rawData.substring(i,i+1);
                   buffer.put(bytestring.getBytes());

        }
         byte[] result = buffer.array();
        return result;
    }

    public String traduzirforHexDecimal(String codigodecimal){
            return formatHexString(hexStringToBytes(codigodecimal));
    }


}

