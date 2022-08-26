package education_program.web.common.analysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomTree;
import weka.core.Instances;

public class WekaRandomTree {

	public HashMap<String, Object> processWekaRandomTree(String fullFileName, int posY) {
		
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		
		Evaluation evaluation = null;
		RandomTree randomTree = new RandomTree();
		
		try {
			
			Instances instances = new Instances(new BufferedReader(new FileReader(fullFileName)));
			
			Instances trainInstances = new Instances(instances, 0 , instances.numInstances());
			trainInstances.setClassIndex(posY);
			
			Instances testInstances = new Instances(instances, 0 , instances.numInstances());
			testInstances.setClassIndex(posY);
			
			
			randomTree.setOptions(new String[] {"-K", "0", "-M", "1.0", "-V", "0.001", "-S", "1"});
			randomTree.buildClassifier(trainInstances);
			
			evaluation = new Evaluation(trainInstances);
			evaluation.evaluateModel(randomTree, testInstances);
			
		} catch (Exception exception) {
			returnMap.put("RESULT_CODE" , "9999");
			returnMap.put("RESULT_MSG" , "실패");
			
			return returnMap;
		}
		
		returnMap.put("RESULT_CODE" , "0000");
		returnMap.put("RESULT_MSG" , "성공");
		
		returnMap.put("DATA_COUNT" , evaluation.numInstances());
		returnMap.put("CORRECT_COUNT" , evaluation.correct());
		returnMap.put("INCORRECT_COUNT" , evaluation.incorrect());
		
		returnMap.put("ALL" , randomTree.toString());
		
		return returnMap;
	}

}
