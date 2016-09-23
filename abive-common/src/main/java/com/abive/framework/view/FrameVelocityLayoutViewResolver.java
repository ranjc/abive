package com.abive.framework.view;

import org.joda.time.DateTime;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver;

import java.util.Map;

/**
 * Created by ranjiangchuan on 15/3/28.
 */
public class FrameVelocityLayoutViewResolver extends VelocityLayoutViewResolver {


    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        AbstractUrlBasedView view = super.buildView(viewName);
        Map<String, Object> attribute = view.getAttributesMap();
        String context = getServletContext().getContextPath();

        attribute.put("version", "?v=" + DateTime.now().getMillis());
        attribute.put("context", context);

        return view;
    }
}
