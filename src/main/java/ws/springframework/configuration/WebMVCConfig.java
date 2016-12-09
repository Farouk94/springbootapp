package ws.springframework.configuration;

import com.fasterxml.jackson.core.JsonGenerator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Created by farou_000 on 30/10/2016.
 */
@Configuration
public class WebMVCConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/logout").setViewName("login");
        registry.addViewController("/groups").setViewName("groupshow");
        registry.addViewController("/memberlist").setViewName("memberlist");
        registry.addViewController("/mygroups").setViewName("groupsowner");
        registry.addViewController("/profil").setViewName("profil");
        registry.addViewController("/edit/profil").setViewName("editprofil");
        registry.addViewController("/editgroup").setViewName("editgroup");
        registry.addViewController("/creategroup").setViewName("creategroup");
        registry.addViewController("/users").setViewName("users");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/comments").setViewName("comments");



    }

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.postgresql.Driver");
        driverManagerDataSource.setUrl("jdbc:postgresql://localhost:5432/db1");
        driverManagerDataSource.setUsername("postgres");
        driverManagerDataSource.setPassword("postgres");
        return driverManagerDataSource;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper =jsonConverter.getObjectMapper();
        SimpleModule module = new SimpleModule("Stream");
        module.addSerializer(Stream.class, new JsonSerializer<Stream>() {


            @Override
            public void serialize(Stream value, JsonGenerator gen, SerializerProvider serializers)
                    throws IOException, JsonProcessingException {
                serializers.findValueSerializer(Iterator.class, null)
                        .serialize(value.iterator(), gen, serializers);

            }
        });

        objectMapper.registerModule(module);
        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }
}
