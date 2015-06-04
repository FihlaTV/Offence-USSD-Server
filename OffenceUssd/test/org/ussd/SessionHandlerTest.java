package org.ussd;
/**
 * 
 * Test the functionality of the session
 * 
 */
import hms.sdp.ussd.MchoiceUssdMessage;
import junit.framework.TestCase;

import org.junit.Test;

public class SessionHandlerTest extends TestCase{
	MockMchoiceUssdMessage ussdMessage = new MockMchoiceUssdMessage();
	Session sessionHandler;
	String result;
	
	@Test
	public void testOffenceGoingToCourtReport(){
		ProgramHandler.init();
		sessionHandler = new Session();
		result = sessionHandler.getMessage();
		
		assertTrue(checkContains(new String[]{"Iroad Menu","Report Offence"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Enter Driver License Number"}));
		
		ussdMessage.setMessage("4002761760");
		result = getResult();
		assertTrue(checkContains(new String[]{"Enter Vehicle Plate Number"}));
		
		ussdMessage.setMessage("T234 BLM");
		result = getResult();
		assertTrue(checkContains(new String[]{"Select Offences"}));
		
		ussdMessage.setMessage("0");
		result = getResult();
		assertTrue(checkContains(new String[]{"Offender has accepted Offences","Offender is going to court"}));
		
		ussdMessage.setMessage("2");
		result = getResult();
		assertTrue(checkContains(new String[]{"Offender is going to court"}));
	}
	@Test
	public void testOffencePaidReport(){
		ProgramHandler.init();
		sessionHandler = new Session();
		result = sessionHandler.getMessage();
		
		assertTrue(checkContains(new String[]{"Iroad Menu","Report Offence"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Enter Offence Date","day/Month/Year"}));
		
		ussdMessage.setMessage("10/12/2014");
		result = getResult();
		assertTrue(checkContains(new String[]{"Enter Driver License Number"}));
		
		ussdMessage.setMessage("4002761760");
		result = getResult();
		
		assertTrue(checkContains(new String[]{"Enter Vehicle Plate Number"}));
		
		ussdMessage.setMessage("T234 BLM");
		result = getResult();
		assertTrue(checkContains(new String[]{"Select Offences"}));
		
		ussdMessage.setMessage("0");
		result = getResult();
		assertTrue(checkContains(new String[]{"Offender has accepted Offences","Offender is going to court"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		assertTrue(checkContains(new String[]{"Cash payment","Mobile payment"}));
		
		ussdMessage.setMessage("1");
		result = getResult();
		
		assertTrue(checkContains(new String[]{"Enter Reciept Number for payment"}));
		
		ussdMessage.setMessage("DGHJSAJYW");
		result = getResult();
		assertTrue(checkContains(new String[]{"Offence saved successfully"}));
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
