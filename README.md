# Chi-square-test-with-corpus
Data mining

## Experiment Description  
There are three major processes.   
**1) Data preprocessing   
2) Sorting in chi-square maximum value by feature   
3) Indexing of category feature by document (ascending order): chi-square value**    


Then read the preprocessing data file to extract the feature. And count how many documents a category has in each category. Using this, chi-square values are obtained, max values per feature are extracted, sorted in descending order, and an index value is added to generate a text file.  

The chi reads the sorted file, maps the preprocessing data file with each category and article, creates a train file, and creates a test file by mapping the article with HKIB-20000_005, preprocessed in the same way.  
Finally, check the results using lib_svm.


## Preprocessing Description  
In the data preprocessing process, only the files of HKIB-20000_001.txt ~ 004 were read and only three items were selected as DocID, CAT'03, and the content of the article excluding <KW>. That is, I made four files into one file containing only the above three items.
Each feature was extracted by adding #, =, + ,,, $, & & in addition to default  

## Performance analysis
**Develop	Environment** :  
Language : JAVA	  
Tool : Eclipse   
OS	: Window10  
CPU :	i3-6100U  
RAM : 4GB  

**Train/test time**   
Train – In 1min   
Test - 1min 30sec     
**The number of line**   
Train - 550 lines   
Test – 175lines  

**Accuracy**  
65.0548%
