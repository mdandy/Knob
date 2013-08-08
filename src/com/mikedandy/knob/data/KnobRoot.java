package com.mikedandy.knob.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "knobs")
@XmlType(name = "knobs", propOrder = {
    "knobs"
})
@XmlAccessorType(XmlAccessType.FIELD)
public class KnobRoot {

	@XmlAttribute(name = "jarName")
    private String jarName;
	
	@XmlElement(name = "knob", required = true)
    private List<KnobEntry> knobs;
	
    public String getJarName() {
    	if (this.jarName == null)
    		this.jarName = "";
        return this.jarName;
    }

    public void setJarName(String value) {
        this.jarName = value;
    }

    public List<KnobEntry> getKnobs() {
    	if (this.knobs == null) {
    		this.knobs = new ArrayList<KnobEntry>();
        }
        return this.knobs;
	}

	public void setKnobs(List<KnobEntry> knobs) {
		this.knobs = knobs;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("USAGE: " + "\n");
		str.append("java -jar " + this.getJarName() + "\n");
		
		String format = "\t" + "%-5s%-32s%-10s%s" + "\n";
		for (KnobEntry knobEntry : this.knobs) {
			str.append(String.format(
					format, 
					"-" + knobEntry.getShortcode(),
					"--" + knobEntry.getName() + knobEntry.getHint(), 
					knobEntry.isRequired() ? "required" : "",
					knobEntry.getDoc()
				));
		}
		
//		System.out.print("USAGE: " + this.getJarName());
//		System.out.print(" --input=<path>");
//		System.out.print(" [--output=<path>]");
//		System.out.print(" [--data_type=[VAR|INT|FLOAT]]");
//		System.out.print(" [--most_frequent]");
//		System.out.println();
//		System.out.print("      ");
//		System.out.print(" [--top_k=<int>]");
//		System.out.print(" [--count_token]");
//		System.out.print(" [--min_value]");
//		System.out.print(" [--max_value]");
//		System.out.print(" [--print_quote]");
//		System.out.print(" [--var_num_occurance]");
//		System.out.println();
//		System.out.print("      ");
//		System.out.print(" [--compression_k=<int>]");
//		System.out.print(" [--compression_output=<path>]");
//		System.out.print(" [--compression_stat=<path>]");
//		System.out.print(" [--help]");
//		System.out.println();
//		System.out.print("      ");
//		System.out.print(" [--print_freq]");
//		System.out.print(" [--print_debug]");
//		System.out.println("\n");
//		
//		System.out.println("DESCRIPTION");
//		String format = "\t%1$-32s%2$-64s\n";
//		System.out.format(format, "--input=<path>", "Specify the input file.");
//		System.out.format(format, "--output=<path>", "Specify the output file.");
//		System.out.format(format, "--data_type=[VAR|INT|FLOAT]", "The datatype to be queried.");
//		System.out.format(format, "--most_frequent", "Find the most frequently found string in the document");
//		System.out.format(format, "", "for the specified datatype token.");
//		System.out.format(format, "--top_k=<int>", "Find the top k strings for the specified datatype token in the");
//		System.out.format(format, "", "processed document, where k <= 20. If k < 0, k = number of tokens.");
//		System.out.format(format, "--count_token", "Count the total number of tokens of specified datatype.");
//		System.out.format(format, "--min_value", "Find minimum values of INT/ FLOAT found in the document.");
//		System.out.format(format, "--max_value", "Find maximum values of INT/ FLOAT found in the document.");
//		System.out.format(format, "--print_quote", "Print all the quotes found in the document.");
//		System.out.format(format, "--var_num_occurance", "The number of occurences of VAR being immediately followed in");
//		System.out.format(format, "", "the document by the INT or FLOAT token.");
//		System.out.format(format, "--compression_k", "The value of k used in the compression algorithm. It is");
//		System.out.format(format, "", "defaulted to 5. k ranges from 1 to 1000. ");
//		System.out.format(format, "--compression_output=<path>", "If specified, the input file will be compressed into the");
//		System.out.format(format, "", "specified output file.");
//		System.out.format(format, "--compression_stat=<path>", "If specified, compression statistic will be produced for");
//		System.out.format(format, "", "k = 200, 400, 600, 800, and 1000");
//		System.out.format(format, "--print_freq", "Print token frequency.");
//		System.out.format(format, "--print_debug", "Print DEBUG statement.");
//		System.out.format(format, "--help", "Print this manual.");
//		System.out.println();
		
		return str.toString();
	}

	
}
