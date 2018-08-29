package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {
    helper helpers = new helper();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("CMB-схеми");
        primaryStage.setScene(new Scene(root, 1140, 800));
        primaryStage.show();

        primaryStage.setResizable(false);

        root.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() {
            /**
             * Перевірка чи зажатий Cntrl, без чого не будуть додаватися нові елементи на панель
             * @param event
             */
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                if(event.getCode().equals(KeyCode.CONTROL)){
                    helpers.setType(1);
                }
            }
        });
/**
 * передача в класс helper параметру 0, що означає що клавішу Cntrl не натиснуто
 */
        root.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.CONTROL)){
                    helpers.setType(0);
                }
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
