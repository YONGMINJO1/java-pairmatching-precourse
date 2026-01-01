package pairmatching.domain;

import java.util.ArrayList;
import java.util.List;

public class Pair {
    private static final int MIN_CREW_COUNT = 2;
    private static final int MAX_CREW_COUNT = 3;

    private final List<Crew> crews;

    public Pair(List<Crew> crews) {
        // 검증
        validateCrews(crews);
        validateCrewCount(crews);
        // 할당
        this.crews = crews;
    }

    private void validateCrews(List<Crew> crews) {
        if (crews == null || crews.isEmpty()) {
            throw new IllegalArgumentException(
                    "[ERROR] 페어는 최소 2명 이상이어야 합니다."
            );
        }
    }

    private void validateCrewCount(List<Crew> crews) {
        int size = crews.size();
        if (size < MIN_CREW_COUNT || size > MAX_CREW_COUNT) {
            throw new IllegalArgumentException(
                    String.format(
                            "[ERROR] 페어는 %d명 또는 %d명으로 구성되어야 합니다.",
                            MIN_CREW_COUNT,
                            MAX_CREW_COUNT
                    )
            );
        }
    }

    public List<Crew> getCrews() {
        return new ArrayList<>(crews);
    }

    public boolean contains(Crew crew) {
        return crews.contains(crew);
    }

    // Stream 버전 (간결)
    public boolean hasCommonCrew(Pair other) {
        return this.crews.stream().anyMatch(other::contains);
    }

    // for문 버전 (명확)
//    public boolean hasCommonCrew(Pair other) {
//        for (Crew crew : this.crews) {
//            if (other.contains(crew)) {
//                return true;
//            }
//        }
//        return false;
//    }
}
