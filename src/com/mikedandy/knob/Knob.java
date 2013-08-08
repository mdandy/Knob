package com.mikedandy.knob;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.mikedandy.knob.data.KnobEntry;
import com.mikedandy.knob.data.KnobRoot;
import com.mikedandy.knob.exception.KnobGenericException;
import com.mikedandy.knob.exception.KnobParserException;
import com.mikedandy.knob.exception.KnobRequiredArgException;

/**
 * This class parses command line options.
 * @author Michael Dandy
 */
public class Knob 
{
	private static Knob instance;
	private KnobRoot knobRoot;
	private List<String> requiredArgs;
	private HashMap<String, String> shortcodes;
	private HashMap<String, KnobEntry> knobs;
	
	/**
	 * Get the instance of Knob.
	 * @return the instance of Knob
	 */
	public static Knob getInstance() {
		if (instance == null)
			instance = new Knob();
		return instance;
	}
	
	/**
	 * Constructor
	 */
	private Knob() {
		this.knobRoot = new KnobRoot();
		this.requiredArgs = new ArrayList<String>();
		this.shortcodes = new HashMap<String, String>();
		this.knobs = new HashMap<String, KnobEntry>();
	}
	
	/**
	 * Initialize knob. 
	 * @param file the configuration xml
	 * @throws KnobGenericException 
	 */
	public void init(File file) throws KnobGenericException {
		if (!file.exists()) {
			throw new KnobGenericException("Unable to locate file " + file.getName());
		}
		
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(KnobRoot.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			this.knobRoot = (KnobRoot) jaxbUnmarshaller.unmarshal(file);
			this.initializeKnob();
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new KnobGenericException(e.getMessage());
		}
	}
	
	private void initializeKnob() {
		for (KnobEntry knob : this.knobRoot.getKnobs()) {
			if (knob.isRequired())
				this.requiredArgs.add(knob.getName());
			if (knob.hasShortcode())
				this.shortcodes.put(knob.getShortcode(), knob.getName());
			this.knobs.put(knob.getName(), knob);
		}
	}
	
	/**
	 * Parse command line arguments into knobs.
	 * @param args command line arguments
	 * @throws KnobParserException, KnobRequiredArgException
	 */
	public void parse(String[] args) throws KnobParserException, KnobRequiredArgException
	{
		for (String arg : args)
		{
			String key = "";
			String value = "";
			
			if (arg.startsWith("--")) {
				arg = arg.substring(2);
				if (arg.contains("=")) {
					key = getKeyPart(arg);
					value = getValuePart(arg);
				} else {
					key = arg.trim();
					value = "true";
				}
			} else if (arg.startsWith("-")) {
				arg = arg.substring(1);
				if (arg.contains("=")) {
					key = this.shortcodes.get(getKeyPart(arg));
					value = getValuePart(arg);
				} else {
					key = this.shortcodes.get(arg.trim());
					value = "true";
				}
			}
			
			if (key != null && this.knobs.containsKey(key)) {
				KnobEntry knob = this.knobs.get(key);
				knob.setValue(value);
			}
		}
		
		if (!checkRequiredArgs())
			throw new KnobRequiredArgException("Missing required arguments.");
	}
	
	private String getKeyPart(String arg) {
		return arg.substring(0, arg.indexOf("=")).trim();
	}
	
	private String getValuePart(String arg) {
		return arg.substring(arg.indexOf("=") + 1).trim();
	}
	
	private boolean checkRequiredArgs() {
		boolean isRequiredArgsSatisfied = true;
		for (String key : this.requiredArgs) {
			isRequiredArgsSatisfied |= this.knobs.get(key).hasValue();
		}
		return isRequiredArgsSatisfied;
	}
	
	/**
	 * Print manual.
	 */
	public void printUsage() {
		System.out.println(this.knobRoot.toString());
	}
	
	/**
	 * Get required arguments.
	 * @return required arguments
	 */
	public List<String> getRequiredArgs() {
		return this.requiredArgs;
	}
	
	/**
	 * Get knobs' key.
	 * @return knobs' key
	 */
	public List<String> getKnobs() {
		return new ArrayList<String>(this.knobs.keySet());
	}
	
	/**
	 * Get knobs' shortcode.
	 * @return knobs' shortcode
	 */
	public List<String> getShortcodes() {
		return new ArrayList<String>(this.shortcodes.keySet());
	}
	
	/**
	 * Get command line option as a String. If option is not found,
	 * the default value will be returned.
	 * @param key the key
	 * @return the command line option as a String
	 */
	public String getString(String key)
	{
		return this.knobs.get(key).getString();
	}
	
	/**
	 * Get command line option as an integer. If option is not found,
	 * the default value will be returned.
	 * @param key the key
	 * @return the command line option as an integer
	 */
	public int getInt(String key)
	{
		return this.knobs.get(key).getInt();
	}
	
	/**
	 * Get command line option as a boolean. If option is not found,
	 * the default value will be returned.
	 * @param key the key
	 * @return the command line option as a boolean
	 */
	public boolean getBoolean(String key)
	{
		return this.knobs.get(key).getBoolean();
	}
	
	/**
	 * Get command line option as a file. If option is not found,
	 * the default value will be returned.
	 * @param key the key
	 * @return the command line option as a boolean
	 */
	public File getFile(String key)
	{
		return this.knobs.get(key).getFile();
	}
	
}