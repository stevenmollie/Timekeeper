package be.sbs.timekeeper.application.valueobjects;

public class CaptchaInput {
    private String captchaResponse;

    public String getCaptchaResponse() {
        return captchaResponse;
    }

    public void setCaptchaResponse(String captchaResponse) {
        this.captchaResponse = captchaResponse;
    }
}
