import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;


public class newMethod {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		// Read the preprocessingfile
		String docFile = "Preprocessing_test.txt";
		// Files whose chi-squared values are sorted in descending order
		String indexFile = "chiValue.txt";

		
		// training file
		String trainFile = "YYYtrain.txt";
		File train = new File(trainFile);
		

		// 2 Hashmaps
		//hm1 : KEY - feature VALUE - index
		//hm2 : KEY - feature VALUE - chi square
		HashMap<String, String> hm1 = new HashMap<>();
		HashMap<String, String> hm2 = new HashMap<>();

		// for mapping with the category 
		HashMap<String, String> category = new HashMap<>();
		category.put("건강과 의학", "1");
		category.put("경제", "2");
		category.put("과학", "3");
		category.put("교육", "4");
		category.put("문화와 종교", "5");
		category.put("사회", "6");
		category.put("산업", "7");
		category.put("여가생활", "8");

		// Include corresponding index and feature in article file
		TreeMap<Integer, String> tm = new TreeMap<>();
		String tempCat = "";
		StringTokenizer docToken;
		String temp = "";
		try {
			String line = "";
			// Read the index file and put it in hash
			Scanner indexScan = new Scanner(new FileInputStream(indexFile));
			//The part to write train file
			BufferedWriter trainF = new BufferedWriter(new FileWriter(trainFile));
			while (indexScan.hasNext()) {
				line = indexScan.nextLine();

				String[] spl = line.split(" ");
				hm1.put(spl[1], spl[0]);
				hm2.put(spl[1], spl[2]);
				// hm1 and hm2 size : 572352
			}

			// read the doc file 
			Scanner docScan = new Scanner(new FileInputStream(docFile));

			while (docScan.hasNext()) {

				line = docScan.nextLine();

				
				// When you start with a category,
				if (line.startsWith("#CAT'03:")) {
					//Temperory Store the category number 
					tempCat = category.get(line.split(":")[1].trim());

					//Next line
					line = docScan.nextLine();

					//If next line isn't 'DocID'--> Until not DocID
					while (!line.startsWith("#DocID")) {
						//Extract feature use the tokenizer on the read line
						docToken = new StringTokenizer(line, " |-|,|/|.|<|>|\"|[|]|{|}|'|;|?|!|:|(|)|。|、|#|=|+|*|$|&");

						// Compares truncated words and places them in treemap
						while (docToken.hasMoreTokens()) {
							temp = docToken.nextToken();
							if (hm1.containsKey(temp)) {
								// store hashmap value
								int index = Integer.parseInt(hm1.get(temp));
								String chi = hm2.get(temp);
								tm.put(index, chi);
							}
						}
						// if : If you have read all the tokens, you will get out and go to the next line.
						// else : If there is no next line, do not exit. If not, go to the next line and try again.
						if (docScan.hasNextLine())
							line = docScan.nextLine();
						else
							break;
					}

					trainF.write(tempCat+" ");
					
					Iterator<Integer> iteratorKey = tm.keySet().iterator();
					for (int i = 0; i < tm.size(); i++) {
						Integer key = iteratorKey.next();
						trainF.write(key + ":" + tm.get(key) + " ");
					}

					tm.clear();
					trainF.write("\n");
					
					if (docScan.hasNextLine())
						line = docScan.nextLine();
					else
						break;

				}

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
