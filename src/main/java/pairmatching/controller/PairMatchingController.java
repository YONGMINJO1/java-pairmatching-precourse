package pairmatching.controller;

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
        // TODO
    }

    private void handleFind() {
        // TODO
    }

    private void handleClear() {
        // TODO
    }
}
