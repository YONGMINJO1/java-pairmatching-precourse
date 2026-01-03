package pairmatching.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import pairmatching.domain.Course;
import pairmatching.domain.Crew;
import pairmatching.domain.Level;
import pairmatching.domain.Pair;

public class PairMatchingService {
    // 파일 경로 상수로 정의
    private static final String BACKEND_CREW_FILE =
            "src/main/resources/backend-crew.md";
    private static final String FRONTEND_CREW_FILE =
            "src/main/resources/frontend-crew.md";

    // 매칭 기록 저장소
    private final Map<String, List<List<Pair>>> matchingHistory;

    public PairMatchingService() {
        this.matchingHistory = new HashMap<>();
    }

    // 헬퍼 메서드 백엔드-1레벨
    private String createKey(Course course, Level level) {
        return course.getName() + "-" + level.getName();
    }

    private List<Crew> loadCrews(Course course) {
        String filePath = getCrewFilePath(course);
        try {
            // stream 버전
            // 1. 파일 읽기
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            // 2. stream 변환 -> 각 이름을 Crew로 -> List로 수집 -> List<Crew>
            return lines.stream().map(name -> new Crew(name, course)).collect(Collectors.toList());

            // for 문 버전
//            List<String> lines = Files.readAllLines(Paths.get(filePath));
//            List<Crew> crews = new ArrayList<>();
//
//            for (String name : lines) {
//                crews.add(new Crew(name, course));
//            }
//            return crews;
        } catch (IOException e) {
            throw new IllegalArgumentException(
                    "[ERROR] 크루 파일을 읽을 수 없습니다."
            );
        }
    }

    private String getCrewFilePath(Course course) {
        if (course == Course.BACKEND) {
            return BACKEND_CREW_FILE;
        }
        return FRONTEND_CREW_FILE;
    }
}
