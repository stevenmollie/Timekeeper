package be.sbs.timekeeper.application.configuration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ReadingConverter
public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    @Nullable
    @Override
    public LocalDate convert(String source) {
        return LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(source));
    }
}
