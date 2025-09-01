package com.cricketarchive.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.Column;

@Entity
@Table(name = "Players")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Player
{
  @Id
  @Column(name = "PLAYERID")
  private int playerId;
	
  @Column(name = "FULLNAME")
  private String full_name;

  @Column(name = "FIRSTNAME")
  private String firstname;

  @Column(name = "SURNAME")
  private String surname;
  
  @Column(name = "TICKERNAME")
  private String ticker_name;
  
  @Column(name = "AbbrvName")
  private String abbrv_Name;
  
  @Column(name = "BOWLINGSTYLE")
  private String bowlingStyle;
  
  @Column(name = "BATTINGSTYLE")
  private String battingStyle;

  @Column(name = "TEAMID")
  private Integer teamId;
  
  @Column(name = "Photo")
  private String Photo;
  
  @Column(name = "Gender")
  private String gender;
  
  @Transient
  private Integer playerPosition;

  @Transient
  private String captainWicketKeeper;

public int getPlayerId() {
	return playerId;
}

public void setPlayerId(int playerId) {
	this.playerId = playerId;
}

public String getFull_name() {
	return full_name;
}

public void setFull_name(String full_name) {
	this.full_name = full_name;
}

public String getFirstname() {
	return firstname;
}

public void setFirstname(String firstname) {
	this.firstname = firstname;
}

public String getSurname() {
	return surname;
}

public void setSurname(String surname) {
	this.surname = surname;
}

public String getTicker_name() {
	return ticker_name;
}

public void setTicker_name(String ticker_name) {
	this.ticker_name = ticker_name;
}

public String getAbbrv_Name() {
	return abbrv_Name;
}

public void setAbbrv_Name(String abbrv_Name) {
	this.abbrv_Name = abbrv_Name;
}

public String getBowlingStyle() {
	return bowlingStyle;
}

public void setBowlingStyle(String bowlingStyle) {
	this.bowlingStyle = bowlingStyle;
}

public String getBattingStyle() {
	return battingStyle;
}

public void setBattingStyle(String battingStyle) {
	this.battingStyle = battingStyle;
}

public Integer getTeamId() {
	return teamId;
}

public void setTeamId(Integer teamId) {
	this.teamId = teamId;
}

public String getPhoto() {
	return Photo;
}

public void setPhoto(String photo) {
	Photo = photo;
}

public String getGender() {
	return gender;
}

public void setGender(String gender) {
	this.gender = gender;
}

public Integer getPlayerPosition() {
	return playerPosition;
}

public void setPlayerPosition(Integer playerPosition) {
	this.playerPosition = playerPosition;
}

public String getCaptainWicketKeeper() {
	return captainWicketKeeper;
}

public void setCaptainWicketKeeper(String captainWicketKeeper) {
	this.captainWicketKeeper = captainWicketKeeper;
}

@Override
public String toString() {
	return "Player [playerId=" + playerId + ", full_name=" + full_name + ", firstname=" + firstname + ", surname="
			+ surname + ", ticker_name=" + ticker_name + ", abbrv_Name=" + abbrv_Name + ", bowlingStyle=" + bowlingStyle
			+ ", battingStyle=" + battingStyle + ", teamId=" + teamId + ", Photo=" + Photo + ", gender=" + gender
			+ ", playerPosition=" + playerPosition + ", captainWicketKeeper=" + captainWicketKeeper + "]";
}

}
