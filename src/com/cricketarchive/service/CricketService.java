package com.cricketarchive.service;

import java.util.List;
import com.cricketarchive.model.Team;

public interface CricketService {
  List<Team> getTeams();
}