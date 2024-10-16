package tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class OTFAddNum {
    // 생성자: Random 객체와 숫자를 저장할 배열을 매개변수로 받음
    public OTFAddNum(Random r, int[] num) {
        // 1부터 25까지의 숫자를 리스트에 저장
        List<Integer> numbers1to25 = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            numbers1to25.add(i + 1);
        }

        // 26부터 50까지의 숫자를 리스트에 저장
        List<Integer> numbers26to50 = new ArrayList<>();
        for (int i = 25; i < 50; i++) {
            numbers26to50.add(i + 1);
        }

        // 각 리스트를 독립적으로 섞기
        Collections.shuffle(numbers1to25, r);
        Collections.shuffle(numbers26to50, r);

        // 두 리스트의 숫자를 배열에 순서대로 저장
        for (int i = 0; i < 25; i++) {
            num[i] = numbers1to25.get(i);
        }
        for (int i = 0; i < 25; i++) {
            num[i + 25] = numbers26to50.get(i);
        }
    }
}
