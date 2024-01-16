import java.util.ArrayList;

public class CourseCard {
    //Declaration of attributes, some are final
    private int year;
    private int term;
    private int schoolYear;
    private boolean graded;
    private ArrayList<String> gradedStatus = new ArrayList<>();
    private ArrayList<String> ratedStatus = new ArrayList<>();
    private ArrayList<String> courses = new ArrayList<>();
    private ArrayList<String> section = new ArrayList<>();
    private ArrayList<Double> midtermGrades = new ArrayList<>();
    private ArrayList<Double> finalGrades = new ArrayList<>();
    public CourseCard(){}
    //Constructor after loading from StudentGrades.csv
    public CourseCard(String[] values, ArrayList<String> grades){
        //Initializes all attributes using values from the arguments

        this.schoolYear = Integer.parseInt(values[1]);
        this.year = Integer.parseInt(values[2]);
        this.term = Integer.parseInt(values[3]);
        this.graded = Boolean.parseBoolean(values[4]);

        for (String line : grades) {
            String[] parts = line.split(" - ");
            this.courses.add(parts[0]);
            this.midtermGrades.add(Double.parseDouble(parts[1]));
            this.finalGrades.add(Double.parseDouble(parts[2]));
            this.gradedStatus.add(parts[3]);
            this.ratedStatus.add(parts[4]);
            this.section.add(parts[5]);
        }
    }
    //New course card after confirming enrollment
    public CourseCard(int schoolYear, int yearLevel, int term, ArrayList<Course> newTerm) {
        this.schoolYear = schoolYear;
        this.year = yearLevel;
        this.term = term;
        for(Course x : newTerm){
            this.courses.add(x.getCourseCode());
            this.ratedStatus.add("UNRATED");
            this.section.add(x.getSection());
            this.gradedStatus.add("UNGRADED");
            //5.0 means ungraded so far
            this.midtermGrades.add(5.0);
            this.finalGrades.add(5.0);
        }
        this.graded = false;

    }
    //Will check if a student is passed on one of their subjects
    public boolean passOrFail(String courseCode){
        for(int i = 0; i < courses.size(); i++){
            if(courses.get(i).equals(courseCode)){
                if(gradedStatus.get(i).equals("PASSED")){
                    return true;
                }
            }
        }
        return false;
    }
    //Grades course card and adds the grades in the ArrayLists
    //Also indicates if passed or not
    public void gradeCourseCard(ArrayList<Double> midtermGrades, ArrayList<Double> finalGrades) {
        this.midtermGrades.clear();
        this.finalGrades.clear();
        this.gradedStatus.clear();
        for(int i = 0; i < midtermGrades.size(); i++){
            this.midtermGrades.add(midtermGrades.get(i));
            this.finalGrades.add(finalGrades.get(i));
            if(finalGrades.get(i) != 0.5){
                this.gradedStatus.add("PASSED");
            }
            else{
                this.gradedStatus.add("FAILED");
            }
        }
        this.graded = true;
    }
    //After answering OFES, indicates that all subjects have been RATED
    public void rateAll(){
        for(int i = 0; i < ratedStatus.size(); i++){
            this.ratedStatus.set(i, "RATED");
        }
    }
    public boolean isGraded() {
        return graded;
    }

    //GET SETTERS
    public ArrayList<String> getCourses() {
        return courses;
    }
    public ArrayList<String> getGradedStatus(){
        return gradedStatus;
    }
    public ArrayList<String> getRatedStatus(){
        return ratedStatus;
    }
    public ArrayList<String> getSection(){
        return section;
    }
    public ArrayList<Double> getFinalGrades(){
        return finalGrades;
    }
    public ArrayList<Double> getMidtermGrades(){
        return midtermGrades;
    }
    public int getTerm(){
        return term;
    }
    public int getYearLevel(){
        return year;
    }
    //Returns value if a course card has been graded
    public int getSchoolYear() {
        return schoolYear;
    }
}
