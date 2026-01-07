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

    @Test
    void 빈_문자열_예외() {
        // given
        String input = "";

        // when & then
        assertThatThrownBy(() -> Course.from(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void null_입력_예외() {
        // given
        String input = null;

        // when & then
        assertThatThrownBy(() -> Course.from(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    void 공백_포함_예외() {
        String input = "백 엔드";  // 공백 있음

        assertThatThrownBy(() -> Course.from(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }
    @Test
    void 영문_입력_예외() {
        String input = "Backend";  // 영문

        assertThatThrownBy(() -> Course.from(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }
    
    @Test
    void 대소문자_혼합_예외() {
        String input = "백엔드Backend";

        assertThatThrownBy(() -> Course.from(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }
    
    @Test
    void 숫자_포함_예외() {
        String input = "백엔드1";

        assertThatThrownBy(() -> Course.from(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }
}
