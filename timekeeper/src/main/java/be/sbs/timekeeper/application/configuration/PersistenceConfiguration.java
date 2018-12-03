package be.sbs.timekeeper.application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

@Configuration
@EnableMongoRepositories("be.sbs.timekeeper.application.repository")
public class PersistenceConfiguration {

    @Bean
    public MongoCustomConversions customConversions() {
        final ArrayList<? extends Converter<? extends Serializable, ? extends Serializable>> converters =
                new ArrayList<>(Arrays.asList(
                        new LocalDateTimeToStringConverter(),
                        new StringToLocalDateTimeConverter(),
                        new LocalDateToStringConverter(),
                        new StringToLocalDateConverter()
                ));
        return new MongoCustomConversions(converters);
    }

}
