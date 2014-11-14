package com.qishen.RESTService.model;

import java.util.HashMap;
import java.util.Set;

public class DAO {
	private static DAO _instance;
	private static HashMap<String, Scientist> inMemoryDB = new HashMap<String, Scientist>();
	
	private DAO() {}
	
	public static DAO getInstance() {
		if (_instance == null) {
			_instance = new DAO();
		}
		return _instance;
	}
	
	public void addObj(Scientist s) {
		inMemoryDB.put(s.getUID(), s);
	} 
	
	
	public Scientist getObj(String id) {
		if (isExist(id)) {
			return inMemoryDB.get(id);
		}
		
		return null;
	}
	
	public boolean deleteObj(String id) {
		if (isExist(id)) {
			inMemoryDB.remove(id);
			return true;
		}
		
		return false;
	}
	
	public Set<String> getUIDSet() {
		return inMemoryDB.keySet();
	}
	
	private boolean isExist(String id) {
		if (inMemoryDB.containsKey(id)) {
			return true;
		} 
		
		return false;
	}
	
	
}
