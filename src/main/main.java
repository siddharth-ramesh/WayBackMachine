package main;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class main {

	public static void main(String[] args) { // main function to test way-back
											 // machine

		AllSitesMap mySitesMap = new AllSitesMap();
		Scanner user_input = new Scanner(System.in);
		
		while (true) {
			System.out.print("Type 'post' or 'get' to put/get site data or 'q' to quit: ");
			String command = user_input.next();
			if (command.equals("post")) {
				System.out.println("\nEnter SiteURL, Date(YYYY-MM-dd), SHA1, and SiteData:");
				String siteURL = user_input.next();
				String dateStr = user_input.next();
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				try {
					date = format.parse(dateStr);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				String SHA1 = user_input.next();
				String siteDataStr = user_input.next();
				System.out.println("Data got inserted successfully: "
						+ mySitesMap.doPostSiteDataForDate(siteURL, date, SHA1, siteDataStr));
			} else if (command.equals("get")) {
				System.out.println("\nEnter SiteURL followed by Date(YYYY-MM-dd):");
				String siteURL = user_input.next();
				String dateStr = user_input.next();
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				try {
					date = format.parse(dateStr);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				SiteData siteData = mySitesMap.doGetSiteDataForDate(siteURL, date);
				if (siteData != null) {
					System.out.println("SHA1: " + siteData.getSHA1());
					System.out.println("Data: " + siteData.getSiteData());
					System.out.println("Date: " + siteData.getDateTaken().toString());
				}
			} else if (command.equals("q")) {
				break;
			} else {
				System.out.println("Invalid command, try again.");
			}
			System.out.println("___________________________\n\n");

		}
		user_input.close();

	}

}
