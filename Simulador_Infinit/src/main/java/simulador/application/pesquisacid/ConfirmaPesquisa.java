package simulador.application.pesquisacid;


import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextField;

public class ConfirmaPesquisa {

    public void mostraDescriacao (ListView<String> lista, Label descricao){
        int n = lista.getSelectionModel().getSelectedItem().length();
        String descricaostring = lista.getSelectionModel().getSelectedItem().substring(5, n);
        descricao.setText(descricaostring);
    }

    public void confirmaPesquisa (ListView<String> lista , TextField evento , ListView<String> qualificador){
        evento.setText(lista.getSelectionModel().getSelectedItem().substring(2, 5));
        if (lista.getSelectionModel().getSelectedItem().substring(1,2).equals("1")) {
             qualificador.getItems().set(0,"E");
        }
        if (lista.getSelectionModel().getSelectedItem().substring(1,2).equals("3")) {
            System.out.print(qualificador.getItems());
        }
    }
}
