package com.bluding.codegen.ui;

import javafx.stage.Stage;

/**
 * Created by myron.pan on 2017/01/28.
 */
public abstract class AbstractController {

    protected Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

}
