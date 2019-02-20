package com.pijukebox.configuration;

//import javafx.application.Application;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import java.util.Locale;

/**
 * This is the replacement of the web.xml
 */
public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Get the default media path specified for the current operating system family.
     * This basically means the *NIX pattern of /dir/for/media/ or the Windows
     * pattern of {drive}\dir\for\media. With escaped backslashes of course.
     *
     * @return the default media path for this OS, as a String.
     * @throws RuntimeException if this OS is an extreme niche that does not
     *                          hold to standards and usable patterns.
     */
    public static String getMediaPath() throws RuntimeException {
        String osName = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
        if (osName.contains("windows")) {
            return "C:\\Users\\Public\\Music\\";
        } else if (osName.contains("linux") || osName.contains("ix") || osName.contains("bsd") || osName.contains("mac") || osName.contains("darwin") || osName.contains("sun") || osName.contains("solaris") || osName.contains("ux")) {
            // There are more OS'es than y'all might think, most based on UNIX or
            // Linux. Most are POSIX-compliant even when not *NIX based. And most
            // Share some common filesystem logic.
            return "/media/music/";
        } else {
            // And then there are the REALLY special options. They have no common
            // filesystem logic, no simple routines or useful OS APIs for access
            // and offer no clear method of navigating the system. Screw those.
            throw new RuntimeException("No usable operating system has been found.\r\n\t" + "This means the filesystem cannot be ensured. Please run on\r\n" + "a real, usable, not extremely obscure OS when using PiJukebox.");
        }
    }

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