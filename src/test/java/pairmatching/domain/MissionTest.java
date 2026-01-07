package pairmatching.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class MissionTest {

    @Test
    void Mission_정상_생성() {
        // given
        String name = "자동차경주";
        Level level = Level.LEVEL1;

        // when
        Mission mission = new Mission(name, level);

        // then
        assertThat(mission).isNotNull();
        assertThat(mission.getName()).isEqualTo("자동차경주");
        assertThat(mission.getLevel()).isEqualTo(Level.LEVEL1);
    }

    @Test
    void 다양한_미션_생성() {
        // 로또
        Mission lotto = new Mission("로또", Level.LEVEL1);
        assertThat(lotto.getName()).isEqualTo("로또");
        assertThat(lotto.getLevel()).isEqualTo(Level.LEVEL1);

        // 장바구니
        Mission cart = new Mission("장바구니", Level.LEVEL2);
        assertThat(cart.getName()).isEqualTo("장바구니");
        assertThat(cart.getLevel()).isEqualTo(Level.LEVEL2);

        // 성능개선
        Mission performance = new Mission("성능개선", Level.LEVEL4);
        assertThat(performance.getName()).isEqualTo("성능개선");
        assertThat(performance.getLevel()).isEqualTo(Level.LEVEL4);
    }

    @Test
    void 메션_이름_null_예외() {
        // given
        String name = null;
        Level level = Level.LEVEL1;

        // when & that
        assertThatThrownBy(() -> new Mission(name, level))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    void 미션_이름_빈_문자열_예외() {
        // given
        String name = "";
        Level level = Level.LEVEL1;

        // when & then
        assertThatThrownBy(() -> new Mission(name, level))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    void 레벨_null_예외() {
        // given
        String name = "자동차경주";
        Level level = null;

        // when & then
        assertThatThrownBy(() -> new Mission(name, level))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    void 같은_이름과_레벨이면_같은_Mission() {
        Mission mission1 = new Mission("자동차경주", Level.LEVEL1);
        Mission mission2 = new Mission("자동차경주", Level.LEVEL1);

        assertThat(mission1).isEqualTo(mission2);
        assertThat(mission1.hashCode()).isEqualTo(mission2.hashCode());
    }

    @Test
    void 다른_이름이면_다른_Mission() {
        Mission mission1 = new Mission("자동차경주", Level.LEVEL1);
        Mission mission2 = new Mission("로또", Level.LEVEL1);

        assertThat(mission1).isNotEqualTo(mission2);
    }

    @Test
    void 다른_레벨이면_다른_Mission() {
        Mission mission1 = new Mission("자동차경주", Level.LEVEL1);
        Mission mission2 = new Mission("자동차경주", Level.LEVEL2);

        assertThat(mission1).isNotEqualTo(mission2);
    }
}
