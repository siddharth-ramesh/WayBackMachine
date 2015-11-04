package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import main.AllSitesMap;
import main.SiteData;

public class AllSitesMapTest {

	@Test
	public void AddingNewSite() {
		try {
		String dateStr = "2012-05-10";
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		Date date = null;
		date = format.parse(dateStr);
		
		AllSitesMap sitesMap = new AllSitesMap();
		sitesMap.doPostSiteDataForDate("http://www.tripadvisor.com", date, "mySHA1", "mySiteData");
		SiteData siteDataRet = sitesMap.doGetSiteDataForDate("http://www.tripadvisor.com", date);
		
		assertEquals("mySHA1", siteDataRet.getSHA1());
		assertEquals("mySiteData", siteDataRet.getSiteData());
		
		} catch (Exception e) {
			e.printStackTrace();
			fail("Ran into exception");
		}
	}
	
	@Test
	public void AddingSameSite() {
		try {
		String dateStr = "2012-05-10";
		String dateStr2 = "2013-05-10";
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		Date date = null;
		Date date2 = null;
		date = format.parse(dateStr);
		date2 = format.parse(dateStr2);
		
		AllSitesMap sitesMap = new AllSitesMap();
		sitesMap.doPostSiteDataForDate("http://www.tripadvisor.com", date, "mySHA1", "mySiteData");
		sitesMap.doPostSiteDataForDate("http://www.tripadvisor.com", date2, "mySecondSHA1", "mySecondSiteData");
		SiteData siteDataRet = sitesMap.doGetSiteDataForDate("http://www.tripadvisor.com", date);
		
		assertEquals("mySHA1", siteDataRet.getSHA1());
		assertEquals("mySiteData", siteDataRet.getSiteData());
		
		} catch (Exception e) {
			e.printStackTrace();
			fail("Ran into exception");
		}
	}
	
	@Test
	public void AddingDifferentSites() {
		try {
		String dateStr = "2012-05-10";
		String dateStr2 = "2013-05-10";
		String dateStr3 = "2012-08-21";
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		Date date = null;
		Date date2 = null;
		Date date3 = null;
		date = format.parse(dateStr);
		date2 = format.parse(dateStr2);
		date3 = format.parse(dateStr3);
		
		AllSitesMap sitesMap = new AllSitesMap();
		sitesMap.doPostSiteDataForDate("http://www.tripadvisor.com", date, "mySHA1", "mySiteData");
		sitesMap.doPostSiteDataForDate("http://www.tripadvisor.com", date2, "mySecondSHA1", "mySecondSiteData");
		sitesMap.doPostSiteDataForDate("http://www.stackoverflow.com", date, "mySHA1", "mySiteData");
		sitesMap.doPostSiteDataForDate("http://www.stackoverflow.com", date2, "mySecondSHA1", "mySecondSiteData");
		
		SiteData siteDataRet = sitesMap.doGetSiteDataForDate("http://www.tripadvisor.com", date3);
		
		assertEquals("mySHA1", siteDataRet.getSHA1());
		assertEquals("mySiteData", siteDataRet.getSiteData());
		
		} catch (Exception e) {
			e.printStackTrace();
			fail("Ran into exception");
		}
	}
	
	@Test
	public void AddingFakeSite() {
		try {
		String dateStr = "2012-05-10";
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		Date date = null;
		date = format.parse(dateStr);
		
		AllSitesMap sitesMap = new AllSitesMap();
		sitesMap.doPostSiteDataForDate("thisIsNotHowAURLLooks", date, "mySHA1", "mySiteData");
		
		SiteData siteDataRet = sitesMap.doGetSiteDataForDate("http://www.tripadvisor.com", date);
		
		assertEquals(null, siteDataRet);
		
		} catch (Exception e) {
			e.printStackTrace();
			fail("Ran into exception");
		}
	}
	
	@Test
	public void NonExistantSiteRetrieval() {
		try {
		String dateStr = "2012-05-10";
		DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		Date date = null;
		date = format.parse(dateStr);
		
		AllSitesMap sitesMap = new AllSitesMap();
		SiteData siteDataRet = sitesMap.doGetSiteDataForDate("http://www.tripadvisor.com", date);
		
		assertEquals(null, siteDataRet);
		
		} catch (Exception e) {
			e.printStackTrace();
			fail("Ran into exception");
		}
	}

}
