package vrbaidu.top.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 捕获异常统一处理
 * 在Controller中抛出的异常，当没有被catch处理时，GlobalExceptionHandler中定义的处理方法可以起作用。
 * 在方法写明注解@ExceptionHandler，并注明其异常类即可。
 * 此种方法不仅可以作用于Controller，同样的在DAO层、service层也可，都可以由GlobalExceptionHandler进行处理。
 * 此种写法减少代码的入侵。
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private  static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final static  String EXPTION_MSG_KEY  = "message";

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public void handleBizExp(HttpServletRequest request ,Exception e){
        logger.info("系统异常:"+e.getMessage());
        request.getSession(true).setAttribute(EXPTION_MSG_KEY, e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    @ResponseBody
    public void handleIOExp(HttpServletRequest request ,Exception e){
        logger.info("IO异常:"+e.getMessage());
        request.getSession(true).setAttribute(EXPTION_MSG_KEY, e.getMessage());
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    @ResponseBody
    public void handleArrayExp(HttpServletRequest request ,Exception e){
        logger.info("数组异常:"+e.getMessage());
        request.getSession(true).setAttribute(EXPTION_MSG_KEY, e.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    public ModelAndView handSqlExp(Exception e){
        logger.info("数据库异常:"+ e.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", e.getMessage());
        modelAndView.setViewName("sql_error");
        return modelAndView;
    }
}
