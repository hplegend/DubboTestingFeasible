package com.hplegend.dubbo.common;

/**
 * 参数 bean
 *
 * @author hp.he
 * @date 2019/9/27 19:48
 */
public class RemoteParserParam {


    private String zkAddress;

    private String dubboGroup;

    private String rpcProtocol;

    private String interfaceName;


    private String dubboVersion;


    public String getZkAddress() {
        return zkAddress;
    }

    public void setZkAddress(String zkAddress) {
        this.zkAddress = zkAddress;
    }

    public String getDubboGroup() {
        return dubboGroup;
    }

    public void setDubboGroup(String dubboGroup) {
        this.dubboGroup = dubboGroup;
    }

    public String getRpcProtocol() {
        return rpcProtocol;
    }

    public void setRpcProtocol(String rpcProtocol) {
        this.rpcProtocol = rpcProtocol;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getDubboVersion() {
        return dubboVersion;
    }

    public void setDubboVersion(String dubboVersion) {
        this.dubboVersion = dubboVersion;
    }
}
