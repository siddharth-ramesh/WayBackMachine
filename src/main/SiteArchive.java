package main;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class SiteArchive {

	private TreeSet<Date> recordLocation = new TreeSet<Date>(); //TreeMap that holds all dates with records
	private Map<Date, SiteData> records = new HashMap<Date, SiteData>(); //HashMap that holds the 
														// website data for a specific date
	private Map<Date, Date> cache = new HashMap<Date, Date>(); // holds cache of previously 
		//made requests mapping a requested date to a date with the corresponding data in records
	
	/**
	 * 
	 * @param date date to access
	 * @return SiteData for the latest held record before or equal to the Date
	 * @throws Exception throws NULL exception if null date is passed
	 */
	public SiteData retrieveSiteData ( Date date ) throws Exception {

		SiteData siteData = null;
			
		if(date == null)
			throw new Exception("NULL Date passed to retrieveSiteData");
		
		Date recordDataKey = null;
		
		if(cache.containsKey(date)) // cache contains key, 
			recordDataKey = cache.get(date);
		else {
			recordDataKey = recordLocation.floor(date);
			if(recordDataKey != null)
				cache.put(date, recordDataKey); // cache it for faster lookups for same date call in future
		}
		
		if(recordDataKey == null)
			return null;
		siteData = records.get(recordDataKey);
		
		return siteData;
	}
	
	/**
	 * 
	 * @param siteData
	 * @throws Exception throws NULL exception if passed siteData is null
	 */
	public void storeSiteData ( SiteData siteData ) throws Exception {
		
		if(siteData == null || siteData.getSHA1() == null || siteData.getSHA1() == "" || siteData.getSiteData() == null || siteData.getSiteData() == "" || siteData.getDateTaken() == null )
			throw new Exception("NULL SiteData passed to storeSiteData");
		
		Date newDate = siteData.getDateTaken();
		SiteData mostRecentData = retrieveSiteData(newDate);
		
		// check if SHA1 is the same as most recent entry, if so, no need to replicate
		if(mostRecentData != null && mostRecentData.getSHA1().equals(siteData.getSHA1())) { 
			cache.put(newDate, mostRecentData.getDateTaken());
		} else { //this is new data
			recordLocation.add(newDate);
			records.put(newDate, siteData);
			cache = new HashMap<Date, Date>(); // clear cache to prevent possible incorrect cache values after new data insertion
			cache.put(newDate, newDate);
		}
	}
}
