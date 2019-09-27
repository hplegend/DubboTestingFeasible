package hplegend.dubbo;

import com.hplegend.dubbo.common.RemoteParserParam;
import com.hplegend.dubbo.executor.DubboCallService;
import com.hplegend.dubbo.parse.RemoteDubboInterfaceAndMethodParser;
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

        RemoteDubboInterfaceAndMethodParser providerService = new RemoteDubboInterfaceAndMethodParser();
        RemoteParserParam parserParam = RemoteParserParam.Builder.builder().zkAddress(zkAd)
                .dubboGroup("/vs/promotion/hp/djtts5dev").registryProtocol("zookeeper")
                .build();
        List<String> methods = providerService.doParser(parserParam);

        DubboCallService dubboCallService = new DubboCallService();
        System.out.println(JsonUtils.toJson(dubboCallService.callDubbo()));

        System.out.println(JsonUtils.toJson(methods));

        return new ResponseEntity<>("{\"id\":\"sdsd\"}", responseHeaders, HttpStatus.OK);
    }

}
