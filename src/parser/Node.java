package parser;

import java.util.HashMap;

public class Node {
	
	private String type;	
	private  HashMap<String, String> hasMap;
	
	
	public Node() {		
		hasMap = new HashMap<String, String>();
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public HashMap<String, String> getHasMap() {
		return hasMap;
	}
	
	public void addHasMap(String key, String  value) {
		hasMap.put(key, value);
	}

	@Override
	public String toString() {
		return "Node [type=" + type + ", hasMap=" + hasMap + "]";
	}
	
		
}
