var editPermissionTree=null;

function initEditFrom(id) {
    /*
    $.ajax({
        type : "POST",  //提交方式
        dataType: 'json',
        url : contextPath+'admin/role/'+id,//路径
        data : {},
        success : function(data) {//返回数据根据结果进行相应的处理
            if(data.operationFlag==1){
                console.log(id);
            }else{
                layer.alert('网络原因,删除失败,请稍后再试');
            }
        },
        error : function () {
            layer.alert('网络原因,删除失败,请稍后再试');
        }
    });


    $("#edit_state").select2({
        placeholder:'请选择',
        allowClear:false
    })

    if(null!=editPermissionTree){
        editPermissionTree.treeObj.destroy();
    }
    editPermissionTree  = new PermissionTree('role_edit_permissionTree',true);
    initEditValidator();
    */
}



function getCheckedUpdPermissionIds() {
    var nodes =editPermissionTree.treeObj.getCheckedNodes(true);
    var checkedIds = []
    for(var i=0 ;i<nodes.length;i++){
        checkedIds.push(nodes[i].id);
    }
    return checkedIds;
}


function initEditValidator(){

    $('#edit_role_form').bootstrapValidator({
        fields: {
            name: {
                message: '用户名验证失败',
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    }
                }
            },
            state: {
                validators: {
                    notEmpty: {
                        message: '状态不能为空'
                    }
                }
            }
        }
    }).on('success.form.bv', function(e) {
        //阻止默认提交(需求使用ajax提交)
        e.preventDefault();
        // Get the form instance
        var $form = $(e.target);
        // Get the BootstrapValidator instance
        var bv = $form.data('bootstrapValidator');
        if (bv.isValid()) {
            $form.ajaxSubmit({
                type: 'post', // 提交方式 get/post
                url: contextPath+'admin/role/ajax/save',
                dataType:'json',
                data:{ //表单内的field 会自动提交 只需要提交没有的数据即可
                    'permissionIds':getCheckedUpdPermissionIds()
                },
                success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
                    console.log(data)
                    if(data.operationFlag==1){
                        layer.alert('更新成功',layerHelp.alertDefaultConfig,function(){
                            layer.closeAll();
                            reloadRoleTable();
                        });
                    }else{
                        bv.disableSubmitButtons(true);
                        layerHelp.formErrorPrompt.show(data.retMessage);
                    }
                },
                error: function(data){
                    bv.disableSubmitButtons(true);
                    layerHelp.formErrorPrompt.show("网络故障，请稍后再试");
                }
            });

        }

    });
}
