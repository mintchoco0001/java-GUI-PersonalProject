package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.ArrayList;

public class MemoryGame extends JFrame implements ActionListener {
	// ī�� ���� ����
	private final int CARD_PAIRS;
	// ī��� ��ư ����Ʈ
	private ArrayList<String> cards = new ArrayList<>();
	private ArrayList<JButton> buttons = new ArrayList<>();
	// ���õ� ī���
	private Card selectedCard1 = null;
	private Card selectedCard2 = null;
	// ī�� �� ������ Ȯ���ϴ� �÷���
	private boolean checking = false;
	// ���� ���� �� ���� �ð�
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	// Ÿ�̸� ���̺�
	private JLabel timerLabel = new JLabel("00:00:00", SwingConstants.CENTER);
	// ���� Ÿ�̸�
	private Timer gameTimer;

	// ������: ī�� ���� ������ �Է¹޾� ������ �ʱ�ȭ
	public MemoryGame(int cardPairs) {
		this.CARD_PAIRS = cardPairs;

		// ī�� ����: �� ī�� ���� �� �� �߰��Ͽ� ¦�� ����
		for (int i = 1; i <= CARD_PAIRS; i++) {
			String cardValue = Integer.toString(i);
			cards.add(cardValue);
			cards.add(cardValue);
		}
		// ī�� ����
		Collections.shuffle(cards);

		// JFrame �⺻ ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// Ÿ�̸� ���̺� ����
		timerLabel.setFont(new Font("Serif", Font.BOLD, 24));
		add(timerLabel, BorderLayout.NORTH);

		// ī�� �г� ����
		JPanel cardPanel = new JPanel(new GridLayout(5, 5));
		// ��ư ���� �� �߰�
		for (int i = 0; i < 2 * CARD_PAIRS; i++) {
			JButton button = new JButton();
			button.addActionListener(this);
			buttons.add(button);
			cardPanel.add(button);
		}
		add(cardPanel, BorderLayout.CENTER);

		pack();
		setSize(680, 680);

		// 3�� ī��Ʈ�ٿ� �� ���� ����
		Timer countdownTimer = new Timer(1000, new ActionListener() {
			int countdown = 3;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (countdown > 0) {
					timerLabel.setText("���� ���۱���: " + countdown + "��");
					countdown--;
				} else {
					((Timer) e.getSource()).stop();
					startGame();
				}
			}
		});
		countdownTimer.start();

		// ��� ��ư�� ��Ȱ��ȭ�ϰ� �ؽ�Ʈ�� ����
		for (JButton button : buttons) {
			button.setEnabled(false);
			button.setText("");
		}
		setLocationRelativeTo(null);
		setVisible(true);
	}

	// ���� ���� �޼���
	private void startGame() {
		// ���� ���� �ð� ���
		startTime = LocalDateTime.now();

		// ���� Ÿ�̸� ����
		gameTimer = new Timer(10, e -> updateTimer());
		gameTimer.start();

		// ��� ��ư�� Ȱ��ȭ��
		for (JButton button : buttons) {
			button.setEnabled(true);
			button.setBackground(Color.white); // ��ư ������ ������� ����
			button.setForeground(Color.black); // ��ư ��Ʈ ������ ������� ����

		}
	}

	// Ÿ�̸� ������Ʈ �޼���
	private void updateTimer() {
		Duration duration = Duration.between(startTime, LocalDateTime.now());
		long minutes = duration.getSeconds() / 60;
		long seconds = duration.getSeconds() % 60;
		long millis = duration.toMillis() % 100;
		timerLabel.setText(String.format("%02d:%02d:%02d", minutes, seconds, millis));
	}

	// ��ư Ŭ�� �� ȣ��Ǵ� �޼���
	@Override
	public void actionPerformed(ActionEvent e) {
		if (checking) {
			return; // ī�� �� ���� ���� �ٸ� �Է��� ����
		}

		JButton clickedButton = (JButton) e.getSource();

		if (selectedCard1 != null && selectedCard1.button == clickedButton) {
			return; // ���� ��ư�� �� �� ������ ���� ����
		}

		if (selectedCard1 != null && selectedCard2 != null) {
			return; // �̹� �� ī�尡 ���õ� ���
		}

		int index = buttons.indexOf(clickedButton);
		String cardValue = cards.get(index);

		clickedButton.setText(cardValue); // ī�� ���� ������

		if (selectedCard1 == null) {
			selectedCard1 = new Card(clickedButton, cardValue);
		} else if (selectedCard2 == null && clickedButton != selectedCard1.button) {
			selectedCard2 = new Card(clickedButton, cardValue);
			checkCards();
		}
	}

	// ���õ� �� ī���� ���� ���ϴ� �޼���
	private void checkCards() {
		checking = true; // ī�� �� ������ ǥ��

		if (selectedCard1.value.equals(selectedCard2.value)) {
			// ���� ���� ���� ī���� ���
			selectedCard1.button.setBackground(Color.lightGray); // ��ư ������ ȸ������ ����
			selectedCard2.button.setBackground(Color.lightGray); // ��ư ������ ȸ������ ����
			selectedCard1.button.setForeground(Color.white); // ��ư ��Ʈ ������ ������� ����
			selectedCard2.button.setForeground(Color.white); // ��ư ��Ʈ ������ ������� ����
			selectedCard1.button.setEnabled(false);
			selectedCard2.button.setEnabled(false);
			resetSelection();
		} else {
			// �ٸ� ���� ���� ī���� ���
			Timer timer = new Timer(500, e -> {
				selectedCard1.button.setText("");
				selectedCard2.button.setText("");
				resetSelection();
			});
			timer.setRepeats(false);
			timer.start();
		}

		// ��� ��ư�� ��Ȱ��ȭ�Ǿ����� Ȯ��
		if (isAllButtonsDisabled()) {
			endTime = LocalDateTime.now(); // ���� ���� �ð� ���
			Duration duration = Duration.between(startTime, endTime);
			long minutes = duration.getSeconds() / 60; // 60�� ���� ���� ��
			long seconds = duration.getSeconds() % 60; // 60�� ���� �������� ��
			long millis = duration.toMillis() % 100;
			gameTimer.stop(); // ���� Ÿ�̸� ����
			JOptionPane.showMessageDialog(this,
					"�����մϴ�! ��� ī�带 ������ϴ�.\n�ҿ� �ð�: " + minutes + " �� " + seconds + "��" + millis);

			dispose();
		}
	}

	// ��� ��ư�� ��Ȱ��ȭ�Ǿ����� Ȯ���ϴ� �޼���
	private boolean isAllButtonsDisabled() {
		for (JButton button : buttons) {
			if (button.isEnabled()) {
				return false; // �ϳ��� Ȱ��ȭ�� ��ư�� ������ false ��ȯ
			}
		}
		return true; // ��� ��ư�� ��Ȱ��ȭ�� ��� true ��ȯ
	}

	// ���õ� ī�� �ʱ�ȭ �޼���
	private void resetSelection() {
		selectedCard1 = null;
		selectedCard2 = null;
		checking = false; // ī�� �񱳰� �������� ǥ��
	}

	// ī�� Ŭ����: ��ư�� ���� ����
	class Card {
		JButton button;
		String value;

		Card(JButton button, String value) {
			this.button = button;
			this.value = value;
		}
	}

	// main�� �����ٶ� ���̵��� �⺻���� 10(main���� ������ ���� ���̵�)�� �ְ� ������
	public static void main(String[] args) {
		new MemoryGame(10);
	}
}