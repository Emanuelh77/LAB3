import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

/**
 * This class creates a HashTable that contains HashObjects
 * @author emanuelhernandez
 *
 * @param <T>
 */

public class HashTable<T>{
	
	private int totalSize = 0;
	private int inserts = 0;
	private int dupAmount = 0;
	private int insertCallCount = 0;
	private int loadFactor;
	private int maximumCapacity = 0;
	
	HashObject<T>[] hashtable;
	
	public HashTable(int inputType, double alpha, int debugLevel){
		
		hashtable = new HashObject[getTotalSize()];
		this.maximumCapacity = (int)Math.ceil(totalSize * alpha);
	}
	
	/**
	 * gets successful hashtable inserts
	 * @return inserts
	 */
	public int getInserts() {
		return this.inserts;
	}
	
	/**
	 * gets the total duplicate count
	 * @return dupAmount
	 */
	public int getDupCount(){
		return dupAmount;
	}
	
	/**
	 * returns the amount of times hashInsert has been called
	 * @return insertCallCount
	 */
	public int getInsertCallCount(){
		return insertCallCount;
	}
	/**
	 * gets the maximum capacity of the hashtable - load factor x totalSize
	 * @return maximumCapacity
	 */
	public int getMaximumCapacity(){
		return maximumCapacity;
	}
	
	/**
	 * returns average number of probes
	 * @return
	 */
	public double getAverage(){
		return (double)getProbes()/(double)getInserts();
	}
	
	/**
	 * gets the capacity of the hash table
	 * @return totalSize
	 */
	public int getTotalSize(){
		totalSize = primeCheck();
		return totalSize;
	}
	
	/**
	 * inserts hash objects into hash tables depending on the probe type
	 * @param probeType
	 * @param object
	 */
	public void hashInsert(int probeType, HashObject object){
		int probeCount = 0;
		insertCallCount++;
		if(probeType != 0 && probeType != 1){
			return;
		}
		// linear hashing begins
		int index = linearProbing(probeCount,object.getKey());
		if(probeType == 0){
			int j = 0;
			while(hashtable[index] != null && j < totalSize){
				if(hashtable[index].equals(object)){
					hashtable[index].incrementDupCount();
					dupAmount++;
					probeCount++;
					return;
				}
				else {
					index++;
					index = index % totalSize;
				}
				object.incrementProbeCount();
				j++;
			}
			if(hashtable[index] == null) {
				hashtable[index] = object;
				inserts++;
				probeCount++;
				hashtable[index].incrementProbeCount();
			}
		}
		
		//double hashing begins
		if(probeType == 1){
			int i = 0;
			while(hashtable[index] != null && i < totalSize){
				if(hashtable[index].equals(object)){
					hashtable[index].incrementDupCount();
					dupAmount++;
					probeCount++;
					return;
				}
				else {
					index = (index + doubleHashing(object.getKey())) % totalSize;
				}
				object.incrementProbeCount();
				i++;
			}
			if(hashtable[index] == null) {
				hashtable[index] = object;
				inserts++;
				probeCount++;
				hashtable[index].incrementProbeCount();
			}
		}
	}
	/**
	 * gets number of probes
	 * @return val
	 */
	public int getProbes() {
		int val = 0;
		for(int i = 0; i < totalSize; i++){
			if(hashtable[i] != null){
			val += hashtable[i].getProbeCount();
			}
		}
		return val;
	}
	
	public boolean isPrime(int n){
		Random randomizer = new Random(100);
		int temp = randomizer.nextInt(100);
		int firstNum = 0;
		String q = Integer.toString(n-1,2);
		for(int i = 0; i < q.length(); i++){
			if(Integer.parseInt(String.valueOf(q.charAt(i))) == 1){
				firstNum = i;
				break;
			}
		}
		Long val = (long) temp;
		for(int i = firstNum + 1; i < q.length(); i++){
			if(Integer.parseInt(String.valueOf(q.charAt(i))) == 0){
				val = (val*val) % n;
			}
			else {
				val = (val*val) % n;
				val = (val * temp) % n;
			}
		}
		if (val == 1){
			return true;
		}
		return false;
	}
	
	public int primeCheck(){
		for(int i = 95500; i < 96000; i++){
			if(isPrime(i) && isPrime(i-2)){
				if(isPrime(i) && isPrime(i-2)){
					return i;
				}
			}
		}
		return -1;
	}
	
	/**
	 * uses the double hash algorithm with given key
	 * @param key
	 * @return val
	 */
	public int doubleHashing(int key){		
		int val = mod(key, totalSize-2);
		return val;
	}
	
	/**
	 * uses the linear probing algorithm with given key and probeCount
	 * @param probeCount
	 * @param key
	 * @return val
	 */
	public int linearProbing(int probeCount, int key){
		int val = (mod(key, totalSize) + probeCount);
		val = mod(val, totalSize);
		return val;
	}
	
	/**
	 * mod method
	 * @param a
	 * @param b
	 * @return result
	 */
	public int mod(int a, int b){
		int result = a % b;
		if(result < 0){
			result += b;
		}
		return result;
	}
	
	/**
	 * creates two dump files, one for linear and one for double
	 * @param file
	 * @throws FileNotFoundException
	 */
	public void dump(File file) throws FileNotFoundException{
		PrintWriter writer = new PrintWriter(file);
		for(int i = 0; i < totalSize; i++){
			if(hashtable[i] != null){
				writer.println("table[" + i + "] " + hashtable[i] + " Duplicates: " + hashtable[i].getDuplicateCount() +
				" Probes: " + hashtable[i].getProbeCount());
			}
		}
	}
	
}