package com.hplegend.dubbo;

import com.google.common.collect.Lists;
import com.hplegend.dubbo.constant.Constants;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.registry.RegistryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * @author hp.he
 * @date 2019/9/26 16:37
 */

public class ProviderService {

    private static final Logger log = LoggerFactory.getLogger("dfsd");
    public static ApplicationConfig application = new ApplicationConfig("hplegendDubboTest");


    ConcurrentMap<String, Map<String, URL>> providerUrls = null;

    // 根据 协议，zk，group，application等获取暴露出来的dubbo接口
    public List<String> getExpodeMethods(String protocol, String address, String group) {
        ReferenceConfig reference = new ReferenceConfig();
        reference.setApplication(application);

        RegistryConfig registry = null;
        switch (protocol) {
            case Constants.REGISTRY_ZOOKEEPER:
                registry = new RegistryConfig();
                registry.setProtocol(Constants.REGISTRY_ZOOKEEPER);
                registry.setGroup(group);
                registry.setAddress(address);
                reference.setRegistry(registry);
                break;
            case Constants.REGISTRY_REDIS:
                registry = new RegistryConfig();
                registry.setProtocol(Constants.REGISTRY_REDIS);
                registry.setGroup(group);
                registry.setAddress(address);
                reference.setRegistry(registry);
                break;
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

            // 这里返回的是在group上注册的所有方法，和参数
            providerUrls = registryServerSync.getRegistryCache().get(com.alibaba.dubbo.common.Constants.PROVIDERS_CATEGORY);
            if (providerUrls != null) {
                ret.addAll(providerUrls.keySet());
            }

            List<String> interfaceAndMethodExpand = Lists.newArrayList();
            for (Map.Entry<String, Map<String, URL>> entry : providerUrls.entrySet()) {
                for (Map.Entry<String,URL> innerEntry: entry.getValue().entrySet()) {
                    interfaceAndMethodExpand.add(entry.getKey() + "*" + innerEntry.getValue().getParameter(com.alibaba.dubbo.common.Constants.METHODS_KEY));
                }
            }
            return interfaceAndMethodExpand;
        } catch (Exception e) {
            log.error("get provider list is error!", e);
            throw new RuntimeException("Can't get the interface list, please check if the address is wrong!", e);
        }

    }
}
