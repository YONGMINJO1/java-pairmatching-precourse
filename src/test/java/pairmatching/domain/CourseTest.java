package pairmatching.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class CourseTest {

    @Test
    void 백엔드_문자열로_Course_생성() {
        // given (준비)
        String input = "백엔드";

        // when (실행)
        Course course = Course.from(input);

        // then (확인) 실제값 / 기대값
        assertThat(course).isEqualTo(Course.BACKEND);
    }

    @Test
    void 프론트엔드_문자열로_Course_생성() {
        // given (준비)
        String input = "프론트엔드";

        // when (실행)
        Course course = Course.from(input);

        // then (확인)
        assertThat(course).isEqualTo(Course.FRONTEND);
    }
    
    @Test
    void 잘못된_과정_이름_예외() {
        // given (준비)
        String input = "잘못된 과정";

        // when (실행) & then (과정) 에러 포함문구
        assertThatThrownBy(() -> Course.from(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }
}
