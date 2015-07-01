package org.ussd;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import hms.sdp.ussd.MchoiceUssdMessage;
@SuppressWarnings("unchecked")
public class SessionHandler {
	private boolean isActive= false;
	private String message;
	private int stage;
	private int currentDataElementIndex = -1;
	private int currentOffencePage = 0;
	private ArrayList<DataElement> dataElements;
	private JSONObject event = new JSONObject();
	private JSONArray dataValues = new JSONArray();
	public SessionHandler(){
		
		init();
	}
	
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
				
				event.put("program", program.get("id"));
				stage = 1;
				message = "Iroad Menu <br />1)Enter Driver License Number <br />2)No Driver License(skip)";
			}else if(ussdMessage.getMessage().equals("2")){
				program = ProgramHandler.getProgramByName("Accident");
				dataElements = new ArrayList<DataElement>();
				dataElements.add(new DataElement("Enter district  where the accident has occured.","Driver License Number"));
				
				event.put("program", program.get("id"));
				stage = 9;
				message = "Enter district  where the accident has occured.";
			}else{
				init();
				return;
			}
			
		}else if(stage == 1){
			if(ussdMessage.getMessage().equals("1")){
				currentDataElementIndex = 0;
				message = dataElements.get(currentDataElementIndex).getQuestion();
				currentDataElementIndex++;
				stage = 2;
			}else if(ussdMessage.getMessage().equals("2")){
				currentDataElementIndex = 1;
				message = dataElements.get(currentDataElementIndex).getQuestion();
				currentDataElementIndex++;
				stage = 2;
			}
		}else if(stage == 2){
			try{
				checkDataElement(ussdMessage.getMessage());
			}catch(Exception ex){
				dataElements.add(new DataElement("Enter Receipt Number","Offence Reciept Number"));
				message = "Select Offences <br />1)Fast Speeding.<br />2)No vehicle registration. <br />3)Invalid license. <br />4)No learners's ID mark (car). <br />5)Driving instruction offense. <br />6)Bad vehicle condition <br />.7)Untighten seatbelt/No helmet <br />8)Damaging other vehicle. <br />9)Failure to stop.<br />0)Next <br />99)Next";
				currentDataElementIndex++;
				stage = 3;
			}
		}else if(stage == 3){
			if(ussdMessage.getMessage().equals("0")){
				message = "Select Offences <br />1)Fast Speeding.<br />2)No vehicle registration. <br />3)Invalid license. <br />4)No learners's ID mark (car). <br />5)Driving instruction offense. <br />6)Bad vehicle condition <br />.7)Untighten seatbelt/No helmet <br />8)Damaging other vehicle. <br />9)Failure to stop.<br />0)Next <br />99)Skip all";
				stage = 3;
			}else if(ussdMessage.getMessage().equals("99")){
				message = "1)Offender has accepted Offences <br />2)Offender is going to court";
				stage = 4;
			}else if(currentOffencePage<4){
				message = "Select Offences "+currentOffencePage;
				currentOffencePage++;
			}else{
				message = "1)Offender has accepted Offences <br />2)Offender is going to court";
				stage = 4;
			}
		}else if(stage == 4){
			if(ussdMessage.getMessage().equals("1"))
			{
				dataElements.add(new DataElement("Enter Driver License Number","Driver License Number"));
				message = "1)Cash payment<br />2)Mobile payment<br />3)Pay another time";
				stage = 5;
			}else if(ussdMessage.getMessage().equals("2")){
				message = "Are you sure you want to head to court? <br />1)Yes <br />2)No ";
				stage = 8;
			}
		}else if(stage == 5){
			if(ussdMessage.getMessage().equals("1"))
			{
				message = "Enter Reciept Number for payment.";
				stage = 6;
			}else if(ussdMessage.getMessage().equals("2"))
			{
				message = "Enter Reference Number for payment.";
				stage = 6;
			}else{
				message = "Are you sure you want to report this offence?";
				stage = 7;
				
			}
		}else if(stage == 6){
			message = "Are you sure you want to report this offence?";
			stage = 7;
		}else if(stage == 7){
			if(ussdMessage.getMessage().equals("1"))
			{
				try{
					checkDataElement(ussdMessage.getMessage());
					message = "Offence saved successfully";
					System.out.println(ProgramHandler.saveEvent(event.toString()));
					isActive = true;
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}else {
				message = "Offence cancelled";
				isActive = true;
			}
			
		}else if(stage == 8){
			if(ussdMessage.getMessage().equals("1"))
			{
				message = "Offender is going to court";
				System.out.println(ProgramHandler.saveEvent(event.toString()));
				isActive = true;
			}else if(ussdMessage.getMessage().equals("2"))
			{
				message = "1)Offender has accepted Offences <br />2)Offender is going to court";
				stage = 4;
			}
			
		}
		
		//Stages for recording accidents alert through USSD
		else if(stage == 9){
			message = "Enter place where the accident has occured.";
			stage = 10;	
		}else if(stage == 10){
			message = "Select the type of accident. <br />1)Vehicle alone. <br />2)Vehicle Vs Pedestrian. <br />3)Vehicle Vs Vehicle. <br />4)Others";
			stage = 11;
		}else if(stage == 11){
			if(ussdMessage.getMessage().equals("1") || ussdMessage.getMessage().equals("2")){
				dataElements.add(new DataElement("Enter Vehicle Plate Number","Vehicle Plate Number"));
				message = "Enter Vehicle Plate Number";
				stage = 13;
			}else if(ussdMessage.getMessage().equals("3")){ 
				message = "Enter number of vehicles involved";
				stage = 12;
			} else if(ussdMessage.getMessage().equals("4")){
				message = "Select accident severity. <br />1)Fatal Accident. <br />2)Severe Injures <br />3)Minor Injuries. <br />4)No injuries";
				stage = 18;
			}
			
		}else if(stage == 12){
			dataElements.add(new DataElement("Enter Vehicle Plate Number","Vehicle Plate Number"));
			message = "Enter Vehicle Plate Number";
			stage = 13;
			
		}else if(stage == 13){
			message = "Does the vehicle have insurance cover number? <br />1)Yes. <br />2)No.";
			stage = 14;
		}else if(stage == 14){
			if(ussdMessage.getMessage().equals("1")){
				message = "Enter vehicle insurance cover number";
				stage = 15;
			}else if(ussdMessage.getMessage().equals("2")){ 
				message = "Does the driver have a license? <br />1)Yes. <br />2)No.";
				stage = 16;
			}	
		}else if (stage == 15){
			message = "Does the driver have a license? <br />1)Yes. <br />2)No.";
			stage = 16;
		}else if (stage == 16){
			if(ussdMessage.getMessage().equals("1")){
				message = "Enter driver license number";
				stage = 17;
			}else if(ussdMessage.getMessage().equals("2")){ 
				message = "Select accident severity. <br />1)Fatal Accident. <br />2)Severe Injures <br />3)Minor Injuries. <br />4)No injuries";
				stage = 18;
			}
		}else if (stage == 17){
			message = "Select accident severity. <br />1)Fatal Accident. <br />2)Severe Injures <br />3)Minor Injuries. <br />4)No injuries";
			stage = 18;
		}else if (stage == 18){
			message = "Are you sure you wan't to report this accident? <br />1)Yes <br />2)No";
			stage = 19;
		}else if (stage == 19){
			if(ussdMessage.getMessage().equals("1")){
				message = "Accident reported successfully";
				isActive = true;
			}else if(ussdMessage.getMessage().equals("2")){ 
				message = "Accident reporting is cancelled";
				isActive = true;
			}
			System.out.println(message);
		}
	}
	private void init(){
		message = "Iroad Menu <br />1)Report Offence  <br />2)Report Accident";
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
