package parser;

import java.util.Stack;

public interface TextToXML {
	Stack<String> readFile(String path);

	boolean parser(Stack<String> stack);

	String textToXML(Stack<String> list, String outputPath);
}
