package com.cricketarchive.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BattingCard
{
  private int playerId;
  
  private int batterPosition;
  
  private String battingStyle;

  private int duration;
  
  private int runs;
  
  private int fours;
  
  private int seconds;
  
  private int sixes;
  
  private int nines;
  
  private int balls;
  
  private int howOutFielderId;
  
  private int howOutBowlerId;

  private String batsmanInningStarted;
  
  private String status;
  
  private String onStrike;
  
  private String howOutText;
  
  private String howOut;

  private String howOutPartOne;

  private String howOutPartTwo;
  
  private String strikeRate;

  private String WasHowOutFielderSubstitute;
  
  private int concussionPlayerId;

  private String startTime;

  private String endTime;
  
  private Player player;
  
  private Player howOutBowler;

  public BattingCard() {
	super();
  }
  
public BattingCard(int playerId, int batterPosition, String status) {
	super();
	this.playerId = playerId;
	this.batterPosition = batterPosition;
	this.status = status;
}

public int getPlayerId() {
	return playerId;
}

public void setPlayerId(int playerId) {
	this.playerId = playerId;
}

public int getBatterPosition() {
	return batterPosition;
}

public void setBatterPosition(int batterPosition) {
	this.batterPosition = batterPosition;
}

public String getBattingStyle() {
	return battingStyle;
}

public void setBattingStyle(String battingStyle) {
	this.battingStyle = battingStyle;
}

public int getDuration() {
	return duration;
}

public void setDuration(int duration) {
	this.duration = duration;
}

public int getRuns() {
	return runs;
}

public void setRuns(int runs) {
	this.runs = runs;
}

public int getFours() {
	return fours;
}

public void setFours(int fours) {
	this.fours = fours;
}

public int getSeconds() {
	return seconds;
}

public void setSeconds(int seconds) {
	this.seconds = seconds;
}

public int getSixes() {
	return sixes;
}

public void setSixes(int sixes) {
	this.sixes = sixes;
}

public int getNines() {
	return nines;
}

public void setNines(int nines) {
	this.nines = nines;
}

public int getBalls() {
	return balls;
}

public void setBalls(int balls) {
	this.balls = balls;
}

public int getHowOutFielderId() {
	return howOutFielderId;
}

public void setHowOutFielderId(int howOutFielderId) {
	this.howOutFielderId = howOutFielderId;
}

public int getHowOutBowlerId() {
	return howOutBowlerId;
}

public void setHowOutBowlerId(int howOutBowlerId) {
	this.howOutBowlerId = howOutBowlerId;
}

public String getBatsmanInningStarted() {
	return batsmanInningStarted;
}

public void setBatsmanInningStarted(String batsmanInningStarted) {
	this.batsmanInningStarted = batsmanInningStarted;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public String getOnStrike() {
	return onStrike;
}

public void setOnStrike(String onStrike) {
	this.onStrike = onStrike;
}

public String getHowOutText() {
	return howOutText;
}

public void setHowOutText(String howOutText) {
	this.howOutText = howOutText;
}

public String getHowOut() {
	return howOut;
}

public void setHowOut(String howOut) {
	this.howOut = howOut;
}

public String getHowOutPartOne() {
	return howOutPartOne;
}

public void setHowOutPartOne(String howOutPartOne) {
	this.howOutPartOne = howOutPartOne;
}

public String getHowOutPartTwo() {
	return howOutPartTwo;
}

public void setHowOutPartTwo(String howOutPartTwo) {
	this.howOutPartTwo = howOutPartTwo;
}

public String getStrikeRate() {
	return strikeRate;
}

public void setStrikeRate(String strikeRate) {
	this.strikeRate = strikeRate;
}

public String getWasHowOutFielderSubstitute() {
	return WasHowOutFielderSubstitute;
}

public void setWasHowOutFielderSubstitute(String wasHowOutFielderSubstitute) {
	WasHowOutFielderSubstitute = wasHowOutFielderSubstitute;
}

public int getConcussionPlayerId() {
	return concussionPlayerId;
}

public void setConcussionPlayerId(int concussionPlayerId) {
	this.concussionPlayerId = concussionPlayerId;
}

public String getStartTime() {
	return startTime;
}

public void setStartTime(String startTime) {
	this.startTime = startTime;
}

public String getEndTime() {
	return endTime;
}

public void setEndTime(String endTime) {
	this.endTime = endTime;
}

public Player getPlayer() {
	return player;
}

public void setPlayer(Player player) {
	this.player = player;
}

public Player getHowOutBowler() {
	return howOutBowler;
}

public void setHowOutBowler(Player howOutBowler) {
	this.howOutBowler = howOutBowler;
}

@Override
public String toString() {
	return "BattingCard [playerId=" + playerId + ", batterPosition=" + batterPosition + ", battingStyle=" + battingStyle
			+ ", duration=" + duration + ", runs=" + runs + ", fours=" + fours + ", seconds=" + seconds + ", sixes="
			+ sixes + ", nines=" + nines + ", balls=" + balls + ", howOutFielderId=" + howOutFielderId
			+ ", howOutBowlerId=" + howOutBowlerId + ", batsmanInningStarted=" + batsmanInningStarted + ", status="
			+ status + ", onStrike=" + onStrike + ", howOutText=" + howOutText + ", howOut=" + howOut
			+ ", howOutPartOne=" + howOutPartOne + ", howOutPartTwo=" + howOutPartTwo + ", strikeRate=" + strikeRate
			+ ", WasHowOutFielderSubstitute=" + WasHowOutFielderSubstitute + ", concussionPlayerId="
			+ concussionPlayerId + ", startTime=" + startTime + ", endTime=" + endTime + ", player=" + player
			+ ", howOutBowler=" + howOutBowler + "]";
}

}