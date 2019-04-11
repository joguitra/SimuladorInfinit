package br.com.fulltime.fullarm.simulador.infinit.infrastructura.conexao;

import java.nio.ByteBuffer;

import static javax.xml.bind.DatatypeConverter.printHexBinary;

public final class   HexTraducao {


    public static String formatHexString(final byte[] data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : data) {
            stringBuilder
                    .append(printHexBinary(new byte[]{b}))
                    .append(" ");

        }
        return stringBuilder.toString();
    }


    public static byte[] hexStringToBytes(String rawData) {
        int len = rawData.length();
        ByteBuffer buffer = ByteBuffer.allocate(len);
        for (int i = 0; i < len; i += 1) {
            String bytestring =rawData.substring(i,i+1);
                   buffer.put(bytestring.getBytes());

        }
         byte[] result = buffer.array();
        return result;
    }

    public static String traduzirforHexDecimal(String codigodecimal){
            return formatHexString(hexStringToBytes(codigodecimal));
    }


    public static int hexTraducaoInteger (String hexprintavel){
        switch (hexprintavel){
            case "1":
                return 1;
            case "2":
                return 2;
            case "3":
                return 3;
            case "4":
                return 4;
            case "5":
                return 5;
            case "6":
                return 6;
            case "7":
                return 7;
            case "8":
                return 8;
            case "9":
                return 9;
            case "A":
                return 10;
            case "B":
                return 11;
            case "C":
                return 12;
            case "D":
                return 13;
            case "E":
                return 14;
            case "F":
                return 15;
        }
        return 0;
    }

}

