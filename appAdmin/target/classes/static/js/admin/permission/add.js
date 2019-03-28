function showError(msg) {
    $('#add_error').removeClass('hidden');
    $('#add_error_label').html(msg)
}

function clearError() {
    $('#add_error').addClass('hidden');
    $('#add_error_label').html('')
}

function savePermissionValidate(){
    clearError();
    if(Validator.validateEmpty($('#add_name').val(),'add_name')){
        showError('权限名称不能为空');
        return false;
    }

    if(Validator.validateEmpty($('#add_permission').val(),'add_permission')){
        showError('权限不能为空');
        return false;
    }

    if(Validator.validateEmpty($('#add_parent').val(),'add_parent')){
        showError('上级权限不能为空');
        return false;
    }

    if(isDuplicateAttr({name:$('#add_name').val()})){
        showError('已存在该权限名称');
        $('#add_name').focus();
        return false;
    }

    if(isDuplicateAttr({permission:$('#add_permission').val()})){
        showError('已存在该权限');
        $('#add_permission').focus();
        return false;
    }

    var order = $('#add_order').val();
    if( !$.isEmptyObject(order) && ""!==order){
        if(!Validator.isNumber($('#add_order').val(),'add_order')){
            showError('显示顺序必须是数字');
            $('#add_order').focus();
            return false;
        }
    }

    return true;

}

function getSaveLevel() {
    var pId = $('#add_parent').val();
    var parent = permissionTree.treeObj.getNodeByParam("id", pId, null);
    return parent.dataMap.level+1;
}

function savePermission() {
    if(savePermissionValidate()){
        clearError();
        var pId = $('#add_parent').val();
        var parentNode = permissionTree.getNodeById(pId);
        var level = getSaveLevel();
        $('#add_level').val(level);
        $('#add_permission_form').ajaxSubmit({
            type: 'post', // 提交方式 get/post
            url: contextPath+'admin/permission/ajax/save',
            dataType:'json',
            data:$('#add_permission_form').serialize(),
            success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
                if(data.operationFlag==1){
                    layer.alert('添加成功',layerHelp.alertDefaultConfig,function(){
                        var permission = data.dataMap.addPermission;
                        var newNode = {
                            id:permission.id,
                            name:permission.name,
                            pId:permission.pId,
                            dataMap:{
                                permission:permission.permission,
                                order:permission.order,
                                desc:permission.desc,
                                level:permission.level
                            }
                        };
                        permissionTree.treeObj.addNodes(parentNode,newNode);
                        permissionTree.treeObj.selectNode(newNode);
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

