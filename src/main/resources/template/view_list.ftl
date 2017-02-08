<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org">
<body>
<table class="table">
  <caption>
    <button class="btn btn-primary" data-toggle="modal" data-target="#myModal">
      创建
    </button>
    <hr/>
  </caption>
  <thead>
  <tr><#list modelConfiguration.fields as field><#if field.columnName!="auto_inc_id" && field.showInList>
    <th>${field.labelText}</th>
  </#if></#list><th width="200">操作</th>
  </tr>
  </thead>
  <tbody>
  <tr th:each="record, iterStat:${"$"}{recordList}">
    <#list modelConfiguration.fields as field>
      <#if field.columnName!="auto_inc_id" && field.showInList>
     <td th:text="${"$"}{record.${field.fieldName}}"></td>
      </#if>
    </#list>
    <td>
      <a class="btn btn-primary" data-toggle="modal"
         th:attr="record-id='' + ${"$"}{record.id}"
         data-target="#myModal">
        修改
      </a>
      <a class="btn btn-danger" data-toggle="modal"
         th:onclick="'deleteDataModel(' + ${"$"}{record.id} + ')'">
        删除
      </a>
    </td>
  </tr>
  </tbody>
</table>
</body>
</html>