package br.com.app;

import javax.swing.JOptionPane;

import br.com.server.Server;

public class ChatApplication {

	public static void main(String[] args) {
		String addr;
		if(args.length > 0) {
			addr = args[1];
		} else {
			addr = "172.18.0.1";
		}
		// conexao socket tcp com servidor
		Client conn = null;
		try {
			conn = new Client(addr, Server.PORT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// nome do cliente
		String name = "";
		while((name == null)|| name.length() == 0) { // le o nome
			name = JOptionPane.showInputDialog("Nome:");
		}
		name = name.trim(); // limpa espaços do começo e fim
		try {
			conn.send(name, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // manda para o servidor sem criptografar
        // abre a janela do chat
		final ChatWindow cw = ChatWindow.open(conn, name);
		// thread para ouvir mensagens
		cw.listener.start();

	}

}
