package main;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AllSitesMap {
	//Ideally, the frontEnd will call the doGet and doPost methods in a HttpServlet
	//Here, it is coded as a "regular" function so it is easy to use as a console application
	
	Map<String, SiteArchive> allSitesMap = new HashMap<String, SiteArchive>(); // holds different sites and their archives
	
	/**
	 * 
	 * @param siteURL URL of site requested
	 * @param date Requested date
	 * @return SiteData or null if data does not exist
	 */
	public SiteData doGetSiteDataForDate ( String siteURL, Date date ) {
		SiteData siteData = null;
		
		try {
			Pattern p = Pattern.compile("(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?");  
		    Matcher m = p.matcher(siteURL); 
		    if(!m.matches())
		    	throw new Exception("Site URL is not valid: "+siteURL);
		    
			if(!allSitesMap.containsKey(siteURL))
				throw new Exception("No Site data available for: "+siteURL);
			
			SiteArchive siteArchive = allSitesMap.get(siteURL);
			siteData = siteArchive.retrieveSiteData(date);
			
		} catch (Exception e) {
			e.printStackTrace();
			return siteData;
		}
		
		return siteData;
	}
	
	/**
	 * 
	 * @param siteURL URL of site from web crawler
	 * @param date Date site was accessed
	 * @param SHA1 SHA1 of the site
	 * @param siteDataStr Data of the site (stored as a string)
	 * @return true if data for the date was successfully inserted in records, otherwise false
	 */
	public boolean doPostSiteDataForDate ( String siteURL, Date date, String SHA1, String siteDataStr ) {
		
		try {
			
			SiteData siteData = new SiteData(SHA1, siteDataStr, date);			
			Pattern p = Pattern.compile("(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?");  
		    Matcher m = p.matcher(siteURL); 
		    if(!m.matches())
		    	throw new Exception("Site URL is not valid: "+siteURL);
			
			if(allSitesMap.containsKey(siteURL)) {
				SiteArchive siteArchive = allSitesMap.get(siteURL);
				siteArchive.storeSiteData(siteData);
			} else {
				SiteArchive siteArchive = new SiteArchive();
				siteArchive.storeSiteData(siteData);
				allSitesMap.put(siteURL, siteArchive);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true; // stored site data successfully
	}
}
