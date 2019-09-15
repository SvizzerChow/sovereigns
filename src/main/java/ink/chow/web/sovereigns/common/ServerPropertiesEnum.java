package ink.chow.web.sovereigns.common;

public enum ServerPropertiesEnum {
    GITHUB(0);


    private int value;

    ServerPropertiesEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
