package qosweb.cached;

import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.terminal.Variable;

import qosweb.WebService;
import qosweb.WebServiceCluster;

public class SimpleQosTestFitnessFunction extends GPFitnessFunction {

    private WebServiceCluster _sc1;
    private WebServiceCluster _sc2;
    private WebServiceCluster _sc3;
    
    // var
    private Variable _sc1Workflow;
    private Variable _sc2Workflow;
    private Variable _sc3Workflow;

    private static Object[] NO_ARGS = new Object[0];

    public SimpleQosTestFitnessFunction(WebServiceCluster _sc1, WebServiceCluster _sc2, WebServiceCluster _sc3,
    		Variable _sc1IndexVar, Variable _sc2IndexVar, Variable _sc3IndexVar) {
    	this._sc1 = _sc1;
    	this._sc2 = _sc2;
    	this._sc3 = _sc3;
    	this._sc1Workflow = _sc1IndexVar;
    	this._sc2Workflow = _sc2IndexVar;
    	this._sc3Workflow = _sc3IndexVar;
    }

    @Override
    protected double evaluate(final IGPProgram program) {
        double result = 0; 
        _sc1Workflow.set(0);
        _sc2Workflow.set(0);
        _sc3Workflow.set(0);
    	// calculate the result
        
        int value = program.execute_int(0, NO_ARGS);
        System.out.println(value);
        WebService s1 = _sc1.getServices().get(value);

        value = program.execute_int(0, NO_ARGS);
        System.out.println(value);
        WebService s2 = _sc2.getServices().get(value);

        value = program.execute_int(0, NO_ARGS);
        System.out.println(value);
        WebService s3 = _sc3.getServices().get(value);
        
        Integer cost = s1.getCost() + s2.getCost() + s3.getCost();
        double reliability = s1.getReliability() * s2.getReliability() * s3.getReliability();
        Integer time = s1.getTime() + s2.getTime() + s3.getTime();
        double availability = s1.getAvailability() * s2.getAvailability() * s3.getAvailability();
                
        // how to fit
        result = cost*.35 + time*.30 + reliability *.30 + availability;
        
        return result;
    }

}
