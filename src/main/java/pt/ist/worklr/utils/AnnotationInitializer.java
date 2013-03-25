package pt.ist.worklr.utils;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@HandlesTypes({ DefaultJsonAdapter.class })
public class AnnotationInitializer implements ServletContainerInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(AnnotationInitializer.class);
    
    @Override
    public void onStartup(Set<Class<?>> classes, ServletContext ctx) throws ServletException {
        LOG.debug("AnnotationInitializer started...");
        if (classes != null) {
            for (Class<?> type : classes) {
                DefaultJsonAdapter defaultJsonAdapter = type.getAnnotation(DefaultJsonAdapter.class);
                if (defaultJsonAdapter != null) {
                    LOG.debug("Default JSON Adapter for {} is {}", defaultJsonAdapter.value(), type.getSimpleName());
                    JsonAwareResource.setDefault(defaultJsonAdapter.value(), type);
                }
            }
        }
        
    }

}
