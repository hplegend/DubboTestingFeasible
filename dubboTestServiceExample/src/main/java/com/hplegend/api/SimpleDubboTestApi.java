package com.hplegend.api;

import com.hplegend.bo.UserInfo;

import java.util.List;

/**
 * @author hp.he
 * @date 2019/9/28 17:44
 */
public interface SimpleDubboTestApi {


    String outputSimpleMessage(String input);

    String doTestBeanParameter(List<UserInfo> userInfo);


}
