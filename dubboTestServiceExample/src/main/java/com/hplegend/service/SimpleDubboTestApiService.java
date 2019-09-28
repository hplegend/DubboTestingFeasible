package com.hplegend.service;

import com.hplegend.api.SimpleDubboTestApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @author hp.he
 * @date 2019/9/28 17:45
 */
@Service
@Slf4j
public class SimpleDubboTestApiService implements SimpleDubboTestApi, InitializingBean {

    public SimpleDubboTestApiService() {
        System.out.println("[SimpleDubboTestApiService] 被初始化");
    }

    @Override
    public String outputSimpleMessage(String input) {
        System.out.println("dddd");
        return "SimpleDubboTestApiService success, input" + input;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("[afterPropertiesSet]~~~~~~~~~~~~");
    }
}
