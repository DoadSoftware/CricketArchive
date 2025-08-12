package com.cricketarchive.dao.impl;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.cricketarchive.dao.CricketDao;
import com.cricketarchive.model.Team;

@Transactional
@Repository("cricketDao")
@SuppressWarnings("unchecked")
public class CricketDaoImpl implements CricketDao {

 @Autowired
 private SessionFactory sessionFactory;
 
@Override
public List<Team> getTeams() {
	return sessionFactory.getCurrentSession().createQuery("from Team").list();
}

}