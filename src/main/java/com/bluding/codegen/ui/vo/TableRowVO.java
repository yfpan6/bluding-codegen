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
    private BooleanProperty isBaseAttr;
    private BooleanProperty required = new SimpleBooleanProperty(false);

    private BooleanProperty showInEntity;
    private BooleanProperty showInList;
    private BooleanProperty showInAddForm;
    private BooleanProperty showInUpdateForm;

    public void initIsBaseAttr(SimpleBooleanProperty value) {
        if ("auto_inc_id".equals(columnName)) {
            isBaseAttr = new SimpleBooleanProperty(true);
        } else {
            isBaseAttr = value;
        }
        showInList = new SimpleBooleanProperty(!isBaseAttr.get());
        showInEntity = new SimpleBooleanProperty(!isBaseAttr.get());
        showInAddForm = new SimpleBooleanProperty(!isBaseAttr.get());
        showInUpdateForm = new SimpleBooleanProperty(!isBaseAttr.get());
    }

}
