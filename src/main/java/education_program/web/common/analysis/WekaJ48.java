package education_program.web.common.analysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public class WekaJ48 {
	
	
	public static void main(String[] args) throws Exception {
		
		/*
		int seed = 1;
		int numfolds = 10;
		int numfold = 1;
		*/
		
		Instances instances = new Instances(new BufferedReader(new FileReader("C:\\Program Files\\Weka-3-8-6\\data\\iris.arff")));
		
		//Instances train = instances.trainCV(numfolds, numfold, new Random(seed));
		//Instances test  = instances.testCV (numfolds, numfold);
		
		Instances trainInstances = new Instances(instances, 0 , 150);
		trainInstances.setClassIndex(4); //종속 변수의 위치
		Instances testInstances = new Instances(instances, 0 , 150);
		testInstances.setClassIndex(4); //종속 변수의 위치
		
		J48 j48 = new J48();
		j48.setOptions(new String[] {"-C", "0.25", "-M", "2" });
		j48.buildClassifier(trainInstances); //J48 알고리즘을 활용하여 모델 생성
		
		System.out.println(j48.toString());
		
		Evaluation evaluation = new Evaluation(trainInstances);
		evaluation.evaluateModel(j48, testInstances);
		
		System.out.println("테스트 데이터 총 수 : " + evaluation.numInstances());
		System.out.println("정분류 개수 및 정분류 율 : " + evaluation.correct());
		System.out.println("오분류 개수 및 정분류 율 : " + evaluation.incorrect());
	}
	
	
}
