package com.cricketarchive.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BowlingCard {

  private Player player;

  private int bowlingPosition;

  private String status;

  private String economyRate;
  
  private int bowling_end;
  
  private int overs;

  private int runs;
  
  private int balls;

  private int wickets;

  private int playerId;
  
  private int wides;
  
  private int noBalls;
  
  private int runOuts;
  
  private int stumpings;
  
  private int catchAsFielder;
  
  private int catchAsBowler;

  private int maidens;

  private int dots;

  private int totalRunsThisOver;

  public BowlingCard() {
	super();
  }
	
public BowlingCard(int playerId,int bowlingPosition, String status, int bowling_end) {
	super();
	this.playerId = playerId;
	this.bowlingPosition = bowlingPosition;
	this.status = status;
	this.bowling_end = bowling_end;
}

public Player getPlayer() {
	return player;
}

public void setPlayer(Player player) {
	this.player = player;
}

public int getBowlingPosition() {
	return bowlingPosition;
}

public void setBowlingPosition(int bowlingPosition) {
	this.bowlingPosition = bowlingPosition;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public String getEconomyRate() {
	return economyRate;
}

public void setEconomyRate(String economyRate) {
	this.economyRate = economyRate;
}

public int getBowling_end() {
	return bowling_end;
}

public void setBowling_end(int bowling_end) {
	this.bowling_end = bowling_end;
}

public int getOvers() {
	return overs;
}

public void setOvers(int overs) {
	this.overs = overs;
}

public int getRuns() {
	return runs;
}

public void setRuns(int runs) {
	this.runs = runs;
}

public int getBalls() {
	return balls;
}

public void setBalls(int balls) {
	this.balls = balls;
}

public int getWickets() {
	return wickets;
}

public void setWickets(int wickets) {
	this.wickets = wickets;
}

public int getPlayerId() {
	return playerId;
}

public void setPlayerId(int playerId) {
	this.playerId = playerId;
}

public int getWides() {
	return wides;
}

public void setWides(int wides) {
	this.wides = wides;
}

public int getNoBalls() {
	return noBalls;
}

public void setNoBalls(int noBalls) {
	this.noBalls = noBalls;
}

public int getRunOuts() {
	return runOuts;
}

public void setRunOuts(int runOuts) {
	this.runOuts = runOuts;
}

public int getStumpings() {
	return stumpings;
}

public void setStumpings(int stumpings) {
	this.stumpings = stumpings;
}

public int getCatchAsFielder() {
	return catchAsFielder;
}

public void setCatchAsFielder(int catchAsFielder) {
	this.catchAsFielder = catchAsFielder;
}

public int getCatchAsBowler() {
	return catchAsBowler;
}

public void setCatchAsBowler(int catchAsBowler) {
	this.catchAsBowler = catchAsBowler;
}

public int getMaidens() {
	return maidens;
}

public void setMaidens(int maidens) {
	this.maidens = maidens;
}

public int getDots() {
	return dots;
}

public void setDots(int dots) {
	this.dots = dots;
}

public int getTotalRunsThisOver() {
	return totalRunsThisOver;
}

public void setTotalRunsThisOver(int totalRunsThisOver) {
	this.totalRunsThisOver = totalRunsThisOver;
}

@Override
public String toString() {
	return "BowlingCard [player=" + player + ", bowlingPosition=" + bowlingPosition + ", status=" + status
			+ ", economyRate=" + economyRate + ", bowling_end=" + bowling_end + ", overs=" + overs + ", runs=" + runs
			+ ", balls=" + balls + ", wickets=" + wickets + ", playerId=" + playerId + ", wides=" + wides + ", noBalls="
			+ noBalls + ", runOuts=" + runOuts + ", stumpings=" + stumpings + ", catchAsFielder=" + catchAsFielder
			+ ", catchAsBowler=" + catchAsBowler + ", maidens=" + maidens + ", dots=" + dots + ", totalRunsThisOver="
			+ totalRunsThisOver + "]";
}  

}