package org.ussd;

/**
 * 
 * Mirrors dataElements in the dhis server
 * 
 * @author Vincent P. Minde
 * 
 */
import org.json.simple.JSONObject;

public class DataElement {
	private String question;
	private String id;
	private String name;
	private String type;
	private Object value;
	/**
	 * 
	 * @param question {String} question the is going to be showed to the user corresponding to the dataElement
	 * 
	 * @param dataElementName {String} Name of the dataElement
	 */
	public DataElement(String question,String dataElementName){
		this.question = question;
		
		//Fetch the data element from the dhis server
		JSONObject dataElementJson = ProgramHandler.getDataElement(dataElementName);
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
	/**
	 * Checks if the value entered is valid
	 * 
	 * @param message
	 * 
	 * @return {boolean} returns true if the message entered by th user is valid and sets the value
	 */
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
