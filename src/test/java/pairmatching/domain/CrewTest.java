package pairmatching.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class CrewTest {

    @Test
    void Crew_정상_생성() {
        // given
        String name = "포비";
        Course course = Course.BACKEND;

        // when
        Crew crew = new Crew(name, course);

        // then
        assertThat(crew).isNotNull();
        assertThat(crew.getName()).isEqualTo("포비");
        assertThat(crew.getCourse()).isEqualTo(Course.BACKEND);
    }

    @Test
    void 다양한_크루_생성() {
        // 백엔드 크루
        Crew pobi = new Crew("포비", Course.BACKEND);
        assertThat(pobi.getName()).isEqualTo("포비");
        assertThat(pobi.getCourse()).isEqualTo(Course.BACKEND);

        // 프론트엔드 크루
        Crew bono = new Crew("보노", Course.FRONTEND);
        assertThat(bono.getName()).isEqualTo("보노");
        assertThat(bono.getCourse()).isEqualTo(Course.FRONTEND);
    }


    @Test
    void 크루_이름_null_예외() {
        // given
        String name = null;
        Course course = Course.BACKEND;

        // when & then
        assertThatThrownBy(() -> new Crew(name, course))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    void 크루_이름_빈_문자열_예외() {
        // given
        String name = "";
        Course course = Course.BACKEND;

        // when & then
        assertThatThrownBy(() -> new Crew(name, course))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    void 과정_null_예외() {
        // given
        String name = "포비";
        Course course = null;

        // when & then
        assertThatThrownBy(() -> new Crew(name, course))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    void 같은_이름이면_같은_Crew() {
        // given
        Crew crew1 = new Crew("포비", Course.BACKEND);
        Crew crew2 = new Crew("포비", Course.BACKEND);

        // when & then
        assertThat(crew1).isEqualTo(crew2);
        assertThat(crew1.hashCode()).isEqualTo(crew2.hashCode());
    }

    @Test
    void 다른_이름이면_다른_Crew() {
        // given
        Crew crew1 = new Crew("포비", Course.BACKEND);
        Crew crew2 = new Crew("크롱", Course.BACKEND);

        // when & then
        assertThat(crew1).isNotEqualTo(crew2);
    }

    @Test
    void 같은_이름이면_과정이_달라도_같은_Crew() {
        // given
        Crew crew1 = new Crew("포비", Course.BACKEND);
        Crew crew2 = new Crew("포비", Course.FRONTEND);

        // when & then
        assertThat(crew1).isEqualTo(crew2);
        assertThat(crew1.hashCode()).isEqualTo(crew2.hashCode());
    }
}
