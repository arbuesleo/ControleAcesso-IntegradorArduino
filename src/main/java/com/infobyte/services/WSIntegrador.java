package com.infobyte.services;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;

import com.infobyte.utils.Serial;

@ClientEndpoint
public class WSIntegrador {
	Serial serialArduino = new Serial();
	@OnMessage
	public void liberaAcessoArduino (){
		serialArduino.enviarMensagemArduino("L");
	}
}
