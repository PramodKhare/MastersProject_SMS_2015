package edu.neu.ccis.sms.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import edu.neu.ccis.sms.config.ConfigurationLoader;
import edu.neu.ccis.sms.config.ConfigurationReader;
import edu.neu.ccis.sms.util.CMISConnector;
import edu.neu.ccis.sms.util.HibernateUtil;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.configuration.CompositeConfiguration;

/**
 * Application Lifecycle Listener implementation class SMSContextListener
 * @author Swapnil Gupta
 *
 */
@WebListener
public class SMSContextListener implements ServletContextListener{

    /**
     * Default constructor. 
     */
    public SMSContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent contextEvent)  { 
    	ServletContext context = contextEvent.getServletContext();
    	
    	setupTerminologyConfigurationInContext(context);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }
    
    /**
     * Setup terminology configuration in servlet context
     * @param context servlet context for current application instance
     */
    public void setupTerminologyConfigurationInContext (ServletContext context){
        // Initialize Hibernate session factory
        HibernateUtil.getSessionFactory();

        // Initialize the CMIS Session
        CMISConnector.getCMISSession();

    	/*CompositeConfiguration terminologyConfig = new ConfigurationLoader().loadConfiguration();
    	LinkedHashMap<String, String> categoryToPropertyKey = 
    			ConfigurationReader.getCategoryToPropertyKey(terminologyConfig);
    	HashMap<String, String> categoryToParent = 
    			ConfigurationReader.getCategoryToParentCategory(categoryToPropertyKey, terminologyConfig);
    	HashMap<String, ArrayList<String>> categoryToHtmlLabels = 
    			ConfigurationReader.getCategoryToHtmlLabels(categoryToPropertyKey, terminologyConfig);
    	
    	context.setAttribute("categoryToParent", categoryToParent);
    	context.setAttribute("categoryToHtmlLabels", categoryToHtmlLabels);
    	context.setAttribute("categoryToPropertyKey", categoryToPropertyKey);*/
    	
//    	MapUtils.verbosePrint(System.out, null, categoryToPropertyKey);
//    	MapUtils.verbosePrint(System.out, null, categoryToParent);
//    	MapUtils.verbosePrint(System.out, null, categoryToHtmlLabels);
//    	
//    	ConfigurationReader.listAllConfigurationProperties(terminologyConfig);
    }	
}
