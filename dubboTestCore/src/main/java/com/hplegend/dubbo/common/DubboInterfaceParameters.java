package com.hplegend.dubbo.common;

import java.util.List;

/**
 * @author hp.he
 * @date 2019/9/28 19:29
 */
public class DubboInterfaceParameters {

    private DubboInterfaceInvokeParam interfaceInvokeParam;

    private List<MethodArgumentAndValue> methodArgumentAndValueList;


    public DubboInterfaceInvokeParam getInterfaceInvokeParam() {
        return interfaceInvokeParam;
    }

    public void setInterfaceInvokeParam(DubboInterfaceInvokeParam interfaceInvokeParam) {
        this.interfaceInvokeParam = interfaceInvokeParam;
    }

    public List<MethodArgumentAndValue> getMethodArgumentAndValueList() {
        return methodArgumentAndValueList;
    }

    public void setMethodArgumentAndValueList(List<MethodArgumentAndValue> methodArgumentAndValueList) {
        this.methodArgumentAndValueList = methodArgumentAndValueList;
    }
}
