package is.hi.recipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@SpringBootApplication
public class RecipeApplication extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String baseDirectory = System.getProperty("user.dir");
        registry
                // map external-facing URI path internally
                .addResourceHandler(
                        "/recipeImage/**", "/**")
                // to the physical path where report images are actually located
                .addResourceLocations("file:" + baseDirectory + "/uploads/recipeImage/", "file:" + baseDirectory + "/src/main/resources/static/");
    }

    public static void main(String[] args) {
        SpringApplication.run(RecipeApplication.class, args);
    }

}
