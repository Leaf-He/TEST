function PermissionTree(divId){
    this.id=divId;
    this.checkEnable=false;

    onClick=function () {}

    perssimonAsyncError=function(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
        alert('权限树加载失败');
    };
    dataFilter=function(treeId, parentNode, childNodes) {
        if (!childNodes) return null;
        for (var i=0, l=childNodes.length; i<l; i++) {
            childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
        }
        return childNodes;
    };

    //返回根节点集合
    getRoots=function (treeObj){
        var nodes = treeObj.getNodesByFilter(function (node) { return node.level == 0 });
        return nodes;
    }

    perssimonAsyncSuccess=function (event, treeId, treeNode, msg) {
        var treeObj = $.fn.zTree.getZTreeObj(treeId);
        if(null==treeNode){
            var nodes = getRoots(treeObj);
            if(null!=nodes && nodes.length>0){
                for (var i = 0; i < nodes.length; i++) { //设置节点展开
                    treeObj.expandNode(nodes[i], true, false, true);
                }
            }
        }else {
            treeObj.expandNode(treeNode, true, true, true);
        }

    }

    this.setting = {
        view: {
            showLine: true,
            selectedMulti: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        check: {
            enable: false,
            chkboxType: { "Y": "s", "N": "s" }
        },
        async: {
            enable: true,
            type: "post",
            contentType: "application/json",
            url: contextPath+"admin/permission/ajax/treeList",
            autoParam: ["id", "name"],
            dataFilter: dataFilter
        },
        callback: {
            onAsyncError:perssimonAsyncError,
            onAsyncSuccess: perssimonAsyncSuccess,
            onClick: function(event, treeId, treeNode){
                var treeObj = $.fn.zTree.getZTreeObj(treeId);
                if(treeObj.setting.check.enable==true){
                    treeObj.checkNode(treeNode, !treeNode.checked, false);
                }
                onClick(event, treeId, treeNode);
            },
            onCheck: function (event, treeId, treeNode) {
                var treeObj = $.fn.zTree.getZTreeObj(treeId);
                treeObj.selectNode(treeNode,false);
            }
        }
    };

    this.init=function(){
        $.fn.zTree.init($("#"+this.id), this.setting);
    };

    this.setCheckEnable=function (enable) {
        this.checkEnable=enable;
        $.fn.zTree.getZTreeObj(this.id).setting.check.enable = enable;
    };

    this.getTreeObj=function () {
       return $.fn.zTree.getZTreeObj(this.id);
    }

    this.onClick=function(func){
        onClick=func;
    }

    this.refresh=function(){
        this.getTreeObj().reAsyncChildNodes(null, "refresh",true);
    }

    this.refreshNode=function(nodeId,isSilent){
        var nodes = this.getTreeObj().getNodesByParam("id", String(nodeId), null);
        this.getTreeObj().reAsyncChildNodes(nodes[0], "refresh",isSilent);
    }
    
    this.expandNode=function (nodeId) {
        var nodes = this.getTreeObj().getNodesByParam("id", String(nodeId), null);
        if(nodes.length>0){
            this.getTreeObj().expandNode(nodes[0], true, true, true);
        }
    }

    this.addNodes=function (panent,nodes) {
        this.getTreeObj().addNodes(panent, nodes);
    }

    this.getNodeById=function (nodeId) {
        var nodes = this.getTreeObj().getNodesByParam("id", String(nodeId), null);
        return nodes[0];
    }

    this.getCheckedNodes=function (checked) {
        return this.getTreeObj().getCheckedNodes(checked);
    }
}