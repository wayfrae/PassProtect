package passProtect;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class PassProtectApp extends JFrame {
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JPanel panelMain;
	private JTextField txtConfirm;
	private JPanel panelNewUser;
	private JPanel panelLogin;
	private JLabel lblErrorMessage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PassProtectApp frame = new PassProtectApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PassProtectApp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 500);

		panelMain = new JPanel();
		getContentPane().add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new BorderLayout(0, 0));

		panelLogin = createPanelLogin();
		panelMain.add(panelLogin);
		//panelMain.add(panelNewUser = createPanelNewUser(),BorderLayout.CENTER);

	}

	private JPanel createPanelLogin() {
		JPanel panelLogin = new JPanel();
		panelLogin.setBackground(new Color(255, 255, 255));
		panelLogin.setLayout(null);

		JLabel lblPassprotect = new JLabel("PassProtect");
		lblPassprotect.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassprotect.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 45));
		lblPassprotect.setBounds(114, 122, 399, 63);
		panelLogin.add(lblPassprotect);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(145, 193, 81, 23);
		panelLogin.add(lblUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(145, 218, 81, 23);
		panelLogin.add(lblPassword);

		txtUsername = new JTextField();
		txtUsername.setBackground(new Color(135, 206, 250));
		txtUsername.setBounds(225, 193, 254, 20);
		panelLogin.add(txtUsername);
		txtUsername.setColumns(10);

		txtPassword = new JPasswordField();
		txtPassword.setBackground(new Color(135, 206, 250));
		txtPassword.setColumns(10);
		txtPassword.setBounds(225, 219, 254, 20);
		panelLogin.add(txtPassword);

		JButton btnNewButton = new JButton("Log In");
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserManager user = new UserManager(txtUsername.getText(), txtPassword.getText());
				if (user.validatePassword()) {
					// TODO: redirect to account page
					lblErrorMessage.setForeground(Color.GREEN);
					lblErrorMessage.setText("Success! Logging you in...");
				} else {
					lblErrorMessage.setText("Incorrect username or password.");
				}
			}
		});
		btnNewButton.setBounds(145, 257, 167, 23);
		panelLogin.add(btnNewButton);

		JButton btnCreateNewAccount = new JButton("Create New Account");
		btnCreateNewAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMain.removeAll();
				panelNewUser = createPanelNewUser();
				panelMain.add(panelNewUser, BorderLayout.CENTER);
				panelMain.revalidate();
				panelMain.repaint();
			}
		});
		btnCreateNewAccount.setBounds(312, 257, 167, 23);
		panelLogin.add(btnCreateNewAccount);

		lblErrorMessage = new JLabel("");
		lblErrorMessage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblErrorMessage.setForeground(Color.RED);
		lblErrorMessage.setBounds(225, 282, 254, 32);
		panelLogin.add(lblErrorMessage);
		
		JLabel lblImage = new JLabel("");
		lblImage.setOpaque(true);
		lblImage.setIcon(new ImageIcon(PassProtectApp.class.getResource("/passProtect/PassLogo1.png")));
		lblImage.setBounds(29, 62, 108, 110);
		panelLogin.add(lblImage);
		return panelLogin;
	}

	private JPanel createPanelNewUser() {
		JPanel panelNewUser = new JPanel();
		panelNewUser.setBackground(new Color(255, 255, 255));
		panelNewUser.setLayout(null);

		JLabel lblPassprotect = new JLabel("Create Account");
		lblPassprotect.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassprotect.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 36));
		lblPassprotect.setBounds(114, 122, 399, 63);
		panelNewUser.add(lblPassprotect);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(145, 193, 75, 23);
		panelNewUser.add(lblUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(145, 218, 75, 23);
		panelNewUser.add(lblPassword);

		txtUsername = new JTextField();
		txtUsername.setBackground(new Color(135, 206, 250));
		txtUsername.setBounds(225, 193, 254, 20);
		panelNewUser.add(txtUsername);
		txtUsername.setColumns(10);

		txtPassword = new JPasswordField();
		txtPassword.setBackground(new Color(135, 206, 250));
		txtPassword.setColumns(10);
		txtPassword.setBounds(225, 219, 254, 20);
		panelNewUser.add(txtPassword);

		JButton btnCreateNewAccount = new JButton("Create Account");
		btnCreateNewAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtUsername.getText().equals("")) {
					if (!txtPassword.getText().equals("") && !txtConfirm.getText().equals("")) {
						if (txtPassword.getText().length() >= 8) {
							if (txtPassword.getText().equals(txtConfirm.getText())) {
								UserManager user = new UserManager(txtUsername.getText(), txtPassword.getText());
								if (user.createUser()) {
									// TODO: redirect to account page
									lblErrorMessage.setForeground(Color.GREEN);
									lblErrorMessage.setText("Success! Logging you in...");
								} else {
									lblErrorMessage.setText("That username is not available.");
								}
							} else {
								lblErrorMessage.setText("The passwords do not match.");
							}
						} else {
							lblErrorMessage.setText("Please use a password of at least 8 characters.");
						}
					} else {
						lblErrorMessage.setText("Please fill out both password fields.");
					}
				} else {
					lblErrorMessage.setText("Please enter a username.");
				}
			}
		});
		btnCreateNewAccount.setBounds(225, 276, 254, 23);
		panelNewUser.add(btnCreateNewAccount);

		JLabel lblConfirmPassword = new JLabel("Confirm Password:");
		lblConfirmPassword.setBounds(106, 245, 114, 23);
		panelNewUser.add(lblConfirmPassword);

		txtConfirm = new JPasswordField();
		txtConfirm.setBackground(new Color(135, 206, 250));
		txtConfirm.setColumns(10);
		txtConfirm.setBounds(225, 246, 254, 20);
		panelNewUser.add(txtConfirm);

		lblErrorMessage = new JLabel("");
		lblErrorMessage.setHorizontalTextPosition(SwingConstants.CENTER);
		lblErrorMessage.setHorizontalAlignment(SwingConstants.LEFT);
		lblErrorMessage.setForeground(Color.RED);
		lblErrorMessage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblErrorMessage.setBounds(225, 298, 399, 46);
		panelNewUser.add(lblErrorMessage);

		JButton btnGoBack = new JButton("\u25C0 Back to login");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMain.removeAll();
				panelLogin = createPanelLogin();
				panelMain.add(panelLogin, BorderLayout.CENTER);
				panelMain.revalidate();
				panelMain.repaint();
			}
		});
		btnGoBack.setForeground(new Color(139, 0, 0));
		btnGoBack.setBounds(238, 57, 150, 23);
		btnGoBack.setFocusPainted(false);
		btnGoBack.setMargin(new Insets(0, 0, 0, 0));
		btnGoBack.setContentAreaFilled(false);
		btnGoBack.setBorderPainted(false);
		btnGoBack.setOpaque(false);
		panelNewUser.add(btnGoBack);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(PassProtectApp.class.getResource("/passProtect/PassLogo1.png")));
		label.setBounds(24, 55, 114, 115);
		panelNewUser.add(label);
		return panelNewUser;
	}
}
