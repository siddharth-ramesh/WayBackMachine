Author: Siddharth Ramesh
Date: October 10, 2015
Application: TripAdvisor - Software Engineer

This document goes over my solution to the given 'Way-Back-Machine' problem.
Written below are the assumptions made, structure of the code, tests needed, time and space complexity, and alternate solution.

______________________________________________________________
Assumptions Made:

- It is assumed that past data for a site can be stored.
	Reasoning: If a way-back machine is created today and it only archives real-time 
	data from a web crawler, it would not have a big archive and would be pretty lame.
	Thus, it should allow storage of past data acquired from 
	other data stores/crawlers, etc.

- Data from any time can be pushed to the storage at any time.
	Reasoning: As a result of the previous assumption, it is assumed that the data stored
	does not follow any order (does not have to be sequential date entries). So for the same site,
	this solution may be asked to store a snapshot of a 1990 page followed by 2010 snapshot 
	followed by 1996 snapshot. This is why I chose TreeSet as my main structure as it keeps an 
	ordered tree of the dates inserted. Multiple times for a day can also be stored.
	
- Assumed that this solution is made to be run easily with basic Java objects as a console application.
	Hence it is not programmed as a Java Servlet to serve a frontend.
	
- It is assumed that the data received in sanitized. Although the url string is checked for validity
	using a regex, it is assumed that the frontend and web crawler pass in appropriate data.
	ie. date data in a specific format (yyyy-MM-dd)
	This being said, null checks are still performed.
	
______________________________________________________________
Structure of Code:
	
- main.AllSitesMap.java: Holds a HashMap of web urls mapped to their respective SiteArchives.
	Allows multiple sites to have archives. Entry function to use Way-Back Machine.
	
- main.SiteArchive.java: Holds a TreeSet of all dates that have data. The reason a TreeMap<Date, SiteData>
 	is not used is because a TreeMap always has a O(logn) retrieval time. Using a TreeSet and a
 	cache (HashMap) optimizes this and provides constant retrieval time for identical queries after the first.
 	A HashMap<Date, SiteData> holds all the data.
 	
 - main.SiteData.java: Basic object that holds the site data retrieved from the web crawler/other storage.
 	Holds the SHA1 as a String, site data as a String, and the date the site was accessed as a Date.
 	
 - main.main.java: Runnable main function quickly put together (looks a bit messy..) to show the
 	way-back machine in action. Takes user inputs to perform gets/posts.
 	
 - tests: Has unit tests for the way-back machine.
 
_______________________________________________________________
Tests needed:
 
 - All functions should do null checks. The functions should always return null in case there was an
 	exception (ie. not pass the exception to the frontend)
 	
 - If a site data for a date is being inserted, it should be checked that the SHA1 does not already exist 
 	in the most recent version of the site. This saves unnecessary replication of data in storage.
 	
 - Valid results should be given for queries (most recent version of site equal to or before query date)

______________________________________________________________
Time and Space Complexity:

- This solution performs data insertions and retrieval in worst-case logn
	Due to the cache implementation, the best case insertion and retrieval is constant time
	
- Storage is n, with a bit of overhead from the cache and TreeSet implementations

_______________________________________________________________
Alternate solution:

- An alternate solution with O(n) insertion and O(1) retrieval time exists. However, for this solution, it must be assumed that 
	data from any *time* cannot be inserted (only 1 a day). This solution has terrible space complexity. 
	
	Explanation: Every time data for a date(newDate) is inserted, all dates in the future that have a date lower than newDate must 
	be replaced with newDate. This is worst case n time. Retrieving a result is simply a constant time lookup.
	
