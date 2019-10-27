package com.kundy.cranberry.thirdparty.xsd;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author kundy
 * @date 2019/10/25 8:29 PM
 */
public class KiritoNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        super.registerBeanDefinitionParser("application", new KiritoBeanDefinitionParser(ApplicationConfig.class));
        super.registerBeanDefinitionParser("service", new KiritoBeanDefinitionParser(ServiceBean.class));
    }
}
