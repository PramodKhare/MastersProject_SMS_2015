package edu.neu.ccis.sms.config;

import java.util.Iterator;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * Configuration loader for current web app context
 * @author Swapnil Gupta
 *
 */
public class ConfigurationLoader {
	
	private final String CONFIG_XML_PATH_ENV_VAR = "configXMLPath";
	private final String MULTI_CONFIG_FILES_ENV_VAR = "multipleConfigFiles";
	
	/**
	 * Load application terminology from all the configuration files(s)
	 * @return composite configuration for the current  webapp context
	 */
	public CompositeConfiguration loadConfiguration() {
		CompositeConfiguration terminologyConfig = null;
		try {
			terminologyConfig = new CompositeConfiguration();
			String envConfigXmlPath = getConfigXMLPathFromEnvironemnt();
			terminologyConfig.addConfiguration(new XMLConfiguration(envConfigXmlPath));
			
			if(null != getMultipleConfigFileStatus()) {
				addConfigurationsFromSources(terminologyConfig);
			}
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		return terminologyConfig;
	}
	
	/**
	 * Add configurations from all the configurations sources specified in the root xml
	 * If configurations sources other than XML files are being used, 
	 * they need to be distinguished and added below appropriately
	 * 
	 * @param terminologyConfig, configuration object
	 * @throws ConfigurationException
	 */
	private void addConfigurationsFromSources(CompositeConfiguration terminologyConfig) 
			throws ConfigurationException {
		Iterator<String> keys = terminologyConfig.getKeys();
		while (keys.hasNext()) {
			String configSource = terminologyConfig.getString(keys.next());
			terminologyConfig.addConfiguration(new XMLConfiguration(configSource));
		}
	}
	
	/**
	 * Get configuration xml path specified in the environment variable
	 * @return the configuration xml path for the current SMS instance
	 */
	private String getConfigXMLPathFromEnvironemnt() {
		System.out.println(System.getProperty(CONFIG_XML_PATH_ENV_VAR));
		return System.getProperty(CONFIG_XML_PATH_ENV_VAR);
	}
	
	/**
	 * Get configuration xml path specified in the environment variable
	 * @return the configuration xml path for the current SMS instance
	 */
	private String getMultipleConfigFileStatus() {
		return System.getProperty(MULTI_CONFIG_FILES_ENV_VAR);
	}
}
