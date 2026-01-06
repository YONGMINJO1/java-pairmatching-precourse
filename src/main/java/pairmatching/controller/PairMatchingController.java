package pairmatching.controller;

import java.util.List;
import pairmatching.domain.Course;
import pairmatching.domain.Level;
import pairmatching.domain.Mission;
import pairmatching.domain.Pair;
import pairmatching.service.PairMatchingService;
import pairmatching.view.InputView;
import pairmatching.view.OutputView;

public class PairMatchingController {
    private final PairMatchingService pairMatchingService;

    public PairMatchingController() {
        this.pairMatchingService = new PairMatchingService();
    }

    public void run() {
        while (true) {
            // 1. 메뉴 출력
            OutputView.printMenu();

            // 2. 기능 선택
            String function = InputView.readFunction();

            // 3. Q면 종료
            if (function.equals("Q")) {
                break;
            }
            // 4. 선택한 기능 실행
            if (function.equals("1")) {
                handleMatching();  // ← 메서드 호출!
            } else if (function.equals("2")) {
                handleFind();
            } else if (function.equals("3")) {
                handleClear();
            }
        }
    }

    private void handleMatching() {
        try {

            // 1. 안내출력
            OutputView.printCoursesAndMissions();
            OutputView.printMatchingInputPrompt();

            // 2. 입력 받기
            String input = InputView.readCourseAndLevelAndMission();

            // 3. 파싱
            String[] parts = parseInput(input);
            Course course = Course.from(parts[0]);
            Level level = Level.from(parts[1]);
            Mission mission = new Mission(parts[2], level);
            
            // 재매칭 처리 (메서드로 분리!)
            if (!handleRematch(course, level, mission)) {
                return;  // 아니오 선택 시
            }
            // 4. 이력 체크 및 재매칭 처리
//            if (pairMatchingService.hasMatchingHistory(course, level, mission)) {
//                // 이력있음 -> 재매칭 확인 필요
//                OutputView.printRematchMessage();
//                String answer = InputView.readCourseAndLevelAndMission();
//
//                if (answer.equals("아니요")) {
//                    // 기존 결과 보여주기
//                    List<Pair> existingPairs = pairMatchingService.find(course, level, mission);
//                    OutputView.printMatchingResult(existingPairs);
//                    return;
//                }
//                // 재재칭 처리
//            }
            // 5. 매칭 수행
            List<Pair> pairs = pairMatchingService.match(course, level, mission);
            // 6. 결과 출력
            OutputView.printMatchingResult(pairs);

        } catch (IllegalArgumentException e) {
            OutputView.printError(e.getMessage());

        }
    }

    private boolean handleRematch(Course course, Level level, Mission mission) {
        // 이력 없으면 true (계속 진행)
        if (!pairMatchingService.hasMatchingHistory(course, level, mission)) {
            return true;
        }

        // 이력이 있으면 재매칭 확인
        OutputView.printRematchMessage();
        String answer = InputView.readCourseAndLevelAndMission();

        if (answer.equals("아니오")) {
            // 기존 결과 출력
            List<Pair> existingPairs = pairMatchingService.find(course, level, mission);
            OutputView.printMatchingResult(existingPairs);
            return false;  // 계속 진행 X
        }
        return true;
    }

    private void handleFind() {
        try {
            OutputView.printCoursesAndMissions();
            OutputView.printMatchingInputPrompt();

            String input = InputView.readCourseAndLevelAndMission();

            String[] parts = parseInput(input);
            Course course = Course.from(parts[0]);
            Level level = Level.from(parts[1]);
            Mission mission = new Mission(parts[2], level);

            // Service 호출
            List<Pair> pairs = pairMatchingService.find(course, level, mission);
            // 검증
            OutputView.printMatchingResult(pairs);
        } catch (IllegalArgumentException e) {
            OutputView.printError(e.getMessage());
        }
    }

    private String[] parseInput(String input) {
        String[] parts = input.split(", ");

        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "[ERROR] 올바른 형식으로 입력해주세요."
            );
        }
        return parts;
    }

    private void handleClear() {
        pairMatchingService.clearAll();
        OutputView.printClearComplete();
    }
}
