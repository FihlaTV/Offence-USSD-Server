package org.ussd;

import java.util.HashMap;
import java.util.Map;

import hms.sdp.ussd.MchoiceUssdException;
import hms.sdp.ussd.MchoiceUssdMessage;
import hms.sdp.ussd.MchoiceUssdResponse;
import hms.sdp.ussd.MchoiceUssdTerminateMessage;
import hms.sdp.ussd.client.MchoiceUssdReceiver;
import hms.sdp.ussd.client.MchoiceUssdSender;

public class MessageReceiver extends MchoiceUssdReceiver {
	
	
	public static Map<String,SessionHandler> sessions = new HashMap<String,SessionHandler>();
    @Override
    public void onMessage(MchoiceUssdMessage ussdMessage){
        System.out.println("============REQUEST=============");
        System.out.println(ussdMessage);
        System.out.println("================================");
        ProgramHandler.init();
        MchoiceUssdResponse mchoiceUssdResponse = null;
        MchoiceUssdSender ussdSender = null;
        try {
        	ussdSender = new MchoiceUssdSender("http://127.0.0.1:8000/ussd/", "appid", "pass");
        	SessionHandler session = null;
        	if(sessions.containsKey(ussdMessage.getConversationId())){
        		session = sessions.get(ussdMessage.getConversationId());
        		session.setUssdMessage(ussdMessage);
        	}else{
        		session = new SessionHandler();
        		sessions.put(ussdMessage.getConversationId(), session);
        	}
        	mchoiceUssdResponse = 
        			ussdSender.sendMessage(
        					session.getMessage(), 
        					ussdMessage.getAddress(), 
        					ussdMessage.getConversationId(), 
        					session.isActive()
        				);
        	
        	System.out.println("=============RESPONSE===============");
            System.out.println(mchoiceUssdResponse);
            System.out.println("====================================");
        } catch (Exception e) {
        	if(ussdSender != null){
        		try {
					mchoiceUssdResponse = ussdSender.sendMessage("Server Error encountered.", ussdMessage.getAddress(), ussdMessage.getConversationId(), false);
				} catch (MchoiceUssdException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		sessions.remove(ussdMessage.getConversationId());
        	}
    		e.printStackTrace();
        }
    }

    @Override
    public void onSessionTerminate(MchoiceUssdTerminateMessage terminateMessage) {
        System.out.println("========terminate");
        System.out.println(terminateMessage);
        sessions.remove(terminateMessage.getConversationId());
    }
}