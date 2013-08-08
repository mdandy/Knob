package com.mikedandy.knob;

import java.io.File;

import com.mikedandy.knob.exception.KnobGenericException;

public class KnobMain {
	
	public static void main(String[] args) {
		String input = "xml/knob.xml";
		
		try {
			File file = new File(input);
			Knob knob = Knob.getInstance();
			knob.init(file);
			knob.parse(args);
			knob.printUsage();
		} catch (KnobGenericException e) {
			e.printStackTrace();
		}
	}
}
