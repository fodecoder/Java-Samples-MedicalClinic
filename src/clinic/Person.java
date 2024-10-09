package clinic;

public class Person {
	
	protected String name, surname, ssn;
	protected Doctor doctor;
	
	public Person(String name, String surname, String ssn) {
		super();
		this.ssn = ssn;
		this.name = name;
		this.surname = surname;
	}
	
	public String getSSN(){
		return ssn;
	}

	public String getFirst() {
		return name;
	}

	public String getLast() {
		return surname;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

}
