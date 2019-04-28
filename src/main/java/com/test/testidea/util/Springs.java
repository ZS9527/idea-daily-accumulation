package com.test.testidea.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * SpringUtil
 *
 * @author
 * @date 2018年6月15日 下午5:36:38
 */

@Component
public class Springs implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (Springs.applicationContext == null) {
            Springs.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return getContext().getBean(name);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<?> clazz) {
        return (T) getContext().getBean(clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name, Class<?> clazz) {
        return (T) getContext().getBean(name, clazz);
    }

    /**
     * 获取当前环境
     *
     * @return example[ dev, prod ]
     */
    public static String getActiveProfile() {
        return applicationContext.getEnvironment().getActiveProfiles()[0];
    }

    /**
     * Class.forName
     *
     * @param className
     * @param clazz
     * @return
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> loadClass(String className, Class<T> clazz) throws ClassNotFoundException {
        return (Class<T>) Class.forName(className);
    }
}
