package com.app.core.vo;

/**
 * datatables 分页时传的VO
 * 根据dataTable控件的属性做出
 */
public class PageRequest {

    private int sEcho;//页数
    private int iColumns;//
    private String sColumns;
    private int iDisplayStart;//开始行数
    private int iDisplayLength;//结束行数

    public int getsEcho() {
        return sEcho;
    }

    public void setsEcho(int sEcho) {
        this.sEcho = sEcho;
    }

    public int getiColumns() {
        return iColumns;
    }

    public void setiColumns(int iColumns) {
        this.iColumns = iColumns;
    }

    public String getsColumns() {
        return sColumns;
    }

    public void setsColumns(String sColumns) {
        this.sColumns = sColumns;
    }

    public int getiDisplayStart() {
        return iDisplayStart;
    }

    public void setiDisplayStart(int iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    public int getiDisplayLength() {
        return iDisplayLength;
    }

    public void setiDisplayLength(int iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }
}
