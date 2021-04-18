package newsapi.enums;

public enum Endpoint {
    TOP_HEADLINES("top-headlines"),
    EVERYTHING("everything");
    public final String value;

    public String getValue() {
        return value;
    }

    Endpoint(String value) {
        this.value = value;
    }
}
