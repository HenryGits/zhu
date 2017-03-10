package vrbaidu.top.exception;

/**
 * Created by Administrator on 2017/3/10.
 * 业务异常类
 */
public class BusinessException extends Exception {
    public static final long serialVersionUID = 1L;

    /**
     * 业务类型
     */
    private String bizType;
    /**
     * 业务代码
     */
    private int bizCode;
    /**
     * 错误信息
     */
    private String message;

    public BusinessException(String message){
        super(message);
        this.bizType = "";
        this.bizCode = -1;
        this.message = message;
    }

    public BusinessException(String bizType, String message){
        super(message);
        this.bizType = bizType;
        this.bizCode = -1;
        this.message = message;
    }

    public BusinessException(int bizCode, String message){
        super(message);
        this.bizType = "";
        this.bizCode = bizCode;
        this.message = message;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public int getBizCode() {
        return bizCode;
    }

    public void setBizCode(int bizCode) {
        this.bizCode = bizCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
