package com.hplegend.dubbo.parse;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.hplegend.dubbo.common.MethodArgument;
import com.hplegend.dubbo.common.RegistryServerSync;
import com.hplegend.dubbo.common.RemoteParserParam;
import com.hplegend.dubbo.constant.Constants;
import com.hplegend.dubbo.utils.ClassUtils;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.registry.RegistryService;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * dubbo parser service
 *
 * @author hp.he
 * @date 2019/9/27 19:16
 */
public class RemoteDubboInterfaceAndMethodParser {

    private static final Logger log = LoggerFactory.getLogger("dfsd");
    private static final ApplicationConfig application = new ApplicationConfig("hplegendDubboTest");
    private ConcurrentMap<String, Map<String, URL>> providerUrls = new ConcurrentHashMap<>();

    public List<String> doParser(RemoteParserParam parserParam) throws Exception {
        ReferenceConfig reference = new ReferenceConfig();
        reference.setApplication(application);

        String protocol = parserParam.getRegistryProtocol();
        String group = parserParam.getDubboGroup();
        String address = parserParam.getZkAddress();
        String serviceVersion = parserParam.getServiceVersion();

        RegistryConfig registry = new RegistryConfig();
        switch (protocol) {
            case Constants.REGISTRY_ZOOKEEPER:
                registry.setProtocol(Constants.REGISTRY_ZOOKEEPER);
                registry.setGroup(group);
                registry.setAddress(address);
                reference.setRegistry(registry);
                reference.setVersion(serviceVersion);
                break;
            case Constants.REGISTRY_REDIS:
                registry.setProtocol(Constants.REGISTRY_REDIS);
                registry.setGroup(group);
                registry.setAddress(address);
                reference.setRegistry(registry);
                reference.setVersion(serviceVersion);
                break;
            default:
                throw new Exception("protocol not support, please check!");
        }
        reference.setInterface("org.apache.dubbo.registry.RegistryService");
        try {
            ReferenceConfigCache cache = ReferenceConfigCache.getCache(address + "_" + group, new ReferenceConfigCache.KeyGenerator() {
                @Override
                public String generateKey(ReferenceConfig<?> referenceConfig) {
                    return referenceConfig.toString();
                }
            });
            RegistryService registryService = (RegistryService) cache.get(reference);
            if (registryService == null) {
                throw new RuntimeException("Can't get the interface list, please check if the address is wrong!");
            }
            RegistryServerSync registryServerSync = RegistryServerSync.get(address + "_" + group);
            registryService.subscribe(RegistryServerSync.SUBSCRIBE, registryServerSync);
            List<String> ret = new ArrayList<String>();

            // return all registry interfaces and methods
            providerUrls = registryServerSync.getRegistryCache().get(com.alibaba.dubbo.common.Constants.PROVIDERS_CATEGORY);
            if (providerUrls != null) {
                ret.addAll(providerUrls.keySet());
            }

            List<String> interfaceAndMethodExpand = Lists.newArrayList();
            for (Map.Entry<String, Map<String, URL>> entry : providerUrls.entrySet()) {
                for (Map.Entry<String, URL> innerEntry : entry.getValue().entrySet()) {

                    String methods = innerEntry.getValue().getParameter(com.alibaba.dubbo.common.Constants.METHODS_KEY);

                    if (null == methods || methods.length() <= 0) {
                        continue;
                    }

                    List<String> methodList = Splitter.on(",").splitToList(methods);
                    methodList.forEach(
                            varMethod -> {
                                interfaceAndMethodExpand.add(entry.getKey() + "--->" + varMethod);
                            }
                    );
                }
            }
            return interfaceAndMethodExpand;
        } catch (Exception e) {
            log.error("get provider list is error!", e);
            throw new RuntimeException("Can't get the interface list, please check if the address is wrong!", e);
        }
    }
}
