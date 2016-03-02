package com.softserve.hotels.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestTag extends TagSupport {

    private static final Logger LOGGER = LogManager.getLogger();
    private String title;

    @Override
    public int doStartTag() throws JspException {

        try {
            JspWriter out = pageContext.getOut();
            out.println("<spring:message code='bookingApartment.checkUser' />");
        } catch (IOException e) {
            LOGGER.error(e);
        }

        return SKIP_BODY;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
