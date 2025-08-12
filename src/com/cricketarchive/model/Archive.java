package com.cricketarchive.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Archive{

  private List<ArchiveData> seasons;
  private List<ArchiveData> series;
  private List<ArchiveData> matches;
  private MatchAllData matchAllData;
  
  public MatchAllData getMatchAllData() {
	return matchAllData;
}
public void setMatchAllData(MatchAllData matchAllData) {
	this.matchAllData = matchAllData;
}
public List<ArchiveData> getMatches() {
	return matches;
}
public void setMatches(List<ArchiveData> matches) {
	this.matches = matches;
}
public List<ArchiveData> getSeasons() {
	return seasons;
}
public void setSeasons(List<ArchiveData> seasons) {
	this.seasons = seasons;
}
public List<ArchiveData> getSeries() {
	return series;
}
public void setSeries(List<ArchiveData> series) {
	this.series = series;
}
@Override
public String toString() {
	return "Archive [seasons=" + seasons + ", series=" + series + ", matches=" + matches + ", matchAllData="
			+ matchAllData + "]";
}
	  
}