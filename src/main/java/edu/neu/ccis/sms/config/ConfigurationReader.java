package edu.neu.ccis.sms.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.configuration.CompositeConfiguration;

/**
 * Configuration reader for the current web app context
 * @author Swapnil Gupta
 *
 */
public class ConfigurationReader {
	
	private static final String HIERARCHY_ROOT_ELEMENT = "category";
	private static final String HIERARCHY_CHILD_ELEMENT = "subCategory";
	
	private static final String PARENT_CATEGORY_ELEMENT = "parent";
	private static final String HTML_LABELS_ROOT_ELEMENT = "labels";
	
	private static final String CATEGORY_ATTRIBUTE_NAME = "name";
	private static final String CATEGORY_ATTRIBUTE_REGISTER = "register";
	
	
	/**
	 * Get attribute key for given property
	 * 
	 * @param attribute, attribute to get from the given property
	 * @param key, property for which we need to get the given attribute
	 * @return key to get given attribute from given property
	 */
	private static String getAttributeKeyForProperty(String attribute, String property) {
		return property + "[@" + attribute + "]";
	}
			
	/**
	 * Find all the categories in the terminology config, mapping them to their property key
	 * Starting from root element, keep on traversing child elements till you traverse all valid properties with the attribute 'name'
	 * 
	 * @param terminologyConfig configuration containing the terminology for current context
	 * @return all the categories mapped to their property keys in the configuration file
	 */
	public static LinkedHashMap<String, String> getCategoryToPropertyKey(CompositeConfiguration terminologyConfig) {
		
		LinkedHashMap<String, String> categoryToPropertyKey = new LinkedHashMap<String, String>();
		ArrayList<String> propertyKeys = new ArrayList<String>();
		propertyKeys.add(HIERARCHY_ROOT_ELEMENT);
		
		while (CollectionUtils.isNotEmpty(propertyKeys)) {
			mapCategoryToPropertyKey(propertyKeys, categoryToPropertyKey, terminologyConfig);
		}
		return categoryToPropertyKey;
	}
	
	
	/**
	 * Get all the categories associated with given property key, recording them in a map
	 * If more than one categories are available for a given property key, they are siblings
	 * and can be distinguished by using the index e.g (0) after the property key, starting from 0
	 * following the sequence in the config file
	 * 
	 * @param propertyKey, property to get the contained categories
	 * @param categoryToPropertyKey, mapping from category to property keys
	 * @param terminologyConfig, configuration object
	 */
	private static void mapCategoryToPropertyKey(ArrayList<String> propertyKeys, 
			LinkedHashMap<String, String> categoryToPropertyKey, CompositeConfiguration terminologyConfig) {
		String propertyKey = propertyKeys.remove(0);
		String namedAttributeKey = getAttributeKeyForProperty(CATEGORY_ATTRIBUTE_NAME, propertyKey);
		Object propertyObj = terminologyConfig.getProperty(namedAttributeKey);
		
		if(propertyObj instanceof Collection) {
			int index = 0;
			List<Object> properties = terminologyConfig.getList(namedAttributeKey);
			for (Object property : properties) {
				String indexedPropertyKey = propertyKey + "(" + index++ + ")";
				propertyKeys.add(indexedPropertyKey + "." + HIERARCHY_CHILD_ELEMENT);
				categoryToPropertyKey.put((String) property, indexedPropertyKey);
			}
		} else if (null != propertyObj) {
			propertyKeys.add(propertyKey + "." + HIERARCHY_CHILD_ELEMENT);
			categoryToPropertyKey.put((String) propertyObj, propertyKey);
		}
	}
	

	/**
	 * Get html labels for each category in the terminology configurations file
	 * All the html labels are contained within the 'labels' element, child of the '(sub)category' element
	 * 
	 * @param categoryToPropertyKey, mapping from category to property keys
	 * @param terminologyConfig, configuration object
	 * @return all the categories mapped to specified html labels
	 */
	public static HashMap<String, ArrayList<String>> getCategoryToHtmlLabels(
			LinkedHashMap<String, String> categoryToPropertyKey, CompositeConfiguration terminologyConfig) {
		
		HashMap<String, ArrayList<String>> categoryToHtmlLabels = new HashMap<String, ArrayList<String>>();
	    Iterator<Entry<String, String>> iterator = categoryToPropertyKey.entrySet().iterator();
	    
	    while (iterator.hasNext()) {
	        Map.Entry<String, String> categoryToProperty = (Map.Entry<String, String>)iterator.next();
	        String category = (String)categoryToProperty.getKey();
	        String propertyKey = (String) categoryToProperty.getValue();
	        
	        ArrayList<String> htmlLabels = new ArrayList<String>();
	        Iterator<String> labelKeys = terminologyConfig.getKeys(propertyKey + "." + HTML_LABELS_ROOT_ELEMENT);

			while (labelKeys.hasNext()) {
				String labelKey = labelKeys.next();
				htmlLabels.add(terminologyConfig.getString(labelKey));
			}
			categoryToHtmlLabels.put(category, htmlLabels);
	    }
	    return categoryToHtmlLabels;
	}
	
	/**
	 * Get parent category for each category in the terminology configurations file
	 * The parent category is specified as the 'parent' element within each (sub)category
	 * 
	 * @param categoryToPropertyKey, mapping from category to property keys
	 * @param terminologyConfig, configuration object
	 * @return all the categories mapped to their parent categories
	 */
	public static HashMap<String, String> getCategoryToParentCategory(
			LinkedHashMap<String, String> categoryToPropertyKey, CompositeConfiguration terminologyConfig) {
		
		HashMap<String, String> categoryToParent = new HashMap<String, String>();
	    Iterator<Entry<String, String>> iterator = categoryToPropertyKey.entrySet().iterator();
	    
	    while (iterator.hasNext()) {
	    	Map.Entry<String, String> categoryToProperty = (Map.Entry<String, String>)iterator.next();
	    	String category = (String)categoryToProperty.getKey();
		    String propertyKey = (String) categoryToProperty.getValue();
		    String parentCategory = terminologyConfig.getString(propertyKey + "." + PARENT_CATEGORY_ELEMENT);
		     
	        categoryToParent.put(category, parentCategory);
	    }
	    return categoryToParent;
	}
	
	/**
	 * Return a hash set of all the categories which have true as value for the given boolean attribute 
	 * 
	 * @param categoryToPropertyKey mapping from category to property keys
	 * @param terminologyConfig, configuration object
	 * @param booleanAttribute boolean attribute we are searching for given category
	 * @return Set of all categories which are <boolean attribute>-able 
	 * 		   e.g for register returns a hash set of all registerables
	 */
	public static HashSet<String> getAllCategoriesForBooleanAttribute (
		LinkedHashMap<String, String> categoryToPropertyKey, CompositeConfiguration terminologyConfig,
		String booleanAttribute) {
		HashSet<String> booleanAttributeCategories = new HashSet<String> ();
		
		for (Map.Entry<String, String> entry : categoryToPropertyKey.entrySet()) {
		    String category = entry.getKey();
		    String propertyKey = entry.getValue();
		    String registerAttributeKey = getAttributeKeyForProperty(booleanAttribute, propertyKey);
		    if(terminologyConfig.getBoolean(registerAttributeKey, false)) {
				booleanAttributeCategories.add(category);
		    }
		}
		return booleanAttributeCategories;
	}
	
	/**
	 * List all configuration properties
	 */
	public static void listAllConfigurationProperties(CompositeConfiguration config) {
		
		Iterator<String> keys = config.getKeys();
		while (keys.hasNext()) {
			String key = keys.next();
			System.out.print(key + ":");
			printKeyValue(key, config);
			System.out.println("");
		}
	}
	
	/**
	 * Print values for given key, even if it is a list
	 * @param key
	 */
	public static void printKeyValue(String key, CompositeConfiguration config) {
		Object property = config.getProperty(key);
		if(property instanceof Collection) {
			List<Object> properties = config.getList(key);
			for (Object propertyKey : properties) {
				System.out.print(propertyKey + ",");
			}
		} else {
			System.out.print(property);
		}
	}
	
}
