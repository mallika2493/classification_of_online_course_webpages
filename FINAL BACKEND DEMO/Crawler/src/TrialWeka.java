 import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.filters.unsupervised.attribute.Standardize;

public class TrialWeka {
	
	public static void main(String args[]) throws Exception

	{
		DataSource source = new DataSource("UmichTrainDemo.csv.arff");
		 Instances data = source.getDataSet();
	
		 if (data.classIndex() == -1)
			   data.setClassIndex(data.numAttributes() - 1);
		 
		 String[] options = new String[1];
		 options[0] = "-U";            // unpruned tree
		 RandomForest rf = new RandomForest();         // new instance of tree
		 	// set the options
		 rf.buildClassifier(data);   // build classifier
		 
		 Instances unlabeled = new DataSource("UmichDemo.csv.arff").getDataSet();
		 unlabeled.setClassIndex(unlabeled.numAttributes() - 1);
		 NumericToNominal nton = new NumericToNominal();
		 nton.setInputFormat(unlabeled);
		 Filter.useFilter(unlabeled, nton);
		// create copy
		 Instances labeled = new Instances(unlabeled);
		 
		 // label instances
		 for (int i = 0; i < unlabeled.numInstances(); i++) {
		   double clsLabel = rf.classifyInstance(unlabeled.instance(i));
		   labeled.instance(i).setClassValue(clsLabel);
		 }
		 // save labeled data
		 BufferedWriter writer = new BufferedWriter(
		                           new FileWriter("UmichLabelledDemo.csv"));
		 writer.write(labeled.toString());
		 writer.newLine();
		 writer.flush();
		 writer.close();
		
	
	}
}
