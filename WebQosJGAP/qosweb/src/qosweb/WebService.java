/**
 * 
 */
package qosweb;

import org.jgap.gp.CommandGene;

/**
 *
 */
public class WebService {
	
	protected String name;
	protected Integer cost;
	protected double reliability;
	protected Integer time;
	protected double availability;
	
	public WebService(String name, Integer cost, double reliability,
			Integer time, double availability) {
		super();
		this.name = name;
		this.cost = cost;
		this.reliability = reliability;
		this.time = time;
		this.availability = availability;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCost() {
		return cost;
	}
	public void setCost(Integer cost) {
		this.cost = cost;
	}
	public double getReliability() {
		return reliability;
	}
	public void setReliability(double reliability) {
		this.reliability = reliability;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public double getAvailability() {
		return availability;
	}
	public void setAvailability(double availability) {
		this.availability = availability;
	}
	
	
}
