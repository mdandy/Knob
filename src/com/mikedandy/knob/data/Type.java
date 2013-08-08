package com.mikedandy.knob.data;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "type")
@XmlEnum
public enum Type {

    STRING,
    NUMBER,
    BOOLEAN,
    FILE,
    LIST;

    public String value() {
        return name();
    }

    public static Type fromValue(String v) {
        return valueOf(v);
    }
 
}
