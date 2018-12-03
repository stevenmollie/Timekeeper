package be.sbs.timekeeper.application.configuration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WritingConverter
public class LocalDateToStringConverter implements Converter<LocalDate, String> {

    @Nullable
    @Override
    public String convert(LocalDate source) {
        return source.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
