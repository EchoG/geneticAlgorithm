package qosweb.cached;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.function.Increment;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Variable;

import qosweb.WebService;
import qosweb.WebServiceCluster;

/**
 * @author carlos
 *
 */
public class SimpleQosTest extends GPProblem {
	
   private WebServiceCluster _sc1;
   private WebServiceCluster _sc2;
   private WebServiceCluster _sc3;

   // var
   private Variable _sc1IndexVar;
   private Variable _sc2IndexVar;
   private Variable _sc3IndexVar;

    public SimpleQosTest() throws InvalidConfigurationException {
        super(new GPConfiguration());
        
        insertSampleData();
        

        GPConfiguration config = getGPConfiguration();

        _sc1IndexVar = Variable.create(config, "SC1_Index", CommandGene.IntegerClass);
        _sc2IndexVar = Variable.create(config, "SC2_Index", CommandGene.IntegerClass);
        _sc3IndexVar = Variable.create(config, "SC3_Index", CommandGene.IntegerClass);

        config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
        config.setMaxInitDepth(4);
        config.setPopulationSize(1000);
        config.setMaxCrossoverDepth(8);
        config.setFitnessFunction(new SimpleQosTestFitnessFunction(_sc1, _sc2, _sc3, _sc1IndexVar, _sc2IndexVar, _sc3IndexVar));
        config.setStrictProgramCreation(true);
    }

    @Override
    public GPGenotype create() throws InvalidConfigurationException {
        GPConfiguration config = getGPConfiguration();

        // The return type of the GP program.
        Class[] types = { CommandGene.IntegerClass };

        // Arguments of result-producing chromosome: none
        Class[][] argTypes = { {} };

        // Next, we define the set of available GP commands and terminals to
        // use.
        CommandGene[][] nodeSets = {
            {
            	_sc1IndexVar,
            	_sc2IndexVar,
            	_sc3IndexVar,
                new Increment(config, CommandGene.IntegerClass)
            }
        };

        GPGenotype result = GPGenotype.randomInitialGenotype(config, types, argTypes,
                nodeSets, 20, true);

        return result;
    }

    public static void main(String[] args) throws Exception {
        GPProblem problem = new SimpleQosTest();

        GPGenotype gp = problem.create();
        gp.setVerboseOutput(true);
        gp.evolve(30);

        System.out.println("Formulaiscover: x^2 + 2y + 3x + 5");
        gp.outputSolution(gp.getAllTimeBest());
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

    	_sc1 = new WebServiceCluster("SC3");
    	_sc1.getServices().add(new WebService("S31", 11, .97, 9, .97));
    	_sc1.getServices().add(new WebService("S32", 12, .89, 12, .89));
    	_sc1.getServices().add(new WebService("S33", 12, .90, 1, .90));
    	_sc1.getServices().add(new WebService("S34", 15, .91, 3, .98));
    	_sc1.getServices().add(new WebService("S35", 18, .56, 6, .56));
    	_sc1.getServices().add(new WebService("S36", 23, .68, 2, .67));
    	_sc1.getServices().add(new WebService("S37", 22, .59, 1, .59));
    	_sc1.getServices().add(new WebService("S38", 21, .92, 2, .89));
    }
    
    
}