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

import java.io.*;

import org.jgap.*;
import org.jgap.data.*;
import org.jgap.impl.*;
import org.jgap.xml.*;
import org.w3c.dom.*;

/**
 * 
 */
public class QosWebTest {

	private WebServiceCluster _sc1;
	private WebServiceCluster _sc2;
	private WebServiceCluster _sc3;

	/**
	 * The total number of times we'll let the population evolve.
	 */
	private static final int MAX_ALLOWED_EVOLUTIONS = 200;

	/**
	 * Executes the genetic algorithm to determine the minimum number of coins
	 * necessary to make up the given target amount of change. The solution will
	 * then be written to System.out.
	 * 
	 * @param a_targetChangeAmount
	 *            the target amount of change for which this method is
	 *            attempting to produce the minimum number of coins
	 * @throws Exception
	 *  
	 * @since 1.0
	 */
	public void calculateQosWeb()
			throws Exception {
		Configuration conf = new DefaultConfiguration();
		conf.setPreservFittestIndividual(true);
		insertSampleData();
		FitnessFunction myFunc = new QosWebFitnessFunction(_sc1, _sc2, _sc3);
		conf.setFitnessFunction(myFunc);

		Gene[] sampleGenes = new Gene[3];
		sampleGenes[0] = new IntegerGene(conf, 0, _sc1.getServices().size()-1); // Service Cluster 1
		sampleGenes[1] = new IntegerGene(conf, 0, _sc2.getServices().size()-1); // Service Cluster 2
		sampleGenes[2] = new IntegerGene(conf, 0, _sc3.getServices().size()-1); // Service Cluster 3

		IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);
		conf.setSampleChromosome(sampleChromosome);
		conf.setPopulationSize(80);
		Genotype population;
		population = Genotype.randomInitialGenotype(conf);	// use random init
		
		// Now we initialize the population randomly, anyway (as an example
		// only)!
		// If wanna to load previous results from file, remove the next line!
		// -----------------------------------------------------------------------
		population = Genotype.randomInitialGenotype(conf);
		// Evolve the population. Since I don't know what the best answer
		// is going to be, I just evolve the max number of times.
		// ---------------------------------------------------------------
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
			if (!uniqueChromosomes(population.getPopulation())) {
				throw new RuntimeException("Invalid state in generation " + i);
			}
			population.evolve();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Evolved: " + MAX_ALLOWED_EVOLUTIONS + " generations");
		System.out.println("Total evolution time: " + (endTime - startTime) + " ms");
		// Save progress to file. A new run of this example will then be able to
		// resume where it stopped before! --> this is completely optional.
		// ---------------------------------------------------------------------

		// Represent Genotype as tree with elements Chromomes and Genes.
		// -------------------------------------------------------------
		DataTreeBuilder builder = DataTreeBuilder.getInstance();
		IDataCreators doc2 = builder.representGenotypeAsDocument(population);
		// create XML document from generated tree
		XMLDocumentBuilder docbuilder = new XMLDocumentBuilder();
		Document xmlDoc = (Document) docbuilder.buildDocument(doc2);
		XMLManager.writeFile(xmlDoc, new File("JGAPExample26.xml"));
		// Display the best solution found.
		// -----------------------------------
		IChromosome bestSolutionSoFar = population.getFittestChromosome();
		System.out.println("The best solution has a fitness value of "
				+ bestSolutionSoFar.getFitnessValue());
		System.out.println("It contained the following: ");
		System.out.println("\t Service 1"
				+ (QosWebFitnessFunction.getNumberOfServiceAtGene(
						bestSolutionSoFar, 0) + 1));
		System.out.println("\t Service 2"
				+ (QosWebFitnessFunction.getNumberOfServiceAtGene(
						bestSolutionSoFar, 1) + 1));
		System.out.println("\t Service 3"
				+ (QosWebFitnessFunction.getNumberOfServiceAtGene(
						bestSolutionSoFar, 2) + 1));
	}

	public static void main(String[] args) throws Exception {
		QosWebTest test = new QosWebTest();
		test.calculateQosWeb();
	}

	/**
	 * @param a_pop
	 *            the population to verify
	 * @return true if all chromosomes in the populationa are unique
	 * 
	 * @since 3.3.1
	 */
	public static boolean uniqueChromosomes(Population a_pop) {
		// Check that all chromosomes are unique
		for (int i = 0; i < a_pop.size() - 1; i++) {
			IChromosome c = a_pop.getChromosome(i);
			for (int j = i + 1; j < a_pop.size(); j++) {
				IChromosome c2 = a_pop.getChromosome(j);
				if (c == c2) {
					return false;
				}
			}
		}
		return true;
	}

	public void insertSampleData() {
		_sc1 = new WebServiceCluster("SC1");
		_sc1.getServices().add(new WebService("S11", 20, .95, 2, .95));
		_sc1.getServices().add(new WebService("S12", 30, .99, 3, .89));
		_sc1.getServices().add(new WebService("S13", 23, .98, 23, .98));
		_sc1.getServices().add(new WebService("S14", 12, .99, 1, .02));
		_sc1.getServices().add(new WebService("S15", 25, .89, 3, .78));

		_sc2 = new WebServiceCluster("SC2");
		_sc2.getServices().add(new WebService("S21", 12, .70, 3, .70));
		_sc2.getServices().add(new WebService("S22", 15, .99, 5, .93));
		_sc2.getServices().add(new WebService("S23", 53, .96, 8, .96));

		_sc3 = new WebServiceCluster("SC3");
		_sc3.getServices().add(new WebService("S31", 11, .97, 9, .97));
		_sc3.getServices().add(new WebService("S32", 12, .89, 12, .89));
		_sc3.getServices().add(new WebService("S33", 12, .90, 1, .90));
		_sc3.getServices().add(new WebService("S34", 15, .91, 3, .98));
		_sc3.getServices().add(new WebService("S35", 18, .56, 6, .56));
		_sc3.getServices().add(new WebService("S36", 23, .68, 2, .67));
		_sc3.getServices().add(new WebService("S37", 22, .59, 1, .59));
		_sc3.getServices().add(new WebService("S38", 21, .92, 2, .89));
	}

}
