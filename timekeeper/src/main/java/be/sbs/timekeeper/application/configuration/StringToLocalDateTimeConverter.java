package be.sbs.timekeeper.application.configuration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WritingConverter
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Nullable
    @Override
    public LocalDateTime convert(String source) {
        return LocalDateTime.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").parse(source));
    }
}
