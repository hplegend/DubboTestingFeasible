package hplegend;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

/**
 * @author hp.he
 * @date 2019/8/19 21:00
 */
@Controller
public class TestController {

    @RequestMapping("/test.view")
    public ModelAndView test() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("test");

        return mv;

    }

    @RequestMapping("/testJson.json")
    public ResponseEntity<String> testJson() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=utf-8");
        return new ResponseEntity<>("{\"id\":\"sdsd\"}", responseHeaders, HttpStatus.OK);
    }

}
