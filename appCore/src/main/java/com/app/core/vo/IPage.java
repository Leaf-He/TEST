package com.app.core.vo;

import com.baomidou.mybatisplus.plugins.Page;

import java.util.ArrayList;
import java.util.List;

public class IPage<T> extends Page<T> {

    /**
     * 第几行开始，默认是0
     */
    private Integer iDisplayStart;
    /**
     * 一行显示多少条记录，默认是10
     */
    private Integer iDisplayLength;
    /**
     *  对应datatables column的name属性值，以逗号隔开
     */
    private String sColumns;
    /**
     * 排序字段的下标
     */
    private Integer iSortCol_0;
    /**
     * 升序还是降序
     */
    private String sSortDir_0;

    public Integer getIDisplayStart() {
        return iDisplayStart;
    }

    public void setIDisplayStart(Integer iDisplayStart) {
        if(iDisplayStart == null){
            iDisplayStart = 0;
        }
        int currentPageNum = 0;
        if(this.getIDisplayLength() == null || this.getIDisplayLength() == 0){
            currentPageNum = iDisplayStart/10;
        }else{
            currentPageNum = iDisplayStart/this.getIDisplayLength();
        }

        this.setCurrent(++currentPageNum);

        this.iDisplayStart = iDisplayStart;
    }

    public Integer getIDisplayLength() {
        return iDisplayLength;
    }

    public void setIDisplayLength(Integer iDisplayLength) {
        if(iDisplayLength == null || iDisplayLength == 0){
            iDisplayLength = 10;
        }
        this.setSize(iDisplayLength);
        this.iDisplayLength = iDisplayLength;
    }

    public String getsColumns() {
        return sColumns;
    }

    public void setsColumns(String sColumns) {
        this.sColumns = sColumns;
    }

    public Integer getiSortCol_0() {
        return iSortCol_0;
    }

    public void setiSortCol_0(Integer iSortCol_0) {
        this.iSortCol_0 = iSortCol_0;
    }

    public String getsSortDir_0() {
        return sSortDir_0;
    }

    public void setsSortDir_0(String sSortDir_0) {

        if(iSortCol_0 != null){
            String[] cols = getsColumns().split(",");
            if(cols != null && iSortCol_0<cols.length){
                List<String> ascOrders;
                List<String> descOrders;
                String orderCol = cols[iSortCol_0];

                if(this.getAscs() == null){
                    ascOrders = new ArrayList<String>();
                    this.setAscs(ascOrders);
                }else{
                    ascOrders = this.getAscs();
                }

                if(this.getDescs() == null){
                    descOrders = new ArrayList<String>();
                    this.setDescs(descOrders);
                }else{
                    descOrders = this.getDescs();
                }

                if("asc".equals(sSortDir_0)){
                    ascOrders.add(orderCol);
                }else{
                    descOrders.add(orderCol);
                }
            }

        }

        this.sSortDir_0 = sSortDir_0;
    }

    /*public void setOrder(IOrder[] order) {
        if(order != null && order.length>0){
            List<String> ascOrders;
            List<String> descOrders;

            if(this.getAscs() == null){
                ascOrders = new ArrayList<String>();
                this.setAscs(ascOrders);
            }else{
                ascOrders = this.getAscs();
            }

            if(this.getDescs() == null){
                descOrders = new ArrayList<String>();
                this.setDescs(descOrders);
            }else{
                descOrders = this.getDescs();
            }

            for(IOrder ord : order){
                if(ord != null){
                    Integer index = ord.getColumn();
                    if(index == null){
                        continue;
                    }else{
                        if("asc".equals(ord.getDir())){
                            ascOrders.add(this.getColumns()[index].getData());
                        }else{
                            descOrders.add(this.getColumns()[index].getData());
                        }
                    }
                }
            }
        }
        this.order = order;
    }*/
}
