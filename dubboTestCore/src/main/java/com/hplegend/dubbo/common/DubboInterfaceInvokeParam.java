package com.hplegend.dubbo.common;

/**
 * @author hp.he
 * @date 2019/9/28 19:22
 */
public class DubboInterfaceInvokeParam {


    private String zkAddress;

    private String dubboGroup;

    private String registryProtocol;

    private String interfaceName;

    private String methodName;

    private String dubboVersion;



    public String getZkAddress() {
        return zkAddress;
    }

    public String getDubboGroup() {
        return dubboGroup;
    }

    public String getRegistryProtocol() {
        return registryProtocol;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getDubboVersion() {
        return dubboVersion;
    }



    public static class Builder {

        private String zkAddress;

        private String dubboGroup;

        private String registryProtocol;

        private String interfaceName;

        private String methodName;

        private String dubboVersion;

        private Builder() {

        }

        public static Builder builder() {
            return new Builder();
        }


        public Builder zkAddress(String zkAddress) {
            this.zkAddress = zkAddress;
            return this;
        }

        public Builder dubboGroup(String dubboGroup) {
            this.dubboGroup = dubboGroup;
            return this;
        }

        public Builder registryProtocol(String registryProtocol) {
            this.registryProtocol = registryProtocol;
            return this;
        }

        public Builder methodName(String methodName) {
            this.methodName = methodName;
            return this;
        }

        public Builder interfaceName(String interfaceName) {
            this.interfaceName = interfaceName;
            return this;
        }

        public Builder dubboVersion(String dubboVersion) {
            this.dubboVersion = dubboVersion;
            return this;
        }


        public DubboInterfaceInvokeParam build() {
            DubboInterfaceInvokeParam var = new DubboInterfaceInvokeParam();
            var.zkAddress = zkAddress;
            var.dubboGroup = dubboGroup;
            var.dubboVersion = dubboVersion;
            var.interfaceName = interfaceName;
            var.methodName = methodName;
            var.registryProtocol = registryProtocol;

            return var;
        }


    }

}
