package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import main.SiteArchive;
import main.SiteData;

public class SiteArchiveTest {

	@Test
	public void testSameDateRetrieval() {
		try {
			String dateStr = "2012-05-10";
			DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
			Date date = null;
			date = format.parse(dateStr);
			SiteData siteData = new SiteData("mySHA1", "mySiteData", date);

			SiteArchive siteArchive = new SiteArchive();
			siteArchive.storeSiteData(siteData);

			SiteData siteDataRet = siteArchive.retrieveSiteData(date);

			assertEquals("mySHA1", siteDataRet.getSHA1());
			assertEquals("mySiteData", siteDataRet.getSiteData());

		} catch (Exception e) {
			e.printStackTrace();
			fail("Ran into exception");
		}
	}

	@Test
	public void testLaterDateRetrieval() {
		try {
			String dateStr = "2012-05-10";
			String dateStr2 = "2013-05-10";
			DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
			Date date = null;
			Date date2 = null;
			date = format.parse(dateStr);
			date2 = format.parse(dateStr2);
			SiteData siteData = new SiteData("mySHA1", "mySiteData", date);

			SiteArchive siteArchive = new SiteArchive();
			siteArchive.storeSiteData(siteData);

			SiteData siteDataRet = siteArchive.retrieveSiteData(date2);

			assertEquals("mySHA1", siteDataRet.getSHA1());
			assertEquals("mySiteData", siteDataRet.getSiteData());

		} catch (Exception e) {
			e.printStackTrace();
			fail("Ran into exception");
		}
	}

	@Test
	public void testEarlierDateRetrieval() {
		try {
			String dateStr = "2012-05-10";
			String dateStr2 = "2011-05-10";
			DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
			Date date = null;
			Date date2 = null;
			date = format.parse(dateStr);
			date2 = format.parse(dateStr2);
			SiteData siteData = new SiteData("mySHA1", "mySiteData", date);

			SiteArchive siteArchive = new SiteArchive();
			siteArchive.storeSiteData(siteData);

			SiteData siteDataRet = siteArchive.retrieveSiteData(date2);

			assertEquals(null, siteDataRet);

		} catch (Exception e) {
			e.printStackTrace();
			fail("Ran into exception");
		}
	}

	@Test
	public void testCache() {
		try {
			String dateStr = "2012-05-10";
			String dateStr2 = "2013-05-10";
			DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
			Date date = null;
			Date date2 = null;
			date = format.parse(dateStr);
			date2 = format.parse(dateStr2);
			SiteData siteData = new SiteData("mySHA1", "mySiteData", date);

			SiteArchive siteArchive = new SiteArchive();
			siteArchive.storeSiteData(siteData);

			SiteData siteDataRet = siteArchive.retrieveSiteData(date2);
			SiteData siteDataRet2 = siteArchive.retrieveSiteData(date2); // should
																			// be
																			// using
																			// cache
																			// for
																			// second
			// retrieval on same date

			assertEquals("mySHA1", siteDataRet2.getSHA1());
			assertEquals("mySiteData", siteDataRet2.getSiteData());

		} catch (Exception e) {
			e.printStackTrace();
			fail("Ran into exception");
		}
	}

	@Test
	public void testDuplicateSHA1() {
		try {
			String dateStr = "2012-05-10";
			String dateStr2 = "2013-05-10";
			DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
			Date date = null;
			Date date2 = null;
			date = format.parse(dateStr);
			date2 = format.parse(dateStr2);
			SiteData siteData = new SiteData("mySHA1", "mySiteData", date);
			SiteData siteData2 = new SiteData("mySHA1", "mySiteData2", date2);

			SiteArchive siteArchive = new SiteArchive();
			siteArchive.storeSiteData(siteData);
			siteArchive.storeSiteData(siteData2);

			SiteData siteDataRet = siteArchive.retrieveSiteData(date2);

			assertEquals(date, siteDataRet.getDateTaken());

		} catch (Exception e) {
			e.printStackTrace();
			fail("Ran into exception");
		}
	}

	@Test
	public void testNullDate() {
		try {

			Date date = null;

			SiteData siteData = new SiteData("mySHA1", "mySiteData", date);

			SiteArchive siteArchive = new SiteArchive();
			siteArchive.storeSiteData(siteData);

			fail("Should have thrown exception: 'NULL Date passed to retrieveSiteData'");

		} catch (Exception e) {
			assertEquals("NULL SiteData passed to storeSiteData", e.getMessage());
		}
	}
}
