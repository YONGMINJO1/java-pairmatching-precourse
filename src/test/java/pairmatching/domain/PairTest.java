package pairmatching.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class PairTest {

    @Test
    void 두명으로_Pair_생성() {
        // given
        Crew pobi = new Crew("포비", Course.BACKEND);
        Crew crong = new Crew("크롱", Course.FRONTEND);
        List<Crew> crews = Arrays.asList(pobi, crong);

        // when
        Pair pair = new Pair(crews);

        // then
        assertThat(pair).isNotNull();
        assertThat(pair.getCrews()).hasSize(2);
        assertThat(pair.getCrews()).containsExactly(pobi, crong);
    }

    @Test
    void 세명으로_Pair_생성() {
        // given
        Crew pobi = new Crew("포비", Course.BACKEND);
        Crew crong = new Crew("크롱", Course.BACKEND);
        Crew gugu = new Crew("구구", Course.BACKEND);
        List<Crew> crews = Arrays.asList(pobi, crong, gugu);

        // when
        Pair pair = new Pair(crews);

        // then
        assertThat(pair).isNotNull();
        assertThat(pair.getCrews()).hasSize(3);
        assertThat(pair.getCrews()).containsExactly(pobi, crong, gugu);
    }

    @Test
    void 한명으로_생성_예외() {
        // given
        Crew pobi = new Crew("포비", Course.BACKEND);
        List<Crew> crews = Arrays.asList(pobi);

        // when & then
        assertThatThrownBy(() -> new Pair(crews))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    void 네명으로_생성_예외() {
        // given
        Crew pobi = new Crew("포비", Course.BACKEND);
        Crew crong = new Crew("크롱", Course.BACKEND);
        Crew gugu = new Crew("구구", Course.BACKEND);
        Crew sunny = new Crew("써니", Course.BACKEND);
        List<Crew> crews = Arrays.asList(pobi, crong, gugu, sunny);

        // when & then
        assertThatThrownBy(() -> new Pair(crews))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    void 빈_리스트로_생성_예외() {
        // given
        List<Crew> crews = Arrays.asList();  // 빈 리스트

        // when & then
        assertThatThrownBy(() -> new Pair(crews))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    void null로_생성_예외() {
        // given
        List<Crew> crews = null;

        // when & then
        assertThatThrownBy(() -> new Pair(crews))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
