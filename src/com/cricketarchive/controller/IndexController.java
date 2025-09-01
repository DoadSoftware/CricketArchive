package com.cricketarchive.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cricketarchive.model.Archive;
import com.cricketarchive.model.Configuration;
import com.cricketarchive.util.CricketFunctions;
import com.cricketarchive.util.CricketUtil;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.cricketarchive.service.CricketService;
import net.sf.json.JSONObject;

@Controller
public class IndexController 
{
	@Autowired
	CricketService cricketService;
	
	public static String expiry_date = "2023-12-31";
	public static String current_date = "";
	public static String error_message = "";
	public static Archive session_archive;
	public static WebDriver driver;
	public static Configuration session_Configurations;
	
	@RequestMapping(value = {"/","/initialise"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String initialisePage(ModelMap model) 
	{
//			session_archive.setMatchAllData(CricketFunctions.getMatchDataFromWebsite(new ChromeDriver(),"GET-SINGLE-MATCH-DATA", CricketUtil.CRIC_INFO, 
//				"https://www.espncricinfo.com/series/west-indies-in-pakistan-2022-1314914/pakistan-vs-west-indies-1st-odi-1315038/full-scorecard"));
		return "initialise";
	}
	
	@RequestMapping(value = {"/cricketarchive"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String cricketArchivePage(ModelMap model,
		@RequestParam(value = "select_broadcaster", required = false, defaultValue = "") String select_broadcaster) 
		throws JAXBException
	{
		session_archive = new Archive();
		
		session_Configurations = new Configuration(select_broadcaster);
		JAXBContext.newInstance(Configuration.class).createMarshaller().marshal(session_Configurations, 
				new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.CONFIGURATIONS_DIRECTORY + CricketUtil.CRIC_ARCHIVE_XML));
		
		model.addAttribute("session_Configurations", session_Configurations);
	
		return "cricketarchive";
	}
	
	@RequestMapping(value = {"/processCricketArchiveProcedures"}, method={RequestMethod.GET,RequestMethod.POST})    
	public @ResponseBody String processCricketArchiveProcedures(
			@ModelAttribute("session_Configurations") Configuration session_Configurations,
			@RequestParam(value = "whatToProcess", required = false, defaultValue = "") String whatToProcess,
			@RequestParam(value = "valueToProcess", required = false, defaultValue = "") String valueToProcess) 
			throws StreamWriteException, DatabindException, JAXBException, IOException, URISyntaxException 
	{	
		switch (whatToProcess) {
		case "GET-ALL-SEASONS":
			
			driver = new FirefoxDriver();
			session_archive.setSeasons(CricketFunctions.getStatsFromWebsite(driver,"GET-ALL-SEASONS",
				session_Configurations.getBroadcaster(), "",null));
			
			return JSONObject.fromObject(session_archive).toString();
			
		case "GET-SEASON-SERIES-DATA":
			
			session_archive.setSeries(CricketFunctions.getStatsFromWebsite(driver,whatToProcess,
				session_Configurations.getBroadcaster(), valueToProcess,null));
			
			return JSONObject.fromObject(session_archive).toString();	
			
		case "GET-SERIES-MATCHES-DATA":
			
			session_archive.setMatches(CricketFunctions.getStatsFromWebsite(driver,
				whatToProcess,session_Configurations.getBroadcaster(), session_archive.getSeries().stream().filter(series -> 
				series.getLabel().equalsIgnoreCase(valueToProcess)).findFirst().orElse(null).getUrl(), cricketService));

			return JSONObject.fromObject(session_archive).toString();	
		
		case "GET-SINGLE-MATCH-DATA":
			
			session_archive.setMatchAllData(CricketFunctions.getMatchDataFromWebsite(driver,"GET-SINGLE-MATCH-DATA", 
				session_Configurations.getBroadcaster(), session_archive.getMatches().stream().filter(match -> 
				match.getLabel().equalsIgnoreCase(valueToProcess)).findFirst().orElse(null).getUrl(), cricketService.getTeams()));
			
			System.out.println("Match = " + session_archive.getMatchAllData().toString());
			
			return JSONObject.fromObject(session_archive).toString();

		default:
			return JSONObject.fromObject(null).toString();	
		}
	}
}