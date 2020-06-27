package learn.kd.catalog.interceptor;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class RestCallInfoIntercepter implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if (request != null && request.getURI() != null)
            System.out.println("Rest Template Http Request: " + request.getURI()
                .toString());
        return execution.execute(request, body);
    }

}
