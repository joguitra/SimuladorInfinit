package br.com.fulltime.fullarm.simulador.infinit.application;

import br.com.fulltime.fullarm.simulador.infinit.infrastructura.conexao.HexTraducao;
import com.sun.xml.internal.ws.api.streaming.XMLStreamWriterFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public  class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("../../../../../../../../resources/Interface.fxml"));
        Scene scene =new Scene(root, 720, 650);
        primaryStage.setTitle("Simulador de Painel");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
