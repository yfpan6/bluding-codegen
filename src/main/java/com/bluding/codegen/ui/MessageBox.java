package com.bluding.codegen.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by myron.pan on 2017/01/29.
 */
public class MessageBox {
    public static void display(Stage owner, String title , String message, ClosedListener listener){
        Stage window = new Stage();
        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UTILITY);
        window.setMinWidth(300);
        window.setMinHeight(150);
        window.initOwner(owner);
        window.setResizable(false);
        Button button = new Button("Close");
        button.setOnAction(e -> {
            window.close();
            if (listener == null) {
                return;
            }
            //listener.closed();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(new Label(message) , button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public interface ClosedListener {
        void closed();
    }
}
