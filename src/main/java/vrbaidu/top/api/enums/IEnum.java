package vrbaidu.top.api.enums;

/**
 * Created by Administrator on 2017/4/10.
 * 反射实现获取枚举里的具体数值
 * 1).定义一个IEnum接口，然后每个枚举类实现此接口;
 * 2).定义常量保存枚举类所在包名，以及接口全路径;
 * 3).在程序启动时，读取枚举类所在包下的所有枚举类的File文件，在从file文件信息中获取每个枚举类的全路径类名集合A;
 * 4).遍历A集合，利用反射获取每个类的class对象，再判断该类是否实现了EnumMessage接口;
 * 5).对于实现了IEnum接口的枚举类，遍历该枚举类的所有对象，保存Map<Object, IEnum>的集合映射;
 * 6).对枚举类保存Map<Class, Map<Object, EnumMessage>>的映射集合。
 */
public interface IEnum<T> extends NameIEnum,ValueIEnum {
}
