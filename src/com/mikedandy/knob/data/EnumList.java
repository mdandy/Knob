package com.mikedandy.knob.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(namespace = "com.mikedandy.knob.data.KnobEntry")
@XmlType(name = "list", propOrder = {
    "enums"
})
@XmlAccessorType(XmlAccessType.FIELD)
public class EnumList {

    @XmlElement(name = "enum", required = true)
    private List<String> enums;

    public List<String> getEnums() {
        if (this.enums == null) {
        	this.enums = new ArrayList<String>();
        }
        return this.enums;
    }
    
	public void setEnums(List<String> enums) {
		this.enums = enums;
	}
	
	public boolean contains(String value) {
		return this.enums.contains(value);
	}

}
