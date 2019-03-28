var addPermissionTree=null;

function initAddFrom(bootstrapValidator_overwrite) {

    if(null!=addPermissionTree){
        addPermissionTree.treeObj.destroy();
    }
    addPermissionTree  = new PermissionTree('role_add_permissionTree',true);
    initAddValidator();
}

function getCheckedPermissionIds() {
    var nodes =addPermissionTree.treeObj.getCheckedNodes(true);
    var checkedIds = []
    for(var i=0 ;i<nodes.length;i++){
        checkedIds.push(nodes[i].id);
    }
    return checkedIds;
}


function initAddValidator(){
    $( "#add_role_form").validate( {
        rules: {
            name: "required",
            state: "required",
            description: "required"
        },
        messages: {
            name: "用户名不能为空",
            state: "状态不能为空",
            description: "状态不能为空"
        },
        submitHandler: function () {

            $( "#add_role_form").ajaxSubmit({
                type: 'post', // 提交方式 get/post
                url: contextPath+'admin/role/ajax/save',
                dataType:'json',
                data:{ //表单内的field 会自动提交 只需要提交没有的数据即可
                    'permissionIds':getCheckedPermissionIds()
                },
                success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
                    if(data.operationFlag==1){
                        layer.alert('添加成功',layerHelp.alertDefaultConfig,function(){
                            layer.closeAll();
                            reloadRoleTable();

                        });
                    }else{
                        layerHelp.formErrorPrompt.show(data.retMessage);
                    }
                },
                error: function(data){
                    layerHelp.formErrorPrompt.show("网络故障，请稍后再试");
                }
            });
        }
    } );

    /*
    $('#add_role_form').bootstrapValidator({
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
                    'permissionIds':getCheckedPermissionIds()
                },
                success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
                    if(data.operationFlag==1){
                        layer.alert('添加成功',layerHelp.alertDefaultConfig,function(){
                            //bv.destroy();
                            //$form.data('bootstrapValidator', null);
                            layer.closeAll();
                            //reloadRoleTable();

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
    */
}
