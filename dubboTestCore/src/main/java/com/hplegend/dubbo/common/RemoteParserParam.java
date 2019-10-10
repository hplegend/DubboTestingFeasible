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

    private String registryProtocol;

    private String interfaceName;

    private String serviceVersion;

    private RemoteParserParam() {

    }


    /**
     * 构造者模式
     */
    public static class Builder {
        private String zkAddress;

        private String dubboGroup;

        private String registryProtocol;

        private String interfaceName;

        private String serviceVersion;

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

        public Builder interfaceName(String interfaceName) {
            this.interfaceName = interfaceName;
            return this;
        }

        public Builder serviceVersion(String serviceVersion) {
            this.serviceVersion = serviceVersion;
            return this;
        }


        public RemoteParserParam build() {
            RemoteParserParam var = new RemoteParserParam();
            var.zkAddress = this.zkAddress;
            var.dubboGroup = this.dubboGroup;
            var.registryProtocol = this.registryProtocol;
            var.interfaceName = this.interfaceName;
            var.dubboGroup = this.dubboGroup;
            var.serviceVersion = this.serviceVersion;

            return var;
        }
    }


    public String getDubboGroup() {
        return dubboGroup;
    }


    public String getRegistryProtocol() {
        return registryProtocol;
    }


    public String getZkAddress() {
        return zkAddress;
    }


    public String getServiceVersion() {
        return serviceVersion;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

}
