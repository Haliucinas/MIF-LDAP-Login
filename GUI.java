/**
 *  @author Mantas Jonytis
 *  @student Informatika, 2 kursas, 1 grupė
 *  @class GUI class
 *  @version 0.1
 */

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.util.concurrent.TimeUnit;

/**
 *  This class implements user interface
 */
public class GUI implements ActionListener {

	static JFrame frame;
	JTextField uname;
	JPasswordField upasswd;
	JButton loginButton;
	JLabel stName, stNumber, stHomeDir, stMail, stPage;

    /**
     *  Creates content pane
     *  @return UI filled panel
     */
	public JPanel createContent() {
		JPanel content = new JPanel();
		content.setLayout(null);
		JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(null);
        fieldsPanel.setLocation(105, 40);
        fieldsPanel.setSize(140, 65);

        uname = new JTextField(8);
        uname.setLocation(0, 0);
        uname.setSize(140, 30);
        uname.setToolTipText("Enter faculty login (eg.: qwer1234).");
        uname.setHorizontalAlignment(JTextField.CENTER);
        fieldsPanel.add(uname);

        upasswd = new JPasswordField(16);
        upasswd.setLocation(0, 35);
        upasswd.setSize(140, 30);
        upasswd.setToolTipText("Enter faculty password.");
        upasswd.setHorizontalAlignment(JTextField.CENTER);
        fieldsPanel.add(upasswd);

        loginButton = new JButton("Login");
        loginButton.setLocation(105, 110);
        loginButton.setSize(140, 30);
        loginButton.addActionListener(this);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(null);
        textPanel.setLocation(30, 180);
        textPanel.setSize(290, 120);

        stName = new JLabel();
        stName.setLocation(0, 0);
        stName.setSize(290, 15);
        stName.setHorizontalAlignment(JLabel.CENTER);
        textPanel.add(stName);
        stNumber = new JLabel();
        stNumber.setLocation(0, 15);
        stNumber.setSize(290, 15);
        stNumber.setHorizontalAlignment(JLabel.CENTER);
        textPanel.add(stNumber);
        stPage = new JLabel();
        stPage.setLocation(0, 30);
        stPage.setSize(290, 15);
        stPage.setHorizontalAlignment(JLabel.CENTER);
        textPanel.add(stPage);
        stMail = new JLabel();
        stMail.setLocation(0, 45);
        stMail.setSize(290, 15);
        stMail.setHorizontalAlignment(JLabel.CENTER);
        textPanel.add(stMail);
        stHomeDir = new JLabel();
        stHomeDir.setLocation(0, 60);
        stHomeDir.setSize(290, 15);
        stHomeDir.setHorizontalAlignment(JLabel.CENTER);
        textPanel.add(stHomeDir);

        content.add(fieldsPanel);
        content.add(loginButton);
        content.add(textPanel);
        content.setLocation(150, 150);
        content.setOpaque(true);
        return content;
	}

    /**
     *  Slides frame up or down
     *  @param u frame length increases
     *  @param d frame length decreases
     */
    private void slide(char c) {
        try {
            for (int i=0; i<=100; i=i+10) {
                if (c == 'u') {
                    frame.setSize(350, 200+i);
                } else if (c == 'd') {
                    frame.setSize(350, 300-i);
                }
                TimeUnit.MILLISECONDS.sleep(25);
            }
        } catch (Exception e) {}
    }

    /**
     *  Login button handler
     *  @param event
     *  @exception thrown on empty fields and incorrect information
     */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == loginButton) {
			try {
                if (frame.getHeight() == 300) {
                    slide('d');
                }
				if ((uname.getText().length() == 0) || (upasswd.getPassword().length == 0)) {
					JOptionPane.showMessageDialog(frame, "Fields cannot be empty!");
					throw new Exception();
				}
				BAuth auth = new BAuth(uname.getText(), upasswd.getPassword());
				if (auth.connect()) {
					stName.setText("Name: " + auth.getName() + " " + auth.getSurname());
					stNumber.setText("Student number: s" + auth.getNr());
                    stPage.setText("Website: http://uosis.mif.vu.lt/~" + uname.getText());
					stMail.setText("Email: " + auth.getMail());
                    stHomeDir.setText("Home dir.: " + auth.getHomedir());
					slide('u');
				} else {
					JOptionPane.showMessageDialog(frame, "Can't connect!\nCheck your information or internet connection.");
				}
			} catch (Exception ex) {}
		}
	}

    /**
     *  Creates frame, uses default system theme
     */
	private static void createAndShowGUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			frame = new JFrame("MIF LDAP Login Window");
			GUI instance = new GUI();
        	frame.add(instance.createContent());
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	frame.setSize(350, 200);
        	frame.setResizable(false);
        	frame.setLocationRelativeTo(null);
        	frame.setVisible(true);
        } catch (Exception e) {
    		e.printStackTrace();
		}
    }

    /**
     *  Runs program on AWT event dispatching thread
     */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}
}