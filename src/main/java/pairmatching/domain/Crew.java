package pairmatching.domain;

import java.util.Objects;

public class Crew {
    private Course course;
    private String name;

    public Crew(String name, Course course) {
        //검증
        validateName(name);
        validateCourse(course);
        //할당
        this.name = name;
        this.course = course;
    }

    private void validateCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException(
                    "[ERROR] 과정을 선택햐야 합니다."
            );
        }
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "[ERROR] 크루 이름은 비어있을 수 없습니다."
            );
        }
    }

    // getter
    public String getName() {
        return name;
    }

    public Course getCourse() {
        return course;
    }
    // equals / hashCode

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Crew crew = (Crew) o;
        return course == crew.course && Objects.equals(name, crew.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, name);
    }
}
