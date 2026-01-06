package pairmatching.service;

import camp.nextstep.edu.missionutils.Randoms;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import pairmatching.domain.Course;
import pairmatching.domain.Crew;
import pairmatching.domain.Level;
import pairmatching.domain.Mission;
import pairmatching.domain.Pair;

public class PairMatchingService {
    // 파일 경로 상수로 정의
    private static final String BACKEND_CREW_FILE =
            "src/main/resources/backend-crew.md";
    private static final String FRONTEND_CREW_FILE =
            "src/main/resources/frontend-crew.md";

    // 매칭 기록 저장소
    private final Map<String, List<List<Pair>>> matchingHistory;

    private boolean hasCommonPair(List<Pair> newPairs, List<Pair> pastPairs) {
        // 새 매칭의 각 페어를
        for (Pair newPair : newPairs) {
            // 과거 매칭의 각 페어와 비교
            for (Pair pastPair : pastPairs) {
                // 공통 크루가 있으면 중복!
                if (newPair.hasCommonCrew(pastPair)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasDuplicatePair(Course course, Level level, List<Pair> newPairs) {
        String key = createKey(course, level);
        
        // 과거 기록이 없으면 중복 없음
        if (!matchingHistory.containsKey(key)) {
            return false;
        }

        List<List<Pair>> pastMatchings = matchingHistory.get(key);
        
        // 과거 모든 매칭과 비교
        for (List<Pair> pastPairs : pastMatchings) {
            if (hasCommonPair(newPairs, pastPairs)) {
                return true; // 중복 발견
            }
        }

        return false; // 중복 없음
    }

    private List<Crew> shuffle(List<Crew> crews) {
        // Randoms.shuffle()은 List<String>만 받음
        // 그래서 변환 필요

        // 1단계 Crew -> 이름<String>
        List<String> crewNames = crews.stream()
                .map(Crew::getName)
                .collect(Collectors.toList());

        // 2단계 섞기
        List<String> shuffledNames = Randoms.shuffle(crewNames);

        // 3단계 이름(String) -> Crew
        return shuffledNames
                .stream()
                .map(name -> findCrewByName(crews, name))
                .collect(Collectors.toList());
        /*
        for문 버전
    private List<Crew> shuffle(List<Crew> crew){

        List<String> crewNames = new ArrayList<>():
        for (Crew crew : crews) {
            crewNames.add(crew.getName());
        }

        List<String> shuffledName = Randoms.shuffle(crewNames);

        List<Crew> shuffledCrews = new ArrayList<>();
        for(String name : shuffledNames) {
            Crew crew = findCrewByName(crews, name);
            shuffledCrews.add(crew);
        }
        return shuffledCrews;
    }
         */
    }

    private Crew findCrewByName(List<Crew> crews, String name) {
        return crews.stream()
                .filter(crew -> crew.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "[ERROR] 크루를 찾을 수 없습니다."
                ));
    }

    public List<Pair> match(Course course, Level level, Mission mission) {
        // 1단계
        List<Crew> crews = loadCrews(course);

        for (int attempt = 0; attempt < 3; attempt++) {
            // 2단계
            List<Crew> shuffledCrews = shuffle(crews);
            // 3단계
            List<Pair> pairs = createPair(shuffledCrews);

            if (!hasDuplicatePair(course, level, pairs)) {
            // 4단계
            savePairs(course, level, pairs);
            return pairs;
            }
        }
        throw new IllegalArgumentException("[ERROR] 매칭할 수 없습니다.");
    }

    private void savePairs(Course course, Level level, List<Pair> pairs) {
        String key = createKey(course, level);

        // 키가 없으면 새로 만들기
        if (!matchingHistory.containsKey(key)) {
            matchingHistory.put(key, new ArrayList<>());
        }

        // 페어 리스트 추가
        matchingHistory.get(key).add(pairs);
    }

    private List<Pair> createPair(List<Crew> crews) {
        List<Pair> pairs = new ArrayList<>();

        for (int i = 0; i < crews.size() - 1; i += 2) {
            List<Crew> pairCrews = Arrays.asList(
                    crews.get(i),
                    crews.get(i + 1)
            );
            pairs.add(new Pair(pairCrews));
        }

        if (crews.size() % 2 == 1) {
            Crew lastCrew = crews.get(crews.size() - 1);
            Pair lastPair = pairs.get(pairs.size() - 1);

            List<Crew> updateCrews = new ArrayList<>(lastPair.getCrews());
            updateCrews.add(lastCrew);

            pairs.set(pairs.size() - 1, new Pair(updateCrews));
        }

        return pairs;
    }

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
