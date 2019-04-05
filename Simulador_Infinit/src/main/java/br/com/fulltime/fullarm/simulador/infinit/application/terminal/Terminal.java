package br.com.fulltime.fullarm.simulador.infinit.application.terminal;

import br.com.fulltime.fullarm.simulador.infinit.infrastructura.conexao.HexTraducao;
import javafx.scene.control.TextArea;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Terminal {

    private TextArea terminal;
    private HexTraducao hextraducao= new HexTraducao();

    private String newLine = System.getProperty("line.separator");

    public void definirTerminal(TextArea terminal){
        this.terminal = terminal;
    }
    public void printTerminal (String comando){
        LocalDateTime c = LocalDateTime.now(ZoneId.of("Brazil/East") );
        terminal.appendText(c + " -[PAINEL] >> "+ hextraducao.traduzirforHexDecimal(comando) +newLine);
    }
    public void printResposta (String comando ){
        LocalDateTime c = LocalDateTime.now(ZoneId.of("Brazil/East") );
        terminal.appendText(c + "-[BROKER] <<" + comando + newLine);
    }
    public void printTerminalBits (byte[] comando){
        LocalDateTime c = LocalDateTime.now(ZoneId.of("Brazil/East") );
        terminal.appendText(c + " -[PAINEL] >> "+ hextraducao.formatHexString(comando) +newLine);
    }

    public void  limparTerminal(){
     terminal.setText("");
    }
}
