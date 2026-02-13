import java.util.*;
import java.io.*;

class Patient implements Serializable {
    int id;
    String name, phone, email;

    Patient(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}

class Appointment implements Serializable {
    int appId;
    int patientId;
    String patientName, doctorName, date, time;

    Appointment(int appId, int patientId, String patientName, String doctorName, String date, String time) {
        this.appId = appId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.date = date;
        this.time = time;
    }
}

public class HospitalApp {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Patient> patients = new ArrayList<>();
    static ArrayList<Appointment> appointments = new ArrayList<>();

    static int patientCounter = 1;
    static int appointmentCounter = 1;

    static final String PATIENT_FILE = "patients.dat";
    static final String APPOINTMENT_FILE = "appointments.dat";

    public static void main(String[] args) {
        loadData();

        while (true) {
            System.out.println("\n===============================");
            System.out.println("HOSPITAL BACKEND (JAVA ONLY)");
            System.out.println("===============================");
            System.out.println("1) Register Patient");
            System.out.println("2) Book Appointment");
            System.out.println("3) View Patients");
            System.out.println("4) View Appointments");
            System.out.println("0) Exit");
            System.out.print("Choose: ");

            int ch = getInt();

            switch (ch) {
                case 1: registerPatient(); break;
                case 2: bookAppointment(); break;
                case 3: viewPatients(); break;
                case 4: viewAppointments(); break;
                case 0:
                    saveData();
                    System.out.println("Saved & Exit. Bye");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    static void registerPatient() {
        System.out.println("\n--- Register Patient ---");
        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Phone: ");
        String phone = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        Patient p = new Patient(patientCounter++, name, phone, email);
        patients.add(p);

        System.out.println("Registered Successfully! Patient ID = " + p.id);
        saveData();
    }

    static void bookAppointment() {
        if (patients.isEmpty()) {
            System.out.println("No patients found! Register first.");
            return;
        }

        System.out.println("\n--- Book Appointment ---");
        System.out.print("Enter Patient ID: ");
        int pid = getInt();

        Patient p = findPatient(pid);
        if (p == null) {
            System.out.println("Patient not found!");
            return;
        }

        System.out.println("Available Doctors:");
        System.out.println("1) Dr.Ramesh - Cardiology");
        System.out.println("2) Dr.Sita - Dermatology");
        System.out.println("3) Dr.Ajay - General");

        System.out.print("Doctor Name (type): ");
        String doctor = sc.nextLine();

        System.out.print("Date (dd-mm-yyyy): ");
        String date = sc.nextLine();

        System.out.print("Time (10AM): ");
        String time = sc.nextLine();

        Appointment a = new Appointment(appointmentCounter++, p.id, p.name, doctor, date, time);
        appointments.add(a);

        System.out.println("Appointment Booked! Appointment ID = " + a.appId);
        saveData();
    }

    static void viewPatients() {
        System.out.println("\n--- Patients ---");
        if (patients.isEmpty()) {
            System.out.println("No patients registered");
            return;
        }
        for (Patient p : patients) {
            System.out.println("ID:" + p.id + " | " + p.name + " | " + p.phone + " | " + p.email);
        }
    }

    static void viewAppointments() {
        System.out.println("\n--- Appointments ---");
        if (appointments.isEmpty()) {
            System.out.println("No appointments");
            return;
        }
        for (Appointment a : appointments) {
            System.out.println("AppID:" + a.appId + " | Patient:" + a.patientName +
                    " | Doctor:" + a.doctorName + " | Date:" + a.date + " | Time:" + a.time);
        }
    }

    static Patient findPatient(int id) {
        for (Patient p : patients) {
            if (p.id == id) return p;
        }
        return null;
    }

    static int getInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Enter valid number: ");
            }
        }
    }

    static void saveData() {
        try {
            ObjectOutputStream out1 = new ObjectOutputStream(new FileOutputStream(PATIENT_FILE));
            out1.writeObject(patients);
            out1.close();

            ObjectOutputStream out2 = new ObjectOutputStream(new FileOutputStream(APPOINTMENT_FILE));
            out2.writeObject(appointments);
            out2.close();

        } catch (Exception e) {
            System.out.println("Save error: " + e.getMessage());
        }
    }

    static void loadData() {
        try {
            File f1 = new File(PATIENT_FILE);
            if (f1.exists()) {
                ObjectInputStream in1 = new ObjectInputStream(new FileInputStream(PATIENT_FILE));
                patients = (ArrayList<Patient>) in1.readObject();
                in1.close();

                int max = 0;
                for (Patient p : patients) if (p.id > max) max = p.id;
                patientCounter = max + 1;
            }

            File f2 = new File(APPOINTMENT_FILE);
            if (f2.exists()) {
                ObjectInputStream in2 = new ObjectInputStream(new FileInputStream(APPOINTMENT_FILE));
                appointments = (ArrayList<Appointment>) in2.readObject();
                in2.close();

                int maxA = 0;
                for (Appointment a : appointments) if (a.appId > maxA) maxA = a.appId;
                appointmentCounter = maxA + 1;
            }
        } catch (Exception e) {
            // ignore first time
        }
    }
}
