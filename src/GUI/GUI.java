package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import Driver.App;

public class GUI extends JFrame {
	private JButton button = new JButton("Login");
	private JTextField inputUser = new JTextField(20);
	private JPasswordField inputPass = new JPasswordField(20);
	private JLabel labelUser = new JLabel("Username");
	private JLabel labelPass = new JLabel("Password");
	private JLabel loginFail = new JLabel("");
	private JPanel panel = new JPanel();

	public GUI() throws Exception {
		super("Simple JDBC");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 200);
		this.setResizable(false);
		ImageIcon image = new ImageIcon("src/GUI/resources/img/logo.png");
		this.setIconImage(image.getImage());

		panel.setLayout(null);
		labelUser.setBounds(10, 20, 80, 25);
		panel.add(labelUser);
		inputUser.setBounds(100, 20, 165, 25);
		panel.add(inputUser);

		labelPass.setBounds(10, 50, 80, 25);
		panel.add(labelPass);
		inputPass.setBounds(100, 50, 165, 25);
		panel.add(inputPass);

		loginFail.setBounds(10, 100, 160, 25);
		panel.add(loginFail);
		
		button.addActionListener(new EventListener(this));
		button.setBounds(100, 80, 80, 25);
		panel.add(button);
		
		this.add(panel, BorderLayout.CENTER);
		this.setVisible(true);
	}

	class EventListener implements ActionListener {
		GUI currentGUI = null;
		App mysqlcon = null;

		public EventListener(GUI gui) {
			currentGUI = gui;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String user = inputUser.getText();
			String pass = String.valueOf(inputPass.getPassword());

			try {
				mysqlcon = new App(user, pass);
				loginFail.setText("");
				currentGUI.setVisible(false);
				//TimeUnit.SECONDS.sleep(5); // for tests

			} catch (Exception exc) {
				loginFail.setText("Connection Failed");
			} finally {
				if (mysqlcon != null) {
					mysqlcon.close_connection(1);
					System.out.println("Connection closed successfuly");
				}
				currentGUI.setVisible(true);
			}
		}
	}
}
