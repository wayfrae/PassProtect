/*
 * Assignment: Final Project
 * Class: CSIS-1410-005
 * Programmers: Alan Banner, Alan Bischoff, Zach Frazier, Tim Lawrence
 * Created: Apr 6, 2017
 */
package passProtect;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;

@SuppressWarnings("serial")
public class PassProtectApp extends JFrame {
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JPanel panelMain;
	private JTextField txtConfirm;
	private JPanel panelNewUser;
	private JPanel panelLogin;
	private JLabel lblErrorMessage;
	private JLabel lblError;

	private JPanel panelManager;
	private JPanel controlPane;
	private JScrollPane scrollPane;
	private JPanel recordPane;
	private JTextField txtSearch;
	private JTextField txtNewDomain;
	private JTextField txtNewUsername;
	private JTextField txtNewPassword;
	private JButton btnNewAdd;
	private String currentSearch = "";

	private UserManager user = null;
	private PasswordManager userpm = null;
	private List<PasswordRecord> records = null;
	private JPanel header;
	private JPanel panelSettings;
	private JLabel lblWelcome;
	private JButton btnSettings;
	private JButton btnLogOut;
	private JTextField txtChangePassword;
	private JTextField txtChangeConfirm;

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
		panelMain.setBackground(Color.WHITE);
		getContentPane().add(panelMain, BorderLayout.CENTER);
		panelMain.setLayout(new BorderLayout(0, 0));
		panelLogin = createPanelLogin();

		panelMain.add(panelLogin); // uncomment to run normally
		// panelMain.add(createPanelSettings(), BorderLayout.CENTER);
		// //uncomment to show settings pane in window builder
		// panelMain.add(createPanelManager(), , BorderLayout.CENTER);
		// //uncomment to show manager pane in window builder
		// panelMain.add(createPanelNewUser(),BorderLayout.CENTER); //uncomment
		// to show new user pane in window builder

	}

	private JPanel createPanelSettings() {
		JPanel panelSettings = new JPanel();
		panelSettings.setBackground(new Color(255, 255, 255));

		panelSettings.setLayout(null);

		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setHorizontalAlignment(SwingConstants.CENTER);
		lblSettings.setFont(new Font("Tahoma", Font.PLAIN, 31));
		lblSettings.setBounds(199, 30, 235, 36);
		panelSettings.add(lblSettings);

		JLabel lblPassword_1 = new JLabel("Password:");
		lblPassword_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword_1.setBounds(40, 133, 163, 16);
		panelSettings.add(lblPassword_1);

		txtChangePassword = new JTextField();
		txtChangePassword.setBackground(new Color(135, 206, 250));
		txtChangePassword.setBounds(213, 131, 215, 19);
		panelSettings.add(txtChangePassword);
		txtChangePassword.setColumns(10);

		JLabel lblConfirmPassword_1 = new JLabel("Confirm Password:");
		lblConfirmPassword_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConfirmPassword_1.setBounds(40, 163, 163, 16);
		panelSettings.add(lblConfirmPassword_1);

		txtChangeConfirm = new JTextField();
		txtChangeConfirm.setBackground(new Color(135, 206, 250));
		txtChangeConfirm.setBounds(213, 161, 215, 19);
		panelSettings.add(txtChangeConfirm);
		txtChangeConfirm.setColumns(10);

		JButton btnChangePassword = new JButton("Change Password");
		btnChangePassword.setBackground(new Color(255, 255, 255));
		btnChangePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtChangeConfirm.getText().equals(txtChangePassword.getText())
						&& !txtChangeConfirm.getText().equals("") && !txtChangePassword.getText().equals("")) {
					if (txtChangePassword.getText().length() >= 8) {
						user.removeUser();
						user.setUserPass(txtChangeConfirm.getText());
						user.createUser();
						lblError.setForeground(new Color(0, 128, 0));
						lblError.setText("You have successfully changed your password!");
					} else {
						lblError.setText("Your password must be at least 8 characters.");
					}
				} else {
					lblError.setText("The Passwords Do Not Match.");
				}
			}
		});
		btnChangePassword.setBounds(213, 191, 215, 25);
		panelSettings.add(btnChangePassword);

		JButton btnDelete = new JButton("Delete Account");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete your account?",
						"Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {
					
					//overwrites password file when deleting account
					try {
						PrintWriter pWriter = new PrintWriter(new FileWriter("./src/files/" + user.getUserName() + ".pwr", false));
						pWriter.print("");
						pWriter.close();
						
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					user.removeUser();
					panelMain.removeAll();
					panelLogin = createPanelLogin();
					panelMain.add(panelLogin, BorderLayout.CENTER);
					panelMain.revalidate();
					panelMain.repaint();
				}
			}
		});
		btnDelete.setForeground(new Color(255, 255, 255));
		btnDelete.setBackground(new Color(255, 0, 0));
		btnDelete.setBounds(206, 323, 221, 25);
		panelSettings.add(btnDelete);

		JLabel lblChangePassword = new JLabel("Change Password");
		lblChangePassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblChangePassword.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblChangePassword.setBounds(150, 84, 342, 36);
		panelSettings.add(lblChangePassword);

		JSeparator separator = new JSeparator();
		separator.setBounds(126, 71, 391, 2);
		panelSettings.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(126, 254, 391, 2);
		panelSettings.add(separator_1);

		JLabel lblPermanentlyDeleteAccount = new JLabel("Permanently Delete Account");
		lblPermanentlyDeleteAccount.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblPermanentlyDeleteAccount.setHorizontalAlignment(SwingConstants.CENTER);
		lblPermanentlyDeleteAccount.setBounds(177, 267, 280, 36);
		panelSettings.add(lblPermanentlyDeleteAccount);

		JLabel lblWarning = new JLabel("(Warning: This is not reversible!)");
		lblWarning.setHorizontalAlignment(SwingConstants.CENTER);
		lblWarning.setBounds(194, 297, 245, 14);
		panelSettings.add(lblWarning);

		JButton btnGoBack = new JButton("\u25C0 Go Back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelMain.removeAll();
				panelMain.add(panelManager, BorderLayout.CENTER);
				panelMain.revalidate();
				panelMain.repaint();
			}
		});
		btnGoBack.setBounds(150, 45, 89, 23);
		btnGoBack.setForeground(new Color(139, 0, 0));
		btnGoBack.setFocusPainted(false);
		btnGoBack.setMargin(new Insets(0, 0, 0, 0));
		btnGoBack.setContentAreaFilled(false);
		btnGoBack.setBorderPainted(false);
		btnGoBack.setOpaque(false);
		panelSettings.add(btnGoBack);

		lblError = new JLabel("");
		lblError.setForeground(new Color(255, 0, 0));
		lblError.setBounds(213, 227, 342, 16);
		panelSettings.add(lblError);
		return panelSettings;
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
				user = new UserManager(txtUsername.getText(), txtPassword.getText());
				if (user.validatePassword()) {
					userpm = new PasswordManager(user.getUserName());
					// TODO: redirect to account page
					lblErrorMessage.setForeground(new Color(0, 128, 0));
					lblErrorMessage.setText("Success! Logging you in...");
					panelMain.removeAll();
					panelManager = createPanelManager();
					panelMain.add(panelManager, BorderLayout.CENTER);
					panelMain.revalidate();
					panelMain.repaint();
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
		lblImage.setIcon(new ImageIcon(PassProtectApp.class.getResource("/passProtect/images/PassLogo1.png")));
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
								user = new UserManager(txtUsername.getText(), txtPassword.getText());
								if (user.createUser()) {
									userpm = new PasswordManager(user.getUserName());
									// TODO: redirect to account page
									lblErrorMessage.setForeground(Color.GREEN);
									lblErrorMessage.setText("Success! Logging you in...");
									panelMain.removeAll();
									panelManager = createPanelManager();
									panelMain.add(panelManager, BorderLayout.CENTER);
									panelMain.revalidate();
									panelMain.repaint();
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
		label.setIcon(new ImageIcon(PassProtectApp.class.getResource("/passProtect/images/PassLogo1.png")));
		label.setBounds(24, 55, 114, 115);
		panelNewUser.add(label);
		return panelNewUser;
	}

	public JPanel createPanelManager() {
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 5));

		// Creates the control panel
		controlPane = new JPanel();
		controlPane.setBackground(Color.WHITE);
		createSearchPane();
		createNewPane();
		contentPane.add(controlPane, BorderLayout.SOUTH);

		// Creates the record panel
		scrollPane = new JScrollPane();
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		recordPane = new JPanel();
		recordPane.setBackground(Color.WHITE);
		scrollPane.setViewportView(recordPane);
		recordPane.setLayout(new BoxLayout(recordPane, BoxLayout.PAGE_AXIS));

		// createRecordEntryPane(new PasswordRecord("domain", "user", "pass"));
		// // TODO: Uncomment this line to view in Design tab
		listRecordEntryPanes("");

		contentPane.add(scrollPane, BorderLayout.CENTER);

		header = new JPanel();
		header.setBackground(new Color(255, 255, 255));
		contentPane.add(header, BorderLayout.NORTH);
		header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));

		lblWelcome = new JLabel("Welcome, " + user.getUserName() + "!");
		lblWelcome.setMaximumSize(new Dimension(2147483647, 2147483647));
		lblWelcome.setHorizontalAlignment(SwingConstants.LEFT);
		header.add(lblWelcome);

		btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMain.removeAll();
				panelLogin = createPanelLogin();
				panelMain.add(panelLogin, BorderLayout.CENTER);
				panelMain.revalidate();
				panelMain.repaint();
			}
		});
		header.add(btnLogOut);

		btnSettings = new JButton("Settings");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelMain.removeAll();
				panelSettings = createPanelSettings();
				panelMain.add(panelSettings);
				panelMain.revalidate();
				panelMain.repaint();
			}
		});
		header.add(btnSettings);

		return contentPane;
	}

	/**
	 * Creates the Search panel on the control panel
	 */
	private void createSearchPane() {
		controlPane.setLayout(new GridLayout(0, 1, 0, 5));
		JPanel searchPane = new JPanel();
		searchPane.setBackground(Color.WHITE);
		searchPane.setLayout(new BoxLayout(searchPane, BoxLayout.X_AXIS));

		JLabel lblSearch = new JLabel("Search");
		lblSearch.setPreferredSize(new Dimension(50, 14));
		lblSearch.setMinimumSize(new Dimension(50, 14));
		lblSearch.setMaximumSize(new Dimension(50, 14));
		searchPane.add(lblSearch);

		txtSearch = new JTextField();
		txtSearch.setBackground(new Color(135, 206, 250));
		txtSearch.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				validate(e);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				validate(e);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				validate(e);
			}

			public void validate(DocumentEvent e) {
				currentSearch = txtSearch.getText();
				listRecordEntryPanes(currentSearch);
			}
		});
		searchPane.add(txtSearch);
		txtSearch.setColumns(10);

		controlPane.add(searchPane);
	}

	/**
	 * Creates the New panel on the control panel
	 */
	private void createNewPane() {
		JPanel newPane = new JPanel();
		newPane.setBackground(Color.WHITE);
		newPane.setLayout(new BoxLayout(newPane, BoxLayout.X_AXIS));

		JLabel lblNew = new JLabel("New:");
		lblNew.setPreferredSize(new Dimension(40, 14));
		lblNew.setMinimumSize(new Dimension(40, 14));
		lblNew.setMaximumSize(new Dimension(40, 14));
		newPane.add(lblNew);

		txtNewDomain = new JTextField();
		txtNewDomain.setBackground(new Color(135, 206, 250));
		txtNewDomain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtNewUsername.requestFocus();
			}
		});
		txtNewDomain.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtNewDomain.getText().equals("Domain"))
					txtNewDomain.setText("");
			}
		});
		txtNewDomain.setText("Domain");
		newPane.add(txtNewDomain);
		txtNewDomain.setColumns(10);

		txtNewUsername = new JTextField();
		txtNewUsername.setBackground(new Color(135, 206, 250));
		txtNewUsername.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtNewPassword.requestFocus();
			}
		});
		txtNewUsername.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtNewUsername.getText().equals("Username"))
					txtNewUsername.setText("");
			}
		});
		txtNewUsername.setText("Username");
		newPane.add(txtNewUsername);
		txtNewUsername.setColumns(10);

		txtNewPassword = new JTextField();
		txtNewPassword.setBackground(new Color(135, 206, 250));
		txtNewPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewAdd.requestFocus();
			}
		});
		txtNewPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtNewPassword.getText().equals("Password"))
					txtNewPassword.setText("");
			}
		});
		txtNewPassword.setText("Password");
		newPane.add(txtNewPassword);
		txtNewPassword.setColumns(10);

		btnNewAdd = new JButton("Add");
		btnNewAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String domain = txtNewDomain.getText();
				if (domain.equals("") || domain.equals("Domain")) {
					JOptionPane.showMessageDialog(null, "No domain specified.", "Could Not Add",
							JOptionPane.INFORMATION_MESSAGE);
					txtNewDomain.requestFocus();
					return;
				}
				String username = txtNewUsername.getText();
				String password = txtNewPassword.getText();
				if ((username.equals("") || username.equals("Username"))
						&& (password.equals("") || password.equals("Password"))) {
					JOptionPane.showMessageDialog(null, "No username or password specified.", "Could Not Add",
							JOptionPane.INFORMATION_MESSAGE);
					txtNewUsername.requestFocus();
					return;
				}
				// TODO: Data validation -- is it a domain, does it meet
				// password reqs?

				PasswordRecord pr = new PasswordRecord(domain, username, password);

				txtNewDomain.setText("Domain");
				txtNewUsername.setText("Username");
				txtNewPassword.setText("Password");

				// TODO: Do however it will really be done
				if (records.isEmpty()) {
					recordPane.removeAll();
				}
				if (userpm.add(pr)) {
					createRecordEntryPane(pr);
				} else {
					JOptionPane.showMessageDialog(null, "Record already exists.", "Could Not Add",
							JOptionPane.INFORMATION_MESSAGE);
				}

				// Repaint so we can see the new recordEntryPane
				recordPane.revalidate();
				recordPane.repaint();
				scrollPane.validate();

				scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
			}
		});
		newPane.add(btnNewAdd);

		controlPane.add(newPane);
	}

	/**
	 * Populate recordPane with RecordentryPanes
	 */
	private void listRecordEntryPanes(String searchPattern) {
		recordPane.removeAll();

		if (searchPattern.isEmpty()) {
			records = userpm.getRecords();
		} else {
			records = userpm.search(searchPattern);
		}

		if (records.isEmpty()) {
			JPanel panel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.LEFT);

			JLabel lblNoneFound = new JLabel("No Records Found ");
			lblNoneFound.setFont(new Font("Tahoma", Font.ITALIC | Font.BOLD, 11));
			// lblNoneFound.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(lblNoneFound);

			recordPane.add(panel);
		} else {
			for (PasswordRecord pr : records) {
				createRecordEntryPane(pr);
			}
		}

		recordPane.revalidate();
		recordPane.repaint();

		// scrollPane.validate();
		// scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
	}

	/**
	 * Create a PasswordRecord entry panel and adds it to the recordPane
	 */
	private void createRecordEntryPane(PasswordRecord pr) {
		// TODO: Is this method too long?

		JPanel recordEntryPane = new JPanel();
		recordEntryPane.setBackground(new Color(245, 245, 245));
		recordEntryPane.setMaximumSize(new Dimension(2147483647, 30));
		recordEntryPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		recordEntryPane.setLayout(new BoxLayout(recordEntryPane, BoxLayout.X_AXIS));

		JLabel lblDomain = new JLabel(pr.getDomain());
		lblDomain.setMinimumSize(new Dimension(100, 14));
		lblDomain.setPreferredSize(new Dimension(100, 14));
		lblDomain.setMaximumSize(new Dimension(2147483647, 14));
		recordEntryPane.add(lblDomain);

		JTextField txtUsername = new JTextField();
		txtUsername.setBackground(new Color(245, 245, 245));
		txtUsername.setPreferredSize(new Dimension(100, 20));
		txtUsername.setMinimumSize(new Dimension(100, 20));
		txtUsername.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getComponent() instanceof JTextField) {
					JTextField txt = (JTextField) e.getComponent();
					txt.setSelectionStart(0);
					txt.setSelectionEnd(txt.getText().length());
				}
			}
		});
		txtUsername.setEditable(false);
		txtUsername.setText(pr.getUsername());
		recordEntryPane.add(txtUsername);
		// txtUsername.setColumns(10);

		JTextField txtPassword = new JTextField();
		txtPassword.setBackground(new Color(245, 245, 245));
		txtPassword.setPreferredSize(new Dimension(100, 20));
		txtPassword.setMinimumSize(new Dimension(100, 20));
		txtPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getComponent() instanceof JTextField) {
					JTextField txt = (JTextField) e.getComponent();
					txt.setSelectionStart(0);
					txt.setSelectionEnd(txt.getText().length());
				}
			}
		});
		txtPassword.setEditable(false);
		txtPassword.setText("*************");
		recordEntryPane.add(txtPassword);
		// txtPassword.setColumns(10);

		JButton btnShow = new JButton("Show");
		btnShow.putClientProperty("record", pr);
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: Verify login -- timeout lock?

				if (e.getSource() instanceof JButton) {
					JButton btn = (JButton) e.getSource();
					JTextField txt = (JTextField) btn.getParent().getComponent(2);
					PasswordRecord pr = (PasswordRecord) btn.getClientProperty("record");
					txt.setText(pr.getPassword());
				}
			}
		});
		recordEntryPane.add(btnShow);

		JButton btnDelete = new JButton("");
		btnDelete.putClientProperty("record", pr);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JButton) {
					JButton btn = (JButton) e.getSource();
					JPanel rpane = (JPanel) btn.getParent(); // get the
																// recordPane
					int response = JOptionPane.showConfirmDialog(null,
							"Are you sure you want to delete the password for "
									+ ((JLabel) rpane.getComponent(0)).getText() + "?",
							"Confirm Deletion!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (response == JOptionPane.YES_OPTION) {
						PasswordRecord pr = (PasswordRecord) btn.getClientProperty("record");
						// JPanel pane = (JPanel)rpane.getParent(); // get the
						// parent of the recordPane
						// pane.remove(rpane); // remove the recordPane

						// TODO: Remove from list however it is going to really
						// be done
						try {
							userpm.remove(pr);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						// passExamples.remove(pr);

						listRecordEntryPanes(currentSearch);
					}
				}
			}
		});
		btnDelete.setMargin(new Insets(2, 2, 2, 2));
		btnDelete.setIcon(new ImageIcon(PassProtectApp.class.getResource("/passProtect/images/trash.png")));
		recordEntryPane.add(btnDelete);

		recordPane.add(recordEntryPane);
	}
}
