package org.ussd;
/**
 * Stores the user session for the offence data entered by user
 * 
 * @author Vincent P. Minde
 * 
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import hms.sdp.ussd.MchoiceUssdMessage;
@SuppressWarnings("unchecked")
public class Session {
	//Stores the session activation status
	private boolean isActive= true;
	//Stores the messege to be sent to the user
	private String message;
	//Stage in traversing the offence
	private int stage;
	private int currentDataElementIndex = -1;
	//DataElementst to input values to
	private ArrayList<DataElement> dataElements;
	//Stores the event which will be sent to the server when session is over
	private JSONObject event = new JSONObject();
	//Stores the data value array
	private JSONArray dataValues = new JSONArray();
	public Session(){
		
		init();
	}
	/**
	 * Sets the ussd message sent
	 * 
	 * @param ussdMessage
	 * 
	 */
	public void setUssdMessage(MchoiceUssdMessage ussdMessage) {
		if(stage == 0){
			event.put("status", "COMPLETED");
			event.put("storedBy","admin");
			JSONObject orgUnit = ProgramHandler.getOrganisationUnit(); 
			event.put("orgUnit",orgUnit.get("id"));
			JSONObject program = null;
			if(ussdMessage.getMessage().equals("1"))
			{
				program = ProgramHandler.getProgramByName("Offence Event");
				dataElements = new ArrayList<DataElement>();
				dataElements.add(new DataElement("Enter Driver License Number","Driver License Number"));
				dataElements.add(new DataElement("Enter Vehicle Plate Number","Vehicle Plate Number"));
			}else{
				init();
				return;
			}
			event.put("program", program.get("id"));
			stage = 1;
			currentDataElementIndex = 0;
			message = dataElements.get(currentDataElementIndex).getQuestion();
			currentDataElementIndex++;
			stage = 1;
		}else if(stage == 1){
			try{
				checkDataElement(ussdMessage.getMessage());
			}catch(Exception ex){
				//ex.printStackTrace();
				//event.put("dataValues", dataValues);
				//System.out.println(ProgramHandler.saveEvent(event.toString()));
				dataElements.add(new DataElement("Enter Receipt Number","Offence Reciept Number"));
				message = "Select Offences";
				currentDataElementIndex++;
				stage = 2;
			}
		}else if(stage == 2){
			//dataElements.add(new DataElement("1)Offender has accepted Offences <br />2)Offender is going to court","Offence Admission Status"));
			message = "1)Offender has accepted Offences <br />2)Offender is going to court";
			stage = 3;
		}else if(stage == 3){
			if(ussdMessage.getMessage().equals("1"))
			{
				dataElements.add(new DataElement("Enter Driver License Number","Driver License Number"));
				message = "1)Cash payment<br />2)Mobile payment";
				stage = 4;
			}else if(ussdMessage.getMessage().equals("2"))
			{
				message = "Offender is going to court";
				System.out.println(ProgramHandler.saveEvent(event.toString()));
				isActive = false;
			}
		}else if(stage == 4){
			if(ussdMessage.getMessage().equals("1"))
			{
				message = "Enter Reciept Number for payment.";
				stage = 5;
			}else if(ussdMessage.getMessage().equals("2"))
			{
				message = "Enter Reference Number for payment.";
				stage = 5;
			}else{
				message = "Please enter the given choices <br />1)Cash payment<br />2)Mobile payment";
				
			}
		}else if(stage == 5){
			try{
				Date date = new Date();
				SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
			    event.put("eventDate", dateFormater.format(date));
			    event.put("dataValues", dataValues);
				checkDataElement(ussdMessage.getMessage());
				message = "Offence saved successfully";
				System.out.println(ProgramHandler.saveEvent(event.toString()));
				isActive = false;
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	/*private boolean isValidDate(String dateString) {
		Date date = null;
		try {
			SimpleDateFormat dateFormater = new SimpleDateFormat("d/M/yyyy");
		    date = dateFormater.parse(dateString);
		    dateFormater = new SimpleDateFormat("yyyy-MM-dd");
		    event.put("eventDate", dateFormater.format(date));
		} catch (ParseException ex) {
		    ex.printStackTrace();
		}
		return date != null;
	}*/
	//Initializes the the session
	private void init(){
		message = "Iroad Menu <br />1)Report Offence";
		stage = 0;
	}
	public String getMessage() {
		return message;
	}
	public boolean isActive(){
		return isActive;
	}
	private void checkDataElement(String message){
		DataElement prevDataElement = dataElements.get(currentDataElementIndex - 1);
		
		if(prevDataElement.isValid(message)){
			
			JSONObject dataValue = new JSONObject();
			dataValue.put("dataElement", prevDataElement.getId());
			dataValue.put("value", prevDataElement.getValue());
			dataValues.add(dataValue);
			DataElement curDataElement = dataElements.get(currentDataElementIndex);
			this.message = curDataElement.getQuestion();
			currentDataElementIndex++;
		}else{
			message = "Wrong entry please Re" + dataElements.get(currentDataElementIndex).getQuestion();
		}
	}
}
