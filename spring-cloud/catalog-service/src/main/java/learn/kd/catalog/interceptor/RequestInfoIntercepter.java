package learn.kd.catalog.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class RequestInfoIntercepter implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        System.out.println("Rest Template Http Request: " + template.feignTarget()
            .url()
            .toString());
    }

}
