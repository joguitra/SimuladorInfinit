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

            switch (bytestring) {
                case "0":
                    buffer.put(((Integer)0).byteValue());
                    break;
                case "1":
                    buffer.put(((Integer)1).byteValue());
                    break;
                case "2":
                    buffer.put(((Integer)2).byteValue());
                    break;
                case "3":
                    buffer.put(((Integer)3).byteValue());
                    break;
                case "4":
                    buffer.put(((Integer)4).byteValue());
                    break;
                case "5":
                    buffer.put(((Integer)5).byteValue());
                    break;
                case "6":
                    buffer.put(((Integer)6).byteValue());
                    break;
                case "7":
                    buffer.put(((Integer)7).byteValue());
                    break;
                case "8":
                    buffer.put(((Integer)8).byteValue());
                    break;
                case "9":
                    buffer.put(((Integer)9).byteValue());
                    break;

                default:
                   buffer.put(bytestring.getBytes());
            }
        }
         byte[] result = buffer.array();
        return result;
    }

    public String traduzirforHexDecimal(String codigodecimal){
            return formatHexString(hexStringToBytes(codigodecimal));
    }


}

