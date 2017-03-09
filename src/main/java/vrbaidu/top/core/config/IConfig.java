package vrbaidu.top.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;


public class IConfig {
	private static Logger logger = LoggerFactory.getLogger(IConfig.class);
	/**
	 * 同步锁
	 */
	private static final Object obj = new Object();
	
	/**
	 * 配置文件
	 */
	private static Properties prop = null;
	
	/**
	 * 配置对象单例模式
	 */
	private static IConfig config = null;
	
	/**
	 * 配置文件名称
	 */
	private final static String FILE_NAME = "/config.properties";
	
	static{
		prop = new Properties();
		try {
			prop.load(IConfig.class.getResourceAsStream(FILE_NAME));
		} catch (IOException e) {
			logger.error(e + "加载文件异常，文件路径：%s", FILE_NAME);
		}
		
	}
	
	/**
	 * 获取单例模式对象实例
	 * @return 唯一对象实例
	 */
	public static IConfig getInstance(){
		if(null==config){
			synchronized (obj) {
				config = new IConfig();
			}
		}
		return config;
	}
	
	/**
	 */
	public static String get(String key){
		return prop.getProperty(key);
	}
	
}
