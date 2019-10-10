package hplegend.dubbo;

import com.google.common.collect.Lists;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author hp.he
 * @date 2019/9/26 17:47
 */

@Controller
public class DubboTestController {

    @RequestMapping("/dubbo/method/listMethods.json")
    @ResponseBody
    public ResponseEntity<String> listMethods(
            @RequestParam("registryProtocol") String registryProtocol,
            @RequestParam("dubboGroup") String dubboGroup,
            @RequestParam("registryAddress") String registryAddress,
            @RequestParam("rpcProtocol") String rpcProtocol,
            @RequestParam(value = "serviceVersion", required = false, defaultValue = "1.0.0") String serviceVersion)
            throws Exception {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=utf-8");

        RemoteDubboInterfaceAndMethodParser providerService = new RemoteDubboInterfaceAndMethodParser();
        RemoteParserParam parserParam = RemoteParserParam.Builder.builder()
                .zkAddress(registryAddress)
                .dubboGroup(dubboGroup)
                .registryProtocol(registryProtocol)
                .serviceVersion(serviceVersion)
                .build();
        List<String> methods = providerService.doParser(parserParam);

        JsonData<List<String>> jsonData = new JsonData<>();

        jsonData.setRet(true);
        jsonData.setData(methods);

        return new ResponseEntity<>(JsonUtils.toJson(jsonData), responseHeaders, HttpStatus.OK);
    }


    @RequestMapping("/dubbo/method/callService.json")
    @ResponseBody
    public ResponseEntity<String> callService(
            @RequestParam("registryProtocol") String registryProtocol,
            @RequestParam("dubboGroup") String dubboGroup,
            @RequestParam("registryAddress") String registryAddress,
            @RequestParam("rpcProtocol") String rpcProtocol,
            @RequestParam("interfaceName") String interfaceName,
            @RequestParam("methodName") String methodName,
            @RequestParam(value = "serviceVersion", required = false, defaultValue = "1.0.0") String serviceVersion,
            @RequestParam(value = "serviceGroup", required = false, defaultValue = "") String serviceGroup,
            HttpServletRequest request) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=utf-8");

        String[] parameterTypes = request.getParameterValues("parameterTypes");
        String[] parameterValues = request.getParameterValues("parameterValues");

        List<MethodArgumentAndValue> methodArgumentAndValues = Lists.newArrayList();

        for (int index = 0; index < parameterTypes.length; ++index) {
            methodArgumentAndValues.add(new MethodArgumentAndValue(parameterTypes[index], parameterValues[index]));
        }

        DubboInterfaceInvokeParam invokeParam = DubboInterfaceInvokeParam.Builder.builder()
                .dubboGroup(dubboGroup)
                .zkAddress(registryAddress)
                .interfaceName(interfaceName)
                .methodName(methodName)
                .dubboGroup(dubboGroup)
                .registryProtocol(registryProtocol)
                .serviceGroup(serviceGroup)
                .serviceVersion(serviceVersion)
                .build();

        DubboInterfaceParameters parameters = new DubboInterfaceParameters();
        parameters.setInterfaceInvokeParam(invokeParam);
        parameters.setMethodArgumentAndValueList(methodArgumentAndValues);

        // 接口调用
        RemoteDubboCallService callService = new RemoteDubboCallService();
        Object ret = callService.doCall(parameters);

        System.out.println("ret: " + ret);
        JsonData jsonData = new JsonData();
        jsonData.setRet(true);
        jsonData.setData(JsonUtils.toJson(ret));
        return new ResponseEntity<>(JsonUtils.toJson(jsonData), responseHeaders, HttpStatus.OK);
    }


    @RequestMapping("/dubbo/config.page")
    public ModelAndView config() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("config");
        return mv;
    }

    @RequestMapping("/dubbo/callConfig.page")
    public ModelAndView callConfig() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("dubboCall");
        return mv;
    }

}
