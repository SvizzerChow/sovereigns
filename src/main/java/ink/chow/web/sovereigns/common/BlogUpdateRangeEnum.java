package ink.chow.web.sovereigns.common;

/**
 * Description:
 *
 * @author ZhouS
 * @date 2019/3/5 20:53
 */
public enum BlogUpdateRangeEnum {
    UPDATE_LATEST(0), UPDATE_ALL_LATEST(1), UPDATE_ALL_LOCAL(2);

    private int value;

    BlogUpdateRangeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }}
