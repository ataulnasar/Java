package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class TextToXMLconverter implements TextToXML
{

	/*Initializing variables */
	private DocumentBuilderFactory docFactory ;
	private DocumentBuilder docBuilder ;
	private Document doc;
	private Element rootElement;
	private Node node;
	protected Stack<Node> commands;

	public Stack<Node> getCommands() {
		return commands;
	}

	public void setCommands(Stack<Node> commands) {
		this.commands = commands;
	}
	
	/*Constructor*/
	public TextToXMLconverter() {
		commands = new Stack<Node>(); 
		docFactory = DocumentBuilderFactory.newInstance();
		
		try {
			
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();
			rootElement = doc.createElement("people");
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	/*This file will read the text file, save it in stack and return it*/
	@Override
	public Stack<String> readFile(String path) 
	{
		Stack<String> list = new Stack<String>();
		try {
			String currentLine;
			FileReader file = new FileReader(path);			
			BufferedReader bufferedReader = new BufferedReader(file);
			while ((currentLine = bufferedReader.readLine()) != null) {
				list.add(currentLine);
			}
			
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();			
		} 
		
		//reverse the stack values.
		Stack<String> reversedStack = new Stack<String>();
		while (!list.empty()) {
			reversedStack.push(list.pop());
		}
		return reversedStack;
	}
	
	/*This parser will check the input data and return true if it is correct format
	 * This method also save parsed data into stack*/
	@Override
	public boolean parser(Stack<String> stack) 
	{	
		Stack<Node> tempCommands = new Stack<Node>();

		while(!stack.isEmpty()) {
			if(!stack.isEmpty() && stack.peek().startsWith("P")) {			
				StringTokenizer personToken = new StringTokenizer(stack.pop(), "|");				
				if (personToken.countTokens()>1) {
					node = new Node();
					node.setType(personToken.nextToken());				
					node.addHasMap("firstname", personToken.nextToken());
					node.addHasMap("lastname", personToken.nextToken());
					tempCommands.push(node);
				}				

				if(!stack.isEmpty() && stack.peek().startsWith("T")){
					StringTokenizer contactToken = new StringTokenizer(stack.pop(), "|");				
					node = new Node();
					node.setType(contactToken.nextToken());				
					node.addHasMap("mobile", contactToken.nextToken());
					node.addHasMap("fixed", contactToken.nextToken());
					tempCommands.push(node);
				}
				if(!stack.isEmpty() && stack.peek().startsWith("A")){
					StringTokenizer addressToken = new StringTokenizer(stack.pop(), "|");				
					node = new Node();
					node.setType(addressToken.nextToken());				
					node.addHasMap("street", addressToken.nextToken());
					node.addHasMap("city", addressToken.nextToken());
					if(addressToken.hasMoreTokens()){
						node.addHasMap("postabcode", addressToken.nextToken());
					}										
					tempCommands.push(node);
				}
				if(!stack.isEmpty() && stack.peek().startsWith("F")) {
					StringTokenizer fContactToken = new StringTokenizer(stack.pop(), "|");				
					node = new Node();
					node.setType(fContactToken.nextToken());				
					node.addHasMap("name", fContactToken.nextToken());
					node.addHasMap("born", fContactToken.nextToken());					
					tempCommands.push(node);					

					if(!stack.isEmpty() && stack.peek().startsWith("T")) {
						StringTokenizer contactToken = new StringTokenizer(stack.pop(), "|");				
						node = new Node();
						node.setType(contactToken.nextToken());				
						node.addHasMap("mobile", contactToken.nextToken());
						node.addHasMap("fixed", contactToken.nextToken());						
						tempCommands.push(node);
					}
					if(!stack.isEmpty() && stack.peek().startsWith("A")) {						
						StringTokenizer addressToken = new StringTokenizer(stack.pop(), "|");				
						node = new Node();
						node.setType(addressToken.nextToken());				
						node.addHasMap("street", addressToken.nextToken());
						node.addHasMap("city", addressToken.nextToken());
						if(addressToken.hasMoreTokens()){
							node.addHasMap("postabcode", addressToken.nextToken());
						}					
						tempCommands.push(node);
					}
					if(stack.peek().substring(0, 1)!="P"){						
						stack.push("P");
					}
				}
			}
		}	

		//reverse the stack values.		
		while (!tempCommands.empty()) {
			commands.push(tempCommands.pop());
		}

		if(stack.isEmpty())
			return true;
		return false;
	}

	/*This textToXML will convert given text data to XML file*/	
	@Override
	public void textToXML(Stack<String> list, String outputPath) 
	{	
		Node node = new Node();
		if(parser(list)) {
			try {
				doc.appendChild(rootElement);				
				while(!commands.isEmpty()) {

					if(!commands.isEmpty() && commands.peek().getType().equalsIgnoreCase("P"))	{
						Element person = doc.createElement("person");
						node = commands.pop();
						if(node.getHasMap().size()>1){
							xmlRoot(doc, person, node);
						}
						if(!commands.isEmpty() && commands.peek().getType().equals("T")) {	
							Element child = doc.createElement("phone");
							xmlChild(doc,person, child, commands.pop());
						}												
						if(!commands.isEmpty() &&  commands.peek().getType().equals("A")){
							Element address = doc.createElement("address");
							xmlChild(doc,person, address, commands.pop());
						}						
						if(!commands.isEmpty() && commands.peek().getType().equals("F")){
							Element family = doc.createElement("family");							
							xmlChild(doc,person, family, commands.pop());

							if(!commands.isEmpty() && commands.peek().getType().equals("T")){
								Element phone = doc.createElement("phone");
								xmlChild(doc,family, phone, commands.pop());
							}
							if(!commands.isEmpty() && commands.peek().getType().equals("A")){
								Element address = doc.createElement("address");
								xmlChild(doc,family, address, commands.pop());
							}
							if(!commands.isEmpty() && commands.peek().getType()!="P"){
								node = new Node();
								node.setType("P");
								commands.push(node);
							}
						}
					}					
				}
				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(outputPath));
				transformer.transform(source, result);
				System.out.println("XML File saved!");
			}
			catch (TransformerException tfe) {
				tfe.printStackTrace();
			}
		}
	}

	/*Utility methods */	
	private void xmlRoot(Document doc, Element person, Node node){
		rootElement.appendChild(person);
		for(Map.Entry<String, String> entry : node.getHasMap().entrySet()) {
			Element firstname = doc.createElement(entry.getKey());
			firstname.appendChild(doc.createTextNode(entry.getValue()));
			person.appendChild(firstname);			
		}
	}

	private void xmlChild(Document doc,Element rootElement, Element child, Node node){
		rootElement.appendChild(child);
		for(Map.Entry<String, String> entry : node.getHasMap().entrySet()) {
			Element firstname = doc.createElement(entry.getKey());
			firstname.appendChild(doc.createTextNode(entry.getValue()));
			child.appendChild(firstname);			
		}
	}
}
