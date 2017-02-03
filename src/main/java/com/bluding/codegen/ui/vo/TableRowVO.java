package com.bluding.codegen.ui.vo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

/**
 * Created by myron.pan on 2017/01/30.
 */
@Data
public class TableRowVO {

    private StringProperty columnName;
    private StringProperty jdbcType;
    private StringProperty jdbcLength;
    private StringProperty fieldName;
    private StringProperty javaType;
    private StringProperty comment;
    private StringProperty fieldLabel;
    private BooleanProperty isBaseAttr = new SimpleBooleanProperty(false);
    private BooleanProperty required = new SimpleBooleanProperty(false);

    private BooleanProperty showInEntity = new SimpleBooleanProperty(true);
    private BooleanProperty showInList = new SimpleBooleanProperty(true);
    private BooleanProperty showInAddForm = new SimpleBooleanProperty(true);
    private BooleanProperty showInUpdateForm = new SimpleBooleanProperty(true);

}
