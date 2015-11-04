package main;
import java.util.Date;

// holds the data for a site.
// data consists of a SHA1, the html data (in here as a String), and the date the data was retrieved
public class SiteData {

	private String SHA1 = "";
	private String siteData = "";
	private Date dateTaken = null;
	
	/**
	 * 
	 * @param SHA1 SHA1 of the site as a String
	 * @param siteData Data of the site as a String
	 * @param dateTaken Date the data/site was accessed
	 */
	public SiteData(String SHA1, String siteData, Date dateTaken) {
		this.SHA1 = SHA1;
		this.siteData = siteData;
		this.dateTaken = dateTaken;
	}

	public Date getDateTaken() {
		return dateTaken;
	}

	public void setDateTaken(Date dateTaken) {
		this.dateTaken = dateTaken;
	}

	public String getSHA1() {
		return SHA1;
	}

	public void setSHA1(String sHA1) {
		SHA1 = sHA1;
	}

	public String getSiteData() {
		return siteData;
	}

	public void setSiteData(String siteData) {
		this.siteData = siteData;
	}
	
	
}
