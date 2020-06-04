package bsu.jp.web.tagProcessors;

import bsu.jp.model.Student;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class InnerTag extends BodyTagSupport {

    @Override
    public int doStartTag() throws JspException {
        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doAfterBody() throws JspException {
        OuterTag outerTag = (OuterTag) findAncestorWithClass(this, OuterTag.class);
        BodyContent bodyContent = getBodyContent();
        Student student = new Student();
        student.setName(bodyContent.getString());
        outerTag.getStudents().add(student);
        bodyContent.clearBody();
        return SKIP_BODY;
    }
}
