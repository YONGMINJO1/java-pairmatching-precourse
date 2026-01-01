package pairmatching.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Mission {
    //상수는 클래스 맨 위
    private static final List<String> LEVEL1_MISSIONS =
            Arrays.asList("자동차경주", "로또", "숫자야구게임");

    private static final List<String> LEVEL2_MISSTIONS =
            Arrays.asList("장바구니", "결제", "지하철노선도");
    private static final List<String> LEVEL4_MISSTIONS =
            Arrays.asList("성능개선", "베포");

    private final String name;
    private final Level level;

    public Mission(String name, Level level) {
        vaildate(name, level); //검증부터
        this.name = name;
        this.level = level;
    }

    private void vaildate(String name, Level level) {
        // 1단계 - 이 레벨에 해당하는 미션 목록 가져오기
        List<String> validMisstions = getMisstionsForLevel(level);

        // 2단계 - 목록애 있는지 확인
        if (!validMisstions.contains(name)) {
            throw new IllegalArgumentException(
                    "[ERROR] 해당 레벨에 존재하지 않는 미션입니다."
            );
        }
    }

    private List<String> getMisstionsForLevel(Level level) {
        if (level == Level.LEVEL1) {
            return LEVEL1_MISSIONS;
        }
        if (level == Level.LEVEL2) {
            return LEVEL2_MISSTIONS;
        }
        if (level == Level.LEVEL4) {
            return LEVEL4_MISSTIONS;
        }

        // 여기 도달 = 레벨 3 또는 레벨 5
        throw new IllegalArgumentException(
                "[ERROR] 해당 레벨에는 미션이 없습니다."
        );
    }

    public String getName() {
        return name;
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Mission mission = (Mission) o;
        return Objects.equals(name, mission.name) && level == mission.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level);
    }
}
