package br.com.fulltime.fullarm.simulador.infinit.infrastructura.conexao;

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
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(rawData.charAt(i), 16) << 4)
                    + Character.digit(rawData.charAt(i + 1), 16));
        }
        return data;
    }

    public String hexdecimal(String codigo){
        byte[] bithex = hexStringToBytes(codigo);
        codigo =formatHexString(bithex);
        return codigo;
    }


}
