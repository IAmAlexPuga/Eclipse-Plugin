package MyPackage;

import java.util.HashMap;
import java.util.Map;

public class MetricsSingleton {
	private static int operators = 0;
	private static int operands = 0;
	private static int expressions = 0;
	private static Map<Integer, Integer> uniqOps =  new HashMap<Integer, Integer>();
	private static Map<String, Integer> uniqOperands =  new HashMap<String, Integer>();
	private static int hLength = 0;
	private static double hVocab = 0;
	private static double hVolume = 0;
	private static double hDiff = 0;
	private static double hEffort = 0;
	
	private static MetricsSingleton instance = null;
	
	private MetricsSingleton() {
	}
	
	public static MetricsSingleton getInstance() {
		if(instance == null) {
			instance = new MetricsSingleton();
		}
		
		return instance;
	}
	
	
	//Computes halstead metrics
	public void computeHLength() {
		this.hLength = this.getOperands() + this.getOps();
	}
	
	public void computeHVocab() {
		this.hVocab = this.getUniqueOperands().size() + this.getUniqueOps().size();
	}
	
	public void computeHVolume() {
		this.hVolume = this.getHLength() * Math.log(this.getHVocab());
	}
	
	public void computeHDiff() {
		this.hDiff = ((.5*this.getUniqueOps().size())* this.getOperands() )/this.getUniqueOperands().size();
	}
	
	public void computeHEffort() {
		this.hEffort = this.getHDiff()*this.getHVolume();
	}
	
	// Resets vars
	public void resetOps() {
		this.operators = 0;
	}
	
	public void resetOperands() {
		this.operands = 0;
	}
	
	public void resetExpressions() {
		this.expressions = 0;
	}
	
	public void resetUniqueOps() {
		this.uniqOps = new HashMap<Integer, Integer>();
	}
	
	public void resetUniqueOperands() {
		this.uniqOperands =  new HashMap<String, Integer>();
	}
	
	public void resetHLength() {
		this.hLength = 0;
	}
	
	public void resetHVocab() {
		this.hVocab = 0;
	}
	
	public void resetHVolume() {
		this.hVolume = 0;
	}
	
	public void resetHDiff() {
		this.hDiff = 0;
	}
	
	public void resetHEffort() {
		this.hEffort = 0;
	}
	
	// Adders
	public void addOps() {
		this.operators += 1;
	}
	
	public void addOperands() {
		this.operands += 1;
	}
	
	public void addExprs() {
		this.expressions += 1;
	}
	
	public void addUniqueOps(int key, int value) {
		this.uniqOps.put(key, value);
	}
	
	public void addUniqueOperands(String key, int value) {
		this.uniqOperands.put(key, value);
	}
	
	// Setters
	public void setHLength(int val) {
		this.hLength = val;
	}
	
	public void setHVocab(int val) {
		this.hVocab = val;
	}
	
	public void setHDiff(int val) {
		this.hDiff = val;
	}
	
	public void setHVolume(int val) {
		this.hVolume = val;
	}
	
	public void setHEffort(int val) {
		this.hEffort = val;
	}
	
	// Getters
	public int getOps() {
		return this.operators;
	}
	
	public int getOperands() {
		return this.operands;
	}
	
	public int getExprs() {
		return this.expressions;
	}
	
	public Map<Integer, Integer> getUniqueOps() {
		return this.uniqOps;
	}
	
	public Map<String, Integer> getUniqueOperands() {
		return this.uniqOperands;
	}
	
	public int getHLength() {
		return this.hLength;
	}
	
	public double getHVocab() {
		return this.hVocab;
	}
	
	public double getHVolume() {
		return this.hVolume;
	}
	
	public double getHDiff() {
		return this.hDiff;
	}
	
	public double getHEffort() {
		return this.hEffort;
	}

}
