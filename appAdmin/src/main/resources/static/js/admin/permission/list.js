var permissionTree;

function isDuplicateAttr(attrData) {
    var dup = false;
    $.ajax({
        type : "POST",  //提交方式
        url : contextPath+'admin/permission/ajax/validatePermission',
        async:false,
        data : attrData,
        success : function(data) {//返回数据根据结果进行相应的处理
            if(data.operationFlag==1){
                if(data.dataMap.exist){
                    dup=true;
                }
            }
        },
        error : function () {
            layer.alert('网络原因,加载权限下拉选项错误');
        }
    });
    return dup;
}

$(function(){
    permissionTree  = new PermissionTree('permissionTree',true);
    permissionTree.onClick(function (event, treeId, treeNode) {
        $('#detail_name').val(treeNode.name);
        $('#detail_permission').val(treeNode.dataMap.permission);
        $('#detail_order').val(treeNode.dataMap.order);
        $('#detail_desc').html(treeNode.dataMap.desc);
        var parentNode = treeNode.getParentNode();
        if(null!=parentNode){
            $('#detail_parent').val(parentNode.name);
        }else{
            $('#detail_parent').val('无');
        }
    });

    $('#permission_add_btn').click(function(){
        var add_html = template('admin_permission_add_tmp',[]);
        layerHelp.popPage(add_html,800,500,'添加权限');
        var node = permissionTree.treeObj.getSelectedNodes()[0];
        getPermissionOptions('add_parent',node);
    });

    $('#permission_edit_btn').click(function(){
        var nodes = permissionTree.treeObj.getCheckedNodes();
        if(!nodes.length>0){
            layer.alert('请勾选一个权限节点进行编辑',layerHelp.alertDefaultConfig);
            return;
        }else if(nodes.length>1) {
            layer.alert('你勾选了多个权限节点,同时只能勾选一个进行编辑',layerHelp.alertDefaultConfig);
            return;
        }
        var node = permissionTree.treeObj.getCheckedNodes()[0];
        var tempData = {"permission":node};
        var edit_html = template('admin_permission_edit_tmp',tempData);
        layerHelp.popPage(edit_html,800,500,'编辑权限');
        getPermissionOptions('edit_parent',node.getParentNode());
    });

    $('#permission_del_btn').click(function(){
        var nodes = permissionTree.treeObj.getCheckedNodes();
        var ids = [];
        if(!nodes.length>0){
            layer.alert('请勾选至少一个权限',layerHelp.alertDefaultConfig);
            return;
        }else{
            for(var i=0 ; i<nodes.length;i++ ){
                if(nodes[i].id=='0'){
                    layer.alert('根节点不能删除，请去掉勾选',layerHelp.alertDefaultConfig);
                    return;
                }else {
                    ids[i]=nodes[i].id;
                }
            }
        }

        layer.confirm('确定要删除吗？', {
            btn: ['确定','取消'] ,
            offset:[layerHelp.defaultOffsetY,layerHelp.defaultOffsetX]
        }, function(index){
            del( ids);
        }, function(index){
            layer.close(index);
        });
    });

    function getPermissionOptions(select2Id,node) {
        //动态获取菜单
        $.ajax({
            type : "POST",  //提交方式
            url : contextPath+'admin/permission/ajax/permissionOptions',//路径
            data : {},
            success : function(data) {//返回数据根据结果进行相应的处理
                if(data.operationFlag==1){
                    $('#'+select2Id).select2({
                        data:data.dataMap.options,
                        placeholder: '请选择上级权限',
                        minimumInputLength: 0,
                        cache : false,
                        allowClear: true
                    });
                    if(!isUndefined(node)){
                        $('#'+select2Id).val(node.id).trigger("change");
                    }
                }else{
                    layer.alert(data.retMessage);
                }
            },
            error : function () {
                layer.alert('网络原因,加载权限下拉选项错误');
            }
        });
    }

    function del(ids) {
        $.ajax({
            type : "POST",  //提交方式
            dataType: 'json',
            url : contextPath+'admin/permission/ajax/del',//路径
            data : {'ids':ids},
            success : function(data) {//返回数据根据结果进行相应的处理
                if(data.operationFlag==1){
                    layer.alert('删除成功',layerHelp.alertDefaultConfig,function(){
                        permissionTree.refresh();
                        layer.closeAll();
                    });
                }else{
                    layer.alert(data.retMessage);
                }
            },
            error : function () {
                layer.alert('网络原因,删除失败,请稍后再试');
            }
        });
    }
});