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
	// ���� ��ü ����
	private Random r = new Random();
	// ��ư �迭 ���� (25��)
	private JButton[] buttons = new JButton[25];
	// ���� ������ �� ���� �ʱ�ȭ
	private int currentNumber = 1;
	// ���� ���� ���� �÷���
	private boolean isGameFinished = false;
	// ���� ���� �ð� ����
	private LocalDateTime startTime;
	// 1���� 50������ ���ڸ� ������ �迭
	private int[] num = new int[50];
	// Ÿ�̸� ���̺� �ʱ�ȭ
	private JLabel timerLabel = new JLabel("00:00:00", SwingConstants.CENTER);
	// 0.001�ʸ��� ���ŵǴ� Ÿ�̸�
	private Timer timer = new Timer(1, new TimerActionListener());

	public OneToFiftyGame() {
		// â ���� ����
		setTitle("1 to 50 ����");
		// â ���� �� ���α׷� ����
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container c = getContentPane();
		// BorderLayout ����
		c.setLayout(new BorderLayout());

		// ��ܿ� Ÿ�̸� ���̺� �߰�
		timerLabel.setFont(new Font(null, Font.BOLD, 20));
		c.add(timerLabel, BorderLayout.NORTH);

		// 5x5 �׸��� ���̾ƿ��� �г� ����
		JPanel buttonPanel = new JPanel(new GridLayout(5, 5));
		c.add(buttonPanel, BorderLayout.CENTER);

		// ���� ���� �迭 ����
		new OTFAddNum(r, num);

		// ��ư ���� �� �߰�
		for (int i = 0; i < 25; i++) {
			// ��ư�� ���� ����
			buttons[i] = new JButton(String.valueOf(this.num[i]));
			buttons[i].setFont(new Font(null, Font.BOLD, 20));
			// �ʱ⿡�� ��ư ��Ȱ��ȭ
			buttons[i].setEnabled(false);
			// �ʱ⿡�� ��ư ����
			buttons[i].setVisible(false);
			// ��ư ������ ������� ����
			buttons[i].setBackground(Color.white);
			// ��ư �гο� �߰�
			buttonPanel.add(buttons[i]);
			// ��ư Ŭ�� �̺�Ʈ ������ �߰�
			buttons[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ButtonActionListener(e);
				}
			});
		}
		// ������ ũ�� �� ���ü� ����
		setSize(720, 720);
		// ���÷��� �߾ӿ� ��ġ
		setLocationRelativeTo(null);
		setVisible(true);

		// 3�� ī��Ʈ�ٿ� �� ���� ����
		startCountdown();
	}

	// 3�� ī��Ʈ�ٿ� �� ���� ����
	private void startCountdown() {
		Timer countdownTimer = new Timer(1000, new ActionListener() {
			int countdown = 3; // ī��Ʈ�ٿ� �ʱⰪ ����

			@Override
			public void actionPerformed(ActionEvent e) {
				if (countdown > 0) {
					// ī��Ʈ�ٿ� ǥ��
					timerLabel.setText("���� ���۱���: " + countdown + "��");
					countdown--;
				} else {
					// ī��Ʈ�ٿ� Ÿ�̸� ����
					((Timer) e.getSource()).stop();
					// ���� ����
					startGame();
				}
			}
		});
		// ī��Ʈ�ٿ� ����
		countdownTimer.start();
	}

	// ���� ����
	private void startGame() {
		// ���� �ð� ���
		startTime = LocalDateTime.now();
		// Ÿ�̸� ����
		startTimer();
		for (int i = 0; i < 25; i++) {
			// ��ư Ȱ��ȭ
			buttons[i].setEnabled(true);
			// ��ư ǥ��
			buttons[i].setVisible(true);
		}
	}

	// Ÿ�̸� ����
	private void startTimer() {
		timer.start();
	}

	// Ÿ�̸� ������Ʈ ������
	private class TimerActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// ���� �ð��� ���� �ð��� ���̸� ���
			Duration duration = Duration.between(startTime, LocalDateTime.now());

			// ��, ��, �и��� ������ �ð� ���� ����
			long minutes = duration.toMinutes();
			long seconds = duration.getSeconds() % 60;
			long millis = duration.toMillis() % 100;

			// �ð� ������ ���ڿ��� �������Ͽ� Ÿ�̸� ���̺� ǥ��
			timerLabel.setText(String.format("%02d:%02d:%02d", minutes, seconds, millis));
		}
	}

	private void ButtonActionListener(ActionEvent e) {
		// ������ ����� ��� ����
		if (isGameFinished) {
			return;
		}
		JButton clickedButton = (JButton) e.getSource();

		// ��Ȯ�� ��ư�� ������ ���� ��ư ó�� �ڵ�
		if (Integer.parseInt(clickedButton.getText()) == currentNumber) {
			// ���� ���ڰ� 25 ������ ��� ���� ������Ʈ
			// ���� ���ڰ� 26 ������ ���
			if (currentNumber <= 25) {
				// ���� ���ڸ� ��ư�� ǥ��
				clickedButton.setText(String.valueOf(num[currentNumber + 24]));
			} else {
				clickedButton.setText("");
			}
			// ������ 50��° ��ư�� ������ ������ �����
			if (currentNumber == 50) {
				isGameFinished = true;
				LocalDateTime endTime = LocalDateTime.now();
				Duration duration = Duration.between(startTime, endTime);
				long minutes = duration.toMinutes();
				long seconds = duration.getSeconds() % 60;
				long millis = duration.toMillis() % 100;
				// Ÿ�̸� ����
				timer.stop();
				// ���� ���� �޽��� ���
				JOptionPane.showMessageDialog(this, "���� ����! �ɸ� �ð�: " + minutes + "�� " + seconds + "��" + millis);
				// â �ݱ�
				dispose();
			}
			// ���� ���ڷ� ����
			currentNumber++;
		} else {
			// �߸��� ��ư�� Ŭ������ �� ���â ����
			if (Integer.parseInt(clickedButton.getText()) != currentNumber) {
				JOptionPane.showMessageDialog(this, currentNumber + "��° ��ư �����Դϴ�!", "���", JOptionPane.WARNING_MESSAGE);
			}
			// ��ư�� �� ���ڿ��� ��� �ƹ��͵� ���� �ʰ� return
			else if (clickedButton.getText() == "") {
				return;
			}
		}

	}

	public static void main(String[] args) {
		new OneToFiftyGame(); // ���� ����
	}
}
