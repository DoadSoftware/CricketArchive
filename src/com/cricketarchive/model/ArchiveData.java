package com.cricketarchive.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArchiveData{

  private long id;
  private String label;
  private String url;

  public ArchiveData() {
	super();
  }
public ArchiveData(long id, String label, String url) {
	super();
	this.id = id;
	this.label = label;
	this.url = url;
}
  
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getLabel() {
	return label;
}
public void setLabel(String label) {
	this.label = label;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
@Override
public String toString() {
	return "ArchiveData [id=" + id + ", label=" + label + ", url=" + url + "]";
}
  
}