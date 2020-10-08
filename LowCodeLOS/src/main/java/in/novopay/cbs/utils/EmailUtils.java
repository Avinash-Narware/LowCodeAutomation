package in.novopay.cbs.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;



public class EmailUtils extends ApiUtils {

	public static String[] suiteNames = { "" };
	public static String from = "system_auto1@novopay.in";
	public static String fromPass = "system_auto1";
	// "sachins@novopay.in","wallet-qa@novopay.in"
//	
	//public static String[] to = {"renuka.b@novopay.in ","sharmila.d@novopay.in","avinash.narware@novopay.in","santhoshi@novopay.in","minju.elizabeth@novopay.in","muthu.subramanian@novopay.in","navaneet@novopay.in","darpan@novopay.in"};
	//public static String[] to = {"santhoshi@novopay.in","renuka.b@novopay.in","muthu.subramanian@novopay.in","nishant.sharma@novopay.in"};
	
	public static String[] to ={"renuka.b@novopay.in","avinash.narware@novopay.in"};
	
	String excelReportFilePath ;
	//
	JavaUtils utils = new JavaUtils();
	
	@BeforeSuite
	public void readConfig() {
		readConfigProperties();
	}

	@Test
	public void emailSummaryReport() {
		excelReportFilePath = configProperties.get("testReport");
		try {
			
			String subject = " Platform Demo Regression Automation Test Execution Status : "+configProperties.get("buildNumber")+" : " + getTodaysDate("dd MMMM yyyy");
			String bodyText = "Hi All,\n\nPFA API Automation Execution report.\n\nRegards,\nNovopay Automation Team\n\n---This is an auto-generated email, Please do not reply to this.<br/><br/><br/>"
					+ utils.report();
			System.out.println(bodyText);
			Properties properties = System.getProperties();
			properties.setProperty("mail.transport.protocol", "smtp");
			properties.setProperty("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.port", "465");
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

			Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from, fromPass);
				}
			});

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			for (String str : to) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(str));
			}

			message.setSubject(subject);
			Multipart multipart = new MimeMultipart();
			MimeBodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setContent(bodyText, "text/html");
			multipart.addBodyPart(messageBodyPart1);

			MimeBodyPart messageBodyPart3 = new MimeBodyPart();
			DataSource source1 = new FileDataSource(excelReportFilePath);
			messageBodyPart3.setDataHandler(new DataHandler(source1));
			messageBodyPart3.setFileName("TestReport.xlsx");
			multipart.addBodyPart(messageBodyPart3);
			message.setContent(multipart);
			Transport.send(message);
			System.out.println("Mail sent...");
		} catch (MessagingException ex) {
			ex.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
