package clinic;

import java.util.Collection;
import java.util.HashSet;

public class Doctor extends Person {

	private String spec;
	private int id;
	private Collection<Person> patients = new HashSet<>();
	
	public Doctor(String name, String surname, String ssn) {
		super(name, surname, ssn);
	}

	public Doctor(String name, String surname, String ssn, int id, String spec) {
		super(name, surname, ssn);
		this.id = id;
		this.spec = spec;
	}

	public int getId(){
		return id;
	}
	
	public String getSpecialization(){
		return spec;
	}
	
	public Collection<Person> getPatients() {
		return patients;
	}
	
	public void addPatient(Person p){
		
		patients.add(p);
		
	}

	public String getSpec() {
		return spec;
	}

}
