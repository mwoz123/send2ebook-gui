package com.github.mwoz123.send2ebook.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import com.github.mwoz123.send2ebook.gui.clipboard.ClipboardHelper;
import com.github.mwoz123.send2ebook.gui.clipboard.Send2Epub2Ftp;

public class Send2EbookGui {

	private JFrame frame;
	private JTextField textField;
	private JButton clearButton;
	private JButton pasteButton;
	private JPanel statusPanel ;
	private Send2Epub2Ftp send2Epub2Ftp;
	private JButton pasteAndSendButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Send2EbookGui window = new Send2EbookGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Send2EbookGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Send2Ebook GUI");
		frame.setBounds(100, 100, 450, 168);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		textField = new JTextField();
		frame.getContentPane().add(textField);
		textField.setColumns(30);
		
		clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
			}
		});
		frame.getContentPane().add(clearButton);
		
		pasteButton = new JButton("Paste");
		pasteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText(ClipboardHelper.getClipboardText());
			}
		});
		frame.getContentPane().add(pasteButton);
		
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send2Epub2Ftp.process(textField.getText());
			}
		});
		
		pasteAndSendButton = new JButton("Paste and Send");
		pasteAndSendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String clipboardText = ClipboardHelper.getClipboardText();
				textField.setText(clipboardText);
				send2Epub2Ftp.process(clipboardText);
			}
		});
		frame.getContentPane().add(pasteAndSendButton);
		frame.getContentPane().add(sendButton);
		
			
			statusPanel = new JPanel();
			
			statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
			frame.getContentPane().add(statusPanel, BorderLayout.SOUTH);
			statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 16));
			statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
			JLabel statusLabel = new JLabel("");
			statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
			statusPanel.add(statusLabel);
			
			send2Epub2Ftp = new Send2Epub2Ftp(statusLabel);

	}

}
