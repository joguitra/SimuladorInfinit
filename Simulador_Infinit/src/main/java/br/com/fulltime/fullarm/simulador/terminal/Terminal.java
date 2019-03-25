package br.com.fulltime.fullarm.simulador.terminal;

import javafx.scene.control.TextArea;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Terminal {

    private TextArea terminal;

    private String newLine = System.getProperty("line.separator");

    public void definirTerminal(TextArea terminal){
        this.terminal = terminal;
    }
    public void printTerminal (String comando){
        LocalDateTime c = LocalDateTime.now(ZoneId.of("Brazil/East") );
        terminal.appendText(c + " -[PAINEL] >> "+comando +newLine);
    }
    public void printResposta (String comando ){
        LocalDateTime c = LocalDateTime.now(ZoneId.of("Brazil/East") );
        terminal.appendText(c + "-[BROKER] <<" + comando + newLine);
    }

    public void  limparTerminal(){
     terminal.setText("");
    }
}
