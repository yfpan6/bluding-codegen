package com.bluding.codegen.ui;

import com.bluding.codegen.context.Context;
import com.bluding.codegen.context.model.Configuration;
import com.bluding.codegen.context.model.FieldConfiguration;
import com.bluding.codegen.context.model.ModelConfiguration;
import com.bluding.codegen.db.DbUtil;
import com.bluding.codegen.db.TableHandler;
import com.bluding.codegen.freemarker.UITemplate;
import com.bluding.codegen.freemarker.UITemplateFile;
import com.bluding.codegen.generator.EntityGenerator;
import com.bluding.codegen.generator.MapperGenerator;
import com.bluding.codegen.generator.ServiceGenerator;
import com.bluding.codegen.ui.vo.TableRowVO;
import com.bluding.codegen.util.StringUtil;
import com.google.common.collect.Lists;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by myron.pan on 2017/01/28.
 */
public class GeneratorViewController extends AbstractController {

    @FXML
    private TextField configFileLocation;
    @FXML
    private ComboBox<String> tableNameSelect;
    @FXML
    private ComboBox<Configuration> dataSourceSelect;
    @FXML
    private TableView<TableRowVO> modelTableView;
    @FXML
    private TableColumn<TableRowVO, String> columnName;
    @FXML
    private TableColumn<TableRowVO, String> jdbcType;
    @FXML
    private TableColumn<TableRowVO, String> jdbcLength;
    @FXML
    private TableColumn<TableRowVO, String> fieldName;
    @FXML
    private TableColumn<TableRowVO, String> javaType;
    @FXML
    private TableColumn<TableRowVO, String> comment;
    @FXML
    private TableColumn<TableRowVO, String> fieldLabel;
    @FXML
    private TableColumn<TableRowVO, Boolean> isBaseAttr;
    @FXML
    private TableColumn<TableRowVO, Boolean> showInEntity;
    @FXML
    private TableColumn<TableRowVO, Boolean> showInList;
    @FXML
    private TableColumn<TableRowVO, Boolean> showInAddForm;
    @FXML
    private TableColumn<TableRowVO, Boolean> showInUpdateForm;

    @FXML
    private TextField alias;
    @FXML
    private TextField entityName;
    @FXML
    private TextField entityDesc;
    @FXML
    private TextField moduleDesc;
    @FXML
    private TextField requestMapping;
    @FXML
    private TextField pageLocation;
    @FXML
    private TextField keyColumnName;

    @FXML
    public void initialize() {
        refresh();
        columnName.setCellValueFactory(row -> row.getValue().getColumnName());
        jdbcType.setCellValueFactory(row -> row.getValue().getJdbcType());
        jdbcLength.setCellValueFactory(row -> row.getValue().getJdbcLength());

        fieldName.setCellValueFactory(row -> row.getValue().getFieldName());
        fieldName.setCellFactory(TextFieldTableCell.<TableRowVO>forTableColumn());
        fieldName.setEditable(true);

        javaType.setCellValueFactory(row -> row.getValue().getJavaType());
        javaType.setCellFactory(TextFieldTableCell.forTableColumn());
        javaType.setEditable(true);

        comment.setCellValueFactory(row -> row.getValue().getComment());
        comment.setCellFactory(TextFieldTableCell.forTableColumn());
        comment.setEditable(true);

        fieldLabel.setCellValueFactory(row -> row.getValue().getFieldLabel());
        fieldLabel.setCellFactory(TextFieldTableCell.forTableColumn());
        fieldLabel.setEditable(true);

        isBaseAttr.setCellValueFactory(row -> row.getValue().getIsBaseAttr());
        isBaseAttr.setCellFactory(CheckBoxTableCell.forTableColumn(isBaseAttr));
        isBaseAttr.setEditable(true);

        showInEntity.setCellValueFactory(row -> row.getValue().getShowInEntity());
        showInEntity.setCellFactory(CheckBoxTableCell.forTableColumn(showInEntity));
        showInEntity.setEditable(true);

        showInList.setCellValueFactory(row -> row.getValue().getShowInList());
        showInList.setCellFactory(CheckBoxTableCell.forTableColumn(showInList));
        showInList.setEditable(true);

        showInAddForm.setCellValueFactory(row -> row.getValue().getShowInAddForm());
        showInAddForm.setCellFactory(CheckBoxTableCell.forTableColumn(showInAddForm));
        showInAddForm.setEditable(true);

        showInUpdateForm.setCellValueFactory(row -> row.getValue().getShowInUpdateForm());
        showInUpdateForm.setCellFactory(CheckBoxTableCell.forTableColumn(showInUpdateForm));
        showInUpdateForm.setEditable(true);

        modelTableView.setEditable(true);

        tableNameSelect.setItems(FXCollections.observableArrayList());

        dataSourceSelect.setCellFactory((ListView<Configuration> param) -> {
            ListCell<Configuration> cell = new ListCell<Configuration>(){
                protected void updateItem(Configuration config, boolean bln) {
                    super.updateItem(config, bln);
                    if(config != null) {
                        setText(config.getConfigGroup());
                        return;
                    }
                    setText(null);
                }
            };
            return cell;
        });
    }

    @FXML
    protected void generate(ActionEvent event) {
        Configuration configuration = dataSourceSelect.getSelectionModel().getSelectedItem();
        if (configuration == null) {
            MessageBox.display(null, "Info", "请先选择数据源。", null);
            return;
        }
        String tableName = tableNameSelect.getSelectionModel().getSelectedItem();
        if (StringUtils.isBlank(tableName)) {
            MessageBox.display(null, "Info", "请先选择表。", null);
            return;
        }
        ModelConfiguration modelConfiguration = new ModelConfiguration(configuration);
        Context.setModelConfiguration(modelConfiguration);
        modelConfiguration.setTableName(tableName);
        modelConfiguration.setAlias(alias.getText());
        modelConfiguration.setEntityName(entityName.getText());
        modelConfiguration.setEntityDesc(entityDesc.getText());
        modelConfiguration.setModuleDesc(moduleDesc.getText());
        modelConfiguration.setRequestMapping(requestMapping.getText());
        modelConfiguration.setPageLocation(pageLocation.getText());

        String keyColumnNameStr = keyColumnName.getText();
        modelConfiguration.setKeyColumnName(StringUtils.isBlank(keyColumnNameStr) ? "id" : keyColumnNameStr);

        List<FieldConfiguration> fieldList = Lists.newArrayList();
        modelTableView.getItems().forEach(tableRowVO -> {
            FieldConfiguration field = new FieldConfiguration(modelConfiguration);
            field.setColumnName(tableRowVO.getColumnName().get());
            field.setJdbcLength(tableRowVO.getJdbcLength().get());
            field.setJdbcType(tableRowVO.getJdbcType().get());
            field.setComment(tableRowVO.getComment().get());
            field.setFieldLabel(tableRowVO.getFieldLabel().get());
            field.setFieldName(tableRowVO.getFieldName().get());
            field.setJavaType(tableRowVO.getJavaType().get());

            field.setBaseAttr(tableRowVO.getIsBaseAttr().get());
            field.setRequired(tableRowVO.getRequired().get());
            field.setShowInAddForm(tableRowVO.getShowInAddForm().get());
            field.setShowInEntity(tableRowVO.getShowInEntity().get());
            field.setShowInList(tableRowVO.getShowInList().get());
            field.setShowInUpdateForm(tableRowVO.getShowInUpdateForm().get());
            fieldList.add(field);
        });

        modelConfiguration.setFields(fieldList);

        new EntityGenerator(modelConfiguration).generateAndwriteToFile();
        new ServiceGenerator(modelConfiguration).generateAndwriteToFile();
        new MapperGenerator(modelConfiguration).generateAndwriteToFile();

        String mapperPackage = configuration.getMapperPackage();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("today", Context.getToday());
        map.put("author", configuration.getAuthor());
        map.put("fcCopyright", configuration.getCopyright());
        map.put("controllerPkg", configuration.getControllerPackage());
        map.put("modelConfiguration",modelConfiguration);
        map.put("mapperPackage", mapperPackage);
        map.put("entityPackage", configuration.getEntityPackage());

        String mapperDir = getPath(configuration, mapperPackage);
        String controllerDir = getPath(configuration, configuration.getControllerPackage());
        UITemplate.write(map, UITemplateFile.MAPPER,
                modelConfiguration.getEntityName() + "Mapper",
                mapperDir
        );
        UITemplate.write(map, UITemplateFile.CONTROLLER,
                modelConfiguration.getEntityName() + "Controller",
                controllerDir
        );

        UITemplate.write(map, UITemplateFile.VIEW_MAIN,
                "view_main.ftl",
                configuration.getSrcPath() + "/" + modelConfiguration.getPageLocation()
        );
        UITemplate.write(map, UITemplateFile.VIEW_LIST,
                "view_list.ftl",
                configuration.getSrcPath() + "/" + modelConfiguration.getPageLocation()
        );
        UITemplate.write(map, UITemplateFile.VIEW_FORM_ADD,
                "view_formAdd.ftl",
                configuration.getSrcPath() + "/" + modelConfiguration.getPageLocation()
        );
        UITemplate.write(map, UITemplateFile.VIEW_FORM_UPDATE,
                "view_formUpdate.ftl",
                configuration.getSrcPath() + "/" + modelConfiguration.getPageLocation()
        );
        MessageBox.display(this.primaryStage, "Message",
                "生成完毕，请到[" + configuration.getSrcPath() +"]目录下查看生成文件。",
                null);
    }

    @FXML
    protected void toConfigurationView(ActionEvent event) {
        Scene scene = SceneHolder.getScene(SceneHolder.CONFIGURATION_VIEW);
        primaryStage.setScene(scene);
    }

    @FXML
    protected void selectTableName(ActionEvent event) {
        Configuration configuration = dataSourceSelect.getSelectionModel().getSelectedItem();
        if (configuration == null) {
            return;
        }
        String tableName = ((ComboBox) event.getSource()).getSelectionModel().getSelectedItem().toString();
        List<TableRowVO> tableRowVOList = TableHandler.getTableColumns4TCModel(configuration, tableName);

        ObservableList<TableRowVO> tableRows = FXCollections.observableArrayList();
        tableRows.addAll(tableRowVOList);
        modelTableView.setItems(tableRows);


        if (tableName != null && !"".equals(tableName.trim()) &&
                tableName.indexOf("选张表吧") < 0) {
            String tempTableName = clearDatabasePrefix(configuration, tableName);
            String classENName = JavaBeansUtil.getCamelCaseString(tempTableName, true);
            entityName.setText(classENName);
            alias.setText(StringUtil.getTableAlias(tempTableName));
            requestMapping.setText("/" + parseRequestMapping(tempTableName));
            pageLocation.setText(JavaBeansUtil.getCamelCaseString(tempTableName, false).toLowerCase());
        }
    }

    private String parseRequestMapping(String tableName) {
        return tableName.toLowerCase().replaceAll("_", "-") + "s";
    }

    private String clearDatabasePrefix(Configuration configuration, String tableName) {
        String prefix = configuration.getDatabasePrefix();
        if (StringUtils.isBlank(prefix)) {
            return tableName;
        }
        String newTableName = tableName.toLowerCase();
        prefix = prefix.toLowerCase() + "_";
        if (newTableName.startsWith(prefix)) {
            return newTableName.substring(prefix.length());
        }
        return tableName;
    }

    @FXML
    public void selectDataSource(ActionEvent event) {
        Configuration configuration = (Configuration) ((ComboBox) event.getSource())
                .getSelectionModel().getSelectedItem();
        if (configuration == null) {
            return;
        }
        DbUtil.getConn(configuration);
        List<String> tableList = TableHandler.getTableNameList(configuration, null);
        setTableNameSelectItems(tableList);
        // refresh database
    }
    public void setTableNameSelectItems(List<String> itemList) {
        if (itemList == null || itemList.isEmpty()) {
            return;
        }
        tableNameSelect.getItems().setAll(itemList);
        if (itemList.size() > 0) {
            tableNameSelect.setValue(itemList.get(0));
        }
    }

    public void refresh() {
        dataSourceSelect.getItems().setAll(Context.getConfigs());
    }

    private String getPath(Configuration configuration, String pkg) {
        if (!StringUtil.isBLank(pkg)) {
            pkg = pkg.replaceAll("\\.","/");
        } else {
            pkg = "";
        }
        String srcPath = configuration.getSrcPath();
        String dir;
        if (StringUtil.isBLank(srcPath)) {
            dir = null;
        } else {
            if (srcPath.endsWith("/") || srcPath.endsWith("\\")) {
                dir = srcPath + pkg;
            } else {
                dir = srcPath + "/" + pkg;
            }
        }
        return dir;
    }
}
