package learn.kd.catalog.config.feign;

import java.io.IOException;
import java.lang.reflect.Type;

import org.springframework.stereotype.Component;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Decoder is masking the response and the caller is always going to the fallback method.
 * That is why this has been removed
 * 
 * @author saukedia1
 *
 */
@Slf4j
@NoArgsConstructor
//@Component
public class CustomFeignDecoder implements Decoder {
    
    private final Decoder delegate = new Decoder.Default();

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        log.info(response.toString());
        
        return delegate.decode(response, type);
    }

}
