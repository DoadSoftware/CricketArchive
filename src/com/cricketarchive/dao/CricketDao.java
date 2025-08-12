package com.cricketarchive.dao;

import java.util.List;
import com.cricketarchive.model.Team;

public interface CricketDao {
  List<Team> getTeams();
}