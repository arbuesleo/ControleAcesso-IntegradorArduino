package com.infobyte.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class Serial implements SerialPortEventListener{
	

	
	
	private static final int RATE = 9600;
	
	public void abrirPortaSerial(String port) {
		
		try {
			CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(port);
			SerialPort serialPort = (SerialPort) portId.open(this.getClass().getName(), 2000);
			serialPort.setSerialPortParams(RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			serialPort.enableReceiveTimeout(1000);
			serialPort.enableReceiveThreshold(0);
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			System.out.println("Conecatado com sucesso na porta serial: " + port + " !" );
			
		} catch (Exception e) {
			System.err.println("Error ao abrir a porta serial. " + e + " " + e.getMessage());
		}
		
		
	}
	
	private BufferedReader input;

	public void serialEvent(SerialPortEvent oEvent) {
		
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			
			try {	
				String tag = this.input.readLine();
				String endereco = "http://localhost:8888/acesso/registrarAcesso?tag=" + tag; 
				System.out.println("Tag capturada: " + tag);							
								
				URL url = new URL(endereco);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				System.out.println("Conectando ao servidor rest...");

				if (conn.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
							+ conn.getResponseCode());
				}
				BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

				String output;
				output = br.readLine();
				System.out.println("Obtendo dados do servidor rest...");
				if(output.equals("L")){
					this.enviarMensagemArduino("L");
					System.out.println("Acesso liberado!");
				}else{
					this.enviarMensagemArduino("B");
					System.out.println("Acesso bloqueado!");
				}
				System.out.println("Desconectando do servidor rest...");
				conn.disconnect();			
				System.out.println("Desconectado do servidor rest!");
				
			} catch (Exception e) {
				System.err.println("Erro ao ler dados do arduino. " + e.getMessage());
			}
		}
	}

	private OutputStream output;
	
	public void enviarMensagemArduino(String message) {
		try {
			this.output.write(message.getBytes());
		} catch (Exception e) {
			System.err.println("Erro ao enviar mensagem para " + "o Arduino. " + e.getMessage());
		}
	}



}
