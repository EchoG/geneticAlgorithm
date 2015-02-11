/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licensing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */
package qosweb;

import org.jgap.*;

/**
 * Sample fitness function for the MakeChange example.
 * 
 */
public class QosWebFitnessFunction extends FitnessFunction {

	private WebServiceCluster _sc1;
	private WebServiceCluster _sc2;
	private WebServiceCluster _sc3;
	
	public QosWebFitnessFunction(WebServiceCluster _sc1,
			WebServiceCluster _sc2, WebServiceCluster _sc3) {
		super();
		this._sc1 = _sc1;
		this._sc2 = _sc2;
		this._sc3 = _sc3;
	}

	public double evaluate(IChromosome a_subject) {
		boolean defaultComparation = a_subject.getConfiguration()
				.getFitnessEvaluator().isFitter(2, 1);
		double result = calculate(a_subject);
		if (!defaultComparation) return 0;
		return result;
	}

	public double calculate(IChromosome a_potentialSolution) {
		int noS1 = getNumberOfServiceAtGene(a_potentialSolution, 0);
		int noS2 = getNumberOfServiceAtGene(a_potentialSolution, 1);
		int noS3 = getNumberOfServiceAtGene(a_potentialSolution, 2);
		
		WebService s1 = _sc1.getServices().get(noS1);
		WebService s2 = _sc2.getServices().get(noS2);
		WebService s3 = _sc3.getServices().get(noS3);
		
        Integer cost = s1.getCost() + s2.getCost() + s3.getCost();
        double reliability = s1.getReliability() * s2.getReliability() * s3.getReliability();
        Integer time = s1.getTime() + s2.getTime() + s3.getTime();
        double availability = s1.getAvailability() * s2.getAvailability() * s3.getAvailability();

		return cost*.35 + time*.30 + reliability *.30 + availability;
	}

	public static int getNumberOfServiceAtGene(IChromosome a_potentialSolution,
			int a_position) {
		Integer serviceNo = (Integer) a_potentialSolution.getGene(a_position)
				.getAllele();
		return serviceNo.intValue();
	}

}
