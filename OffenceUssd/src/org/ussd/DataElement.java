package org.ussd;

import org.json.simple.JSONObject;

public class DataElement {
	private String question;
	private String id;
	private String name;
	private String type;
	private Object value;
	public DataElement(String question,String dataElement){
		this.question = question;
		
		JSONObject dataElementJson = ProgramHandler.getDataElement(dataElement);
		name = (String) dataElementJson.get("name");
		type = (String) dataElementJson.get("type");
		id = (String) dataElementJson.get("id");
	}
	public String getQuestion() {
		return question;
	}
	public String getId() {
		return id;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isValid(String message) {
		if(type.equals("int")){
			try{
				value = Integer.parseInt(message);
				return true;
			}catch(Exception e){
				return false;
			}
		}else if(type.equals("bool")){
			if(message.equals("1")){
				value = true;
				return true;
			}else if(message.equals("2")){
				value = false;
				return true;
			}
		}else if(type.equals("string")){
			value = message;
			return true;
		}
		return false;
	}
	public String getName() {
		return name;
	}
}
