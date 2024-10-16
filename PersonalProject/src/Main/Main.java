package Main;

import javax.swing.*;
import Game.*;
import java.awt.*;
import java.awt.event.*;

//JFrame을 상속받아 GUI 애플리케이션을 생성
public class Main extends JFrame {
	// 생성자
	Main() {
		// 프레임 제목 설정
		setTitle("간단한 게임 실행기");
		setDefaultCloseOperation(EXIT_ON_CLOSE); // 닫기 버튼 클릭 시 애플리케이션 종료

		// 컨테이너와 레이아웃 설정
		Container c = getContentPane();
		c.setLayout(new GridBagLayout()); // GridBagLayout 사용
		GridBagConstraints gbc1 = new GridBagConstraints();
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc1.insets = new Insets(30, 5, 5, 5); // 위, 왼쪽, 아래, 오른쪽 간격 설정
		gbc2.insets = new Insets(5, 5, 5, 5); // 위, 왼쪽, 아래, 오른쪽 간격 설정

		// 레이블 생성 및 추가
		JLabel titleLabel = new JLabel("간단한 게임 실행기");
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		c.add(titleLabel, gbc1); // 레이블을 컨테이너에 추가

		// 버튼 생성 및 추가
		JButton btn1 = new JButton("1 to 50 게임");
		JButton btn2 = new JButton("메모리게임");
		JButton btn3 = new JButton("게임 종료");
		gbc1.gridy = 1;
		c.add(btn1, gbc1); // 첫 번째 버튼 추가
		gbc2.gridy = 2;
		c.add(btn2, gbc2); // 두 번째 버튼 추가
		gbc2.gridy = 3;
		c.add(btn3, gbc2); // 세 번째 버튼 추가

		// 버튼 이벤트 리스너 추가
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 1 to 50 게임 시작 메서드 호출
				startOneToFiftyGame();
			}
		});

		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 메모리 게임 시작 메서드 호출
				startMemoryGame();
			}
		});

		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 게임 종료
				System.exit(0);
			}
		});

		// 프레임 크기 및 가시성 설정
		c.setBackground(Color.white); // 배경색 설정
		setSize(250, 300); // 프레임 크기 설정
		setVisible(true); // 프레임 가시성 설정
	}

	// 1 to 50 게임 시작 메서드
	private void startOneToFiftyGame() {
		new OneToFiftyGame(); // OneToFiftyGame 클래스 인스턴스 생성
	}

	// 메모리 게임 시작 메서드
	private void startMemoryGame() {
		// 난이도 선택 옵션
		Object[] Options = { "쉬움(카드 6쌍)", "보통(카드 10쌍)", "어려움(카드 15쌍)" };
		int difficulty = JOptionPane.showOptionDialog(null, "난이도를 선택하세요.", "난이도 선택", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, Options, Options[1]);

		int cardPairs;
		switch (difficulty) {
		case 0: // 쉬움
			cardPairs = 6;
			break;
		case 1: // 보통
			cardPairs = 10;
			break;
		case 2: // 어려움
			cardPairs = 15;
			break;
		default:
			cardPairs = 10; // 기본값
		}
		new MemoryGame(cardPairs); // MemoryGame 클래스 인스턴스 생성
	}

	// 메인 메서드
	public static void main(String[] args) {
		// GUI 실행
		new Main(); // Main 클래스 인스턴스 생성
	}
}