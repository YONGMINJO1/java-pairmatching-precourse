package pairmatching.view;

import java.util.List;
import pairmatching.domain.Crew;
import pairmatching.domain.Pair;

public class OutputView {
    public static void printMenu() {
        System.out.println("\n기능을 선택하세요.");
        System.out.println("1. 페어 매칭");
        System.out.println("2. 페어 조회");
        System.out.println("3. 페어 초기화");
        System.out.println("Q. 종료");
    }

    public static void printCoursesAndMissions() {
        System.out.println("\n#############################################");
        System.out.println("과정: 백엔드 | 프론트엔드");
        System.out.println("미션:");
        System.out.println("  - 레벨1: 자동차경주 | 로또 | 숫자야구게임");
        System.out.println("  - 레벨2: 장바구니 | 결제 | 지하철노선도");
        System.out.println("  - 레벨3: ");
        System.out.println("  - 레벨4: 성능개선 | 배포");
        System.out.println("  - 레벨5: ");
        System.out.println("############################################");
    }

    public static void printMatchingResult(List<Pair> pairs) {
        // 1단계
        System.out.println("\n페어 매칭 결과입니다.");

        // 2단계
        for (Pair pair : pairs) {
            printPair(pair);
        }
    }

    private static void printPair(Pair pair) {
        List<Crew> crews = pair.getCrews();

        // 첫 번째 이름
        System.out.print(crews.get(0).getName());

        // 나머지 이름들
        for (int i = 1; i < crews.size(); i++) {
            System.out.print(" : ");
            System.out.print(crews.get(i).getName());
        }

        // 줄바꿈
        System.out.println();

    }

    public static void printError(String message) {
        System.out.println(message);
    }

    public static void printClearComplete() {
        System.out.println("\n초기화 되었습니다.");
    }

    public static void printRematchMessage() {
        System.out.println("\n매칭 정보가 있습니다. 다시 매칭하시겠습니까?");
        System.out.println("네 | 아니오");
    }
}
