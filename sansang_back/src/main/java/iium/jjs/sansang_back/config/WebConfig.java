package iium.jjs.sansang_back.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "iium.jjs.sansang_back")
public class WebConfig implements WebMvcConfigurer {

    @Value("${files.add-resource-handler}")
    private String ADD_IMG_RESOURCE_LOCATION;

    @Value("${files.add-resource-locations}")
    private String ADD_RESOURCE_IMG_URL;

    @Value("${files.file-dir}")
    private String ADD_IMG_DIR;

    @Value("${files.file-url}")
    private String ADD_IMG_URL;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
          .allowedOrigins("http://localhost:3000")
          .allowedHeaders("*")
          .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(ADD_RESOURCE_IMG_URL)
          .addResourceLocations(ADD_IMG_RESOURCE_LOCATION);

        registry.addResourceHandler(ADD_IMG_URL)
          .addResourceLocations("file:" + ADD_IMG_DIR);
    }
}
