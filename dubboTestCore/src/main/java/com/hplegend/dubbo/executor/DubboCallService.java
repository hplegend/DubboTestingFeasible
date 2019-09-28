package com.hplegend.dubbo.executor;

import com.hplegend.dubbo.common.MethodArgument;
import com.hplegend.dubbo.constant.Constants;
import com.hplegend.dubbo.utils.ClassUtils;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hp.he
 * @date 2019/9/26 19:30
 */
public class DubboCallService {

    private static final Logger log = LoggerFactory.getLogger("dfsd");
    public static ApplicationConfig application = new ApplicationConfig("hplegendDubboTest");

    String zkAd = "";

    public Object callDubbo() {
        // This instance is heavy, encapsulating the connection to the registry and the connection to the provider,
        // so please cache yourself, otherwise memory and connection leaks may occur.
        ReferenceConfig reference = new ReferenceConfig();
        // set application
        reference.setApplication(application);
        RegistryConfig registry = null;
        // check address
        // AbstractSampler是TestElement的一个子类，因此，这里可以直接用父类
        // zk de dizhi
        String address = zkAd;
        // get rpc protocol
        String rpcProtocol = "dubbo";
        // get registry protocol
        String protocol = "zookeeper";
        // get registry group
        String registryGroup = "/vs/promotion/hp/djtts5dev";
        switch (protocol) {
            case Constants.REGISTRY_ZOOKEEPER:
                registry = new RegistryConfig();
                registry.setProtocol(Constants.REGISTRY_ZOOKEEPER);
                registry.setGroup(registryGroup);
                registry.setAddress(address);
                reference.setRegistry(registry);
                reference.setProtocol(rpcProtocol);
                break;
            case Constants.REGISTRY_MULTICAST:
                registry = new RegistryConfig();
                registry.setProtocol(Constants.REGISTRY_MULTICAST);
                registry.setGroup(registryGroup);
                registry.setAddress(address);
                reference.setRegistry(registry);
                reference.setProtocol(rpcProtocol);
                break;
            case Constants.REGISTRY_REDIS:
                registry = new RegistryConfig();
                registry.setProtocol(Constants.REGISTRY_REDIS);
                registry.setGroup(registryGroup);
                registry.setAddress(address);
                reference.setRegistry(registry);
                reference.setProtocol(rpcProtocol);
                break;
            case Constants.REGISTRY_SIMPLE:
                registry = new RegistryConfig();
                registry.setAddress(address);
                reference.setRegistry(registry);
                reference.setProtocol(rpcProtocol);
                break;
            default:
                // direct invoke provider
                StringBuffer sb = new StringBuffer();
                sb.append(protocol)
                        .append(address)
                        .append("/").append("dobbMethodName");
                //# fix dubbo 2.7.3 Generic bug https://github.com/apache/dubbo/pull/4787
                String version = "1.0.0";
                if (!StringUtils.isBlank(version)) {
                    sb.append(":").append(version);
                }
                log.debug("rpc invoker url : " + sb.toString());
                reference.setUrl(sb.toString());
        }
        try {
            // set interface
            String interfaceName = "dobbMethodName";
            if (StringUtils.isBlank(interfaceName)) {
                return null;
            }
            reference.setInterface(interfaceName);

            // set retries
            Integer retries = 3;

            if (retries != null) {
                reference.setRetries(retries);
            }

            // set cluster
            String cluster = "failfast";
            if (!StringUtils.isBlank(cluster)) {
                reference.setCluster(cluster);
            }

            // set version
            String version = "1.0.0";
            if (!StringUtils.isBlank(version)) {
                reference.setVersion(version);
            }

            // set timeout
            Integer timeout = 3000;
            if (timeout != null) {
                reference.setTimeout(timeout);
            }

            // set group
            String group = "";
            if (!StringUtils.isBlank(group)) {
                reference.setGroup(group);
            }

            // set connections
            Integer connections = 10;
            if (connections != null) {
                reference.setConnections(connections);
            }

            // set loadBalance
            String loadBalance = "random";
            if (!StringUtils.isBlank(loadBalance)) {
                reference.setLoadbalance(loadBalance);
            }

            // set async
            String async = "sync";
            if (!StringUtils.isBlank(async)) {
                reference.setAsync(Constants.ASYNC.equals(async) ? true : false);
            }

            // set generic
            reference.setGeneric(true);

            String methodName = "bindCashToUserByPay";

            // The registry's address is to generate the ReferenceConfigCache key
            ReferenceConfigCache cache = ReferenceConfigCache.getCache(zkAd, new ReferenceConfigCache.KeyGenerator() {
                @Override
                public String generateKey(ReferenceConfig<?> referenceConfig) {
                    return referenceConfig.toString();
                }
            });
            GenericService genericService = (GenericService) cache.get(reference);
            if (genericService == null) {

                log.info("");
                return null;
            }
            String[] parameterTypes = null;
            Object[] parameterValues = null;
            List<MethodArgument> args = new ArrayList<MethodArgument>() {{add(new MethodArgument("String","uuuu"));
            add(new MethodArgument("String","nqk200cjsd"));
                add(new MethodArgument("Integer","1"));
                add(new MethodArgument("String","zidingdiaoyongsfsd"));}};
            List<String> paramterTypeList = new ArrayList<String>();

            List<Object> parameterValuesList = new ArrayList<Object>();

            for (MethodArgument arg : args) {
                ClassUtils.parseParameter(paramterTypeList, parameterValuesList, arg);
            }
            parameterTypes = paramterTypeList.toArray(new String[paramterTypeList.size()]);
            parameterValues = parameterValuesList.toArray(new Object[parameterValuesList.size()]);

            // 附件参数
            List<MethodArgument> attachmentArgs =  null;  //Constants.getAttachmentArgs(this);
            if (attachmentArgs != null && !attachmentArgs.isEmpty()) {
                RpcContext.getContext().setAttachments(attachmentArgs.stream().collect(Collectors.toMap(MethodArgument::getParamType, MethodArgument::getParamValue)));
            }


            Object result = null;
            try {
                result = genericService.$invoke(methodName, parameterTypes, parameterValues);

            } catch (Exception e) {
                log.error("RpcException：", e);
                //TODO
                //当接口返回异常时，sample标识为successful，通过响应内容做断言来判断是否标识sample错误，因为sample的错误会统计到用例的error百分比内。
                //比如接口有一些校验性质的异常，不代表这个操作是错误的，这样就可以灵活的判断，不至于正常的校验返回导致测试用例error百分比的不真实

                result = e;
            }
            return result;
        } catch (Exception e) {
            log.error("UnknownException：", e);
            return e;
        } finally {
            //TODO 不能在sample结束时destroy
//            if (registry != null) {
//                registry.destroyAll();
//            }
//            reference.destroy();
        }
    }


}
