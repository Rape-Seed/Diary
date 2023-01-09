package com.example.diary.domain.recommend.dto;

public class ExcelData {
    private Double column1;
    private String column2;

    public ExcelData() {
    }

    public ExcelData(Double column1, String column2) {
        this.column1 = column1;
        this.column2 = column2;
    }

    public Double getColumn1() {
        return column1;
    }

    public void setColumn1(Double column1) {
        this.column1 = column1;
    }

    public String getColumn2() {
        return column2;
    }

    public void setColumn2(String column2) {
        this.column2 = column2;
    }
}
