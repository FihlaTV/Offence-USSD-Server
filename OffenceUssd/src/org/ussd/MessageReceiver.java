package org.ussd;
/**
 * Servlet to recieve ussd request
 * 
 * @author Vincent P. Minde
 * 
 */
import java.util.HashMap;
import java.util.Map;

import hms.sdp.ussd.MchoiceUssdException;
import hms.sdp.ussd.MchoiceUssdMessage;
import hms.sdp.ussd.MchoiceUssdResponse;
import hms.sdp.ussd.MchoiceUssdTerminateMessage;
import hms.sdp.ussd.client.MchoiceUssdReceiver;
import hms.sdp.ussd.client.MchoiceUssdSender;

public class MessageReceiver extends MchoiceUssdReceiver {
	
	/**
	 * Stores user sessions mapped using the session id to session object 
	 * @field session {Map<String,SessionHandler>}
	 */
	public static Map<String,Session> sessions = new HashMap<String,Session>();
	
	
    @Override
    /**
     * Recieves the message from user 
     * 
     * @param ussdMessage {MchoiceUssdMessage}
     */
    public void onMessage(MchoiceUssdMessage ussdMessage){
        System.out.println("============REQUEST=============");
        System.out.println(ussdMessage);
        System.out.println("================================");
        ProgramHandler.init();
        MchoiceUssdResponse mchoiceUssdResponse = null;
        
        MchoiceUssdSender ussdSender = null;
        try {
        	//Initialize object to communicate to operator
        	ussdSender = new MchoiceUssdSender("http://127.0.0.1:8000/ussd/", "appid", "pass");
        	Session session = null;
        	if(sessions.containsKey(ussdMessage.getConversationId())){//If session already exists
        		//Get session
        		session = sessions.get(ussdMessage.getConversationId());
        		//Set the message from the user
        		session.setUssdMessage(ussdMessage);
        	}else{//If session does not exists
        		//Create new session
        		session = new Session();
        		//Add session to map
        		sessions.put(ussdMessage.getConversationId(), session);
        	}
        	//Send the message to the Operator
        	mchoiceUssdResponse = 
        			ussdSender.sendMessage(
        					session.getMessage(), 
        					ussdMessage.getAddress(), 
        					ussdMessage.getConversationId(), 
        					!session.isActive()
        				);
        	
        	System.out.println("=============RESPONSE===============");
            System.out.println(mchoiceUssdResponse);
            System.out.println("====================================");
        } catch (Exception e) {
        	if(ussdSender != null){
        		try {
        			//Sende an error to the server
					mchoiceUssdResponse = ussdSender.sendMessage("Server Error encountered.", ussdMessage.getAddress(), ussdMessage.getConversationId(), false);
				} catch (MchoiceUssdException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		//Remove the session from memmory
        		sessions.remove(ussdMessage.getConversationId());
        	}
    		e.printStackTrace();
        }
    }

    @Override
    public void onSessionTerminate(MchoiceUssdTerminateMessage terminateMessage) {
        System.out.println("========terminate");
        System.out.println(terminateMessage);
        //Remove the session from memmory
        sessions.remove(terminateMessage.getConversationId());
    }
}