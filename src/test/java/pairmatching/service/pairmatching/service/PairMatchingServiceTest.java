package pairmatching.service.pairmatching.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pairmatching.domain.Course;
import pairmatching.domain.Level;
import pairmatching.domain.Mission;
import pairmatching.domain.Pair;
import pairmatching.service.PairMatchingService;

public class PairMatchingServiceTest {
    private PairMatchingService service;

    @BeforeEach
    void setUp() {
        service = new PairMatchingService();
    }

    @Test
    void clearAll_테스트() {
        // given - 매칭 수행 (데이터 생성)
        Course course = Course.BACKEND;
        Level level = Level.LEVEL1;
        Mission mission = new Mission("자동차경주", level);

        service.match(course, level, mission);

        // 매칭 이력 있는지 확인
        assertThat(service.hasMatchingHistory(course, level, mission))
                .isTrue();

        // when - 초기화
        service.clearAll();

        // then - 이력 없어야 함
        assertThat(service.hasMatchingHistory(course, level, mission))
                .isFalse();
    }

    @Test
    void 매칭_이력_없음() {
        // given
        Course course = Course.BACKEND;
        Level level = Level.LEVEL1;
        Mission mission = new Mission("자동차경주", level);

        // when & then
        assertThat(service.hasMatchingHistory(course, level, mission))
                .isFalse();
    }

    @Test
    void 매칭_이력_있음() {
        // given
        Course course = Course.BACKEND;
        Level level = Level.LEVEL1;
        Mission mission = new Mission("자동차경주", level);

        // when
        service.match(course, level, mission);

        // then
        assertThat(service.hasMatchingHistory(course, level, mission))
                .isTrue();
    }

    @Test
    void 다른_레벨은_이력_없음() {
        // given
        Course course = Course.BACKEND;
        Level level1 = Level.LEVEL1;
        Level level2 = Level.LEVEL2;
        Mission mission1 = new Mission("자동차경주", level1);
        Mission mission2 = new Mission("장바구니", level2);

        // when - LEVEL1만 매칭
        service.match(course, level1, mission1);

        // then
        assertThat(service.hasMatchingHistory(course, level1, mission1))
                .isTrue();
        assertThat(service.hasMatchingHistory(course, level2, mission2))
                .isFalse();  // LEVEL2는 없음!
    }

    @Test
    void find_매칭_이력_없으면_예외() {
        // given
        Course course = Course.BACKEND;
        Level level = Level.LEVEL1;
        Mission mission = new Mission("자동차경주", level);

        // when & then
        assertThatThrownBy(() -> service.find(course, level, mission))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    void find_매칭_결과_조회() {
        // given
        Course course = Course.BACKEND;
        Level level = Level.LEVEL1;
        Mission mission = new Mission("자동차경주", level);

        // when - 매칭 수행
        List<Pair> matchedPairs = service.match(course, level, mission);

        // then - 조회 결과가 매칭 결과와 같아야 함
        List<Pair> foundPairs = service.find(course, level, mission);

        assertThat(foundPairs).isNotNull();
        assertThat(foundPairs).hasSize(matchedPairs.size());
    }

    @Test
    void match_정상_매칭() {
        // given
        Course course = Course.BACKEND;
        Level level = Level.LEVEL1;
        Mission mission = new Mission("자동차경주", level);

        // when
        List<Pair> pairs = service.match(course, level, mission);

        // then
        assertThat(pairs).isNotNull();
        assertThat(pairs).isNotEmpty();

        // 각 Pair 가 2~3명인지 확인
        for (Pair pair : pairs) {
            int size = pair.getCrews().size();
            assertThat(size).isBetween(2, 3);
        }
    }

    @Test
    void match_후_이력_저장() {
        // given
        Course course = Course.BACKEND;
        Level level = Level.LEVEL1;
        Mission mission = new Mission("자동차경주", level);

        // when
        service.match(course, level, mission);

        // then - 이력이 저장되었는지
        assertThat(service.hasMatchingHistory(course, level, mission))
                .isTrue();

        // 조회도 가능한지
        List<Pair> foundPairs = service.find(course, level, mission);
        assertThat(foundPairs).isNotNull();
    }

    @Test
    void 프론트엔드_매칭() {
        // given
        Course course = Course.FRONTEND;
        Level level = Level.LEVEL1;
        Mission mission = new Mission("자동차경주", level);

        // when
        List<Pair> pairs = service.match(course, level, mission);

        // then
        assertThat(pairs).isNotNull();
        assertThat(pairs).isNotEmpty();

        for (Pair pair : pairs) {
            int size = pair.getCrews().size();
            assertThat(size).isBetween(2, 3);
        }
    }

    @Test
    void 백엔드와_프론트엔드_독립적() {
        // given
        Course backend = Course.BACKEND;
        Course frontend = Course.FRONTEND;
        Level level = Level.LEVEL1;
        Mission mission1 = new Mission("자동차경주", level);
        Mission mission2 = new Mission("자동차경주", level);

        // when - 각각 매칭
        service.match(backend, level, mission1);
        service.match(frontend, level, mission2);

        // then - 둘 다 이력 있어야 함
        assertThat(service.hasMatchingHistory(backend, level, mission1))
                .isTrue();
        assertThat(service.hasMatchingHistory(frontend, level, mission2))
                .isTrue();
    }
}
