/**
 * This class creates a hash object of type <T>
 * @author emanuelhernandez
 *
 * @param <T>
 */
public class HashObject<T>{
	private int duplicateCount;
	private int probeCount;
	private T hashObject;
	private int key;
	
	public HashObject(T object){
		this.duplicateCount = 0;
		this.probeCount = 0;
		hashObject = object;
		key = object.hashCode();
	}
	
	/**
	 * gets the number of duplicates of the object
	 * @return duplicateCount
	 */
	public int getDuplicateCount(){
		return duplicateCount;
	}
	
	/**
	 * gets the number of probes of the object
	 * @return probeCount
	 */
	public int getProbeCount(){
		return probeCount;
	}
	
	/**
	 * sets the duplicateCount equal to the parameter
	 * @param dupCount
	 */
	public void setDuplicateCount(int dupCount){
		this.duplicateCount = dupCount;
	}
	
	/**
	 * sets the probeCount equal to the parameter
	 * @param probCount
	 */
	public void setProbeCount(int probCount){
		this.probeCount = probCount;
	}
	
	/**
	 * increments duplicate count
	 */
	public void incrementDupCount(){
		this.duplicateCount++;
	}
	
	/**
	 * increments the probe count
	 */
	public void incrementProbeCount(){
		this.probeCount++;
	}
	
	/**
	 * equals method
	 */
	public boolean equals(HashObject obj){
		if(this.hashObject.equals(obj.hashObject)){
			return true;
		}
		else return false;
	}
	
	/**
	 * gets the key
	 * @return key
	 */
	public int getKey(){
		return key;
	}
	
	/**
	 * gets the hash object of type <T>
	 * @return hashObject
	 */
	public T getObject(){
		return hashObject;
	}
	
	/**
	 * toString method
	 */
	public String toString(){
		String str = "Contents: " + hashObject.toString() + " Frequency: " + probeCount;
		return str;
	}
}