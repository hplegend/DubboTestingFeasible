package com.hplegend.dubbo.common;

/**
 * @author hp.he
 * @date 2019/9/28 19:30
 */
public class MethodArgumentAndValue {

    private String methodType;
    private String methodValue;


    public MethodArgumentAndValue(String methodType, String methodValue) {
        this.methodType = methodType;
        this.methodValue = methodValue;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getMethodValue() {
        return methodValue;
    }

    public void setMethodValue(String methodValue) {
        this.methodValue = methodValue;
    }
}
