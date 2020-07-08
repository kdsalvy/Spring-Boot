package learn.kd.catalog.config.feign;

import java.lang.reflect.Type;

import org.springframework.stereotype.Component;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@Component
public class CustomFeignEncoder implements Encoder {

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
        log.info(template.toString());
        log.info(object.toString());
    }

}
