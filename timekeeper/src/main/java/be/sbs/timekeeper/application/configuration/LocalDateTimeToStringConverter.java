package be.sbs.timekeeper.application.configuration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WritingConverter
public class LocalDateTimeToStringConverter implements Converter<LocalDateTime, String> {

    @Nullable
    @Override
    public String convert(LocalDateTime source) {
        return source.format(source != null ? DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss") : null);
    }
}
