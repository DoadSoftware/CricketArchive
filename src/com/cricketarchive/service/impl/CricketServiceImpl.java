package com.cricketarchive.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cricketarchive.dao.CricketDao;
import com.cricketarchive.model.Team;
import com.cricketarchive.service.CricketService;

@Service("cricketService")
@Transactional
public class CricketServiceImpl implements CricketService {

	@Autowired
	private CricketDao cricketDao;
 
	@Override
	public List<Team> getTeams() {
		return cricketDao.getTeams();
	}
}