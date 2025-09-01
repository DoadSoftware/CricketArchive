package com.cricketarchive.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Partnership
{
  private int partnershipNumber;
  
  private int firstBatterNo;

  private int secondBatterNo;
  
  private int firstBatterRuns;

  private int secondBatterRuns;

  private int firstBatterBalls;

  private int secondBatterBalls;
  
  private int totalRuns;
  
  private int totalBalls;

  private int totalFours;

  private int totalSixes;

  private int totalNines;

public int getPartnershipNumber() {
	return partnershipNumber;
}

public void setPartnershipNumber(int partnershipNumber) {
	this.partnershipNumber = partnershipNumber;
}

public int getFirstBatterNo() {
	return firstBatterNo;
}

public void setFirstBatterNo(int firstBatterNo) {
	this.firstBatterNo = firstBatterNo;
}

public int getSecondBatterNo() {
	return secondBatterNo;
}

public void setSecondBatterNo(int secondBatterNo) {
	this.secondBatterNo = secondBatterNo;
}

public int getFirstBatterRuns() {
	return firstBatterRuns;
}

public void setFirstBatterRuns(int firstBatterRuns) {
	this.firstBatterRuns = firstBatterRuns;
}

public int getSecondBatterRuns() {
	return secondBatterRuns;
}

public void setSecondBatterRuns(int secondBatterRuns) {
	this.secondBatterRuns = secondBatterRuns;
}

public int getFirstBatterBalls() {
	return firstBatterBalls;
}

public void setFirstBatterBalls(int firstBatterBalls) {
	this.firstBatterBalls = firstBatterBalls;
}

public int getSecondBatterBalls() {
	return secondBatterBalls;
}

public void setSecondBatterBalls(int secondBatterBalls) {
	this.secondBatterBalls = secondBatterBalls;
}

public int getTotalRuns() {
	return totalRuns;
}

public void setTotalRuns(int totalRuns) {
	this.totalRuns = totalRuns;
}

public int getTotalBalls() {
	return totalBalls;
}

public void setTotalBalls(int totalBalls) {
	this.totalBalls = totalBalls;
}

public int getTotalFours() {
	return totalFours;
}

public void setTotalFours(int totalFours) {
	this.totalFours = totalFours;
}

public int getTotalSixes() {
	return totalSixes;
}

public void setTotalSixes(int totalSixes) {
	this.totalSixes = totalSixes;
}

public int getTotalNines() {
	return totalNines;
}

public void setTotalNines(int totalNines) {
	this.totalNines = totalNines;
}

@Override
public String toString() {
	return "Partnership [partnershipNumber=" + partnershipNumber + ", firstBatterNo=" + firstBatterNo
			+ ", secondBatterNo=" + secondBatterNo + ", firstBatterRuns=" + firstBatterRuns + ", secondBatterRuns="
			+ secondBatterRuns + ", firstBatterBalls=" + firstBatterBalls + ", secondBatterBalls=" + secondBatterBalls
			+ ", totalRuns=" + totalRuns + ", totalBalls=" + totalBalls + ", totalFours=" + totalFours + ", totalSixes="
			+ totalSixes + ", totalNines=" + totalNines + "]";
}
  
}