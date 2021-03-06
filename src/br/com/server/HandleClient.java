package br.com.server;

public class HandleClient extends Thread{
	
	final private static String WARNING = "\\warning";
	private ClientInfo client;
	private Server server;
	
	public HandleClient(ClientInfo client, Server server) {
		this.client = client;
		this.server = server;
	}
	
	public void run() {

		// Envia uma mensagem ao cliente solicitando seu nome
		server.readClientName(client);
		
		// Informa a entrada do cliente à todos os outros conectados
		server.broadcast(null, WARNING+ client.name + " entrou!");
		
		// adiciona o cliente na lista de clients conectados
		server.clients.add(client);
		
		String msg;
		while(true) {
			try {
				msg = client.reader.readLine(); // lê uma mensagem enviada pelo clientes
				if(msg == null) {
					break;
				}
				System.out.println(msg); // log das mensagens enviadas
				server.broadcast(client, msg); // replica a mensagem enviada
			}
			catch(Exception e) {
				break;
			}
		}
		
		// log de saída
		System.out.println("Cliente " + client.conn.getRemoteSocketAddress() + " saiu!");
		
		// disconecta o cliente do servidor
		client.disconnect();

		// Informa a entrada do cliente à todos os outros conectados
		server.broadcast(null, WARNING+client.name + " saiu!");
		
	}

}
