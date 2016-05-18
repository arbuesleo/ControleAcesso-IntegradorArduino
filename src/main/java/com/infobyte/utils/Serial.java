package com.infobyte.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import app.dao.CartaoDao;
import app.model.Cartao;
import app.services.WSIntegracaoArduino;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;

public class Serial {
	
private BufferedReader input;
	
	
	@Override
	public void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				WSIntegracaoArduino ws = new WSIntegracaoArduino();	
				
				
			} catch (Exception e) {
				System.err.println("Erro ao ler dados do arduino. " + e.getMessage());
			}
		}
		
	}
	
	private static final int RATE = 9600;
	
	private static final String PORT = "COM3";
	

	CartaoDao cartaoDao;
	
	public void abrirPortaSerial() {
		try {
			CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(PORT);
			SerialPort serialPort = (SerialPort) portId.open(this.getClass().getName(), 2000);
			serialPort.setSerialPortParams(RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			serialPort.enableReceiveTimeout(1000);
			serialPort.enableReceiveThreshold(0);
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			
		} catch (Exception e) {
			System.err.println("Error ao abrir a porta serial. " + e + " " + e.getMessage());
		}
		
		Cartao cartao = new Cartao(0, "Teste", "A", "Teste", null);	
		this.cartaoDao.save(cartao);
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
