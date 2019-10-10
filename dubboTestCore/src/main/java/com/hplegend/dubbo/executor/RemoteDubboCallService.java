package com.hplegend.dubbo.executor;

import com.google.common.collect.Lists;
import com.hplegend.dubbo.common.DubboInterfaceParameters;
import com.hplegend.dubbo.utils.ClassUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.List;

/**
 * @author hp.he
 * @date 2019/9/28 19:34
 */

@Slf4j
public class RemoteDubboCallService {

    private final static ApplicationConfig application = new ApplicationConfig("hplegendDubboTest");


    public Object doCall(DubboInterfaceParameters parameters) {

        // parameters
        String zkAddress = parameters.getInterfaceInvokeParam().getZkAddress();
        String dubboGroup = parameters.getInterfaceInvokeParam().getDubboGroup();
        String registryProtocol = parameters.getInterfaceInvokeParam().getRegistryProtocol();
        String rpcProtocol = "dubbo";
        String serviceVersion = parameters.getInterfaceInvokeParam().getServiceVersion();
        String serviceGroup = parameters.getInterfaceInvokeParam().getServiceGroup();

        String interfaceName = parameters.getInterfaceInvokeParam().getInterfaceName();
        String methodName = parameters.getInterfaceInvokeParam().getMethodName();

        // build registry
        RegistryConfig registry = new RegistryConfig();
        registry.setProtocol(registryProtocol);
        registry.setGroup(dubboGroup);
        registry.setAddress(zkAddress);

        // build reference config (same as xml configeration)
        ReferenceConfig reference = new ReferenceConfig();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setProtocol(rpcProtocol);
        reference.setInterface(interfaceName);
        reference.setRetries(3);
        reference.setCluster("failfast");
        reference.setVersion(serviceVersion);
        reference.setTimeout(30000);
        // service group, not dubbo registry group
        reference.setGroup(serviceGroup);
        reference.setConnections(10);
        reference.setLoadbalance("random");

        // sync or async
        reference.setAsync(false);
        reference.setGeneric(true);


        ReferenceConfigCache cache = ReferenceConfigCache.getCache(zkAddress, new ReferenceConfigCache.KeyGenerator() {
            @Override
            public String generateKey(ReferenceConfig<?> referenceConfig) {
                return referenceConfig.toString();
            }
        });


        GenericService genericService = (GenericService) cache.get(reference);
        if (genericService == null) {
            log.info("genericService error");
            return null;
        }

        // do invoke
        List<String> parameterTypeList = Lists.newArrayList();
        List<Object> parameterValuesList = Lists.newArrayList();

        parameters.getMethodArgumentAndValueList().stream().forEach(
                varParam ->
                        ClassUtils.parseParameter(parameterTypeList, parameterValuesList, varParam)
        );


        String[] parameterTypes = parameterTypeList.toArray(new String[parameterTypeList.size()]);
        Object[] parameterValues = parameterValuesList.toArray(new Object[parameterValuesList.size()]);

        return genericService.$invoke(methodName, parameterTypes, parameterValues);
    }

}
