package hplegend.dubbo;

import com.hplegend.dubbo.DubboCallService;
import com.hplegend.dubbo.ProviderService;
import com.hplegend.dubbo.utils.JsonUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author hp.he
 * @date 2019/9/26 17:47
 */

@Controller
public class DubboTestController {


    String zkAd = "zk.beta.corp.qunar.com:2181";


    @RequestMapping("/dubbo/method/list.json")
    @ResponseBody
    public ResponseEntity<String> dubboMethodList() {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=utf-8");

        ProviderService providerService = new ProviderService();
        List<String> methos = providerService.getExpodeMethods("zookeeper", zkAd, "/vs/promotion/hp/djtts5dev");

        DubboCallService dubboCallService = new DubboCallService();
        System.out.println(JsonUtils.toJson(dubboCallService.callDubbo()));

        System.out.println(JsonUtils.toJson(methos));

        return new ResponseEntity<>("{\"id\":\"sdsd\"}", responseHeaders, HttpStatus.OK);
    }

}
