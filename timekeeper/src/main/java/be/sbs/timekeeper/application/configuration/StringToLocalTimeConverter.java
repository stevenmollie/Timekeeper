package be.sbs.timekeeper.application.configuration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import javax.annotation.Nullable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@ReadingConverter
public class StringToLocalTimeConverter implements Converter<String, LocalTime> {

    @Nullable
    @Override
    public LocalTime convert(String source) {
        return LocalTime.from(DateTimeFormatter.ofPattern("HH:mm:ss").parse(source));
    }
}
