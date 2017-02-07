package com.bluding.codegen.ui;

import com.bluding.codegen.context.Context;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Context.loadConfigs();

        primaryStage.setTitle("Code Generator");

        createScene(primaryStage, SceneHolder.CONFIGURATION_VIEW, SceneHolder.CONFIGURATION_FXML, 1024, 700);
        Scene generatorView = createScene(primaryStage, SceneHolder.GENERATOR_VIEW, SceneHolder.GENERATOR_FXML,
                1024, 700);

        primaryStage.setScene(generatorView);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Scene createScene(Stage primaryStage, String sceneId, String fxml, int width, int height) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

        Pane pane = loader.load();
        Scene scene = new Scene(pane, width, height);

        AbstractController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
        SceneHolder.putScene(sceneId, scene, controller);
        return scene;
    }

    public static void showWindow(String[] args) {
        launch(args);
    }
}
