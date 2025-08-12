package com.cricketarchive.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.cricketarchive.model.BattingCard;
import com.cricketarchive.model.BowlingCard;
import com.cricketarchive.model.FallOfWicket;
import com.cricketarchive.model.Inning;
import com.cricketarchive.model.Match;
import com.cricketarchive.model.MatchAllData;
import com.cricketarchive.model.Player;
import com.cricketarchive.model.Setup;
import com.cricketarchive.model.ArchiveData;
import com.cricketarchive.model.Team;
import com.cricketarchive.service.CricketService;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class CricketFunctions {

	public static ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
	
	public static String getOnlineCurrentDate() throws MalformedURLException, IOException
	{
		HttpURLConnection httpCon = (HttpURLConnection) new URL("https://mail.google.com/").openConnection();
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date(httpCon.getDate()));
	}	
	
	public static MatchAllData getMatchDataFromWebsite(WebDriver driver, String whatToProcess, 
		String broadcaster, String valueToProcess, List<Team> all_teams) 
		throws StreamWriteException, DatabindException, JAXBException, IOException, URISyntaxException 
	{
		List<BattingCard> this_battingcard = new ArrayList<BattingCard>();
		List<FallOfWicket> this_FoWs = new ArrayList<FallOfWicket>();
		List<Inning> this_inn = new ArrayList<Inning>();
		List<BowlingCard> this_bowlingcard = new ArrayList<BowlingCard>();
		Player this_player = new Player();
		WebElement this_webElement;
		int column_data_count = 0,bowling_card_count = 1;
		boolean extras_found = false, total_found = false;
		String data_to_process = "";

		MatchAllData this_match = new MatchAllData();
		this_match.setMatch(new Match());
		this_match.setSetup(new Setup());
		
		if(valueToProcess.toUpperCase().contains("-" + CricketUtil.TEST + "-")) {
			this_match.getSetup().setMatchType(CricketUtil.TEST);
			this_match.getSetup().setMaxOvers(CricketUtil.TEST_MAXIMUM_OVERS);
		} else if(valueToProcess.toUpperCase().contains("-" + CricketUtil.ODI + "-") || valueToProcess.toUpperCase().contains("-" + CricketUtil.OD + "-")) {
			this_match.getSetup().setMatchType(CricketUtil.ODI);
			this_match.getSetup().setMaxOvers(CricketUtil.ODI_MAXIMUM_OVERS);
		} else {
			this_match.getSetup().setMatchType(CricketUtil.IT20);
			this_match.getSetup().setMaxOvers(CricketUtil.T20_MAXIMUM_OVERS);
		}
		System.out.println(valueToProcess);
		this_match.getSetup().setSaveMatchFileAs("JSON");
		this_match.getSetup().setMatchIdent(valueToProcess.split("/")[valueToProcess.split("/").length-2]);
		this_match.getSetup().setTournament(valueToProcess.split("/")[valueToProcess.split("/").length-2]);
		switch (broadcaster.toUpperCase()) {
		case CricketUtil.CRIC_INFO:
			
			switch (whatToProcess) {
			case "GET-SINGLE-MATCH-DATA":
				
				driver.get(valueToProcess);
				
				for (WebElement this_team : driver.findElements(By.className("ci-team-score"))) {
					if(this_match.getMatch().getMatchResult() == null) {
						this_webElement = this_team.findElement(By.xpath("../../.."));
						System.out.println("this_webElement tag name = " + this_webElement.getTagName());
						System.out.println("this_webElement p/span is empty = " + this_webElement.findElements(By.xpath("./p/span")).isEmpty());
						if(!this_webElement.findElements(By.xpath("./p/span")).isEmpty()) {
							System.out.println("Result = " + this_webElement.findElement(By.xpath("./p/span")).getText());
							this_match.getMatch().setMatchResult(this_webElement.findElement(By.xpath("./p/span")).getText());
						}
					}
					//column_data_count = 0;
					for (WebElement team_anchor : this_team.findElements(By.tagName("a"))) {
						if(team_anchor.getAttribute("href").contains("/team/")
							&& team_anchor.getAttribute("href").contains("-")) {
							System.out.println("team_anchor href = " + team_anchor.getAttribute("href"));
							data_to_process = team_anchor.getAttribute("href").split("/")[team_anchor.getAttribute("href").split("/").length-1];
							System.out.println("team name = " + team_anchor.findElement(By.tagName("span")).getText());
							System.out.println("team ID = " + data_to_process.split("-")[data_to_process.split("-").length-1]);
							for (Team team : all_teams) {
								if(team_anchor.findElement(By.tagName("span")).getText().toLowerCase().contains(team.getTeamName1().toLowerCase())
										|| team_anchor.findElement(By.tagName("span")).getText().toLowerCase().contains(team.getTeamName4().toLowerCase())) {
									
									column_data_count = column_data_count + 1;
									
									switch (column_data_count) {
									case 1:
										System.out.println("column_data_count1 = " + column_data_count);
										System.out.println(team_anchor.findElement(By.tagName("span")).getText().toLowerCase());
										System.out.println("teams = " + team.getTeamName4().toLowerCase());
										this_match.getSetup().setHomeTeam(team);
										this_match.getSetup().getHomeTeam().setTeamId(Integer.valueOf(data_to_process.split("-")[data_to_process.split("-").length-1]));
										this_match.getSetup().setHomeTeamId(Integer.valueOf(data_to_process.split("-")[data_to_process.split("-").length-1]));
										break;
									case 2:
										System.out.println("column_data_count2 = " + column_data_count);
										System.out.println(team_anchor.findElement(By.tagName("span")).getText().toLowerCase());
										System.out.println("teams = " + team.getTeamName4().toLowerCase());
										this_match.getSetup().setAwayTeam(team);
										this_match.getSetup().getAwayTeam().setTeamId(Integer.valueOf(data_to_process.split("-")[data_to_process.split("-").length-1]));
										this_match.getSetup().setAwayTeamId(Integer.valueOf(data_to_process.split("-")[data_to_process.split("-").length-1]));
										break;
									}
									break;
								}
							}
						}
						if(column_data_count > 2) {
							break;
						}
					}
				}
				
				for (WebElement batting_card_table : driver.findElements(By.className("ci-scorecard-table"))) {

					this_inn.add(new Inning()); // Add new inning when batting card is detected
					this_inn.get(this_inn.size()-1).setInningNumber(this_inn.size());
					this_battingcard = new ArrayList<BattingCard>();
					
					for(WebElement row : batting_card_table.findElements(By.xpath("./tbody/tr")))
					{
						column_data_count = 0;
						for(WebElement column : row.findElements(By.tagName("td")))
						{
							System.out.println("column.getText() = " + column.getText());
							
							if(column.getText().equalsIgnoreCase(CricketUtil.EXTRAS)) {
								extras_found = true;
							} else if(column.getText().equalsIgnoreCase(CricketUtil.TOTAL)) {
								total_found = true;
							}
							if(extras_found == true) {
								
								if(!column.getText().isEmpty() && column.getText().contains("(")
									&& column.getText().contains(")")) {
									
									data_to_process = column.getText().replace("(", "");
									data_to_process = data_to_process.replace(")", "");
									System.out.println("Extra data_to_process = " + data_to_process);
									if(data_to_process.contains(",")) {
										for (String ext : data_to_process.split(",")) {
											if(ext.toUpperCase().contains("LB")) {
												this_inn.get(this_inn.size()-1).setTotalLegByes(
													Integer.valueOf(ext.toUpperCase().replace("LB", "").trim()));
											}else if(ext.toUpperCase().contains("NB")) {
												this_inn.get(this_inn.size()-1).setTotalNoBalls(
													Integer.valueOf(ext.toUpperCase().replace("NB", "").trim()));
											}else if(ext.toUpperCase().contains("B")) {
												this_inn.get(this_inn.size()-1).setTotalByes(
													Integer.valueOf(ext.toUpperCase().replace("B", "").trim()));
											}else if(ext.toUpperCase().contains("W")) {
												this_inn.get(this_inn.size()-1).setTotalWides(
													Integer.valueOf(ext.toUpperCase().replace("W", "").trim()));
											}
										}
									} else {
										if(data_to_process.toUpperCase().contains("LB")) {
											this_inn.get(this_inn.size()-1).setTotalLegByes(
												Integer.valueOf(data_to_process.toUpperCase().replace("LB", "").trim()));
										}else if(data_to_process.toUpperCase().contains("NB")) {
											this_inn.get(this_inn.size()-1).setTotalNoBalls(
												Integer.valueOf(data_to_process.toUpperCase().replace("NB", "").trim()));
										}else if(data_to_process.toUpperCase().contains("B")) {
											this_inn.get(this_inn.size()-1).setTotalByes(
												Integer.valueOf(data_to_process.toUpperCase().replace("B", "").trim()));
										}else if(data_to_process.toUpperCase().contains("W")) {
											this_inn.get(this_inn.size()-1).setTotalWides(
												Integer.valueOf(data_to_process.toUpperCase().replace("W", "").trim()));
										}
									}
									
								} else if(!column.findElements(By.tagName("strong")).isEmpty()) {
									
									this_inn.get(this_inn.size()-1).setTotalExtras(
										Integer.valueOf(column.getText()));
									
									extras_found = false;
									
								} 

								
							} else if(total_found == true) {
								
								if(!column.findElements(By.tagName("span")).isEmpty()) {
									for (WebElement this_span : column.findElements(By.tagName("span"))) {
										System.out.println("this_span = " + this_span);
										System.out.println("TOTAL OVERS = " + this_span.getText());
										if(this_span.getText().toUpperCase().contains(" OV") && !this_span.getText().toUpperCase().contains(",")) {
											data_to_process = this_span.getText().toUpperCase().replace("OV", "").trim();
											System.out.println("Overs = " + data_to_process);
											if(data_to_process.contains(".") && !data_to_process.contains(",")) {
												System.out.println("Over no = " + data_to_process.substring(0,data_to_process.indexOf(".")));
												System.out.println("Ball no = " + data_to_process.substring(data_to_process.indexOf(".")+1));
												this_inn.get(this_inn.size()-1).setTotalOvers(
													Integer.valueOf(data_to_process.substring(0,data_to_process.indexOf("."))));
												this_inn.get(this_inn.size()-1).setTotalBalls(
														Integer.valueOf(data_to_process.substring(data_to_process.indexOf(".")+1)));
											}else if(!data_to_process.contains(",")) {
												System.out.println("50Over no = " + data_to_process);
												this_inn.get(this_inn.size()-1).setTotalOvers(
													Integer.valueOf(data_to_process));
												this_inn.get(this_inn.size()-1).setTotalBalls(0);
											}
										} else {
											if(this_span.getText().toUpperCase().contains("(RR:")) {
												data_to_process = this_span.getText().toUpperCase().replace("(RR:", "");
												data_to_process = data_to_process.replace(")", "").trim();
												this_inn.get(this_inn.size()-1).setRunRate(data_to_process);
											}
										}
									}
								} else {
									System.out.println("TOTAL = " + column.getText());
									data_to_process = column.getText().trim();
									if(data_to_process.contains("/")) {
										System.out.println("Total Score = " + data_to_process);
										System.out.println("Runs = " + data_to_process.substring(0,data_to_process.indexOf("/")));
										System.out.println("Wickets = " + data_to_process.substring(data_to_process.indexOf("/")+1));
										this_inn.get(this_inn.size()-1).setTotalRuns(
											Integer.valueOf(data_to_process.substring(0,data_to_process.indexOf("/"))));
										this_inn.get(this_inn.size()-1).setTotalWickets(
											Integer.valueOf(data_to_process.substring(data_to_process.indexOf("/")+1)));
										total_found = false;
									} else {
										if(NumberUtils.isCreatable(data_to_process)) {
											System.out.println("Total Runs without / = " + data_to_process);
											System.out.println("Total Wickets = " + this_battingcard.size());
											this_inn.get(this_inn.size()-1).setTotalRuns(Integer.valueOf(data_to_process));
											this_inn.get(this_inn.size()-1).setTotalWickets(this_battingcard.size()-1);
											total_found = false;
										}
									}
								}
								
							} else { // Batting card data
								
								if(column.getText().toUpperCase().contains("FALL OF WICKETS:")) {
									
									data_to_process = column.getText().toUpperCase().replace("FALL OF WICKETS:", "").trim();
									System.out.println("FoW -> data_to_process = " + data_to_process);
									for(int i=0; i < data_to_process.split(",").length; i++) {
										
										for (BattingCard bc : this_battingcard) {

											if(bc.getPlayer() != null && bc.getPlayer().getFull_name() != null
												&& bc.getPlayer().getFull_name().equalsIgnoreCase(data_to_process.split(",")[i].substring(
														data_to_process.split(",")[i].indexOf("(") + 1))) {
												
												this_FoWs.add(new FallOfWicket(Integer.valueOf(data_to_process.split(",")[i].substring(0, 
													data_to_process.split(",")[i].indexOf("-")).trim()), 
													bc.getPlayer().getPlayerId(), Integer.valueOf(data_to_process.split(",")[i].substring(
													data_to_process.split(",")[i].indexOf("-") + 1, data_to_process.split(",")[i].indexOf("(") 
													- data_to_process.split(",")[i].indexOf("-") + 1).trim()), 
													Integer.valueOf(data_to_process.split(",")[i+1].toUpperCase()
													.replace("OV)", "").substring(0,data_to_process.split(",")[i+1].indexOf(".")).trim()), 
													Integer.valueOf(data_to_process.split(",")[i+1].toUpperCase()
													.replace("OV)", "").substring(data_to_process.split(",")[i+1].indexOf(".")+1).trim()), ""));
												i++;
												System.out.println("FoW Variable = " + this_FoWs.get(this_FoWs.size()-1).toString());
												break;
											}
										}										
										
									}
								} else if(column.getText().toUpperCase().contains("DID NOT BAT:")) {
									for (WebElement did_not_bat_anchor : column.findElements(By.tagName("a"))) {
										if(did_not_bat_anchor.getAttribute("href").contains("/cricketers/")
												&& did_not_bat_anchor.getAttribute("href").contains("-")) {
											System.out.println("did_not_bat_anchor = " + did_not_bat_anchor.getAttribute("href"));
											System.out.println("did_not_bat_anchor span->span = " + did_not_bat_anchor.findElement(
												By.xpath("./span/span")).getText());
											this_player = new Player();
											this_player.setPlayerId(Integer.valueOf(did_not_bat_anchor.getAttribute("href").split("-")[
											    did_not_bat_anchor.getAttribute("href").split("-").length-1]));
											this_player.setFull_name(did_not_bat_anchor.findElement(
												By.xpath("./span/span")).getText().replace(",", "").trim());
											if(did_not_bat_anchor.findElement(
													By.xpath("./span/span")).getText().replace(",", "").trim().contains(" ")) {
												this_player.setFirstname(did_not_bat_anchor.findElement(
														By.xpath("./span/span")).getText().replace(",", "").trim().split(" ")[0]);
												this_player.setSurname(did_not_bat_anchor.findElement(
														By.xpath("./span/span")).getText().replace(",", "").trim().split(" ")[1]);
												this_player.setTicker_name(did_not_bat_anchor.findElement(
														By.xpath("./span/span")).getText().replace(",", "").trim().split(" ")[1]);
											}else {
												this_player.setFirstname(did_not_bat_anchor.findElement(
														By.xpath("./span/span")).getText().replace(",", "").trim());
												this_player.setSurname(did_not_bat_anchor.findElement(
														By.xpath("./span/span")).getText().replace(",", "").trim());
												this_player.setTicker_name(did_not_bat_anchor.findElement(
														By.xpath("./span/span")).getText().replace(",", "").trim());
											}
											
											this_battingcard.add(new BattingCard(this_player.getPlayerId(), 
												this_battingcard.size() + 1,CricketUtil.STILL_TO_BAT));
											this_battingcard.get(this_battingcard.size()-1).setPlayer(this_player);
										}
									}
								} else {
									if(!column.findElements(By.tagName("a")).isEmpty()) {
										if(column.findElement(By.tagName("a")).getAttribute("href").contains("/cricketers/")
											&& column.findElement(By.tagName("a")).getAttribute("href").contains("-")) {
										
											System.out.println("Bat anchor HREF = " + column.findElement(
													By.tagName("a")).getAttribute("href"));
											System.out.println("Bat player full name = " + column.findElement(By.tagName("a")).
												findElement(By.tagName("span")).getText());
											this_battingcard.add(new BattingCard(Integer.valueOf(column.findElement(
												By.tagName("a")).getAttribute("href").split("-")[
												column.findElement(By.tagName("a")).getAttribute("href").split("-").length-1]), 
												this_battingcard.size() + 1,CricketUtil.STILL_TO_BAT));
											this_player = new Player();
											this_player.setPlayerId(Integer.valueOf(column.findElement(
												By.tagName("a")).getAttribute("href").split("-")[
												column.findElement(By.tagName("a")).getAttribute("href").split("-").length-1]));
											this_player.setFull_name(column.findElement(
													By.tagName("a")).getAttribute("title"));
											if(column.findElement(
													By.tagName("a")).getAttribute("title").contains(" ")) {
												this_player.setFirstname(column.findElement(
														By.tagName("a")).getAttribute("title").split(" ")[0]);
												this_player.setSurname(column.findElement(
														By.tagName("a")).getAttribute("title").split(" ")[1]);
												this_player.setTicker_name(column.findElement(
														By.tagName("a")).getAttribute("title").split(" ")[1]);
											}else {
												this_player.setFirstname(column.findElement(
														By.tagName("a")).getAttribute("title"));
												this_player.setSurname(column.findElement(
														By.tagName("a")).getAttribute("title"));
												this_player.setTicker_name(column.findElement(
														By.tagName("a")).getAttribute("title"));
											}
											
											this_battingcard.get(this_battingcard.size()-1).setPlayer(this_player);

											column_data_count = 0;
											
										}
										
									} else if(!column.findElements(By.xpath("./span/span")).isEmpty()) {

										data_to_process = column.findElement(By.tagName("span")).findElement(
												By.tagName("span")).getText().replaceAll("[^a-zA-Z0-9\\s\\/]", "");
										
										if(data_to_process.equalsIgnoreCase("not out")) {
											this_battingcard.get(this_battingcard.size()-1).setStatus(CricketUtil.NOT_OUT);
										} else if(!data_to_process.isEmpty()) {

											this_battingcard.get(this_battingcard.size()-1).setStatus(CricketUtil.OUT);
											
											this_player = new Player();
											
											if(data_to_process.toLowerCase().contains("c ") && data_to_process.toLowerCase().contains(" b ") && 
												!data_to_process.toLowerCase().contains("c & b")) {
												this_battingcard.get(this_battingcard.size()-1).setHowOut(CricketUtil.CAUGHT);
												if(data_to_process.toLowerCase().contains(" b ")) {
													this_battingcard.get(this_battingcard.size()-1).setHowOutPartOne(data_to_process.toLowerCase().split(" b ")[0]);
													this_battingcard.get(this_battingcard.size()-1).setHowOutPartTwo("b " + data_to_process.toLowerCase().split(" b ")[1]);
													this_player.setFull_name(data_to_process.toLowerCase().split(" b ")[1]);
													if(data_to_process.toLowerCase().split(" b ")[1].contains(" ")) {
														this_player.setFirstname(data_to_process.toLowerCase().split(" b ")[1].split(" ")[0]);
														this_player.setSurname(data_to_process.toLowerCase().split(" b ")[1].split(" ")[1]);
														this_player.setTicker_name(data_to_process.toLowerCase().split(" b ")[1].split(" ")[1]);
													}else {
														this_player.setFirstname(data_to_process.toLowerCase().split(" b ")[1]);
														this_player.setSurname(data_to_process.toLowerCase().split(" b ")[1]);
														this_player.setTicker_name(data_to_process.toLowerCase().split(" b ")[1]);
													}
												}
												this_battingcard.get(this_battingcard.size()-1).setHowOutText(data_to_process);
												this_battingcard.get(this_battingcard.size()-1).setHowOutBowler(this_player);
												
											}else if(data_to_process.toLowerCase().contains("c & b")) {
												this_battingcard.get(this_battingcard.size()-1).setHowOut(CricketUtil.CAUGHT_AND_BOWLED);
												this_battingcard.get(this_battingcard.size()-1).setHowOutPartOne("c & b");
												this_battingcard.get(this_battingcard.size()-1).setHowOutPartTwo(data_to_process.split("c & b ")[1]);
												this_battingcard.get(this_battingcard.size()-1).setHowOutText(data_to_process);
												this_player.setFull_name(data_to_process.split("c & b ")[1]);
												if(data_to_process.toLowerCase().split("c & b ")[1].contains(" ")) {
													this_player.setFirstname(data_to_process.toLowerCase().split("c & b ")[1].split(" ")[0]);
													this_player.setSurname(data_to_process.toLowerCase().split("c & b ")[1].split(" ")[1]);
													this_player.setTicker_name(data_to_process.toLowerCase().split("c & b ")[1].split(" ")[1]);
												}else {
													this_player.setFirstname(data_to_process.toLowerCase().split("c & b ")[1]);
													this_player.setSurname(data_to_process.toLowerCase().split("c & b ")[1]);
													this_player.setTicker_name(data_to_process.toLowerCase().split("c & b ")[1]);
												}
												this_battingcard.get(this_battingcard.size()-1).setHowOutBowler(this_player);
												
											}else if(data_to_process.toLowerCase().contains("b ")) {
												this_player.setFull_name(data_to_process.split("b ")[1]);
												if(data_to_process.toLowerCase().split("b ")[1].contains(" ")) {
													this_player.setFirstname(data_to_process.toLowerCase().split("b ")[1].split(" ")[0]);
													this_player.setSurname(data_to_process.toLowerCase().split("b ")[1].split(" ")[1]);
													this_player.setTicker_name(data_to_process.toLowerCase().split("b ")[1].split(" ")[1]);
												}else {
													this_player.setFirstname(data_to_process.toLowerCase().split("b ")[1]);
													this_player.setSurname(data_to_process.toLowerCase().split("b ")[1]);
													this_player.setTicker_name(data_to_process.toLowerCase().split("b ")[1]);
												}
												this_battingcard.get(this_battingcard.size()-1).setHowOutBowler(this_player);
												this_battingcard.get(this_battingcard.size()-1).setHowOutText("b " + this_battingcard.get(this_battingcard.size()-1).getHowOutBowler().getTicker_name());
												this_battingcard.get(this_battingcard.size()-1).setHowOut(CricketUtil.BOWLED);
												this_battingcard.get(this_battingcard.size()-1).setHowOutPartOne("");
												this_battingcard.get(this_battingcard.size()-1).setHowOutPartTwo("b " + this_battingcard.get(this_battingcard.size()-1).getHowOutBowler().getTicker_name());
												
											}else if(data_to_process.toLowerCase().contains("lbw b ")) { // lbw
												
												this_player.setFull_name(data_to_process.split("lbw b ")[1]);
												if(data_to_process.toLowerCase().split("lbw b ")[1].contains(" ")) {
													this_player.setFirstname(data_to_process.toLowerCase().split("lbw b ")[1].split(" ")[0]);
													this_player.setSurname(data_to_process.toLowerCase().split("lbw b ")[1].split(" ")[1]);
													this_player.setTicker_name(data_to_process.toLowerCase().split("lbw b ")[1].split(" ")[1]);
												}else {
													this_player.setFirstname(data_to_process.toLowerCase().split("lbw b ")[1]);
													this_player.setSurname(data_to_process.toLowerCase().split("lbw b ")[1]);
													this_player.setTicker_name(data_to_process.toLowerCase().split("lbw b ")[1]);
												}
												this_battingcard.get(this_battingcard.size()-1).setHowOutBowler(this_player);
												this_battingcard.get(this_battingcard.size()-1).setHowOutText(data_to_process);
												this_battingcard.get(this_battingcard.size()-1).setHowOut(CricketUtil.LBW);
												this_battingcard.get(this_battingcard.size()-1).setHowOutPartOne(CricketUtil.LBW);
												this_battingcard.get(this_battingcard.size()-1).setHowOutPartTwo("b " + this_battingcard.get(this_battingcard.size()-1).getHowOutBowler().getTicker_name());
												
											}else if(data_to_process.toLowerCase().contains("st ") && data_to_process.toLowerCase().contains(" b ")) {
												
												this_player.setFull_name(data_to_process.split(" b ")[1]);
												if(data_to_process.toLowerCase().split(" b ")[1].contains(" ")) {
													this_player.setFirstname(data_to_process.toLowerCase().split(" b ")[1].split(" ")[0]);
													this_player.setSurname(data_to_process.toLowerCase().split(" b ")[1].split(" ")[1]);
													this_player.setTicker_name(data_to_process.toLowerCase().split(" b ")[1].split(" ")[1]);
												}else {
													this_player.setFirstname(data_to_process.toLowerCase().split(" b ")[1]);
													this_player.setSurname(data_to_process.toLowerCase().split(" b ")[1]);
													this_player.setTicker_name(data_to_process.toLowerCase().split(" b ")[1]);
												}
												this_battingcard.get(this_battingcard.size()-1).setHowOutBowler(this_player);
												this_battingcard.get(this_battingcard.size()-1).setHowOutText(data_to_process);
												this_battingcard.get(this_battingcard.size()-1).setHowOut(CricketUtil.STUMPED);
												this_battingcard.get(this_battingcard.size()-1).setHowOutPartOne(data_to_process.split(" b ")[0]);
												this_battingcard.get(this_battingcard.size()-1).setHowOutPartTwo("b " + this_battingcard.get(this_battingcard.size()-1).getHowOutBowler().getTicker_name());
												
											}else if(data_to_process.toLowerCase().contains("run out")) {
												
												data_to_process = data_to_process.toLowerCase().split("run out ")[1]
													.replace("(", "").replace(")", ""); 
												this_player.setFirstname("");
												if(data_to_process.toLowerCase().contains("/")) {
													this_player.setFull_name(data_to_process.split("/")[1]);
													this_player.setSurname(data_to_process.split("/")[1]);
													this_player.setTicker_name(data_to_process.split("/")[1]);
												} else {
													this_player.setFull_name(data_to_process);
													this_player.setSurname(data_to_process);
													this_player.setTicker_name(data_to_process);
												}
												this_battingcard.get(this_battingcard.size()-1).setHowOutBowler(this_player);
												this_battingcard.get(this_battingcard.size()-1).setHowOutText(data_to_process);
												this_battingcard.get(this_battingcard.size()-1).setHowOut(CricketUtil.RUN_OUT);
												this_battingcard.get(this_battingcard.size()-1).setHowOutPartOne(CricketUtil.RUN_OUT.replace("_", " "));
												this_battingcard.get(this_battingcard.size()-1).setHowOutPartTwo(data_to_process);
											}
										} 
										
									} else if(!column.findElements(By.tagName("strong")).isEmpty()) {
										
										System.out.println("Bat Runs = " + column.findElement(By.tagName("strong")).getText());
										this_battingcard.get(this_battingcard.size()-1).setRuns(Integer.valueOf(
											column.findElement(By.tagName("strong")).getText()));

									} else {
										
										if(this_inn.get(this_inn.size()-1).getTotalRuns() <= 0 && this_inn.get(this_inn.size()-1).getTotalWickets() <= 0 &&
											this_inn.get(this_inn.size()-1).getTotalOvers() <= 0 && this_inn.get(this_inn.size()-1).getTotalBalls() <= 0) {
											
											if(column.getText().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
												this_battingcard.get(this_battingcard.size()-1).setStatus(CricketUtil.NOT_OUT);
											}else {
												column_data_count++;
												data_to_process = column.getText().trim();
												if(data_to_process.isEmpty()) {
													if(column_data_count == 5) {
														data_to_process = "0.0";
													} else {
														data_to_process = "0";
													}
												}
												System.out.println("Bat column_data_count = " + column_data_count + " -> " + data_to_process);
												switch (column_data_count) {
												case 1:
													this_battingcard.get(this_battingcard.size()-1).setBalls(
														Integer.valueOf(data_to_process));
													break;
												case 2: // Minutes
													break;
												case 3:
													this_battingcard.get(this_battingcard.size()-1).setFours(
														Integer.valueOf(data_to_process));
													break;
												case 4:
													this_battingcard.get(this_battingcard.size()-1).setSixes(
														Integer.valueOf(data_to_process));
													break;
												case 5:
													this_battingcard.get(this_battingcard.size()-1).setStrikeRate(data_to_process);
													break;
												}
											}
											
										}
									}
								}
							}
						}
					}
					switch (this_inn.size()) {
					case 1:
						if(this_match.getSetup().getHomeTeam() != null) {
							this_inn.get(this_inn.size() - 1).setBatting_team(this_match.getSetup().getHomeTeam());
							this_inn.get(this_inn.size() - 1).setBattingTeamId(this_match.getSetup().getHomeTeamId());
							this_inn.get(this_inn.size() - 1).setBowling_team(this_match.getSetup().getAwayTeam());
							this_inn.get(this_inn.size() - 1).setBowlingTeamId(this_match.getSetup().getAwayTeamId());
						}
						break;
					case 2:
						if(this_match.getSetup().getAwayTeam() != null) {
							this_inn.get(this_inn.size() - 1).setBatting_team(this_match.getSetup().getAwayTeam());
							this_inn.get(this_inn.size() - 1).setBattingTeamId(this_match.getSetup().getAwayTeamId());
							this_inn.get(this_inn.size() - 1).setBowling_team(this_match.getSetup().getHomeTeam());
							this_inn.get(this_inn.size() - 1).setBowlingTeamId(this_match.getSetup().getHomeTeamId());
						}
						break;
					}
					for (BattingCard bc : this_battingcard) {
						this_inn.get(this_inn.size() - 1).setTotalFours(this_inn.get(this_inn.size() - 1).getTotalFours() + bc.getFours());
						this_inn.get(this_inn.size() - 1).setTotalSixes(this_inn.get(this_inn.size() - 1).getTotalSixes() + bc.getSixes());
					}
					this_inn.get(this_inn.size() - 1).setBattingCard(this_battingcard);
					this_inn.get(this_inn.size() - 1).setFallsOfWickets(this_FoWs);
				}
				
				List<Player> home_squad = new ArrayList<Player>();
				List<Player> away_squad = new ArrayList<Player>();
				for(int i=0; i<this_inn.size(); i++) {
					this_inn.get(i).setInningStatus(CricketUtil.PAUSE);
					if(i == this_inn.size()-1) {
						this_inn.get(i).setIsCurrentInning(CricketUtil.YES);
					} else {
						this_inn.get(i).setIsCurrentInning(CricketUtil.NO);
					}
					switch (i) {
					case 0:
						home_squad = new ArrayList<Player>();
						for (BattingCard bc : this_inn.get(i).getBattingCard()) {
							home_squad.add(bc.getPlayer());
						}
						if(this_inn.size() > 1) {
							away_squad = new ArrayList<Player>();
							for (BattingCard bc : this_inn.get(i+1).getBattingCard()) {
								away_squad.add(bc.getPlayer());
							}
						}
						break;
					}
				}
				if(home_squad.size() > 0) {
					this_match.getSetup().setHomeSquad(home_squad);
				}
				if(away_squad.size() > 0) {
					this_match.getSetup().setAwaySquad(away_squad);
				}
				for (WebElement bowling_card_table : driver.findElements(By.tagName("table"))) {
					
					data_to_process = "";
					System.out.println("Table head:row:th found = " + bowling_card_table.findElements(By.xpath("./thead/tr/th")).size());
					if(bowling_card_table.findElements(By.xpath("./thead/tr/th")).size() > 0) {
						for (WebElement table_header : bowling_card_table.findElements(By.xpath("./thead/tr/th"))) {
							System.out.println("table_header.getText() = " + table_header.getText());
							data_to_process = table_header.getText().toUpperCase().trim();
							break;
						}
					}
					if(data_to_process.equalsIgnoreCase("BOWLING")) {
						
						this_bowlingcard = new ArrayList<BowlingCard>();
						
						for(WebElement row : bowling_card_table.findElements(By.xpath("./tbody/tr")))
						{
							System.out.println("row.getAttribute(class) = " + row.getAttribute("class"));
							if(!row.getAttribute("class").toLowerCase().contains("ds-hidden")) {
								column_data_count = 0;
								for(WebElement column : row.findElements(By.tagName("td")))
								{
									if(!column.findElements(By.tagName("a")).isEmpty())  
									{
										if(column.findElement(By.tagName("a")).getAttribute("href").contains("/cricketers/")
											&& column.findElement(By.tagName("a")).getAttribute("href").contains("-")) {
											
											System.out.println("Bowling anchor HREF = " + column.findElement(By.tagName("a")).getAttribute("href"));
											System.out.println("Bowling player full name = " + column.findElement(By.tagName("a")).findElement(By.tagName("span")).getText());
											this_bowlingcard.add(new BowlingCard(Integer.valueOf(column.findElement(
													By.tagName("a")).getAttribute("href").split("-")[
													column.findElement(By.tagName("a")).getAttribute("href").split("-").length-1]), 
													this_bowlingcard.size() + 1, CricketUtil.LAST, 0));
											
											this_player = new Player();
											this_player.setPlayerId(Integer.valueOf(column.findElement(
													By.tagName("a")).getAttribute("href").split("-")[
													column.findElement(By.tagName("a")).getAttribute("href").split("-").length-1]));
											this_player.setFull_name(column.findElement(By.tagName("a")).findElement(By.tagName("span")).getText());
											if(column.findElement(By.tagName("a")).findElement(By.tagName("span")).getText().contains(" ")) {
												this_player.setFirstname(column.findElement(By.tagName("a")).findElement(By.tagName("span")).getText().split(" ")[0]);
												this_player.setSurname(column.findElement(By.tagName("a")).findElement(By.tagName("span")).getText().split(" ")[1]);
												this_player.setTicker_name(column.findElement(By.tagName("a")).findElement(By.tagName("span")).getText().split(" ")[1]);
											}else {
												this_player.setFirstname(column.findElement(By.tagName("a")).findElement(By.tagName("span")).getText());
												this_player.setSurname(column.findElement(By.tagName("a")).findElement(By.tagName("span")).getText());
												this_player.setTicker_name(column.findElement(By.tagName("a")).findElement(By.tagName("span")).getText());
											}
											
											this_bowlingcard.get(this_bowlingcard.size()-1).setPlayer(this_player);
											
											column_data_count = 0;
											
										}
									} else {
										
										column_data_count++;
										data_to_process = column.getText().trim();
										if(data_to_process.isEmpty()) {
											if(column_data_count == 5) {
												data_to_process = "0.0";
											} else {
												data_to_process = "0";
											}
										}
										System.out.println("Bowl column_data_count = " + column_data_count + " -> " + data_to_process);
										switch (column_data_count) {
										case 1:
											if(column.getText().contains(".")) {
												this_bowlingcard.get(this_bowlingcard.size()-1).setOvers(
													Integer.valueOf(data_to_process.substring(0,data_to_process.indexOf("."))));
												this_bowlingcard.get(this_bowlingcard.size()-1).setBalls((
													Integer.valueOf(data_to_process.substring(data_to_process.indexOf(".")+1))));
											} else {
												this_bowlingcard.get(this_bowlingcard.size()-1).setOvers(Integer.valueOf(data_to_process));
											}
											break;
										case 2: 
											this_bowlingcard.get(this_bowlingcard.size()-1).setMaidens(Integer.valueOf(data_to_process));
											break;
										case 3:
											this_bowlingcard.get(this_bowlingcard.size()-1).setRuns(Integer.valueOf(data_to_process));
											break;
										case 4:
											this_bowlingcard.get(this_bowlingcard.size()-1).setWickets(Integer.valueOf(data_to_process));
											break;
										case 5:
											this_bowlingcard.get(this_bowlingcard.size()-1).setEconomyRate(data_to_process);
											break;
										case 6:
											this_bowlingcard.get(this_bowlingcard.size()-1).setDots(Integer.valueOf(data_to_process));
											break;
										case 9:
											this_bowlingcard.get(this_bowlingcard.size()-1).setWides(Integer.valueOf(data_to_process));
											break;
										case 10:
											this_bowlingcard.get(this_bowlingcard.size()-1).setNoBalls(Integer.valueOf(data_to_process));
											break;	
										}
									}
								}
							}
						}
						
						System.out.println("Bowling card " + this_bowlingcard.toString());
						System.out.println("Inn Size " + this_inn.size());
						this_inn.get(bowling_card_count - 1).setBowlingCard(this_bowlingcard);
						bowling_card_count = bowling_card_count + 1;
						
					} else if (data_to_process.equalsIgnoreCase("PLAYER NAME") || data_to_process.equalsIgnoreCase("TEAM")) {
						break;
					}
				}
				this_match.getMatch().setInning(this_inn);
				this_match.getMatch().setMatchFileName(valueToProcess.split("/")[valueToProcess.split("/").length-2] + "." + CricketUtil.JSON);
				this_match.getMatch().setMatchStatus("");
				break;
			}
			break;
		}
		readOrSaveMatchFile("WRITE", CricketUtil.SETUP, this_match);
		readOrSaveMatchFile("WRITE", CricketUtil.MATCH, this_match);
		
		return this_match;
	}
	public static List<ArchiveData> getStatsFromWebsite(WebDriver driver, String whatToProcess, 
			String broadcaster, String valueToProcess, CricketService cricketService)
	{
		List<ArchiveData> all_stats = new ArrayList<ArchiveData>();
		String this_url = "",this_team_id ="";
		List<String> this_teams = new ArrayList<String>();
		int teams_found_count = 0;
		
		switch (broadcaster.toUpperCase()) {
		case CricketUtil.CRIC_INFO:
			
			switch (whatToProcess) {
			case "GET-SERIES-MATCHES-DATA":
				
				driver.get(valueToProcess);
				System.out.println("valueToProcess = " + valueToProcess);
				System.out.println("this_team_id = " + this_team_id);
				this_team_id = valueToProcess.split("/")[valueToProcess.split("/").length-2];
				System.out.println("1st this_team_id = " + this_team_id);
				this_team_id = this_team_id.split("-")[this_team_id.split("-").length-1];
				System.out.println("2nd this_team_id = " + this_team_id);
				
				for (Team team : cricketService.getTeams()) {
					System.out.println("team = " + team.getTeamName1());
					if(valueToProcess.toLowerCase().contains(
							team.getTeamName1().replace(" ", "-").toLowerCase())) {
						this_teams.add(team.getTeamName1().replace(" ", "-").toLowerCase());
						System.out.println("this_teams = " + this_teams.get(this_teams.size()-1));
					}
				}
				for(WebElement anchor : driver.findElements(By.tagName("a")))
				{
					teams_found_count = 0;
					System.out.println("anchor.getAttribute(href) = " + anchor.getAttribute("href"));
					if(anchor.getAttribute("href").toLowerCase().contains("/full-scorecard")
						|| anchor.getAttribute("href").toLowerCase().contains("/live-cricket-score")) {
						
						if(this_teams.size() > 0) {
							for (String team_str : this_teams) {
								if(anchor.getAttribute("href").toLowerCase().contains(team_str.toLowerCase())) {
									teams_found_count++;
								}
							}
						} else if(anchor.getAttribute("href").toLowerCase().contains("/series/")
							&& anchor.getAttribute("href").toLowerCase().contains(this_team_id + "/")) {
							teams_found_count = 2;
						}
						System.out.println("teams_found_count = " + teams_found_count);
						if(teams_found_count == 2) {
							this_url = anchor.getAttribute("href").split("/")[anchor.getAttribute("href").split("/").length - 2];
							System.out.println("this_url = " + this_url);
							if(this_url.contains("-")) {
								System.out.println("match id = " + Long.valueOf(this_url.split("-")[this_url.split("-").length-1]));
								all_stats.add(new ArchiveData(Long.valueOf(this_url.split("-")[this_url.split("-").length-1]), 
									this_url, anchor.getAttribute("href")));
							}
						}
					}
				}
				break;
				
			case "GET-SEASON-SERIES-DATA":
				
				driver.get("https://www.espncricinfo.com/ci/engine/series/index.html?season=" 
					+ valueToProcess + ";view=season");
				for(WebElement section : driver.findElements(By.className("series-summary-block")))
				{
					all_stats.add(new ArchiveData(Long.valueOf(section.getAttribute("data-series-id")), 
						section.findElement(By.tagName("a")).getText(), 
						section.findElement(By.tagName("a")).getAttribute("href")));
				}
				break;
				
			case "GET-ALL-SEASONS":
				
				driver.get("https://www.espncricinfo.com/ci/engine/series/index.html");
				for(WebElement section : driver.findElements(By.className("season-links")))
				{
					for(WebElement anchor : section.findElements(By.tagName("a")))
					{
						all_stats.add(new ArchiveData(0,anchor.getText(), anchor.getAttribute("href")));
					}
				}
				break;
				
			}
			break;
		}
		return all_stats;
	}
	public static MatchAllData readOrSaveMatchFile(String whatToProcess, String whichFileToProcess, MatchAllData match) 
		throws JAXBException, StreamWriteException, DatabindException, IOException, URISyntaxException
	{
		switch (whatToProcess) {
		case CricketUtil.WRITE:
			if(whichFileToProcess.toUpperCase().contains(CricketUtil.SETUP)) {
				Files.write(Paths.get(CricketUtil.CRICKET_DIRECTORY + CricketUtil.SETUP_DIRECTORY 
					+ match.getMatch().getMatchFileName()), 
					objectWriter.writeValueAsString(match.getSetup()).getBytes());			
			}
			if(match.getSetup().getMatchDataUpdate() != null && match.getSetup().getMatchDataUpdate().equalsIgnoreCase(CricketUtil.START)) {
				if(whichFileToProcess.toUpperCase().contains(CricketUtil.MATCH)) {
					Files.write(Paths.get(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY 
						+ match.getMatch().getMatchFileName()), 
						objectWriter.writeValueAsString(match.getMatch()).getBytes());
				}
			}
			break;
		case CricketUtil.READ:
			if(whichFileToProcess.toUpperCase().contains(CricketUtil.SETUP)) {
				if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.SETUP_DIRECTORY + match.getMatch().getMatchFileName().toUpperCase().replace(
					".XML", ".JSON")).exists() == true) {
					match.setSetup(new ObjectMapper().readValue(new InputStreamReader(new FileInputStream(new File(CricketUtil.CRICKET_DIRECTORY 
							+ CricketUtil.SETUP_DIRECTORY + match.getMatch().getMatchFileName())), StandardCharsets.UTF_8), Setup.class));
				}
			}
			if(whichFileToProcess.toUpperCase().contains(CricketUtil.MATCH)) {
				match.setMatch(new ObjectMapper().readValue(new File(CricketUtil.CRICKET_DIRECTORY 
					+ CricketUtil.MATCHES_DIRECTORY + match.getMatch().getMatchFileName()), Match.class));
			}
			break;
		}
		return match;
	}
}
