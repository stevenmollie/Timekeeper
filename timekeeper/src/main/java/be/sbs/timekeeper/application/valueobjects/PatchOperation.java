package be.sbs.timekeeper.application.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatchOperation {
    private String op;
    private String path;
    private String value;
}
