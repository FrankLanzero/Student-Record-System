public class OFES {
    //Declaration of Attributes
    private String courseCode;
    private int academicYear;
    private int term;
    private double[] averageRating = new double[8];
    //Constructor when creating an OFES object after grading a staff
    public OFES(String courseCode, int academicYear, int term, double[] averageRating){
        this.courseCode = courseCode;
        this.academicYear = academicYear;
        this.term = term;
        System.arraycopy(averageRating, 0, this.averageRating, 0, 8);
    }
    //Loading OFES from the file OFESRecords.csv
    public OFES(String[] values){
        this.courseCode = values[1];
        this.academicYear = Integer.parseInt(values[2]);
        this.term = Integer.parseInt(values[3]);
        for(int i = 0; i < 8; i++){
            this.averageRating[i] = Double.parseDouble(values[i + 4]);
        }
    }

    //GET SETTERS
    public int getAcademicYear() {
        return academicYear;
    }

    public int getTerm() {
        return term;
    }

    public String getCourseCode() {
        return courseCode;
    }
    public double getAverageRating(int loc){
        return this.averageRating[loc];
    }
    public double[] getOFESRating(){
        return this.averageRating;
    }
}
