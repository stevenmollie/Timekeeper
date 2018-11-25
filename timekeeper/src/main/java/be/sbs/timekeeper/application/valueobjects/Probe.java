package be.sbs.timekeeper.application.valueobjects;

public class Probe {

    String status;

    public Probe(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
