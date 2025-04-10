import java.util.ArrayList;

public class Students {
    private ArrayList<Student> studentsList = new ArrayList<>();


    public void add(Student student) {
        studentsList.add(student);
    }

    public Student get(int num) {
        if (num >= 0 && num <= studentsList.size())
            return studentsList.get(num);
        return null;
    }

    public float overall() {
        if (studentsList.isEmpty())
            return 0;
        float result = 0;
        for (int i = 0; i < studentsList.size(); i++) {
            result += studentsList.get(i).getMark();
        }
        return (result / studentsList.size());
    }

}
