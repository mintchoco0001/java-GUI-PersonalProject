package Game;

import tools.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

public class OneToFiftyGame extends JFrame {
	// 랜덤 객체 생성
	private Random r = new Random();
	// 버튼 배열 생성 (25개)
	private JButton[] buttons = new JButton[25];
	// 현재 눌러야 할 숫자 초기화
	private int currentNumber = 1;
	// 게임 종료 여부 플래그
	private boolean isGameFinished = false;
	// 게임 시작 시간 저장
	private LocalDateTime startTime;
	// 1부터 50까지의 숫자를 저장할 배열
	private int[] num = new int[50];
	// 타이머 레이블 초기화
	private JLabel timerLabel = new JLabel("00:00:00", SwingConstants.CENTER);
	// 0.001초마다 갱신되는 타이머
	private Timer timer = new Timer(1, new TimerActionListener());

	public OneToFiftyGame() {
		// 창 제목 설정
		setTitle("1 to 50 게임");
		// 창 닫을 때 프로그램 종료
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container c = getContentPane();
		// BorderLayout 설정
		c.setLayout(new BorderLayout());

		// 상단에 타이머 레이블 추가
		timerLabel.setFont(new Font(null, Font.BOLD, 20));
		c.add(timerLabel, BorderLayout.NORTH);

		// 5x5 그리드 레이아웃의 패널 생성
		JPanel buttonPanel = new JPanel(new GridLayout(5, 5));
		c.add(buttonPanel, BorderLayout.CENTER);

		// 랜덤 숫자 배열 생성
		new OTFAddNum(r, num);

		// 버튼 생성 및 추가
		for (int i = 0; i < 25; i++) {
			// 버튼에 숫자 설정
			buttons[i] = new JButton(String.valueOf(this.num[i]));
			buttons[i].setFont(new Font(null, Font.BOLD, 20));
			// 초기에는 버튼 비활성화
			buttons[i].setEnabled(false);
			// 초기에는 버튼 숨김
			buttons[i].setVisible(false);
			// 버튼 배경색을 흰색으로 설정
			buttons[i].setBackground(Color.white);
			// 버튼 패널에 추가
			buttonPanel.add(buttons[i]);
			// 버튼 클릭 이벤트 리스너 추가
			buttons[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ButtonActionListener(e);
				}
			});
		}
		// 프레임 크기 및 가시성 설정
		setSize(720, 720);
		// 디스플레이 중앙에 배치
		setLocationRelativeTo(null);
		setVisible(true);

		// 3초 카운트다운 후 게임 시작
		startCountdown();
	}

	// 3초 카운트다운 후 게임 시작
	private void startCountdown() {
		Timer countdownTimer = new Timer(1000, new ActionListener() {
			int countdown = 3; // 카운트다운 초기값 설정

			@Override
			public void actionPerformed(ActionEvent e) {
				if (countdown > 0) {
					// 카운트다운 표시
					timerLabel.setText("게임 시작까지: " + countdown + "초");
					countdown--;
				} else {
					// 카운트다운 타이머 정지
					((Timer) e.getSource()).stop();
					// 게임 시작
					startGame();
				}
			}
		});
		// 카운트다운 시작
		countdownTimer.start();
	}

	// 게임 시작
	private void startGame() {
		// 시작 시간 기록
		startTime = LocalDateTime.now();
		// 타이머 시작
		startTimer();
		for (int i = 0; i < 25; i++) {
			// 버튼 활성화
			buttons[i].setEnabled(true);
			// 버튼 표시
			buttons[i].setVisible(true);
		}
	}

	// 타이머 시작
	private void startTimer() {
		timer.start();
	}

	// 타이머 업데이트 리스너
	private class TimerActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// 현재 시간과 시작 시간의 차이를 계산
			Duration duration = Duration.between(startTime, LocalDateTime.now());

			// 분, 초, 밀리초 단위로 시간 정보 추출
			long minutes = duration.toMinutes();
			long seconds = duration.getSeconds() % 60;
			long millis = duration.toMillis() % 100;

			// 시간 정보를 문자열로 포맷팅하여 타이머 레이블에 표시
			timerLabel.setText(String.format("%02d:%02d:%02d", minutes, seconds, millis));
		}
	}

	private void ButtonActionListener(ActionEvent e) {
		// 게임이 종료된 경우 리턴
		if (isGameFinished) {
			return;
		}
		JButton clickedButton = (JButton) e.getSource();

		// 정확한 버튼을 눌렀을 때의 버튼 처리 코드
		if (Integer.parseInt(clickedButton.getText()) == currentNumber) {
			// 현재 숫자가 25 이하인 경우 숫자 업데이트
			// 현재 숫자가 26 이하인 경우
			if (currentNumber <= 25) {
				// 다음 숫자를 버튼에 표시
				clickedButton.setText(String.valueOf(num[currentNumber + 24]));
			} else {
				clickedButton.setText("");
			}
			// 마지막 50번째 버튼을 누르면 게임이 종료됨
			if (currentNumber == 50) {
				isGameFinished = true;
				LocalDateTime endTime = LocalDateTime.now();
				Duration duration = Duration.between(startTime, endTime);
				long minutes = duration.toMinutes();
				long seconds = duration.getSeconds() % 60;
				long millis = duration.toMillis() % 100;
				// 타이머 종료
				timer.stop();
				// 게임 종료 메시지 출력
				JOptionPane.showMessageDialog(this, "게임 종료! 걸린 시간: " + minutes + "분 " + seconds + "초" + millis);
				// 창 닫기
				dispose();
			}
			// 다음 숫자로 증가
			currentNumber++;
		} else {
			// 잘못된 버튼을 클릭했을 때 경고창 띄우기
			if (Integer.parseInt(clickedButton.getText()) != currentNumber) {
				JOptionPane.showMessageDialog(this, currentNumber + "번째 버튼 차례입니다!", "경고", JOptionPane.WARNING_MESSAGE);
			}
			// 버튼이 빈 문자열인 경우 아무것도 하지 않고 return
			else if (clickedButton.getText() == "") {
				return;
			}
		}

	}

	public static void main(String[] args) {
		new OneToFiftyGame(); // 게임 실행
	}
}
