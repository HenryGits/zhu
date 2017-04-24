package vrbaidu.top.util;

import org.aspectj.lang.JoinPoint;

/**
 * 多数据源AOP切面编程实现。
 */
public class DataSourceAspect {

    /**
     * @param point
     */
    public void before(JoinPoint point) {

        //获取目标对象的类类型
        Class<?> aClass = point.getTarget().getClass();

        //获取包名用于区分不同数据源
        String whichDataSource = aClass.getName().substring(25, aClass.getName().lastIndexOf("."));
        if ("ssmone".equals(whichDataSource)) {
            DataSourceHolder.setDataSources("datasource1");
        } else {
            DataSourceHolder.setDataSources("datasource2");
        }

    }


    /**
     * 执行后将数据源置为空
     */
    public void after() {
        DataSourceHolder.setDataSources(null);
    }

}
