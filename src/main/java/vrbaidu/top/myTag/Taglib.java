package vrbaidu.top.myTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by Administrator on 2017/4/1.
 * 自定义jstl标签
 */
public class Taglib extends SimpleTagSupport{
    private String tagName;

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspFragment jspFragment = getJspBody();
        ////括号里面可以改成任意文字，即为标签中的内容加其它额外的文字
        getJspContext().getOut().write("</"+ tagName+ ">");
        jspFragment.invoke(null);
    }
}
