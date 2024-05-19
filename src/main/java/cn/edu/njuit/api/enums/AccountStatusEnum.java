package cn.edu.njuit.api.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Objects;

/**
 * 账户状态枚举类
 * @author DingYihang
 */
@Getter
@AllArgsConstructor
public enum AccountStatusEnum {
    /**
     * 停用
     */
    DISABLE(0, "停用"),

    /**
     * 正常
     */
    ENABLED(1, "正常");

    /**
     * 状态值
     */
    private final int value;

    /**
     * 状态名称
     */
    private final String name;

    /**
     * 根据值获取名称
     *
     * @param value 状态值
     * @return 状态名称
     */
    public static String getNameByValue(int value) {
        for (AccountStatusEnum s : AccountStatusEnum.values()) {
            if (s.getValue() == value) {
                return s.getName();
            }
        }
        return "";
    }

    /**
     * 根据名称获取值
     *
     * @param name 状态名称
     * @return 状态值
     */
    public static Integer getValueByName(String name) {
        for (AccountStatusEnum s : AccountStatusEnum.values()) {
            if (Objects.equals(s.getName(), name)) {
                return s.getValue();
            }
        }
        return null;
    }
}
