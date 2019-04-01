package br.com.fulltime.fullarm.simulador.infinit.infrastructura.conexao;

public class HexTraducao {

    public int i;

    public String traduzirCodigoHex(String codigosemtraducao){
            String codigotraduzido = new String();
            for (i=0; i<=codigosemtraducao.length();i++ ) {
                try {
                     String partecodigo = codigosemtraducao.substring(i, i + 1);
                    switch (partecodigo) {
                        case ":":
                            partecodigo = "A";
                            break;
                        case ";":
                            partecodigo = "B";
                            break;
                        case "<":
                            partecodigo = "C";
                            break;
                        case "=":
                            partecodigo = "D";
                            break;
                        case ">":
                            partecodigo = "E";
                            break;
                        case "?":
                            partecodigo = "F";
                            break;
                    }


                    if (codigotraduzido == null) {
                        codigotraduzido = partecodigo;
                    } else {
                        codigotraduzido += partecodigo;
                    }
                } catch (Exception ignorar) {
                }
            }
            return codigotraduzido;
        }

        public String convertorHexDecimal (int numerodecimal){
            String numerohexdecimal = new String();
            switch (numerodecimal) {
                case 10:
                    numerohexdecimal = "A";
                    break;
                case 11:
                    numerohexdecimal = "B";
                    break;
                case 12 :
                    numerohexdecimal = "C";
                    break;
                case 13:
                    numerohexdecimal = "D";
                    break;
                case 14:
                    numerohexdecimal = "E";
                    break;
                case 15:
                    numerohexdecimal = "F";
                    break;
                    default:
                    numerohexdecimal = String.valueOf(numerodecimal);
            }
            return  numerohexdecimal;
        }
        public String rescritaHexCodigo (String codigonormal){
        String partecodigo = new String();
        String codigorescrito = new String();
        for (i=0; i<=codigonormal.length();i++ ) {
                try {
                    partecodigo = codigonormal.substring(i, i + 1);
                    switch (partecodigo) {
                        case "A":
                            partecodigo = ":";
                            break;
                        case "B":
                            partecodigo = ";";
                            break;
                        case "C":
                            partecodigo = "<";
                            break;
                        case "D":
                            partecodigo = "=";
                            break;
                        case "E":
                            partecodigo = ">";
                            break;
                        case "F":
                            partecodigo = "?";
                            break;
                    }


                    if (codigorescrito == null) {
                        codigorescrito = partecodigo;
                    } else {
                        codigorescrito += partecodigo;
                    }
                } catch (Exception ignorar) {
                }
            }
            return codigorescrito;
        }
}
