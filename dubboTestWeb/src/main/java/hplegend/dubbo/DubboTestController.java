package hplegend.dubbo;

import com.hplegend.dubbo.common.DubboInterfaceInvokeParam;
import com.hplegend.dubbo.common.DubboInterfaceParameters;
import com.hplegend.dubbo.common.MethodArgumentAndValue;
import com.hplegend.dubbo.common.RemoteParserParam;
import com.hplegend.dubbo.executor.RemoteDubboCallService;
import com.hplegend.dubbo.parse.RemoteDubboInterfaceAndMethodParser;
import com.hplegend.dubbo.utils.JsonUtils;
import hplegend.utils.JsonData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hp.he
 * @date 2019/9/26 17:47
 */

@Controller
public class DubboTestController {


    String zkAd = "";


    @RequestMapping("/dubbo/method/list.json")
    @ResponseBody
    public ResponseEntity<String> dubboMethodList(
            @RequestParam("registryProtocol") String registryProtocol,
            @RequestParam("dubboGroup") String dubboGroup,
            @RequestParam("registryAddress") String registryAddress,
            @RequestParam("input") String input) throws Exception {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=utf-8");

        // 接口解析
        RemoteDubboInterfaceAndMethodParser providerService = new RemoteDubboInterfaceAndMethodParser();
        RemoteParserParam parserParam = RemoteParserParam.Builder.builder()
                .zkAddress(registryAddress)
                .dubboGroup(dubboGroup).registryProtocol(registryProtocol)
                .build();
        List<String> methods = providerService.doParser(parserParam);


        List<MethodArgumentAndValue> methodArgumentAndValues =
                new ArrayList<MethodArgumentAndValue>() {{
                    add(new MethodArgumentAndValue("java.util.List", "[{\"userName\":\"sdf\",\"nickName\":\"sdfsdfsdfsd\"},{\"userName\":\"sdf\",\"nickName\":\"sdfsdfsdfsd\"}]"));
                }};

        DubboInterfaceInvokeParam invokeParam = DubboInterfaceInvokeParam.Builder.builder()
                .dubboGroup(dubboGroup)
                .zkAddress(registryAddress)
                .interfaceName("com.hplegend.api.SimpleDubboTestApi")
                .methodName("doTestBeanParameter")
                .dubboGroup(dubboGroup)
                .registryProtocol(registryProtocol)
                .build();

    /*    DubboInterfaceInvokeParam invokeParam = DubboInterfaceInvokeParam.Builder.builder()
                .dubboGroup(dubboGroup)
                .zkAddress(registryAddress)
                .interfaceName("com.qunar.vacation.rate.gateway.api.label.CommentLabelService")
                .methodName("outputSimpleMessage")
                .dubboGroup(dubboGroup)
                .registryProtocol(registryProtocol)
                .build();*/

        DubboInterfaceParameters parameters = new DubboInterfaceParameters();
        parameters.setInterfaceInvokeParam(invokeParam);
        parameters.setMethodArgumentAndValueList(methodArgumentAndValues);

        // 接口调用
        RemoteDubboCallService callService = new RemoteDubboCallService();
        Object ret = callService.doCall(parameters);

        System.out.println("ret: " + ret);

        return new ResponseEntity<>(JsonUtils.toJson(ret), responseHeaders, HttpStatus.OK);
    }


    @RequestMapping("/dubbo/method/listMethods.json")
    @ResponseBody
    public ResponseEntity<String> listMethods(
            @RequestParam("registryProtocol") String registryProtocol,
            @RequestParam("dubboGroup") String dubboGroup,
            @RequestParam("registryAddress") String registryAddress,
            @RequestParam("rpcProtocol") String rpcProtocol) throws Exception {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=utf-8");

        RemoteDubboInterfaceAndMethodParser providerService = new RemoteDubboInterfaceAndMethodParser();
        RemoteParserParam parserParam = RemoteParserParam.Builder.builder()
                .zkAddress(registryAddress)
                .dubboGroup(dubboGroup).registryProtocol(registryProtocol)
                .build();
        List<String> methods = providerService.doParser(parserParam);

        JsonData<List<String>> jsonData = new JsonData<>();

        jsonData.setRet(true);
        jsonData.setData(methods);


        return new ResponseEntity<>(JsonUtils.toJson(jsonData), responseHeaders, HttpStatus.OK);
    }


    @RequestMapping("/dubbo/config.page")
    public ModelAndView config() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("config");
        return mv;
    }

}
