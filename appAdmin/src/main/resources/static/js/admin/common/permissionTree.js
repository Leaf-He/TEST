function PermissionTree(divId,checkEnable){

    this.id;
    this.checkEnable;
    this.onClickCallBack=function (event, treeId, treeNode) {};
    this.treeObj;

    var me = this; //私有函数里面调用父结构的指针

    //私有 设置
    var setting = {
        view: {
            showLine: true,
            selectedMulti: false
        },
        async: {
            enable: true,
            type: "post",
            contentType: "application/json",
            url: contextPath+"admin/permission/ajax/treeList",
            autoParam: ["id", "name"],
            dataFilter: dataFilter
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        check: {
            enable: isUndefined(checkEnable)?false:checkEnable,
            chkboxType: { "Y": "s", "N": "s" }
        },
        callback: {
            onAsyncError:perssimonAsyncError,
            onAsyncSuccess: perssimonAsyncSuccess,
            onClick: function(event, treeId, treeNode){
                var treeObj = $.fn.zTree.getZTreeObj(treeId);
                if(treeObj.setting.check.enable==true){
                    treeObj.checkNode(treeNode, !treeNode.checked, false);
                }
                me.onClickCallBack(event, treeId, treeNode);
            },
            onCheck: function (event, treeId, treeNode) {
                var treeObj = $.fn.zTree.getZTreeObj(treeId);
                treeObj.selectNode(treeNode,false);
            }
        }
    };

    function perssimonAsyncSuccess(event, treeId, treeNode, msg) {
        var treeObj = $.fn.zTree.getZTreeObj(treeId);
        if(null==treeNode){
            var nodes = getRootsByTreeObj(treeObj);
            if(null!=nodes && nodes.length>0){
                for (var i = 0; i < nodes.length; i++) { //设置节点展开
                    treeObj.expandNode(nodes[i], true, false, true);
                }
            }
        }else {
            treeObj.expandNode(treeNode, true, true, true);
        }

    }

    function perssimonAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
        layer.alert('权限树加载失败');
    };

    //私有
    function loadSuccess (treeId) {
        var treeObj = getTreeObj(treeId);
        var nodes = getRootsByTreeObj(treeObj);
        if(null!=nodes && nodes.length>0){
            for (var i = 0; i < nodes.length; i++) { //设置节点展开
                treeObj.expandNode(nodes[i], true, false, true);
            }
        }
        me.treeObj=treeObj;
    }
    //私有
    function dataFilter(treeId, parentNode, childNodes) {
        if (!childNodes) return null;
        for (var i=0, l=childNodes.length; i<l; i++) {
            childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
        }
        return childNodes;
    };

    //私有 获取树对象
    getTreeObj=function (id) {
        return $.fn.zTree.getZTreeObj(id);
    }

    //私有
    init =function(){
        me.id=divId;
        me.checkEnable=checkEnable;
        $.ajax({
            type : "POST",  //提交方式
            url : contextPath+'admin/permission/ajax/treeList',
            async:false,
            data : [],
            success : function(data) {//返回数据根据结果进行相应的处理
                $.fn.zTree.init($("#"+me.id), setting,data);
                loadSuccess(me.id);
            },
            error : function () {
                layer.alert('网络原因,加载权限下拉选项错误');
            }
        });

    };

    //返回根节点集合
    getRootsByTreeObj=function (treeObj){
        var nodes = null;
        if(!isUndefined(treeObj) && null!=treeObj){
            nodes = treeObj.getNodesByFilter(function (node) { return node.level == 0 });
        }
        return nodes;
    };

    //私有
    init();
}

PermissionTree.prototype = {
    constructor: PermissionTree,
    //返回根节点集合
    getRoots:function (){
        return getRootsByTreeObj(this.treeObj);
    },
    setCheckEnable:function (enable) {
        this.checkEnable=enable;
        this.treeObj.setting.check.enable = enable;
        this.treeObj.refresh();
    },
    onClick:function(func){
        this.onClickCallBack=func;
    },
    getNodeById:function (nodeId) {
        var nodes = this.treeObj.getNodesByParam("id", String(nodeId), null);
        return nodes[0];
    },
    refresh:function(){
        this.treeObj.reAsyncChildNodes(null, "refresh",true);
    }

}