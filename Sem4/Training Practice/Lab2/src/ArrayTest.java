public class ArrayTest {
    public static void main(String[] args) {
        String icon = "";
        Object[][] data = {
                {icon, "Smith", 5, false},
                {icon, "Doe", 3, true},
                {icon, "Black", 2, true},
                {icon, "White", 20, true},
                {icon, "Brown", 10, false}
        };
        System.out.println(data[1].length);
    }
}
