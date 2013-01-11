package pt.ist.worklr.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ist.fenixframework.FenixFramework;

import com.sun.jersey.spi.container.servlet.ServletContainer;

public class Bootstrap extends ServletContainer {

    private static final Logger LOG = LoggerFactory.getLogger(Bootstrap.class);

    private static final long serialVersionUID = 1L;

    static {
	LOG.debug("Initializing Worklr...");
	FenixFramework.initialize(PropertiesManager.getFenixFrameworkConfig());
    }

}