package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.ArrayList;

public class MemoryGame extends JFrame implements ActionListener {
	// 카드 쌍의 개수
	private final int CARD_PAIRS;
	// 카드와 버튼 리스트
	private ArrayList<String> cards = new ArrayList<>();
	private ArrayList<JButton> buttons = new ArrayList<>();
	// 선택된 카드들
	private Card selectedCard1 = null;
	private Card selectedCard2 = null;
	// 카드 비교 중인지 확인하는 플래그
	private boolean checking = false;
	// 게임 시작 및 종료 시간
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	// 타이머 레이블
	private JLabel timerLabel = new JLabel("00:00:00", SwingConstants.CENTER);
	// 게임 타이머
	private Timer gameTimer;

	// 생성자: 카드 쌍의 개수를 입력받아 게임을 초기화
	public MemoryGame(int cardPairs) {
		this.CARD_PAIRS = cardPairs;

		// 카드 세팅: 각 카드 값을 두 번 추가하여 짝을 만듦
		for (int i = 1; i <= CARD_PAIRS; i++) {
			String cardValue = Integer.toString(i);
			cards.add(cardValue);
			cards.add(cardValue);
		}
		// 카드 섞기
		Collections.shuffle(cards);

		// JFrame 기본 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// 타이머 레이블 설정
		timerLabel.setFont(new Font("Serif", Font.BOLD, 24));
		add(timerLabel, BorderLayout.NORTH);

		// 카드 패널 생성
		JPanel cardPanel = new JPanel(new GridLayout(5, 5));
		// 버튼 생성 및 추가
		for (int i = 0; i < 2 * CARD_PAIRS; i++) {
			JButton button = new JButton();
			button.addActionListener(this);
			buttons.add(button);
			cardPanel.add(button);
		}
		add(cardPanel, BorderLayout.CENTER);

		pack();
		setSize(680, 680);

		// 3초 카운트다운 후 게임 시작
		Timer countdownTimer = new Timer(1000, new ActionListener() {
			int countdown = 3;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (countdown > 0) {
					timerLabel.setText("게임 시작까지: " + countdown + "초");
					countdown--;
				} else {
					((Timer) e.getSource()).stop();
					startGame();
				}
			}
		});
		countdownTimer.start();

		// 모든 버튼을 비활성화하고 텍스트를 지움
		for (JButton button : buttons) {
			button.setEnabled(false);
			button.setText("");
		}
		setLocationRelativeTo(null);
		setVisible(true);
	}

	// 게임 시작 메서드
	private void startGame() {
		// 게임 시작 시간 기록
		startTime = LocalDateTime.now();

		// 게임 타이머 시작
		gameTimer = new Timer(10, e -> updateTimer());
		gameTimer.start();

		// 모든 버튼을 활성화함
		for (JButton button : buttons) {
			button.setEnabled(true);
			button.setBackground(Color.white); // 버튼 배경색을 흰색으로 설정
			button.setForeground(Color.black); // 버튼 폰트 색상을 흰색으로 설정

		}
	}

	// 타이머 업데이트 메서드
	private void updateTimer() {
		Duration duration = Duration.between(startTime, LocalDateTime.now());
		long minutes = duration.getSeconds() / 60;
		long seconds = duration.getSeconds() % 60;
		long millis = duration.toMillis() % 100;
		timerLabel.setText(String.format("%02d:%02d:%02d", minutes, seconds, millis));
	}

	// 버튼 클릭 시 호출되는 메서드
	@Override
	public void actionPerformed(ActionEvent e) {
		if (checking) {
			return; // 카드 비교 중일 때는 다른 입력을 무시
		}

		JButton clickedButton = (JButton) e.getSource();

		if (selectedCard1 != null && selectedCard1.button == clickedButton) {
			return; // 같은 버튼을 두 번 누르는 것을 방지
		}

		if (selectedCard1 != null && selectedCard2 != null) {
			return; // 이미 두 카드가 선택된 경우
		}

		int index = buttons.indexOf(clickedButton);
		String cardValue = cards.get(index);

		clickedButton.setText(cardValue); // 카드 값을 보여줌

		if (selectedCard1 == null) {
			selectedCard1 = new Card(clickedButton, cardValue);
		} else if (selectedCard2 == null && clickedButton != selectedCard1.button) {
			selectedCard2 = new Card(clickedButton, cardValue);
			checkCards();
		}
	}

	// 선택된 두 카드의 값을 비교하는 메서드
	private void checkCards() {
		checking = true; // 카드 비교 중임을 표시

		if (selectedCard1.value.equals(selectedCard2.value)) {
			// 같은 값을 가진 카드일 경우
			selectedCard1.button.setBackground(Color.lightGray); // 버튼 배경색을 회색으로 설정
			selectedCard2.button.setBackground(Color.lightGray); // 버튼 배경색을 회색으로 설정
			selectedCard1.button.setForeground(Color.white); // 버튼 폰트 색상을 흰색으로 설정
			selectedCard2.button.setForeground(Color.white); // 버튼 폰트 색상을 흰색으로 설정
			selectedCard1.button.setEnabled(false);
			selectedCard2.button.setEnabled(false);
			resetSelection();
		} else {
			// 다른 값을 가진 카드일 경우
			Timer timer = new Timer(500, e -> {
				selectedCard1.button.setText("");
				selectedCard2.button.setText("");
				resetSelection();
			});
			timer.setRepeats(false);
			timer.start();
		}

		// 모든 버튼이 비활성화되었는지 확인
		if (isAllButtonsDisabled()) {
			endTime = LocalDateTime.now(); // 게임 종료 시간 기록
			Duration duration = Duration.between(startTime, endTime);
			long minutes = duration.getSeconds() / 60; // 60을 나눈 값이 분
			long seconds = duration.getSeconds() % 60; // 60을 나눈 나머지가 초
			long millis = duration.toMillis() % 100;
			gameTimer.stop(); // 게임 타이머 중지
			JOptionPane.showMessageDialog(this,
					"축하합니다! 모든 카드를 맞췄습니다.\n소요 시간: " + minutes + " 분 " + seconds + "초" + millis);

			dispose();
		}
	}

	// 모든 버튼이 비활성화되었는지 확인하는 메서드
	private boolean isAllButtonsDisabled() {
		for (JButton button : buttons) {
			if (button.isEnabled()) {
				return false; // 하나라도 활성화된 버튼이 있으면 false 반환
			}
		}
		return true; // 모든 버튼이 비활성화된 경우 true 반환
	}

	// 선택된 카드 초기화 메서드
	private void resetSelection() {
		selectedCard1 = null;
		selectedCard2 = null;
		checking = false; // 카드 비교가 끝났음을 표시
	}

	// 카드 클래스: 버튼과 값을 포함
	class Card {
		JButton button;
		String value;

		Card(JButton button, String value) {
			this.button = button;
			this.value = value;
		}
	}

	// main에 보내줄때 난이도의 기본값인 10(main에서 설정한 보통 난이도)을 주고 보내줌
	public static void main(String[] args) {
		new MemoryGame(10);
	}
}