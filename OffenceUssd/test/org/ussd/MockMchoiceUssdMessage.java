package org.ussd;

import hms.sdp.ussd.MchoiceUssdMessage;

public class MockMchoiceUssdMessage implements MchoiceUssdMessage{
	private String message;
	public MockMchoiceUssdMessage(){
		
	}
	@Override
	public String getAddress() {
		// TODO Auto-generated method stub
		return "Address";
	}

	@Override
	public String getConversationId() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getCorrelationId() {
		// TODO Auto-generated method stub
		return "";
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public boolean isSessionTermination() {
		// TODO Auto-generated method stub
		return false;
	}

}
