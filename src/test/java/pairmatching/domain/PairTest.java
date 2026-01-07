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

    @Test
    void 포함된_크루_확인() {
        // given
        Crew pobi = new Crew("포비", Course.BACKEND);
        Crew crong = new Crew("크롱", Course.BACKEND);
        Pair pair = new Pair(Arrays.asList(pobi, crong));

        // when & then
        assertThat(pair.contains(pobi)).isTrue();
        assertThat(pair.contains(crong)).isTrue();
    }

    @Test
    void 포함되지_않은_크루_확인() {
        // given
        Crew pobi = new Crew("포비", Course.BACKEND);
        Crew crong = new Crew("크롱", Course.BACKEND);
        Crew gugu = new Crew("구구", Course.BACKEND);
        Pair pair = new Pair(Arrays.asList(pobi, crong));

        // when & then
        assertThat(pair.contains(gugu)).isFalse();
    }

    @Test
    void 공통_크루_있음_일부_겹침() {
        // given
        Crew pobi = new Crew("포비", Course.BACKEND);
        Crew crong = new Crew("크롱", Course.BACKEND);
        Crew gugu = new Crew("구구", Course.BACKEND);

        Pair pair1 = new Pair(Arrays.asList(pobi, crong));
        Pair pair2 = new Pair(Arrays.asList(pobi, gugu));

        // when & then
        assertThat(pair1.hasCommonCrew(pair2)).isTrue();
        assertThat(pair2.hasCommonCrew(pair1)).isTrue();  // 반대도 true!
    }

    @Test
    void 공통_크루_있음_완전_겹침() {
        // given
        Crew pobi = new Crew("포비", Course.BACKEND);
        Crew crong = new Crew("크롱", Course.BACKEND);

        Pair pair1 = new Pair(Arrays.asList(pobi, crong));
        Pair pair2 = new Pair(Arrays.asList(pobi, crong));

        // when & then
        assertThat(pair1.hasCommonCrew(pair2)).isTrue();
    }

    @Test
    void 공통_크루_없음() {
        // given
        Crew pobi = new Crew("포비", Course.BACKEND);
        Crew crong = new Crew("크롱", Course.BACKEND);
        Crew gugu = new Crew("구구", Course.BACKEND);
        Crew sunny = new Crew("써니", Course.BACKEND);

        Pair pair1 = new Pair(Arrays.asList(pobi, crong));
        Pair pair2 = new Pair(Arrays.asList(gugu, sunny));

        // when & then
        assertThat(pair1.hasCommonCrew(pair2)).isFalse();
        assertThat(pair2.hasCommonCrew(pair1)).isFalse();
    }

    @Test
    void 세명_페어끼리_공통_크루_확인() {
        // given
        Crew pobi = new Crew("포비", Course.BACKEND);
        Crew crong = new Crew("크롱", Course.BACKEND);
        Crew gugu = new Crew("구구", Course.BACKEND);
        Crew sunny = new Crew("써니", Course.BACKEND);
        Crew jason = new Crew("제이슨", Course.BACKEND);

        Pair pair1 = new Pair(Arrays.asList(pobi, crong, gugu));
        Pair pair2 = new Pair(Arrays.asList(gugu, sunny, jason));

        // when & then
        assertThat(pair1.hasCommonCrew(pair2)).isTrue();  // 구구 공통
    }
}
