package utils.property;

public enum PropertyKeys {
    URL("url"),
    EMAIL("email"),
    PASSWORD("password"),
    IMPLICIT_WAIT("implicitWait"),
    EXPLICIT_WAIT("explicitWait");

    private String value;

    PropertyKeys(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }



}
