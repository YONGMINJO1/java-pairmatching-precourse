package pairmatching.domain;

import java.util.Arrays;

public enum Course {
    BACKEND("백엔드"),
    FRONTEND("프론트엔드"),
    ;

    private final String name;

    Course(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // 백앤드 -> CourseBACKEND
    public static Course form(String name) {
        return Arrays.stream(values())
                .filter(course -> course.name.equals(name))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException(
                        "[ERROR] 올바른 과정을 입력해주세요."
                ));
    }
}
