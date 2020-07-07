package learn.kd.catalog.config.feign;

import org.springframework.http.HttpStatus;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {
        Exception exception = defaultErrorDecoder.decode(s, response);

        if (exception instanceof RetryableException) {
            return exception;
        }

        log.error(response.status() + " :: " + "Server error" + " :: " + response.request()
            .httpMethod() + " :: " + response.request());

        if (HttpStatus.valueOf(response.status())
            .is5xxServerError()) {
            return new RetryableException(response.status(), "Server error", response.request()
                .httpMethod(), null, response.request());
        }

        return exception;
    }

}
