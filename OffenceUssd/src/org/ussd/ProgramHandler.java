package org.ussd;

/**
 * Handles Programs
 * 
 * @author Vincent P. Minde
 */
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ProgramHandler {
	public static String programs;
	public static String me;
	public ProgramHandler(){
		
	}
	public static void init(){
		DHISAPIHttpRequestor dhisAPIHttpRequestor = new DHISAPIHttpRequestor();
		//Get Programs
		programs = dhisAPIHttpRequestor.get("programs?filters=type:eq:3&paging=false&fields=id,name,version,programStages[id,version,programStageSections[id],programStageDataElements[dataElement[id,name,code,type,optionSet[id,name,options[id,name],version]]]]");
		//Get User Information
		me = dhisAPIHttpRequestor.get("me");
	}
	/**
	 * Get dataElement JSON from Programs loaded from server
	 * 
	 * @param dataElementName {String}
	 * 
	 * @return {JSONObject} JSON Object of dataElement
	 */
	public static JSONObject getDataElement(String dataElementName){
		JSONParser parser = new JSONParser();
		try {
			JSONObject programsJson = (JSONObject) parser.parse(programs);
			JSONArray programsArray = (JSONArray) programsJson.get("programs");
			for(int i = 0;i < programsArray.size();i++){
				JSONObject programJson = (JSONObject) programsArray.get(i);
				JSONObject programStageJson = (JSONObject) ((JSONArray)programJson.get("programStages")).get(0);
				JSONArray programStageDataElements = (JSONArray) programStageJson.get("programStageDataElements");
				//System.out.println(programStageDataElements);
				for(int j = 0;j < programStageDataElements.size();j++){
					JSONObject dataElementJson = (JSONObject) programStageDataElements.get(j);
					//System.out.println(dataElementJson);
					dataElementJson = (JSONObject) dataElementJson.get("dataElement");
					if(dataElementJson.get("name").equals(dataElementName)){
						return dataElementJson;
					}
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * Get program JSON from Programs loaded from server
	 * 
	 * @param programName {String}
	 * 
	 * @return {JSONObject} JSON Object of Program
	 */
	public static JSONObject getProgramByName(String programName) {
		JSONParser parser = new JSONParser();
		try {
			JSONObject programsJson = (JSONObject) parser.parse(programs);
			JSONArray programsArray = (JSONArray) programsJson.get("programs");
			for(int i = 0;i < programsArray.size();i++){
				JSONObject programJson = (JSONObject) programsArray.get(i);
				if(programJson.get("name").equals(programName)){
					return programJson;
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Get organization Unit
	 * 
	 * @return {JSONObject} JSON Object of Ogrganization Unit
	 */
	public static JSONObject getOrganisationUnit() {
		JSONParser parser = new JSONParser();
		try {
			JSONObject meJson = (JSONObject) parser.parse(me);
			JSONArray orgUnitsArray = (JSONArray) meJson.get("organisationUnits");
			return (JSONObject) orgUnitsArray.get(0);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Saves event to the dhis server
	 * 
	 * @param event {String}
	 * 
	 * @return
	 */
	public static String saveEvent(String event){
		DHISAPIHttpRequestor dhisAPIHttpRequestor = new DHISAPIHttpRequestor();
		return dhisAPIHttpRequestor.post("events.json",event);
	}
}
