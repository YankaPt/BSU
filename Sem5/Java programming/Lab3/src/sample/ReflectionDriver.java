package sample;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionDriver {
    private Class clazz;

    public ReflectionDriver(String clazz) throws ClassNotFoundException{
        this.clazz = Class.forName(clazz);
    }

    public List<Method> getMethods() {
        return Arrays.stream(clazz.getDeclaredMethods()).collect(Collectors.toList());
    }

    public Object invokeMethod(Method method, Object[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        method.setAccessible(true);
        if (method.getReturnType().getSimpleName().equals("Void")) {
            if (args.length == 0) {
                method.invoke(getInstance());
            } else {
                method.invoke(getInstance(), args);
            }
            return "The void method was invoked";
        } else {

            return args.length == 0 ? method.invoke(getInstance()) : method.invoke(getInstance(), args);
        }
    }

    public Object getInstance() throws InstantiationException {
        Constructor[] constructors = clazz.getConstructors();
        if (constructors.length == 0) {
            try {
                Method method = clazz.getMethod("getInstance", clazz);
                return method.invoke(clazz);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                throw new InstantiationException();
            }
        }
        try {
            for (Constructor constructor : constructors) {
                Class[] paramTypes = constructor.getParameterTypes();
                if (paramTypes.length == 0) {
                    return constructor.newInstance();
                }
            }
            throw new InstantiationException();
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new InstantiationException();
        }
    }
}
