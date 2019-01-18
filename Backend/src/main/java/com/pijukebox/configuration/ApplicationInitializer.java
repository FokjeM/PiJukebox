package com.pijukebox.configuration;

//import javafx.application.Application;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * This is the replacement of the web.xml
 */
public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * We accept all incoming requests starting at /
     *
     * @return All the mappings we accept
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     * The classes we use for configuring our ApplicationContext
     *
     * @return An array containing configuration classes for our ApplicationContext
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    /**
     * We have no special DispatcherServlet logic, so we do all the configuration in {@link WebConfig}
     * It is useful if you have multiple DispatcherServlets and you want to specifically manage the different
     * WebApplicationContexts
     * https://stackoverflow.com/questions/35258758/getservletconfigclasses-vs-getrootconfigclasses-when-extending-abstractannot
     *
     * @return null, as it's not necessary for us.
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }
}
