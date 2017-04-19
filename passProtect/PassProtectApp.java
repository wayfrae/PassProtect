package passProtect;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class PassProtectApp extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField txtPassword;

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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username: ");
		lblUsername.setBounds(153, 184, 67, 16);
		panel.add(lblUsername);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(230, 181, 227, 22);
		txtUsername.setText("username");
		panel.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password: ");
		lblPassword.setBounds(153, 234, 64, 16);
		panel.add(lblPassword);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(230, 228, 227, 22);
		txtPassword.setText("password");
		panel.add(txtPassword);
		txtPassword.setColumns(10);
		
		JLabel lblPassprotect = new JLabel("PassProtect");
		lblPassprotect.setFont(new Font("Copperplate Gothic Bold", Font.PLAIN, 45));
		lblPassprotect.setBounds(134, 101, 339, 70);
		panel.add(lblPassprotect);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(230, 272, 88, 25);
		panel.add(btnSubmit);
		
		JButton btnCreateAccount = new JButton("Create account...");
		btnCreateAccount.setBounds(318, 272, 139, 25);
		panel.add(btnCreateAccount);
	}
}

