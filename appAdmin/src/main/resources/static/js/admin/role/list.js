var roleTable;

$(function(){

    roleTable = dataTablesUtil.initTable('role_table',{
        "sAjaxSource": contextPath+'admin/role/ajax/list',
        "aaSorting": [ 1, "asc" ],
        "fnServerData": function ( sSource, aoData, fnCallback ) {
            $.ajax({
                "dataType": 'json',
                "type": 'POST',
                "url": sSource,
                "data": aoData,
                "success": function (result) {

                    var page = result.dataMap.page;
                    result.recordsTotal =  page.total;
                    result.recordsFiltered =  page.total;
                    result.data = page.records;
                    fnCallback(result);
                }
            });
        },
        columns: [
            {
                data: 'id' ,
                name: 'role_id',
                cellType: "th",
                orderable: false,
                title: '<input type="checkbox" id="check_all" onclick="dataTablesUtil.checkedAllByName(this,\'role_id\')" />&nbsp全选',
                render : idSelectHtml,
                width : '10%'
            },
            { data: 'name' ,name: 'role_name',title: '角色名称', width : '20%'},
            { data: 'state' ,name: 'role_state',title: '状态',width : '20%', render : stateHtml},
            {   data: 'description' ,
                name: 'role_description',
                title: '描述',
                width : '20%',
                render : dataTablesUtil.nullHtml
            },
            {
                orderable: false,
                title: '操作',
                render : optHtml,
                width : '20%'
            }
         ]
    });

    function stateHtml(data, type, full, meta){
        if(data==1){
            return '启用';
        }else{
            return '禁用';
        }
    }

    function idSelectHtml(data, type, full, meta){
        return "<input type='checkbox' name='role_id' value='" + data + "' />";
    }

    function optHtml(data, type, full, meta) {
        var optHtml = '<div>'
        optHtml += '<button  class="btn btn-sm btn-warning" onclick="edit(\''+full.id+'\')">编辑</button>&nbsp;';
        optHtml += '<button  class="btn btn-sm btn-danger" onclick="delConfirm([\''+full.id+'\'])">删除</button>';
        optHtml +='</div>';
        return optHtml;
    }

    $('#role_add_btn').click(function(){
        var add_html = template('admin_role_add_tmp',[]);
        layerHelp.popPage(add_html,800,600,'添加角色');
        /*
        $("#add_state").select2({
            placeholder:'请选择',
            allowClear:false
        })
        */
        initAddFrom();

    });


    $('#role_del_btn').click(function(){
        var ids=[];
        $('input[name="role_id"]:checked').each(function(){
            ids.push($(this).val());
        });
        delConfirm(ids);
    });

});


function edit(id){
    /*
    var edit_html = template('admin_role_edit_tmp',[]);
    layerHelp.popPage(edit_html,800,600,'编辑角色');
    initEditFrom(id);
    */
};

function reloadRoleTable() {
    roleTable.ajax.reload();
    $('#check_all').removeAttr("checked",false);
}

function delConfirm(ids) {
    debugger
    if(!ids.length>0){
        layer.alert('请勾选至少一个角色',layerHelp.alertDefaultConfig);
        return;
    }

    layer.confirm('确定要删除吗？', {
        btn: ['确定','取消'] ,
        offset:[layerHelp.defaultOffsetY,layerHelp.defaultOffsetX]
    }, function(index){
        del( ids);
    }, function(index){
        layer.close(index);
    });
}

function del(ids){
    $.ajax({
        type : "POST",  //提交方式
        dataType: 'json',
        url : contextPath+'admin/role/ajax/del',//路径
        data : {'ids':ids},
        success : function(data) {//返回数据根据结果进行相应的处理
            if(data.operationFlag==1){
                layer.alert('删除成功',layerHelp.alertDefaultConfig,function(){
                    layer.closeAll();
                    reloadRoleTable();
                });
            }else{
                layer.alert(data.retMessage,layerHelp.alertDefaultConfig);
            }
        },
        error : function () {
            layer.alert('网络原因,删除失败,请稍后再试',layerHelp.alertDefaultConfig);
        }
    });
}