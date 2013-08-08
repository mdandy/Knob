package com.mikedandy.knob.data;

import java.io.File;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.mikedandy.knob.exception.KnobParserException;

@XmlRootElement(namespace = "com.mikedandy.knob.data.KnobRoot")
@XmlType(name = "knob", propOrder = {
		"name",
		"shortcode",
		"type",
		"list",
		"required",
		"defaultValue",
		"doc",
		"value"
})
@XmlAccessorType(XmlAccessType.FIELD)
public class KnobEntry {

	@XmlElement(required = true)
	private String name;

	@XmlElement(required = true)
	private String shortcode;

	@XmlElement(required = true)
	private Type type;

	private EnumList list;

	@XmlElement(required = true)
	private boolean required;

	@XmlElement(name = "default")
	private String defaultValue;

	@XmlElement(required = true)
	private String doc;

	private Object value;

	public String getName() {
		return name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getShortcode() {
		return shortcode;
	}

	public void setShortcode(String value) {
		this.shortcode = value;
	}
	
	public boolean hasShortcode() {
		if (this.shortcode == null)
			return false;
		return !this.shortcode.isEmpty(); 
	}

	public Type getType() {
		return type;
	}

	public void setType(Type value) {
		this.type = value;
	}

	public EnumList getList() {
		return list;
	}

	public void setList(EnumList value) {
		this.list = value;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean value) {
		this.required = value;
	}

	private Object getDefault() {
		switch (this.type) {
			case BOOLEAN:
				return Boolean.parseBoolean(this.defaultValue);
			case NUMBER:
				try {
					return Integer.parseInt(this.defaultValue);
				} catch (Exception e) {
					return 0;
				}
			case FILE:
				return new File(this.defaultValue == null ? "" : this.defaultValue);
			case LIST:
			case STRING:
			default:
				return this.defaultValue == null ? "" : this.defaultValue;
		}
	}

	public void setDefault(String value) {
		this.defaultValue = value;
	}

	public String getDoc() {
		return doc == null ? "" : doc;
	}

	public void setDoc(String value) {
		this.doc = value;
	}
	
	/**
	 * Get usage hint for this command line option.
	 * @return usage hint for this command line option
	 */
	public String getHint() {
		switch (this.type) {
			case NUMBER:
				return "=<int>";
			case FILE:
				return "=<path>";
			case LIST:
				StringBuilder hint = new StringBuilder();
				hint.append("=[");
				List<String> enumList = this.list.getEnums();
				for (int i = 0; i < enumList.size(); i++) {
					if (i != 0) hint.append("|");
					hint.append(enumList.get(i));
				}
				hint.append("]");
				return hint.toString();
			case STRING:
				return "=<string>";
			default:
				return "";
		}
	}
	
	/**
	  * Get the knob value. If the value is undefined, it returns the
	  * default value.
	  * @return the knob value
	  */
	 private Object getValue() {
		 if (this.value != null)
			 return this.value;
		 return this.getDefault();
	 }

	/**
	 * Set knob value.
	 * @param value the value
	 * @throws KnobParserException 
	 */
	 public void setValue(String value) throws KnobParserException {
		 switch (this.type) {
			case BOOLEAN:
				this.value = Boolean.parseBoolean(value);
				break;
			case LIST:
				if (this.list.contains(value))
					this.value = value;
				break;
			case NUMBER:
				try {
					this.value = Integer.parseInt(value);
				} catch (Exception e) {}
				break;
			case FILE:
				this.value = new File(value == null ? "" : value);
				break;
			case STRING:
				this.value = value;
				break;
		 }
	 }
	 
	 /**
	  * Check weather this knob has value or not.
	  * @return true if this knob has value or false otherwise
	  */
	 public boolean hasValue() {
		 return this.value != null;
	 }

	 /**
	  * Get the knob value as a String. If the value is undefined, 
	  * it returns the default value.
	  * @return the knob value as a String
	  */
	 public String getString() {
		 return this.getValue().toString();
	 }
	 
	 /**
	  * Get the default knob value as a String. If the value is undefined, 
	  * it returns the default value.
	  * @return the knob value as a String
	  */
	 public String getDefaultString() {
		 return this.getDefault().toString();
	 }

	 /**
	  * Get the knob value as an integer. If the value is undefined, 
	  * it returns the default value.
	  * @return the knob value as an integer
	  */
	 public int getInt() {
		 return (Integer) this.getValue();
	 }

	 /**
	  * Get the knob value as a boolean. If the value is undefined, 
	  * it returns the default value.
	  * @return the knob value as a boolean
	  */
	 public boolean getBoolean() {
		 return (Boolean) this.getValue();
	 }

	 /**
	  * Get the knob value as a file. If the value is undefined, 
	  * it returns the default value.
	  * @return the knob value as a boolean
	  */
	 public File getFile() {
		 return (File) this.getValue();
	 }
	 
}
