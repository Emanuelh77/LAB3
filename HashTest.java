import java.io.*;
import java.text.*;
import java.util.*;

/**
 * This class tests the functionality of the hash table and hash object
 * @author emanuelhernandez
 *
 */
public class HashTest{
	
	static int inputType = 1;
	static int inputs = 0;
	static double loadFactor = 1;
	static int debugLevel = 0;
	static String dataType = null;
	private static HashTable doubleTable;
	private static HashTable linearTable;
	static HashTable tempTable = new HashTable(1,0.7,0);
	static int hashTableSize = tempTable.getTotalSize();

	public static void main(String[] args) {
		
		if(args.length == 0){
			System.out.println("Syntax: java HashTest <input type> <load factor> [<debug level>]\n");
			System.exit(1);
		}
		
		if(args.length == 2) {
			try {
				inputType = Integer.parseInt(args[0]);
				if(inputType > 3 || inputType < 1){
					System.out.println("Input type can only be 1, 2 or 3");
					System.exit(1);
				}
				loadFactor = Double.parseDouble(args[1]);
				if(loadFactor > 1 || loadFactor < 0){
					System.out.println("Load factor can only be in the range between 0 and 1");
					System.exit(1);
				}
				debugLevel = 0;
			} catch (Exception e) {
				System.err.println("Syntax: java HashTest <input type> <load factor> [<debug level>]\n" 
						+ e.getMessage());
				System.exit(1);
			}			
		}
		else
		{
			try {
				inputType = Integer.parseInt(args[0]);
				if(inputType > 3 || inputType < 1){
					System.out.println("Input type can only be 1, 2 or 3");
					System.exit(1);
				}
				loadFactor = Double.parseDouble(args[1]);
				if(loadFactor > 1 || loadFactor < 0){
					System.out.println("Load factor can only be in the range between 0 and 1");
					System.exit(1);
				}
				debugLevel = Integer.parseInt(args[2]);
				if(debugLevel > 1){
					System.out.println("Debug level > 1.....it has been set equal to 1");
					debugLevel = 1;
				}
				else if(debugLevel < 0){
					System.out.println("Debug level < 0.....it has been set equal to 0");
					debugLevel = 0;
				}
			} catch (Exception e) {
				System.err.println("Syntax:\njava HashTest <input type> <load factor> [<debug level>]\n" 
						+ e.getMessage());
				System.exit(1);

			}
		}

		linearTable = new HashTable(inputType, loadFactor, debugLevel);
		doubleTable = new HashTable(inputType, loadFactor, debugLevel);
		
		System.out.println("A good table size is found: " + linearTable.getTotalSize());
		
		if(inputType == 1){
			System.out.println("Data source type: Random number generator");
			Random rand = new Random();
			
			for(int i = 0; i < linearTable.getMaximumCapacity(); i++){
				int n = rand.nextInt(Integer.MAX_VALUE);
				if(n < 0){
					n = n % linearTable.getTotalSize() + linearTable.getTotalSize();
				}
				HashObject obj1 = new HashObject(n);
				HashObject obj2 = new HashObject(n);

				linearTable.hashInsert(0, obj1);
				doubleTable.hashInsert(1, obj2);
			}
			
		}
		
		if(inputType == 2){
			System.out.println("Data source type: Current time in milliseconds");
			
			while(linearTable.getInserts() < linearTable.getMaximumCapacity()){
				long time = System.currentTimeMillis();
				
				HashObject obj1 = new HashObject(time);
				HashObject obj2 = new HashObject(time);

				linearTable.hashInsert(0, obj1);
				doubleTable.hashInsert(1, obj2);
			}
		}
		
		if(inputType == 3){
			System.out.println("Data source type: words from word-list");
			try{
				String line;
				BufferedReader br = null;
				File file = new File("src/word-list");
				Scanner scan = new Scanner(file);
				while(scan.hasNextLine() && linearTable.getInserts() < linearTable.getMaximumCapacity()){
						String word = scan.nextLine();
						HashObject obj1 = new HashObject(word);
						HashObject obj2 = new HashObject(word);
						
						linearTable.hashInsert(0, obj1);
						doubleTable.hashInsert(1, obj2);
				}
			}
			catch(IOException t){
				System.out.println(t);
				System.exit(1);
			}
		}
		printResults(debugLevel);
	}
	private static void printResults(int debug_level){
		
		if(debugLevel == 0){
		DecimalFormat loadFormat = new DecimalFormat("#.##");
		StringBuilder str = new StringBuilder();

		str.append("\n");
		str.append("Using linear hashing....\n");
		str.append("Inserted " + linearTable.getInsertCallCount() + ", of which " + linearTable.getDupCount() + " duplicates.\n");
		str.append("load factor = " + loadFactor + ", Avg. no of probes " + linearTable.getAverage() + "\n");
		
		str.append("\n");
		str.append("Using double hashing....\n");
		str.append("Inserted " + doubleTable.getInsertCallCount() + ", of which " + doubleTable.getDupCount() + " duplicates.\n");
		str.append("load factor = " + loadFactor + ", Avg. no of probes " + doubleTable.getAverage() + "\n");

		System.out.println(str.toString());
		}
		if(debugLevel == 1){
			File file = new File("linear-dump");
			File file2 = new File("double-dump");
			try {
				linearTable.dump(file);
				doubleTable.dump(file2);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}