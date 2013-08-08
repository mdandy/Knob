package com.mikedandy.knob.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import junit.framework.TestCase;

import com.mikedandy.knob.Knob;

public class BasicTest extends TestCase {
	private String configFile;
	private Knob knob;

	public void setUp() throws Exception {
		this.knob = Knob.getInstance();
		this.configFile = "knob.xml";

		String configuration = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<knobs jarName=\"test.jar\">" +
				"    <knob>" +
				"        <name>input</name>" +
				"        <shortcode>i</shortcode>" +
				"        <type>FILE</type>" +
				"        <required>true</required>" +
				"        <doc>Specify the input file.</doc>" +
				"    </knob>" +
				"    <knob>" +
				"        <name>data_type</name>" +
				"        <shortcode>dt</shortcode>" +
				"        <type>LIST</type>" +
				"        <list>" +
				"            <enum>VAR</enum>" +
				"            <enum>INT</enum>" +
				"            <enum>FLOAT</enum>" +
				"        </list>" +
				"        <required>false</required>" +
				"        <default>VAR</default>" +
				"        <doc>The datatype to be queried.</doc>" +
				"    </knob>" +
				"    <knob>" +
				"        <name>outputName</name>" +
				"        <shortcode>o</shortcode>" +
				"        <type>STRING</type>" +
				"        <required>true</required>" +
				"        <default>out.txt</default>" +
				"        <doc>Output filename.</doc>" +
				"    </knob>" +
				"    <knob>" +
				"        <name>numThread</name>" +
				"        <shortcode>t</shortcode>" +
				"        <type>NUMBER</type>" +
				"        <required>false</required>" +
				"        <default>1</default>" +
				"    </knob>" +
				"    <knob>" +
				"        <name>help</name>" +
				"        <shortcode>h</shortcode>" +
				"        <type>BOOLEAN</type>" +
				"        <required>false</required>" +
				"        <default>false</default>" +
				"        <doc>Print this manual.</doc>" +
				"    </knob>" +
				"</knobs>";
		
		writeConfigFile(this.configFile, configuration);
	}

	public void tearDown() throws Exception {
		File file = new File(this.configFile);
		file.delete();
	}

	private void writeConfigFile(String filename, String configuration) throws Exception {
		FileWriter fstream = new FileWriter(filename, true);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(configuration);
		out.flush();
		out.close();
		fstream.close();
	}

	public void testKnobInit() throws Exception {
		File file = new File(this.configFile);
		knob.init(file);
		
		List<String> knobs = this.knob.getKnobs();
		assertEquals("The number of knobs doesn't match", 5, knobs.size());
		assertTrue("Wrong knob: input", knobs.contains("input"));
		assertTrue("Wrong knob: data_type", knobs.contains("data_type"));
		assertTrue("Wrong knob: outputName", knobs.contains("outputName"));
		assertTrue("Wrong knob: numThread", knobs.contains("numThread"));
		assertTrue("Wrong knob: help", knobs.contains("help"));
		
		List<String> shortcodes = this.knob.getShortcodes();
		assertEquals("The number of knobs doesn't match", 5, shortcodes.size());
		assertTrue("Wrong knob: i", shortcodes.contains("i"));
		assertTrue("Wrong knob: dt", shortcodes.contains("dt"));
		assertTrue("Wrong knob: o", shortcodes.contains("o"));
		assertTrue("Wrong knob: t", shortcodes.contains("t"));
		assertTrue("Wrong knob: h", shortcodes.contains("h"));
		
		List<String> requiredArgs = this.knob.getRequiredArgs();
		assertEquals("The number of required args doesn't match", 2, requiredArgs.size());
		assertTrue("Wrong required arg: input", requiredArgs.contains("input"));
		assertTrue("Wrong required arg: outputName", requiredArgs.contains("outputName"));		
	}

	public void testKnobParse() throws Exception {
		String[] args = {
				"--input=" + this.configFile, 
				"--data_type=VAR",
				"--outputName=output.out",
				"--numThread=2",
				"--help"
			};
		
		File file = new File(this.configFile);
		knob.init(file);
		knob.parse(args);
		
		assertEquals("Wrong arg value: input", this.configFile, knob.getFile("input").getName());
		assertEquals("Wrong arg value: data_type", "VAR", knob.getString("data_type"));
		assertEquals("Wrong arg value: outputName", "output.out", knob.getString("outputName"));
		assertEquals("Wrong arg value: numThread", 2, knob.getInt("numThread"));
		assertTrue("Wrong arg value: help", knob.getBoolean("help"));
	}
	
	public void testKnobShortcodeParse() throws Exception {
		String[] args = {
				"-i=" + this.configFile, 
				"-dt=VAR",
				"-o=output.out",
				"-t=2",
				"-h"
			};
		
		File file = new File(this.configFile);
		knob.init(file);
		knob.parse(args);
		
		assertEquals("Wrong arg value: input", this.configFile, knob.getFile("input").getName());
		assertEquals("Wrong arg value: data_type", "VAR", knob.getString("data_type"));
		assertEquals("Wrong arg value: outputName", "output.out", knob.getString("outputName"));
		assertEquals("Wrong arg value: numThread", 2, knob.getInt("numThread"));
		assertTrue("Wrong arg value: help", knob.getBoolean("help"));
	}
	
	public void testInvalidKnobParse() throws Exception {
		String[] args = {
				"-i=" + this.configFile, 
				"-dt=BOGUS",
				"-o=output.out",
				"-t=NAN"
			};
		
		File file = new File(this.configFile);
		knob.init(file);
		knob.parse(args);
		
		assertEquals("Wrong arg value: input", this.configFile, knob.getFile("input").getName());
		assertEquals("Wrong arg value: data_type", "VAR", knob.getString("data_type"));
		assertEquals("Wrong arg value: outputName", "output.out", knob.getString("outputName"));
		assertEquals("Wrong arg value: numThread", 1, knob.getInt("numThread"));
		assertFalse("Wrong arg value: help", knob.getBoolean("help"));
	}

}