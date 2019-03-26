package be.sbs.timekeeper.application.configuration.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import javax.annotation.Nullable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@WritingConverter
public class LocalTimeToStringConverter implements Converter<LocalTime, String> {

    @Nullable
    @Override
    public String convert(LocalTime source) {
        return source.format(source != null ? DateTimeFormatter.ofPattern("HH:mm:ss") : null);
    }
}
