<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org">
<body>
<input type="hidden" id="record_id"/>
<form class="form-horizontal" role="form">
  <#list modelConfiguration.fields as field>
    <#if field.columnName!="auto_inc_id" && field.showInAddForm>
  <div class="form-group">
    <label class="col-sm-2 control-label">${field.labelText}</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" name="${field.fieldName}"/>
    </div>
  </div>
    </#if>
  </#list>
</form>
</body>
</html>