package com.infobyte.views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.infobyte.utils.Serial;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		setBounds(100, 100, 508, 312);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);		
		txtPortaCom = new JTextField();
		txtPortaCom.setText("COM3");
		txtPortaCom.setBounds(96, 11, 86, 20);
		contentPane.add(txtPortaCom);
		txtPortaCom.setColumns(10);
		JLabel lblPortaCom = new JLabel("Porta COM:");
		lblPortaCom.setBounds(4, 14, 110, 14);
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
		btnConectar.setBounds(192, 10, 89, 23);
		contentPane.add(btnConectar);
		
		JTextPane textLog = new JTextPane();
		textLog.setBounds(4, 69, 488, 193);
		
		contentPane.add(textLog);
		textLog.setEditable(false);
		JLabel lblLogsDoIntegrador = new JLabel("Logs do Integrador");
		lblLogsDoIntegrador.setBounds(14, 42, 135, 14);
		contentPane.add(lblLogsDoIntegrador);
		
		JButton btnLimparLog = new JButton("Limpar Log");
		btnLimparLog.setBounds(358, 38, 124, 23);
		contentPane.add(btnLimparLog);
		
	}
}
