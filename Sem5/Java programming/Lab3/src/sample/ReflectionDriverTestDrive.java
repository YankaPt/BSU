package sample;

public class ReflectionDriverTestDrive {
    public static void main(String[] args) {
        try {
            ReflectionDriver reflectionDriver = new ReflectionDriver("java.lang.String");
            String s = "";
            System.out.println(reflectionDriver.invokeMethod(reflectionDriver.getMethods().get(4), new Object[0]));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
