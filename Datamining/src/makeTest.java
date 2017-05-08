
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


public class makeTest {


	//
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String file = "HKIB-20000_005.txt";

		//Preprocessing file cut only in the fifth file article
		String docFile = "Processing.txt";
		File document = new File(docFile);

		// test file
		String testFile = "YYYTest.txt";
		File test = new File(testFile);

		// File with chi-squared values are sorted in descending order
		String indexFile = "chiValue.txt";

		// 2 Hashmaps 
		HashMap<String, String> hm1 = new HashMap<>();
		HashMap<String, String> hm2 = new HashMap<>();

		// For mapping the category
		HashMap<String, String> category = new HashMap<>();

		category.put("건강과 의학", "1");
		category.put("경제", "2");
		category.put("과학", "3");
		category.put("교육", "4");
		category.put("문화와 종교", "5");
		category.put("사회", "6");
		category.put("산업", "7");
		category.put("여가생활", "8");
		
		//Put the index of feature and chi-value
		TreeMap<Integer, String> tm = new TreeMap<>();

		String tempCat = "";
		StringTokenizer docToken;
		String temp = "";
		/*
		 * Preprocessing 
		 * 
		 */
		try {
			String line = "";
			Scanner scanTest = new Scanner(new FileInputStream(file));
			BufferedWriter fw = new BufferedWriter(new FileWriter(docFile));

			boolean tokenFound = false;

			while (scanTest.hasNextLine()) {
				line = scanTest.nextLine().trim();
				if (line.contains("#DocID :")) {
					fw.write(line + "\n");
				}
				if (line.contains("#CAT'03:")) {
					fw.write(line.split("/")[0] + line.split("/")[1] + "\n");
				}
				// line, not scanner.
				if (line.equals("#TEXT  :")) // tag in the txt to locate
												// //position
				{
					tokenFound = true;
				} else if (line.equals("@DOCUMENT") || line.equals("<KW>")) {
					tokenFound = false;
				}

				if ((tokenFound) && (!line.equals("#TEXT  :"))) {
					fw.write(line + "\n");
				}
			}
			fw.flush();
			fw.close();

		} catch (FileNotFoundException e) {

		}

		String line = "";
		Scanner indexScan = new Scanner(new FileInputStream(indexFile));
		// Write the test file 
		BufferedWriter testF = new BufferedWriter(new FileWriter(testFile));
		while (indexScan.hasNext()) {
			line = indexScan.nextLine();

			String[] spl = line.split(" ");
			hm1.put(spl[1], spl[0]);
			hm2.put(spl[1], spl[2]);
			// hm1 and hm2 size : 572352
		}

		// Read the doc file made above 
		Scanner docScan = new Scanner(new FileInputStream(docFile));

		while (docScan.hasNext()) {

			line = docScan.nextLine();

			// 문서수 15981
			
			if (line.startsWith("#CAT'03:")) {
				
				
				tempCat = category.get(line.split(":")[1].trim());

				
				line = docScan.nextLine();

				//If next line isn't 'DocID'--> Until not DocID
				while (!line.startsWith("#DocID")) {
					//Extract feature use the tokenizer on the read line
					docToken = new StringTokenizer(line, " |-|,|/|.|<|>|\"|[|]|{|}|'|;|?|!|:|(|)|。|、|#|=|+|*|$|&");

					// Compares truncated words and places them in treemap
					while (docToken.hasMoreTokens()) {
						temp = docToken.nextToken();
						if (hm1.containsKey(temp)) {
							
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

				testF.write(tempCat + " ");

				Iterator<Integer> iteratorKey = tm.keySet().iterator();
				for (int i = 0; i < tm.size(); i++) {
					Integer key = iteratorKey.next();
					testF.write(key + ":" + tm.get(key) + " ");
				}

				tm.clear();
				testF.write("\n");

				if (docScan.hasNextLine())
					line = docScan.nextLine();
				else
					break;

			}

		}

	}

}