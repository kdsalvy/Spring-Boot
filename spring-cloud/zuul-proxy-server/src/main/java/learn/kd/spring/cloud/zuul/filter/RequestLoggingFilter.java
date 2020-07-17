package learn.kd.spring.cloud.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RequestLoggingFilter extends ZuulFilter {

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("Logging content of the request");
        // getting the current HTTP request that is to be handle
        HttpServletRequest request = RequestContext.getCurrentContext()
            .getRequest();
        log.info("Request URI: {}", request.getRequestURI());
        return null;
    }

    @Override
    public String filterType() {
        // Runs before sending the request
        return "pre";
    }

    @Override
    public int filterOrder() {
        // Filter execution order 1
        return 1;
    }

}
