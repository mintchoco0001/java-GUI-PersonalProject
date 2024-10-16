package Main;

import javax.swing.*;
import Game.*;
import java.awt.*;
import java.awt.event.*;

//JFrame�� ��ӹ޾� GUI ���ø����̼��� ����
public class Main extends JFrame {
	// ������
	Main() {
		// ������ ���� ����
		setTitle("������ ���� �����");
		setDefaultCloseOperation(EXIT_ON_CLOSE); // �ݱ� ��ư Ŭ�� �� ���ø����̼� ����

		// �����̳ʿ� ���̾ƿ� ����
		Container c = getContentPane();
		c.setLayout(new GridBagLayout()); // GridBagLayout ���
		GridBagConstraints gbc1 = new GridBagConstraints();
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc1.insets = new Insets(30, 5, 5, 5); // ��, ����, �Ʒ�, ������ ���� ����
		gbc2.insets = new Insets(5, 5, 5, 5); // ��, ����, �Ʒ�, ������ ���� ����

		// ���̺� ���� �� �߰�
		JLabel titleLabel = new JLabel("������ ���� �����");
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		c.add(titleLabel, gbc1); // ���̺��� �����̳ʿ� �߰�

		// ��ư ���� �� �߰�
		JButton btn1 = new JButton("1 to 50 ����");
		JButton btn2 = new JButton("�޸𸮰���");
		JButton btn3 = new JButton("���� ����");
		gbc1.gridy = 1;
		c.add(btn1, gbc1); // ù ��° ��ư �߰�
		gbc2.gridy = 2;
		c.add(btn2, gbc2); // �� ��° ��ư �߰�
		gbc2.gridy = 3;
		c.add(btn3, gbc2); // �� ��° ��ư �߰�

		// ��ư �̺�Ʈ ������ �߰�
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 1 to 50 ���� ���� �޼��� ȣ��
				startOneToFiftyGame();
			}
		});

		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// �޸� ���� ���� �޼��� ȣ��
				startMemoryGame();
			}
		});

		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ���� ����
				System.exit(0);
			}
		});

		// ������ ũ�� �� ���ü� ����
		c.setBackground(Color.white); // ���� ����
		setSize(250, 300); // ������ ũ�� ����
		setVisible(true); // ������ ���ü� ����
	}

	// 1 to 50 ���� ���� �޼���
	private void startOneToFiftyGame() {
		new OneToFiftyGame(); // OneToFiftyGame Ŭ���� �ν��Ͻ� ����
	}

	// �޸� ���� ���� �޼���
	private void startMemoryGame() {
		// ���̵� ���� �ɼ�
		Object[] Options = { "����(ī�� 6��)", "����(ī�� 10��)", "�����(ī�� 15��)" };
		int difficulty = JOptionPane.showOptionDialog(null, "���̵��� �����ϼ���.", "���̵� ����", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, Options, Options[1]);

		int cardPairs;
		switch (difficulty) {
		case 0: // ����
			cardPairs = 6;
			break;
		case 1: // ����
			cardPairs = 10;
			break;
		case 2: // �����
			cardPairs = 15;
			break;
		default:
			cardPairs = 10; // �⺻��
		}
		new MemoryGame(cardPairs); // MemoryGame Ŭ���� �ν��Ͻ� ����
	}

	// ���� �޼���
	public static void main(String[] args) {
		// GUI ����
		new Main(); // Main Ŭ���� �ν��Ͻ� ����
	}
}