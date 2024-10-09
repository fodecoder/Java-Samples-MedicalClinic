package clinic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.TreeMap;

public class Clinic {
	private TreeMap<String, Person> patients = new TreeMap<>();
	private TreeMap<Integer, Doctor> doctors = new TreeMap<>();

	public void addPatient(String first, String last, String ssn) {
		Person p = new Person(first, last, ssn);
		patients.put(ssn, p);
	}

	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
		Doctor d = new Doctor(first, last, ssn, docID, specialization);
		doctors.put(docID, d);
	}

	public Person getPatient(String ssn) throws NoSuchPatient {
		Person p = patients.get(ssn);

		if (p != null)
			return p;
		throw new NoSuchPatient();
	}

	public Doctor getDoctor(int docID) throws NoSuchDoctor {
		Doctor d = (Doctor) doctors.get(docID);

		if (d != null)
			return d;
		throw new NoSuchDoctor();
	}

	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {
		Person p = patients.get(ssn);
		Doctor d = (Doctor) doctors.get(docID);

		if (p == null)
			throw new NoSuchPatient();
		else if (d == null)
			throw new NoSuchDoctor();

		p.setDoctor(d);
		d.addPatient(p);
	}

	/**
	 * returns the collection of doctors that have no patient at all, sorted in
	 * alphabetic order.
	 */
	Collection<Doctor> idleDoctors() {

		return doctors.values().stream().filter(d -> d.getPatients().isEmpty())
				.sorted((d1, d2) -> d1.surname.compareTo(d2.surname)).collect(new MyCollector());

	}

	/**
	 * returns the collection of doctors that a number of patients larger than
	 * the average.
	 */
	Collection<Doctor> busyDoctors() {

		Long numD = doctors.values().stream().count();
		Long totPatients = (long) 0;
		Long avg;

		for (Doctor d : doctors.values()) {

			totPatients += d.getPatients().stream().count();

		}

		avg = totPatients / numD;

		return doctors.values().stream().filter(d -> {
			return d.getPatients().stream().count() >= avg;
		}).collect(new MyCollector());
	}

	/**
	 * returns list of strings containing the name of the doctor and the
	 * relative number of patients with the relative number of patients, sorted
	 * by decreasing number.<br>
	 * The string must be formatted as "<i>### : ID SURNAME NAME</i>" where
	 * <i>###</i> represent the number of patients (printed on three
	 * characters).
	 */
	Collection<String> doctorsByNumPatients() {

		LinkedList<String> docBNP = new LinkedList<>();

		for (Doctor d : doctors.values()) {

			Long num = d.getPatients().stream().count();
			String s;

			s = String.format("%3l", num) + " : " + d.getId() + " " + d.surname + " " + d.name;

			docBNP.add(s);

		}

		docBNP.sort((a, b) -> Integer.parseInt(a) - Integer.parseInt(b));

		return docBNP;
	}

	/**
	 * computes the number of patients per (their doctor's) specialization. The
	 * elements are sorted first by decreasing count and then by alphabetic
	 * specialization.<br>
	 * The strings are structured as "<i>### - SPECIALITY</i>" where <i>###</i>
	 * represent the number of patients (printed on three characters).
	 */
	public Collection<String> countPatientsPerSpecialization() {

		LinkedList<String> docBPS = new LinkedList<>();
		LinkedList<String> specs = new LinkedList<>();
		String tmp;
		
		for (Doctor d : doctors.values()) {

			if (!specs.contains(d.getSpec())){
				
				specs.add(d.getSpec());
				
			}

		}
		
		for (String s : specs){
			
			Long numP = (long) 0;
			
			for (Doctor d : doctors.values()) {
				
				if (d.getSpec().equals(s)){
					
					numP += d.getPatients().stream().count();
					
				}

			}
			
			tmp = String.format("%3l", numP) + " - " + s;
			
			docBPS.add(tmp);
			
		}

		docBPS.sort((a, b) -> Integer.parseInt(b) - Integer.parseInt(a) );
		docBPS.sort((a, b) -> a.compareTo(b));

		return docBPS;
	}

	public void loadData(String path) throws IOException {

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			String line;
			LinkedList<String> lines = new LinkedList<>();
			while ((line = br.readLine()) != null) {

				lines.add(line);

			}

			br.close();

			lines.forEach(r -> {

				String[] row = r.split(";");
				if (row[0].equals("P")) {

					this.addPatient(row[1], row[2], row[3]);

				} else if (row[0].equals("M")) {

					this.addDoctor(row[2], row[3], row[4], Integer.parseInt(row[1]), row[5]);

				}

			});
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
