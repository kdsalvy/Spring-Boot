package learn.kd.spring.cloud.zuul.filter;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;

import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ResponseLoggingFilter extends ZuulFilter {

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("Logging content of the response");
        // getting the current HTTP request that is to be handle
        RequestContext context = RequestContext.getCurrentContext();
        try (final InputStream responseDataStream = context.getResponseDataStream()) {

            if (responseDataStream == null) {
                log.info("BODY: {}", "");
                return null;
            }

            String responseData = CharStreams.toString(new InputStreamReader(responseDataStream, "UTF-8"));
            log.info("BODY: {}", responseData);

            context.setResponseBody(responseData);
        } catch (Exception e) {
            throw new ZuulException(e, 500, e.getMessage());
        }
        return null;
    }

    @Override
    public String filterType() {
        // Runs before sending the request
        return "post";
    }

    @Override
    public int filterOrder() {
        // Filter execution order 1
        return 500;
    }

}
