import java.util.*;

public class Patient extends User {
    private int age; // patient's age
    private String birthday; // patient's birthday
    private String sex; // patient's biological sex
    private String gender_identification; // patient's gender identification
    private int height; // patient's height
    private int weight; // patient's weight

    LinkedList<String> medicalHistory = new LinkedList<>();
    LinkedList<String> medicationHistory = new LinkedList<>();

    // Grabs the Patient's ID and Name from the Parent/User Class
    public Patient(String id, String name) {
        super(id, name);
    }

    /* I didn't do these two functions right
    public String getMedicalHistory() {
        System.out.println(medicalHistory);
    }

    public String getMedications() {
        System.out.println(medicationHistory);
    }
     */

    // Outputs the Profile Information
    public void viewProfile() {
        System.out.println("Patient ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Birthday: " + birthday);
        System.out.println("Sex: " + sex);
        System.out.println("Gender: " + gender_identification);
        System.out.println("Height: " + height);
        System.out.println("Weight: " + weight);
    }
}