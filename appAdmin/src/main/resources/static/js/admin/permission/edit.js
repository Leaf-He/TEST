function showError(msg) {
    $('#edit_error').removeClass('hidden');
    $('#edit_error_label').html(msg)
}

function clearError() {
    $('#edit_error').addClass('hidden');
    $('#edit_error_label').html('')
}

function updatePermissionValidate(){
    clearError();
    if(Validator.validateEmpty($('#edit_name').val(),'add_name')){
        showError('权限名称不能为空');
        return false;
    }

    if(Validator.validateEmpty($('#edit_permission').val(),'add_permission')){
        showError('权限不能为空');
        return false;
    }

    if(Validator.validateEmpty($('#edit_parent').val(),'add_parent')){
        showError('上级权限不能为空');
        return false;
    }

    if(isDuplicateAttr({name:$('#edit_name').val(),id:$('#edit_permission_id').val()})){
        showError('已存在该权限名称');
        $('#add_name').focus();
        return false;
    }

    if(isDuplicateAttr({permission:$('#edit_permission').val(),id:$('#edit_permission_id').val()})){
        showError('已存在该权限');
        $('#add_permission').focus();
        return false;
    }

    var order = $('#edit_order').val();
    if( !$.isEmptyObject(order) && ""!==order){
        if(!Validator.isNumber($('#edit_order').val(),'add_order')){
            showError('显示顺序必须是数字');
            $('#add_order').focus();
            return false;
        }
    }

    return true;

}

function getUpdateLevel() {
    var pId = $('#edit_parent').val();
    var parent = permissionTree.treeObj.getNodeByParam("id", pId, null);
    return parent.dataMap.level+1;
}

function updatePermission() {
    if(updatePermissionValidate()){
        clearError();
        var pId = $('#edit_parent').val();
        var parentNode = permissionTree.getNodeById(pId);
        var level = getUpdateLevel();
        $('#edit_level').val(level);
        $('#edit_permission_form').ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: contextPath+'admin/permission/ajax/update',
            dataType:'json',
            data:$('#edit_permission_form').serialize(),
            success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
                if(data.operationFlag==1){
                    layer.alert('修改成功',layerHelp.alertDefaultConfig,function(){
                        var permission = data.dataMap.updatePermission;
                        /*
                        var updateNode = permissionTree.getNodeById(permission.id);
                        updateNode.name=permission.name;
                        updateNode.pId=permission.pId;
                        updateNode.dataMap.permission=permission.permission;
                        updateNode.dataMap.order=permission.order;
                        updateNode.dataMap.desc=permission.desc;
                        updateNode.dataMap.level=permission.level;
                        permissionTree.treeObj.updateNode(updateNode);
                        */
                        permissionTree.refresh();
                        layer.closeAll();
                    });
                }else{
                    showError(data.retMessage);
                }
            },
            error: function(data){
                showError("网络故障，请稍后再试");
            }
        });

    }
}

