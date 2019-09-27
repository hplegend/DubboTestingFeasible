package com.hplegend.dubbo.parse;

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
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * 远程的dubbo解析解析
 *
 * @author hp.he
 * @date 2019/9/27 19:16
 */
public class RemoteDubboInterfaceAndMethodParser {

    private static final Logger log = LoggerFactory.getLogger("dfsd");
    public static ApplicationConfig application = new ApplicationConfig("hplegendDubboTest");
    ConcurrentMap<String, Map<String, URL>> providerUrls = null;
    String zkAd = "zk.beta.corp.qunar.com:2181";

    public Object doParser(RemoteParserParam parserParam) {
        ReferenceConfig reference = new ReferenceConfig();
        reference.setApplication(application);

        String protocol = parserParam.getRpcProtocol();
        String group = parserParam.getDubboGroup();
        String address = parserParam.getZkAddress();

        RegistryConfig registry = new RegistryConfig();
        switch (protocol) {
            case Constants.REGISTRY_ZOOKEEPER:
                registry.setProtocol(Constants.REGISTRY_ZOOKEEPER);
                registry.setGroup(group);
                registry.setAddress(address);
                reference.setRegistry(registry);
                break;
            case Constants.REGISTRY_REDIS:
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
                for (Map.Entry<String, URL> innerEntry : entry.getValue().entrySet()) {
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
