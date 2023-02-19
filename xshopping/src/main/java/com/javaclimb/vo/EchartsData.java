package com.javaclimb.vo;

/**
 * echarts图表数据转换
 */
public class EchartsData {
    private String name;
    private Integer value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public EchartsData() {
    }

    public EchartsData(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}
