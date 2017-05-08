import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;


public class dp {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String file1 = "HKIB-20000_001.txt";
		String file2 = "HKIB-20000_002.txt";
		String file3 = "HKIB-20000_003.txt";
		String file4 = "HKIB-20000_004.txt";

		// Preprocessing file name
		String fileName = "Preprocessing_test.txt";
		File outFile = new File(fileName);

		// Format is 'index feature chi-value'
		String output = "chiValue.txt";
		File result = new File(output);
		BufferedWriter fw2;
		/*
		 * Tree set To store features per category (Unique feature per document)
		 */
		TreeSet[] cat = new TreeSet[8];

		for (int i = 0; i < 8; i++) {
			cat[i] = new TreeSet<String>();
		}

		// The number of documents per category
		int[] docCnt = new int[8];
		// Total number of features with duplicates removed
		TreeSet features = new TreeSet();

		try {
			Scanner scanner1 = new Scanner(new FileInputStream(file1));
			Scanner scanner2 = new Scanner(new FileInputStream(file2));
			Scanner scanner3 = new Scanner(new FileInputStream(file3));
			Scanner scanner4 = new Scanner(new FileInputStream(file4));

			BufferedWriter fw = new BufferedWriter(new FileWriter(fileName));
			// Moved the rest of the code within the try block.
			// As it was before, if there where any problems loading the file,
			// you would have gotten an error message (File not found)
			// as per your catch block but you would then have gotten an
			// unhandled null pointer exception when you would have tried to
			// execute this bit: scanner.hasNextLine()

			/*
			 * Preprocessing Extraction Three Items : DocID, CAT'03, Contents of
			 * #TEXT from 4 files.
			 */
			boolean tokenFound = false;

			while (scanner1.hasNextLine()) {
				String line = scanner1.nextLine().trim();
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

			while (scanner2.hasNextLine()) {
				String line = scanner2.nextLine().trim();
				if (line.contains("#DocID :")) {
					fw.write(line + "\n");
				}
				if (line.contains("#CAT'03:")) {
					fw.write(line.split("/")[0] + line.split("/")[1] + "\n");
				}
				// line, not scanner.
				if (line.equals("#TEXT  :")) // tag in the txt to locate //
												// position
				{
					tokenFound = true;
				} else if (line.equals("@DOCUMENT") || line.equals("<KW>")) {
					tokenFound = false;
				}

				if ((tokenFound) && (!line.equals("#TEXT  :"))) {
					fw.write(line + "\n");
				}
			}

			while (scanner3.hasNextLine()) {
				String line = scanner3.nextLine().trim();
				if (line.contains("#DocID :")) {
					fw.write(line + "\n");
				}
				if (line.contains("#CAT'03:")) {
					fw.write(line.split("/")[0] + line.split("/")[1] + "\n");
				}
				// line, not scanner.
				if (line.equals("#TEXT  :")) // tag in the txt to locate //
												// position
				{
					tokenFound = true;
				} else if (line.equals("@DOCUMENT") || line.equals("<KW>")) {
					tokenFound = false;
				}

				if ((tokenFound) && (!line.equals("#TEXT  :"))) {
					fw.write(line + "\n");
				}
			}

			while (scanner4.hasNextLine()) {
				String line = scanner4.nextLine().trim();
				if (line.contains("#DocID :")) {
					fw.write(line + "\n");
				}
				if (line.contains("#CAT'03:")) {
					fw.write(line.split("/")[0] + line.split("/")[1] + "\n");
				}
				// line, not scanner.
				if (line.equals("#TEXT  :")) // tag in the txt to locate //
												// position
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

		} catch (Exception e) {
			System.out.println("File not found.");
		}

		/*
		 * Read the preprocessed file. 
		 * Extract feature per document with delimiter set. 
		 * Count the number of documents featured per category.
		 * Chi square Computes the result and creates a new text file. (INDEX FEATURE VALUE)
		 * 
		 */
		try {
			// re-read
			Scanner scanner = new Scanner(new FileInputStream(fileName));
			String docid = "";
			StringTokenizer st;
			String tmp = "";
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();

				// docID
				if (line.contains("#DocID :")) {
					docid = line.split(":")[1];
				}

				// category
				if (line.equals("#CAT'03: 건강과 의학")) {
					docCnt[0]++;
					String next = scanner.nextLine();
					while (!next.contains("#DocID :")) {

						st = new StringTokenizer(next, " |-|,|/|.|<|>|\"|[|]|{|}|'|;|?|!|:|(|)|。|、|#|=|+|*|$|&");

						while (st.hasMoreTokens()) {
							tmp = st.nextToken();
							if (!tmp.contains("@DOCUMENT")) {

								cat[0].add(docid + " " + tmp);
								features.add(tmp);
							}
						}
						next = scanner.nextLine();
					}
				} else if (line.equals("#CAT'03: 경제")) {
					docCnt[1]++;
					String next = scanner.nextLine();
					while (!next.contains("#DocID :")) {
						st = new StringTokenizer(next, " |-|,|/|.|<|>|\"|[|]|{|}|'|;|?|!|:|(|)|。|、|#|=|+|*|$|&");

						while (st.hasMoreTokens()) {
							tmp = st.nextToken();
							if (!tmp.contains("@DOCUMENT")) {
								cat[1].add(docid + " " + tmp);
								features.add(tmp);
							}
						}
						next = scanner.nextLine();
					}

				} else if (line.equals("#CAT'03: 과학")) {
					docCnt[2]++;
					String next = scanner.nextLine();
					while (!next.contains("#DocID :")) {
						st = new StringTokenizer(next, " |-|,|/|.|<|>|\"|[|]|{|}|'|;|?|!|:|(|)|。|、|#|=|+|*|$|&");
						while (st.hasMoreTokens()) {
							tmp = st.nextToken();
							if (!tmp.contains("@DOCUMENT")) {
								cat[2].add(docid + " " + tmp);
								features.add(tmp);
							}
						}
						next = scanner.nextLine();
					}
				} else if (line.equals("#CAT'03: 교육")) {
					docCnt[3]++;
					String next = scanner.nextLine();
					while (!next.contains("#DocID :")) {
						st = new StringTokenizer(next, " |-|,|/|.|<|>|\"|[|]|{|}|'|;|?|!|:|(|)|。|、|#|=|+|*|$|&");
						while (st.hasMoreTokens()) {
							tmp = st.nextToken();
							if (!tmp.contains("@DOCUMENT")) {
								cat[3].add(docid + " " + tmp);
								features.add(tmp);
							}
						}
						next = scanner.nextLine();
					}

				} else if (line.equals("#CAT'03: 문화와 종교")) {
					docCnt[4]++;
					String next = scanner.nextLine();
					while (!next.contains("#DocID :")) {
						st = new StringTokenizer(next, " |-|,|/|.|<|>|\"|[|]|{|}|'|;|?|!|:|(|)|。|、|#|=|+|*|$|&");

						while (st.hasMoreTokens()) {
							tmp = st.nextToken();
							if (!tmp.contains("@DOCUMENT")) {
								cat[4].add(docid + " " + tmp);
								features.add(tmp);
							}
						}
						next = scanner.nextLine();
					}
				} else if (line.equals("#CAT'03: 사회")) {
					docCnt[5]++;
					String next = scanner.nextLine();
					while (!next.contains("#DocID :")) {
						st = new StringTokenizer(next, " |-|,|/|.|<|>|\"|[|]|{|}|'|;|?|!|:|(|)|。|、|#|=|+|*|$|&");

						while (st.hasMoreTokens()) {
							tmp = st.nextToken();
							if (!tmp.contains("@DOCUMENT")) {
								cat[5].add(docid + " " + tmp);
								features.add(tmp);
							}
						}
						next = scanner.nextLine();
					}
				} else if (line.equals("#CAT'03: 산업")) {
					docCnt[6]++;
					String next = scanner.nextLine();
					while (!next.contains("#DocID :")) {
						st = new StringTokenizer(next, " |-|,|/|.|<|>|\"|[|]|{|}|'|;|?|!|:|(|)|。|、|#|=|+|*|$|&");

						while (st.hasMoreTokens()) {
							tmp = st.nextToken();
							if (!tmp.contains("@DOCUMENT")) {
								cat[6].add(docid + " " + tmp);
								features.add(tmp);
							}
						}
						next = scanner.nextLine();
					}
				} else if (line.equals("#CAT'03: 여가생활")) {
					docCnt[7]++;
					String next = scanner.nextLine();
					while (!next.contains("#DocID :")) {
						st = new StringTokenizer(next, " |-|,|/|.|<|>|\"|[|]|{|}|'|;|?|!|:|(|)|。|、|#|=|+|*|$|&");

						while (st.hasMoreTokens()) {
							tmp = st.nextToken();
							if (!tmp.contains("@DOCUMENT")) {
								cat[7].add(docid + " " + tmp);
								features.add(tmp);
							}
						}
						next = scanner.nextLine();
					}
				}

			}

		} catch (Exception e) {
			System.out.println("File not found.");
		}

		//
		fw2 = new BufferedWriter(new FileWriter(result));
		// The number of documents
		int sum = 0;
		for (int i = 0; i < 8; i++) {

			sum += docCnt[i];
		}

		System.out.println("The number of feature : " + features.size());

		/*
		 * Chi- square
		 * (Feature,Category) = N*((A*D)-(B*C))*((A*D)-(B*C))/((A+C)*(B+D)*(A+B)*(C+D))
		 * N : 15978
		 * A : Number of documents with feature in category i
		 * B: Number of documents with no feature in category i (category i total number of documents - A)
		 * C: Number of documents with feature in Category 1-i
		 * D: the number of documents for which category 1-i is not featured (total number of documents in the remaining category-C)
		 */

		// The number of documnets
		int N = sum;
		int A = 0;
		int B = 0;
		int C = 0;
		int D = 0;
		String contents = "";
		TreeMap<Float, String> sorted_map = new TreeMap<Float, String>();

		float res = 0;
		// Extracted Features (remove rebundant) : convert treeset to Arraylist
		// for chi-square
		ArrayList<String> fList = new ArrayList<String>(features);
		// Extracted Features of each categories : convert treeset to Arraylist
		// for chi-square
		ArrayList<String>[] catList = new ArrayList[8];
		for (int i = 0; i < 8; i++)
			catList[i] = new ArrayList<String>(cat[i]);

		StringTokenizer stn;
		for (int i = 0; i < 8; i++) {
			for (int k = 0; k < catList[i].size(); k++) {
				stn = new StringTokenizer(catList[i].get(k), " ");
				stn.nextToken();
				catList[i].set(k, stn.nextToken()); // 수정
			}
		}

		//

		int appearDocSum = 0;
		int num;
		ArrayList<Float> squarelist = new ArrayList<Float>();

		for (int i = 0; i < fList.size(); i++) {
			// The number of documents with the feature in the category
			// Must be initialized each time the feature changes
			int[] appearDoc = new int[8];

			for (int j = 0; j < 8; j++) {
				while ((num = catList[j].indexOf(fList.get(i).trim())) != -1) {

					catList[j].remove(num);
					appearDoc[j]++;
					appearDocSum++;
				}
			}

			// The part that makes the i-th feature chi-square
			for (int l = 0; l < 8; l++) {

				A = appearDoc[l];
				B = docCnt[l] - A;
				C = appearDocSum - appearDoc[l];
				D = sum - docCnt[l] - C;

				float a = A + C;
				float b = B + D;
				float c = A + B;
				float d = C + D;

				squarelist.add((float) ((N * Math.pow((A * D - B * C), 2)) / (a * b * c * d)));
			}

			//Filtering calculated value and Extract the maxValue (automatic removed duplication)
			if (!Collections.max(squarelist).isNaN())
				sorted_map.put(Collections.max(squarelist), fList.get(i).trim());

			appearDocSum = 0;
			squarelist.clear();

		}

		
		Iterator<Float> iteratorKey = sorted_map.descendingKeySet().iterator();
		for (int i = 0; i < sorted_map.size(); i++) {
			Float key = iteratorKey.next();
			fw2.write(++i + " " + sorted_map.get(key) + " " + key + "\n");

		}

		//
		System.out.println("over");
		fw2.flush();
		fw2.close();
	}
}
