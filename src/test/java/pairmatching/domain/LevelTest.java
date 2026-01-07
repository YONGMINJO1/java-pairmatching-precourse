package pairmatching.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class LevelTest {

    @Test
    void 레벨1_문자열로_Level_생성() {
        // given (준비)
        String input = "레벨1";

        // when (실행)
        Level level = Level.from(input);

        // then (확인)
        assertThat(level).isEqualTo(Level.LEVEL1);
    }

    @Test
    void 레벨2_문자열로_Level_생성() {
        // given (준비)
        String input = "레벨2";

        // when (실행)
        Level level = Level.from(input);

        // then (확인)
        assertThat(level).isEqualTo(Level.LEVEL2);
    }

    @Test
    void 레벨3_문자열로_Level_생성() {
        // given (준비)
        String input = "레벨3";

        // when (실행)
        Level level = Level.from(input);

        // then (확인)
        assertThat(level).isEqualTo(Level.LEVEL3);
    }

    @Test
    void 레벨4_문자열로_Level_생성() {
        // given (준비)
        String input = "레벨4";

        // when (실행)
        Level level = Level.from(input);

        // then (확인)
        assertThat(level).isEqualTo(Level.LEVEL4);
    }

    @Test
    void 레벨5_문자열로_Level_생성() {
        // given (준비)
        String input = "레벨5";

        // when (실행)
        Level level = Level.from(input);

        // then (확인)
        assertThat(level).isEqualTo(Level.LEVEL5);
    }

    @Test
    void 잘못된_레벨_이름_예외() {
        // given (준비)
        String input = "레벨6";

        // when & then
        assertThatThrownBy(() -> Level.from(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    void 레벨0_예외() {
        // given
        String input = "레벨0";

        // when & then
        assertThatThrownBy(() -> Level.from(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void  영문_레벨_예외() {
        // given
        String input = "Level1";

        // when & then
        assertThatThrownBy(() -> Level.from(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 공백_포함_예외() {
        // given
        String input = "레벨 1";

        // when & then
        assertThatThrownBy(() -> Level.from(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
