# Java Knob

Knob is a command line arguments parser for Java that is configurable through a 
config XML. Please refer to knob.xsd for XML schema.


### Usage

Before you can start using Knob, you have to initialize it first with a config 
XML:
 
	public void initializeKnob() {
		Knob knob = Knob.getInstance();
		knob.init(new File("config.xml"));
	}
	
Then, you can start parsing the command line arguments:

	public void parseCommandLineArgs(String[] args) {
		knob.parse(args);
	}
	
Once you parse the command line arguments, you can then query for the value:

	public String getArgument(String argKey) {
		knob.getString(argKey);
	}

If you want to display a help message or manual, simply call ```printUsage()```
method:

	public void printManual() {
		knob.printUsage();
	}

A sample of config XML and its schema can be found under ```xml``` directory.