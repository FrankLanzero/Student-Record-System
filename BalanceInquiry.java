import java.util.ArrayList;

public class BalanceInquiry {
    //Attributes that describe a balance inquiry in the student record system
    private int term;
    private int studentNumber;
    private int yearLevel;
    private int schoolYear;
    //Only non-final attribute
    //fullyPaid will update once the student has fully paid
    private boolean fullyPaid;
    private double totalAssessment;
    private ArrayList<Double> paymentList = new ArrayList<>();
    private ArrayList<String> paymentDate = new ArrayList<>();

    //Constructor for when the BalanceInquiry is made from the StudentFinance.csv
    public BalanceInquiry(double totalAssessment, String[] generalInfo, ArrayList<String> paymentsAndDate) {
        this.totalAssessment = totalAssessment;
        this.studentNumber = Integer.parseInt(generalInfo[0]);
        this.schoolYear = Integer.parseInt(generalInfo[1]);
        this.yearLevel = Integer.parseInt(generalInfo[2]);
        this.term = Integer.parseInt(generalInfo[3]);
        this.fullyPaid = false;
        for (String entry : paymentsAndDate) {
            String[] parts = entry.split(" - ");

            this.paymentList.add(Double.parseDouble(parts[0]));
            this.paymentDate.add(parts[1]);
        }
        double cumulative = 0;
        for(Double x : this.paymentList){
            cumulative += x;
        }
        if(cumulative >= totalAssessment){
            this.fullyPaid = true;
        }
    }
    //Constructor for when the BalanceInquiry is made after a student enrolls
    public BalanceInquiry(int schoolYear, int yearLevel, int term, Student student, double totalAssessment) {
        this.totalAssessment = totalAssessment;
        this.studentNumber = student.getStudentNumber();
        this.schoolYear = schoolYear;
        this.yearLevel = yearLevel;
        this.term = term;
        this.fullyPaid = false;
    }
    //Using GUI, displays all the payments made by a student in a term
    public void displayPayments(Student student) {
        new GUI(this, student);
    }
    //After paying, balance inquiry will be added values
    //Will also check if a student is paid after paying
    public void addPayment(double payment, String date){
        this.paymentList.add(payment);
        this.paymentDate.add(date);

        updatePaymentLeft();
    }
    //Returns the remaining balance through an algorithm that takes the total payments
    //Subtracted from total assessment
    public double getRemainingBalance(){
        double sum = 0;
        for(Double x : this.paymentList){
            sum += x;
        }
        return this.totalAssessment - sum;
    }
    //Will update the current assessment if the student is paid
    public void updatePaymentLeft(){
        double cumulative = 0;
        for(Double x : paymentList){
            cumulative += x;
        }
        this.fullyPaid = cumulative >= this.totalAssessment;
    }
    //GET SETTERS
    public int getTerm() {
        return this.term;
    }
    public int getSchoolYear() {
        return this.schoolYear;
    }
    public int getYearLevel() {
        return this.yearLevel;
    }
    public ArrayList<Double> getPaymentList() {
        return this.paymentList;
    }
    public ArrayList<String> getPaymentDate() {
        return this.paymentDate;
    }
    public boolean getFullyPaid(){
        return this.fullyPaid;
    }
    public double getTotalAssessment(){
        return this.totalAssessment;
    }

    public int getStudentNumber() {
        return studentNumber;
    }
}
