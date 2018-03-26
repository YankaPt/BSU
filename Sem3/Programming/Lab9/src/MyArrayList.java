import java.util.ArrayList;

public class MyArrayList<T extends Student> extends ArrayList<T> {
    public Student min() {
        Student minStudent=this.get(0);
        for(Student i:this)
            if(i.compareTo(minStudent) <0)
                minStudent=i;
        return minStudent;
    }
    public Student max() {
        Student maxStudent=this.get(0);
        for(Student i:this)
            if(i.compareTo(maxStudent) >0)
                maxStudent=i;
        return maxStudent;
    }

}