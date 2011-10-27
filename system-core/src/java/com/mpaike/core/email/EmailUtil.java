package com.mpaike.core.email;

import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mpaike.core.util.base64.Base64;


public class EmailUtil {
	
	private static Log log = LogFactory.getLog(EmailUtil.class);

	private int sendNumber = 0 ;
	private int count = 0;
	
	private String host;
	private int port = 25;
	private final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

	private String fromName;
	private String fromMail;
	private String fromMails[];
	private String password;
	private Properties props;
	
	public EmailUtil(String host,int port,boolean ssl, String fromName,String fromMail,String password){
		this.host = host;
		this.port = port;
		this.fromName = fromName;
		this.fromMail = fromMail;
		this.fromMails = fromMails;
		this.password = password;
		this.props = System.getProperties();
	    // Setup mail server
	    props.put("mail.smtp.host", host);
	    props.put("mail.smtp.auth", "true"); //smtp auth
	    props.put("mail.smtp.quitwait", "false");
	    if(ssl){
	    	  props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
	    	  props.setProperty("mail.smtp.socketFactory.fallback", "false");
	    	  props.setProperty("mail.smtp.port", "465");
	    	  props.setProperty("mail.smtp.socketFactory.port", "465");
	    }else{
	    	props.setProperty("mail.smtp.port",String.valueOf(port));
	    }
	    //props.put("mail.debug", "true");
	}
	
	public boolean sendMail(Long userId,String toName,String toMail,String ccMail,String subject,String body,Date createDate,String attachFiles){
		try{
	    		if(fromMail!=null){
				    MyAuthenticator myauth = new MyAuthenticator(fromMail, password);
				    Session session = Session.getInstance(props, myauth);
			
			//	    session.setDebug(true);
			
				    // Define message
				    MimeMessage message = new MimeMessage(session);
			    
				    // Set the from address
					message.setFrom(new InternetAddress(fromMail));
	
				    // Set Recipient
					message.addRecipients(Message.RecipientType.TO, splitMail(toMail));
				    if(ccMail!=null&&!"".equals(ccMail)&&!fromMail.equals(ccMail)){
				    	message.addRecipients(Message.RecipientType.CC, splitMail(ccMail));
				    }
			
				    // Set the subject
				    message.setSubject(subject);
			
				    // Set the content
				    //message.setText(body);
				    
		            MimeMultipart mp = new MimeMultipart("alternative");   
					MimeBodyPart bPart = new MimeBodyPart();
					bPart.setContent(body,"text/html; charset=utf-8");
					bPart.setDisposition(Part.INLINE);
					
					//add attach file
					MimeMultipart attMultipart = new MimeMultipart("mixed"); 

					if(attachFiles!=null&&!"".equals(attachFiles)){
						String[] atts = attachFiles.split(",");
						if(atts!=null&&atts.length>0)
						for(int i=0,n=atts.length;i<n;i++){
							attMultipart.addBodyPart(addAttach(atts[i]));
						}

					}
					BodyPart attBody = new MimeBodyPart(); 
					
					attBody.setContent(attMultipart);
					
					//add body
					
					mp.addBodyPart(bPart);
					mp.addBodyPart(attBody);
					message.setContent(mp);
						
					//Transport.send cover saveChanges()
				    //message.saveChanges();
			
				    Transport.send(message);

				    return true;
	    		}else{
					throw new MessagingException("fromMail can't null.");
	    		}
		}catch(MessagingException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private BodyPart addAttach(String fujianPath){
		     MimeBodyPart fujian = new MimeBodyPart();
		     String fileName = fujianPath.trim(); 
		     DataSource source = new FileDataSource(fileName);
		     
		     try {
				fujian.setDataHandler(new DataHandler(source));
			     fujian.setFileName("=?GBK?B?"+Base64.encodeBytes((source.getName()).getBytes())+"?=");
			     fujian.setFileName(source.getName());
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return fujian;

	}

	
	
	private synchronized String nextFromMail(){
		if(sendNumber<450){
			sendNumber++;
			return fromMails[count];
		}else{
			count++;
			sendNumber = 0;
			if(fromMails.length<=count){
				return fromMails[count];
			}else{
				count = 0;
				return fromMails[count];
			}
				
		}
	}
	
	public static InternetAddress[] splitMail(String mailString) throws AddressException{
		InternetAddress[] iaArray = null;
		String[] mails = null; 
		if(mailString!=null&&!"".equals(mailString)){
			mails = mailString.split(";");
			iaArray = new InternetAddress[mails.length];
			for(int i=0;i<mails.length;i++){
				iaArray[i] = new InternetAddress(mails[i].trim());
			}
		}
		return iaArray;
	}
	
	public static boolean isEmail(String email) {
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(email);
		return m.find();
	}
	
	
	public static void main(String[] args){
		//System.out.print(isEmail("willis.zhang139.com"));
		
	}

	  
	  

}
