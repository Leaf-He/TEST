package com.app.core.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * 树形结构VO 与ztree结构一样
 */
public class TreeNode implements Serializable {

    private static final long serialVersionUID = -2721195090850798613L;

    private String id;

    private String pId;

    private String name;

    private Map<String,Object> dataMap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }
}
