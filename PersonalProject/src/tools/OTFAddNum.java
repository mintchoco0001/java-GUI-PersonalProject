package tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class OTFAddNum {
    // ������: Random ��ü�� ���ڸ� ������ �迭�� �Ű������� ����
    public OTFAddNum(Random r, int[] num) {
        // 1���� 25������ ���ڸ� ����Ʈ�� ����
        List<Integer> numbers1to25 = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            numbers1to25.add(i + 1);
        }

        // 26���� 50������ ���ڸ� ����Ʈ�� ����
        List<Integer> numbers26to50 = new ArrayList<>();
        for (int i = 25; i < 50; i++) {
            numbers26to50.add(i + 1);
        }

        // �� ����Ʈ�� ���������� ����
        Collections.shuffle(numbers1to25, r);
        Collections.shuffle(numbers26to50, r);

        // �� ����Ʈ�� ���ڸ� �迭�� ������� ����
        for (int i = 0; i < 25; i++) {
            num[i] = numbers1to25.get(i);
        }
        for (int i = 0; i < 25; i++) {
            num[i + 25] = numbers26to50.get(i);
        }
    }
}
