package com.infobyte.views;


import java.awt.EventQueue;
import java.awt.ScrollPane;
import java.awt.Scrollbar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import com.infobyte.utils.CustomOutputStream;
import com.infobyte.utils.Serial;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.DropMode;
import java.awt.TextArea;

public class Principal extends JFrame {

	private JPanel contentPane;
	private JTextField txtPortaCom;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
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
	public Principal() {
		setResizable(false);
		setTitle("Integrador Arduino");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 730, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);		
		txtPortaCom = new JTextField();
		txtPortaCom.setText("COM3");
		txtPortaCom.setBounds(301, 12, 86, 20);
		contentPane.add(txtPortaCom);
		txtPortaCom.setColumns(10);
		JLabel lblPortaCom = new JLabel("Porta COM:");
		lblPortaCom.setBounds(209, 15, 110, 14);
		contentPane.add(lblPortaCom);
		final Serial serialArduino = new Serial();
		final JButton btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(btnConectar.getText() == "Conectar"){					
					serialArduino.abrirPortaSerial(txtPortaCom.getText());
					btnConectar.setText("Desconectar");
				}else{
					btnConectar.setText("Conectar");
					serialArduino.fecharSerial();
				}
			}
		});
		btnConectar.setBounds(397, 11, 124, 23);
		contentPane.add(btnConectar);
		JLabel lblLogsDoIntegrador = new JLabel("Logs do Integrador");
		lblLogsDoIntegrador.setBounds(14, 42, 135, 14);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(4, 67, 710, 326);
		contentPane.add(scrollPane);
		contentPane.add(lblLogsDoIntegrador);
		final JTextArea textLog = new JTextArea();
		scrollPane.setViewportView(textLog);
		
		
		
		PrintStream printStream = new PrintStream(new CustomOutputStream(textLog));
		System.setOut(printStream);
		System.setErr(printStream);	
		
		JButton btnLimparLog = new JButton("Limpar Log");
		btnLimparLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textLog.setText("");
			}
		});
		btnLimparLog.setBounds(590, 38, 124, 23);
		contentPane.add(btnLimparLog);
		
	
		
		
		
	
		
	}
}
