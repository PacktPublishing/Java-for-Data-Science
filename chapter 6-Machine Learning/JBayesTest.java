package com.packt.java.jayes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.System.out;
import com.github.vangj.jbayes.inf.prob.Graph;
import com.github.vangj.jbayes.inf.prob.Node;
import com.github.vangj.jbayes.inf.prob.util.CsvUtil;

public class JBayesTest {
	
	public static void main(String[] args){
		//each node must have its name and values defined
		Node storms = Node.newBuilder().name("Thunderstorm").value("t").value("f").build();
		Node traffic = Node.newBuilder().name("Traffic").value("t").value("f").build();
		Node powerOut = Node.newBuilder().name("PowerOutage").value("t").value("f").build();
		Node alarm = Node.newBuilder().name("Alarm").value("t").value("f").build();
		Node overslept = Node.newBuilder().name("Overslept").value("t").value("f").build();
		Node lateToWork = Node.newBuilder().name("LateToWork").value("t").value("f").build();

		//nodes may have parents
		traffic.addParent(storms);
		powerOut.addParent(storms);
		lateToWork.addParent(traffic);
		alarm.addParent(powerOut);
		overslept.addParent(alarm);
		lateToWork.addParent(overslept);

		//define the CPTs for each node
		storms.setCpt(new double[][] {
			{0.7, 0.3}
		});
		traffic.setCpt(new double[][] {
			{0.8, 0.2}
		});
		powerOut.setCpt(new double[][] {
			{0.5, 0.5}
		});
		alarm.setCpt(new double[][] {
			{0.7, 0.3}
		});
		overslept.setCpt(new double[][] {
			{0.5, 0.5}
		});
		lateToWork.setCpt(new double[][] {
			{0.5, 0.5},
			{0.5, 0.5}
		});

		//create a graph from the nodes
		Graph bayesGraph = new Graph();
		bayesGraph.addNode(storms);
		bayesGraph.addNode(traffic);
		bayesGraph.addNode(powerOut);
		bayesGraph.addNode(alarm);
		bayesGraph.addNode(overslept);
		bayesGraph.addNode(lateToWork);

		//samples and computes the marginal probabilities aka the inference
		double d = bayesGraph.sample(1000);
		out.println(d);

		//look at the marginal probabilities
		double[] stormProb = storms.probs();
		double[] trafficProb = traffic.probs();
		double[] powerProb = powerOut.probs();
		double[] alarmProb = alarm.probs();
		double[] oversleptProb = overslept.probs();
		double[] lateProb = lateToWork.probs();
		
		out.println("\nStorm Probabilities");
		out.println("True: " + stormProb[0] + " False: " + stormProb[1]);
		out.println("\nTraffic Probabilities");
		out.println("True: " + trafficProb[0] + " False: " + trafficProb[1]);
		out.println("\nPower Outage Probabilities");
		out.println("True: " + powerProb[0] + " False: " + powerProb[1]);
		out.println("vAlarm Probabilities");
		out.println("True: " + alarmProb[0] + " False: " + alarmProb[1]);
		out.println("\nOverslept Probabilities");
		out.println("True: " + oversleptProb[0] + " False: " + oversleptProb[1]);
		out.println("\nLate to Work Probabilities");
		out.println("True: " + lateProb[0] + " False: " + lateProb[1]);

		bayesGraph.setSaveSamples(true); //stores samples in memory!
		bayesGraph.sample(100);

		try {
			CsvUtil.saveSamples(bayesGraph, new FileWriter(new File("C://Jenn Personal//Packt Data Science//Chapter 6 Machine Learning//jbayes.csv")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //save samples into CSV file

		bayesGraph.clearSamples(); //clear samples, this might help with memory usage
	}

}
