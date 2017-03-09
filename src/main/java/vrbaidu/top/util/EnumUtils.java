package vrbaidu.top.util;

/**
 * Created by Administrator on 2017/2/28.
 */
public class EnumUtils {
    /**
     * 根据名称获取枚举值
     * @param clazz
     * @param name
     * @param <T>
     * @return
     */
    public static <T extends Enum<T>> T getNameByValue(Class<T> clazz, final  String name) {
        return (T) Enum.valueOf(clazz, name);
    }

    /**
     * 根据序号获取枚举值
     * @param clazz
     * @param code
     * @param <T>
     * @return
     */
    public static <T extends Enum<T>> T getNameByCode(Class<T> clazz, final  int code) {
        return (T) clazz.getEnumConstants()[code];
    }


}
