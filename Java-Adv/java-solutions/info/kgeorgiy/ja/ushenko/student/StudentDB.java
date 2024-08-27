package info.kgeorgiy.ja.ushenko.student;

import info.kgeorgiy.java.advanced.student.GroupName;
import info.kgeorgiy.java.advanced.student.Student;
import info.kgeorgiy.java.advanced.student.StudentQuery;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StudentDB implements StudentQuery {
    private final Comparator<Student> studentComparator = Comparator.comparing(Student::getLastName)
            .thenComparing(Student::getFirstName)
            .thenComparing(Student::getId, Comparator.reverseOrder());

    private <T> List<T> getList(Collection<Student> students, Function<Student, T> function) {
        return students.stream().map(function).toList();
    }

    private List<Student> getSortedList(Collection<Student> students, Comparator<? super Student> comparator) {
        return students.stream()
                .sorted(comparator)
                .toList();
    }

    private <T> List<Student> getFilteredStudents(Collection<Student> students, Function<Student, T> predicate, T str) {
        return students.stream()
                .filter(student -> str.equals(predicate.apply(student)))
                .sorted(studentComparator)
                .toList();
    }

    @Override
    public List<String> getFirstNames(List<Student> students) {
        return getList(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return getList(students, Student::getLastName);
    }

    @Override
    public List<GroupName> getGroups(List<Student> students) {
        return getList(students, Student::getGroup);
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return getList(students, e -> e.getFirstName() + " " + e.getLastName());
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return new LinkedHashSet<>(getList(students, Student::getFirstName));
    }

    @Override
    public String getMaxStudentFirstName(List<Student> students) {
        return students.stream()
                .max(Student::compareTo)
                .map(Student::getFirstName)
                .orElse("");
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return getSortedList(students, Student::compareTo);
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return getSortedList(students, studentComparator);
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return getFilteredStudents(students, Student::getFirstName, name);
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return getFilteredStudents(students, Student::getLastName, name);
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, GroupName group) {
        return getFilteredStudents(students, Student::getGroup, group);
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, GroupName group) {
        return findStudentsByGroup(students, group).stream()
                .collect(
                        Collectors.toMap(
                                Student::getLastName,
                                Student::getFirstName,
                                BinaryOperator.minBy(Comparator.naturalOrder())
                        )
                );
    }


}
