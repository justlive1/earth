package justlive.earth.breeze.snow.common.web.base;

import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * web容器通用配置
 * 
 * @author wubo
 *
 */
@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Primary
  @Bean
  ObjectMapper objectMapper() {

    ObjectMapper mapper = new ObjectMapper();
    // 使用JsonView处理某个具体请求时Pojo转换成Json时显示内容
    mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.setSerializationInclusion(Include.NON_NULL);
    return mapper;
  }

  @Primary
  @Bean
  public MappingJackson2HttpMessageConverter jsonConverter() {

    MappingJackson2HttpMessageConverter converter =
        new MappingJackson2HttpMessageConverter(objectMapper());
    converter.setSupportedMediaTypes(
        Arrays.asList(MediaType.APPLICATION_JSON_UTF8, MediaType.TEXT_PLAIN));
    return converter;
  }

  @Bean
  public Jaxb2RootElementHttpMessageConverter xmlConverter() {
    Jaxb2RootElementHttpMessageConverter converter = new Jaxb2RootElementHttpMessageConverter();
    converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_XML));
    return converter;
  }

  @Bean
  public HttpMessageConverter<String> stringConverter() {
    StringHttpMessageConverter converter = new StringHttpMessageConverter();
    converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.ALL));
    return converter;
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.add(jsonConverter());
    converters.add(stringConverter());
    converters.add(xmlConverter());
  }
}
