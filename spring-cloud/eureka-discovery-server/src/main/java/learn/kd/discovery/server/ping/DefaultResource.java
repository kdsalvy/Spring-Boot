package learn.kd.discovery.server.ping;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultResource {

    @RequestMapping(value = "/")
    public String healthCheck(HttpServletRequest request) {
        return "";
    }
}
