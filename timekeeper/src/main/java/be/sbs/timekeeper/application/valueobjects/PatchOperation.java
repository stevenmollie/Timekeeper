package be.sbs.timekeeper.application.valueobjects;

import java.util.StringJoiner;

public class PatchOperation {
    private String op;
    private String path;
    private String value;

    public PatchOperation() {
    }

    public PatchOperation(String op, String path, String value) {
        this.op = op;
        this.path = path;
        this.value = value;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PatchOperation.class.getSimpleName() + "[", "]")
                .add("op='" + op + "'")
                .add("path='" + path + "'")
                .add("value=" + value)
                .toString();
    }
}
