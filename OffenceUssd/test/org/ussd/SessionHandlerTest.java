package org.ussd;

import hms.sdp.ussd.MchoiceUssdMessage;
import junit.framework.TestCase;

import org.junit.Test;

public class SessionHandlerTest extends TestCase{
	MockMchoiceUssdMessage ussdMessage = new MockMchoiceUssdMessage();
	SessionHandler sessionHandler;
	String result;
	
	
	@Test
	public void testOffencePaidReportByChoosingOffencesFromFirstPageList(){
		ProgramHandler.init();
		sessionHandler = new SessionHandler();
		result = sessionHandler.getMessage();
		
		assertTrue(checkContains(new String[]{"Iroad Menu","Report Offence","Report Accident"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Enter Driver License Number","No Driver License(skip)"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		
		assertTrue(checkContains(new String[]{"Enter Driver License Number"}));
		
		ussdMessage.setMessage("4002761760");
		result = getResult();
		
		assertTrue(checkContains(new String[]{"Enter Vehicle Plate Number"}));
		
		ussdMessage.setMessage("T234 BLM");
		result = getResult();
		assertTrue(checkContains(new String[]{"Select Offences"}));
		
		ussdMessage.setMessage("123");
		result = getResult();
		assertTrue(checkContains(new String[]{"Select Offences"}));
		
		ussdMessage.setMessage("99");
		result = getResult();
		assertTrue(checkContains(new String[]{"Offender has accepted Offences","Offender is going to court"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Cash payment","Mobile payment","Pay another time"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		
		assertTrue(checkContains(new String[]{"Enter Reciept Number for payment"}));
		
		ussdMessage.setMessage("DGHJSAJYW");
		result = getResult();
		assertTrue(checkContains(new String[]{"Are you sure you want to report this offence?"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Offence saved successfully"}));
	}
	
	
	@Test
	public void testOffencePaidReportByChoosingOffenceInAllOffenceListPages(){
		ProgramHandler.init();
		sessionHandler = new SessionHandler();
		result = sessionHandler.getMessage();
		
		assertTrue(checkContains(new String[]{"Iroad Menu","Report Offence","Report Accident"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Enter Driver License Number","No Driver License(skip)"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		
		assertTrue(checkContains(new String[]{"Enter Driver License Number"}));
		
		ussdMessage.setMessage("4002761760");
		result = getResult();
		
		assertTrue(checkContains(new String[]{"Enter Vehicle Plate Number"}));
		
		ussdMessage.setMessage("T234 BLM");
		result = getResult();
		assertTrue(checkContains(new String[]{"Select Offences"}));
		
		ussdMessage.setMessage("123");
		result = getResult();
		assertTrue(checkContains(new String[]{"Select Offences"}));
		
		ussdMessage.setMessage("123");
		result = getResult();
		assertTrue(checkContains(new String[]{"Select Offences"}));
		
		ussdMessage.setMessage("123");
		result = getResult();
		assertTrue(checkContains(new String[]{"Select Offences"}));
		
		ussdMessage.setMessage("123");
		result = getResult();
		assertTrue(checkContains(new String[]{"Select Offences"}));
		
		ussdMessage.setMessage("123");
		result = getResult();
		assertTrue(checkContains(new String[]{"Offender has accepted Offences","Offender is going to court"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Cash payment","Mobile payment","Pay another time"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		
		assertTrue(checkContains(new String[]{"Enter Reciept Number for payment"}));
		
		ussdMessage.setMessage("DGHJSAJYW");
		result = getResult();
		assertTrue(checkContains(new String[]{"Are you sure you want to report this offence?"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Offence saved successfully"}));
	}
	
	
	@Test
	public void testOffenceWithNoDrivingLicence(){
		ProgramHandler.init();
		sessionHandler = new SessionHandler();
		result = sessionHandler.getMessage();
		
		assertTrue(checkContains(new String[]{"Iroad Menu","Report Offence","Report Accident"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Enter Driver License Number","No Driver License(skip)"}));
		
		ussdMessage.setMessage("2");
		result = getResult();
		assertTrue(checkContains(new String[]{"Enter Vehicle Plate Number"}));
		
		ussdMessage.setMessage("T234 BLM");
		result = getResult();
		assertTrue(checkContains(new String[]{"Select Offences"}));
		
		ussdMessage.setMessage("123");
		result = getResult();
		assertTrue(checkContains(new String[]{"Select Offences"}));
		
		ussdMessage.setMessage("123");
		result = getResult();
		assertTrue(checkContains(new String[]{"Select Offences"}));
		
		ussdMessage.setMessage("99");
		result = getResult();
		assertTrue(checkContains(new String[]{"Offender has accepted Offences","Offender is going to court"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Cash payment","Mobile payment","Pay another time"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		
		assertTrue(checkContains(new String[]{"Enter Reciept Number for payment"}));
		
		ussdMessage.setMessage("DGHJSAJYW");
		result = getResult();
		assertTrue(checkContains(new String[]{"Are you sure you want to report this offence?"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Offence saved successfully"}));
	}
	
	@Test
	public void testOffenceGoingToCourtReport(){
		ProgramHandler.init();
		sessionHandler = new SessionHandler();
		result = sessionHandler.getMessage();
		
		
		assertTrue(checkContains(new String[]{"Iroad Menu","Report Offence","Report Accident"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Enter Driver License Number","No Driver License(skip)"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		
		assertTrue(checkContains(new String[]{"Enter Driver License Number"}));
		
		ussdMessage.setMessage("4002761760");
		result = getResult();
		
		assertTrue(checkContains(new String[]{"Enter Vehicle Plate Number"}));
		
		ussdMessage.setMessage("T234 BLM");
		result = getResult();
		assertTrue(checkContains(new String[]{"Select Offences"}));
		
		ussdMessage.setMessage("123");
		result = getResult();
		assertTrue(checkContains(new String[]{"Select Offences"}));
		
		ussdMessage.setMessage("123");
		result = getResult();
		assertTrue(checkContains(new String[]{"Select Offences"}));
		
		ussdMessage.setMessage("123");
		result = getResult();
		assertTrue(checkContains(new String[]{"Select Offences"}));
		
		ussdMessage.setMessage("123");
		result = getResult();
		assertTrue(checkContains(new String[]{"Select Offences"}));
		
		ussdMessage.setMessage("123");
		result = getResult();
		assertTrue(checkContains(new String[]{"Offender has accepted Offences","Offender is going to court"}));
		
		ussdMessage.setMessage("2");
		result = getResult();
		assertTrue(checkContains(new String[]{"Are you sure you want to head to court?","Yes","No"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Offender is going to court"}));
	}
	
	@Test
	public void testReportAccident(){
		ProgramHandler.init();
		sessionHandler = new SessionHandler();
		result = sessionHandler.getMessage();
		
		assertTrue(checkContains(new String[]{"Iroad Menu","Report Offence","Report Accident"}));
		
		ussdMessage.setMessage("2");
		result = getResult();
		assertTrue(checkContains(new String[]{"Enter district  where the accident has occured"}));
		
		ussdMessage.setMessage("Kinondoni");
		result = getResult();
		assertTrue(checkContains(new String[]{"Enter place where the accident has occured"}));
		
		ussdMessage.setMessage("Mwenge Mataa");
		result = getResult();
		assertTrue(checkContains(new String[]{"Select the type of accident","Vehicle alone","Vehicle Vs Pedestrian","Vehicle Vs Vehicle","Others"}));
		
		ussdMessage.setMessage("3");
		result = getResult();
		assertTrue(checkContains(new String[]{"Enter number of vehicles involved"}));
		
		ussdMessage.setMessage("2");
		result = getResult();
		assertTrue(checkContains(new String[]{"Enter Vehicle Plate Number"}));
		
		ussdMessage.setMessage("T234 BLM");
		result = getResult();
		assertTrue(checkContains(new String[]{"Does the vehicle have insurance cover number","Yes","No"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Enter vehicle insurance cover number"}));
		
		ussdMessage.setMessage("2314459023");
		result = getResult();
		assertTrue(checkContains(new String[]{"Does the driver have a license","Yes","No"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Enter driver license number"}));
		
		ussdMessage.setMessage("4002761760");
		result = getResult();
		assertTrue(checkContains(new String[]{"Select accident severity","Fatal Accident","Severe Injures","Minor Injuries","No injuries"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Are you sure you wan't to report this accident","Yes","No"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Accident reported successfully"}));
	}
	
	@Test
	public void testReportVehicleAndPedestrianAccident(){
		ProgramHandler.init();
		sessionHandler = new SessionHandler();
		result = sessionHandler.getMessage();
		
		assertTrue(checkContains(new String[]{"Iroad Menu","Report Offence","Report Accident"}));
		
		ussdMessage.setMessage("2");
		result = getResult();
		assertTrue(checkContains(new String[]{"Enter district  where the accident has occured"}));
		
		ussdMessage.setMessage("Kinondoni");
		result = getResult();
		assertTrue(checkContains(new String[]{"Enter place where the accident has occured"}));
		
		ussdMessage.setMessage("Mwenge Mataa");
		result = getResult();
		assertTrue(checkContains(new String[]{"Select the type of accident","Vehicle alone","Vehicle Vs Pedestrian","Vehicle Vs Vehicle","Others"}));
		
		ussdMessage.setMessage("2");
		result = getResult();
		assertTrue(checkContains(new String[]{"Enter Vehicle Plate Number"}));
		
		ussdMessage.setMessage("T234 BLM");
		result = getResult();
		assertTrue(checkContains(new String[]{"Does the vehicle have insurance cover number","Yes","No"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Enter vehicle insurance cover number"}));
		
		ussdMessage.setMessage("2314459023");
		result = getResult();
		assertTrue(checkContains(new String[]{"Does the driver have a license","Yes","No"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Enter driver license number"}));
		
		ussdMessage.setMessage("4002761760");
		result = getResult();
		assertTrue(checkContains(new String[]{"Select accident severity","Fatal Accident","Severe Injures","Minor Injuries","No injuries"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Are you sure you wan't to report this accident","Yes","No"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Accident reported successfully"}));
	}
	
	@Test
	public void testReportOtherTypeOfAccident(){
		ProgramHandler.init();
		sessionHandler = new SessionHandler();
		result = sessionHandler.getMessage();
		
		assertTrue(checkContains(new String[]{"Iroad Menu","Report Offence","Report Accident"}));
		
		ussdMessage.setMessage("2");
		result = getResult();
		assertTrue(checkContains(new String[]{"Enter district  where the accident has occured"}));
		
		ussdMessage.setMessage("Kinondoni");
		result = getResult();
		assertTrue(checkContains(new String[]{"Enter place where the accident has occured"}));
		
		ussdMessage.setMessage("Mwenge Mataa");
		result = getResult();
		assertTrue(checkContains(new String[]{"Select the type of accident","Vehicle alone","Vehicle Vs Pedestrian","Vehicle Vs Vehicle","Others"}));
		
		ussdMessage.setMessage("4");
		result = getResult();
		System.out.println(result);
		assertTrue(checkContains(new String[]{"Select accident severity","Fatal Accident","Severe Injures","Minor Injuries","No injuries"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Are you sure you wan't to report this accident","Yes","No"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Accident reported successfully"}));
	}
	
	
	
	private String getResult(){
		sessionHandler.setUssdMessage(ussdMessage);
		return sessionHandler.getMessage();
	}
	private boolean checkContains(String[] strings){
		for(int i = 0;i <strings.length;i++){
			if(!result.contains(strings[i])){
				return false;
			}
		}
		return true;
	}
}
