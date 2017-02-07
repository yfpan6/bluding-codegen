package com.bluding.codegen.ui;

import com.bluding.codegen.context.Context;
import com.bluding.codegen.context.model.Configuration;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Created by myron.pan on 2017/01/28.
 */
public class ConfigurationViewController extends AbstractController implements Initializable {

    @FXML
    private ListView<Configuration> configGroupList;
    @FXML
    private TextField configGroup;
    @FXML
    private TextField jdbcUrl;
    @FXML
    private TextField driverCLass;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField srcPath;
    @FXML
    private TextField basePackage;
    @FXML
    private TextField author;
    @FXML
    private TextField copyright;
    @FXML
    private TextField baseEntity;
    @FXML
    private TextField baseService;
    @FXML
    private TextField baseServiceImpl;
    @FXML
    private TextField baseMapper;

    @FXML
    private TextField entityPackage;
    @FXML
    private TextField servicePackage;
    @FXML
    private TextField serviceImplPackage;
    @FXML
    private TextField mapperPackage;
    @FXML
    private TextField controllerPackage;

    @FXML
    private TextArea  baseFileds;
    @FXML
    private TextField databasePrefix;

    @FXML
    protected void createNewConfiguration(ActionEvent event) {
        Configuration configuration = new Configuration();
        configuration.setConfigGroup("New Configuration");
        configuration.setId(UUID.randomUUID().toString());
        Context.putConfiguration(configuration);
        Context.setCurrConfiguration(configuration);
        refreshConfigGroupList();
        resetViewInfo(configuration);
        configGroupList.getSelectionModel().select(configuration);
    }

    private Configuration empty = new Configuration();

    @FXML
    protected void deleteConfiguration(ActionEvent event) {
        Context.removeCurrConfiguration();
        Context.saveConfig();
        refreshConfigGroupList();
        resetViewInfo(empty);
    }

    @FXML
    protected void toGeneratorView(ActionEvent event) {
        if (Context.getCurrConfiguration() != null) {
//            setValueToCurrConfiguration();
//            Context.saveConfig();
        }
        GeneratorViewController controller = SceneHolder.getController(SceneHolder.GENERATOR_VIEW);
        controller.refresh();
        Scene scene = SceneHolder.getScene(SceneHolder.GENERATOR_VIEW);
        primaryStage.setScene(scene);
    }

    @FXML
    protected void saveConfiguration(ActionEvent event) {
        if (Context.getCurrConfiguration() != null) {
            setValueToCurrConfiguration();
            refreshConfigGroupList();
            Context.saveConfig();
        }
    }

    @FXML
    protected void resetCurrConfiguration(ActionEvent event) {
        Configuration configuration = Context.getCurrConfiguration();
        if (configuration != null) {
            resetViewInfo(configuration);
            refreshConfigGroupList();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshConfigGroupList();
        configGroupList.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends Configuration> observable,
                              Configuration oldValue, Configuration newValue)->{
                    Context.setCurrConfiguration(newValue);
                    resetViewInfo(newValue);
        });

        configGroupList.setCellFactory((ListView<Configuration> param)-> {
            final ListCell<Configuration> cell = new ListCell<Configuration>(){
                protected void updateItem(Configuration config, boolean bln) {
                    super.updateItem(config, bln);
                    if(config != null){
                        setText(config.getConfigGroup());
                    }else{
                        setText(null);
                    }
                }

            };
            return cell;
        });

        final InvalidationListener invalidationListener = new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (configGroup.isFocused()) {
                    return;
                }
                if (Context.getCurrConfiguration() == null) {
                    return;
                }
                if (StringUtils.isBlank(configGroup.getText())) {
                    observable.removeListener(this);
                    MessageBox.display(primaryStage, "Info", "This inputfield can't be empty.", null);
                    configGroup.requestFocus();
                    observable.addListener(this);
                    return;
                }
//                setValueToCurrConfiguration();
//                refreshConfigGroupList();
            }
        };
        configGroup.focusedProperty().addListener(invalidationListener);
    }

    private void refreshConfigGroupList() {
        ObservableList<Configuration> observableList = FXCollections.observableArrayList();
        observableList.setAll(Context.getConfigs());
        configGroupList.setItems(observableList);
        configGroupList.refresh();
    }

    private void resetViewInfo(Configuration configuration) {
        if (configuration == null) {
            return;
        }
        configGroup.setText(configuration.getConfigGroup());
        driverCLass.setText(configuration.getDriverCLass());
        jdbcUrl.setText(configuration.getJdbcUrl());
        username.setText(configuration.getUsername());
        password.setText(configuration.getPassword());
        srcPath.setText(configuration.getSrcPath());
        basePackage.setText(configuration.getBasePackage());
        author.setText(configuration.getAuthor());
        copyright.setText(configuration.getCopyright());
        baseEntity.setText(configuration.getBaseEntity());
        baseService.setText(configuration.getBaseService());
        baseServiceImpl.setText(configuration.getBaseServiceImpl());
        baseMapper.setText(configuration.getBaseMapper());

        entityPackage.setText(configuration.getEntityPackage());
        servicePackage.setText(configuration.getServicePackage());
        serviceImplPackage.setText(configuration.getServiceImplPackage());
        mapperPackage.setText(configuration.getMapperPackage());
        controllerPackage.setText(configuration.getControllerPackage());

        baseFileds.setText(configuration.getBaseFields());
        databasePrefix.setText(configuration.getDatabasePrefix());
    }

    private void setValueToCurrConfiguration() {
        Configuration configuration = Context.getCurrConfiguration();
        if (configuration != null) {
            configuration.setConfigGroup(configGroup.getText());
            configuration.setDriverCLass(driverCLass.getText());
            configuration.setJdbcUrl(jdbcUrl.getText());
            configuration.setUsername(username.getText());
            configuration.setPassword(password.getText());
            configuration.setSrcPath(srcPath.getText());
            configuration.setBasePackage(basePackage.getText());
            configuration.setAuthor(author.getText());
            configuration.setCopyright(copyright.getText());
            configuration.setBaseEntity(baseEntity.getText());
            configuration.setBaseService(baseService.getText());
            configuration.setBaseServiceImpl(baseServiceImpl.getText());
            configuration.setBaseMapper(baseMapper.getText());

            configuration.setEntityPackage(entityPackage.getText());
            configuration.setServicePackage(servicePackage.getText());
            configuration.setServiceImplPackage(serviceImplPackage.getText());
            configuration.setMapperPackage(mapperPackage.getText());
            configuration.setControllerPackage(controllerPackage.getText());
            configuration.setBaseFields(baseFileds.getText());

            configuration.setDatabasePrefix(databasePrefix.getText());
        }
    }
}
