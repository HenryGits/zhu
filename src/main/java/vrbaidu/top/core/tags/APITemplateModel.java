package vrbaidu.top.core.tags;

import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vrbaidu.top.core.statics.Constant;
import vrbaidu.top.util.SpringContextUtil;

import java.util.HashMap;
import java.util.Map;

import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;
import static oracle.net.aso.C05.e;

/**
 * Freemarker 自定义标签 API公共入口
 */

public class APITemplateModel extends WYFTemplateModel {
	private static final Logger logger = LoggerFactory.getLogger(APITemplateModel.class);
	@Override
	@SuppressWarnings({  "unchecked" })
	protected Map<String, TemplateModel> putValue(Map params)
			throws TemplateModelException {
		
		Map<String, TemplateModel> paramWrap = null ;
		if(null != params && params.size() != 0 || null != params.get(Constant.TARGET)){
			String name =  params.get(Constant.TARGET).toString() ;
			paramWrap = new HashMap<String, TemplateModel>(params);
			
			/**
			 * 获取子类，用父类接收，
			 */
			SuperCustomTag tag =  SpringContextUtil.getBean(name, SuperCustomTag.class);
			//父类调用子类方法
			Object result = tag.result(params);
			
			//输出
			paramWrap.put(Constant.OUT_TAG_NAME, DEFAULT_WRAPPER.wrap(result));
		}else{
			logger.error("Cannot be null, must include a 'name' attribute!", e);
		}
		return paramWrap;
	}

	
	
	
	
	
}
