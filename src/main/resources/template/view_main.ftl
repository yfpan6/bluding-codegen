<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="UTF-8"/>
  <title>${modelConfiguration.entityDesc}</title>
  <link rel="stylesheet" href="/bootstrap/3.3.7/css/bootstrap.min.css"/>
  <link rel="stylesheet" href="/lib/toastr/css/toastr.min.css"/>
  <script src="/jquery/2.1.1/jquery.min.js"></script>
  <script src="/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="/lib/toastr/js/toastr.min.js"></script>
</head>
<body>
<div class="container">
  <div class="row" id="data_list">
  </div>
</div>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
          ×
        </button>
        <h4 class="modal-title" id="myModalLabel">
          ${modelConfiguration.entityDesc}Form:
        </h4>
      </div>
      <div class="modal-body" id="form_body">
      </div>
      <div class="modal-footer">
        <button class="btn btn-default" data-dismiss="modal">
          关闭
        </button>
        <button class="btn btn-primary" id="submit_form">
          提交
        </button>
      </div>
    </div>
  </div>
</div>
<script type="text/javascript">
  ${"$"}(function() {
    ${"$"}('${"#"}data_list').load('${modelConfiguration.requestMapping}/views/list');
    ${"$"}('${"#"}submit_form').on('click', function() {
      submitForm();
    });

    ${"$"}('${"#"}myModal').on('show.bs.modal', function (e) {
      var target = e.relatedTarget;
      var id = 0;
      if (${"$"}(target).attr('record-id')) {
        id = ${"$"}(target).attr('record-id');
      }
      ${"$"}('${"#"}form_body').load('${modelConfiguration.requestMapping}/views/form?id=' + id);
    });
  });

  function submitForm() {
    var dataModel = {};
    var fileName, val, me;
    ${"$"}('form input').each(function() {
      me = $(this)
      fileName = me.attr('name');
      if (!fileName) {
        console.error('input id undefined.')
        return;
      }
      val = me.val();
      setValue(dataModel, fileName, val);
    });
    var id = $('${"#"}record_id').val();
    var url = '${modelConfiguration.requestMapping}', type = 'POST';
    /*<![CDATA[*/
    if (id && id != '0') {
      url = url + '/' + id;
      type = 'PUT';
      dataModel['id'] = id;
    }
    /*]]>*/
    ${"$"}.ajax({
      url: url,
      type: type,
      data: dataModel,
      dataType: 'json',
      success: function(resp) {
        $('${"#"}myModal').modal('hide');
        toastr.info('保存成功');
        $('${"#"}data_list').load('${modelConfiguration.requestMapping}/views/list');
      },
      error: function(resp) {
        toastr.error('出现错误，请检查配置。');
      }
    });
  }

  function deleteDataModel(id) {
    ${"$"}.ajax({
      url: '${modelConfiguration.requestMapping}/' + id,
      type: 'delete',
      dataType: 'json',
      success: function(resp) {
        toastr.info('删除成功');
        $('${"#"}data_list').load('${modelConfiguration.requestMapping}/views/list');
      },
      error: function(resp) {
        toastr.error('服务端出现错误。');
      }
    });
  }

  function setValue(obj, fieldName, val) {
    if (val) {
      obj[fieldName] = val;
    }
  }
</script>
</body>
</html>