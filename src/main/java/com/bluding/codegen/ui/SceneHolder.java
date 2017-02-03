package com.bluding.codegen.ui;

import com.google.common.collect.Maps;
import javafx.scene.Scene;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by myron.pan on 2017/01/28.
 */
public class SceneHolder {

    public static final String GENERATOR_VIEW = "genView";
    public static final String GENERATOR_FXML = "generator-view.fxml";
    public static final String CONFIGURATION_VIEW = "configurationView";
    public static final String CONFIGURATION_FXML = "configuration-view.fxml";

    private static final Map<String, SceneWrap> sceneHolder = Maps.newHashMap();

    public static void putScene(String id, Scene scene, AbstractController controller) {
        sceneHolder.put(id, new SceneWrap(scene, controller));
    }

    public static Scene getScene(String id) {
        SceneWrap wrap = sceneHolder.get(id);
        return wrap != null ? wrap.scene : null;
    }

    public static SceneWrap getSceneWrap(String id) {
        return sceneHolder.get(id);
    }

    public static <T extends AbstractController> T getController(String id) {
        SceneWrap wrap = sceneHolder.get(id);
        return wrap != null ? (T) wrap.controller : null;
    }

    public static class SceneWrap {
        private Scene scene;
        private AbstractController controller;

        SceneWrap(Scene scene, AbstractController controller) {
            this.scene = scene;
            this.controller = controller;
        }
    }

}
