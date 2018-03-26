package com.YankaPt;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jan on 12.2.18.
 */
public class ProgrammingTest {
    public static void main(String[] args) {
        Academic academic1 = new Academic("Levakov");
        Academic academic2 = new Academic("Alsievich");
        Set<Student> students = Stream.of(
                new Undergraduate("Belousov", "bel", "fpm.zhyrniypidar@bsu.by", academic1),
                new Undergraduate("Ematinov", "emt", "fpm.ematinov@bsu.by", academic2),
                new Postgraduate("Antipov", "ant", "fpm.antipov@bsu.by", academic1),
                new Postgraduate("Atremchuk", "ant", "fpm.artemchuk@bsu.by", academic2),
                new Postgraduate("Horobec", "hor", "fpm.horobec@bsu.by", academic1)
        ).collect(Collectors.toSet());
        Course course = new Course("2", students);
        Set<Postgraduate> postgraduates = course.getPostgraduates("Levakov");
        Notifier notifier = new Notifier(postgraduates);
        notifier.doNotifyAll();
    }
}
