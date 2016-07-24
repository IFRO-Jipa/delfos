package ping;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestaPing {

	public static void main(String[] args) {
		try {
			// Endereço IP a ser verificado
			String addr = "192.168.1.103";
			// Verifica se o IP responde com timeout de 2 segundos (2000 milissegundos)
			if (InetAddress.getByName(addr).isReachable(2000)) {
				String nome = InetAddress.getByName(addr).getHostName();
				System.out.println("Host " + nome + " (" + addr + ") ativo!");
			} else {
				System.out.println("Host " + addr + " inativo!");
			}
		} catch (UnknownHostException ex) {
			Logger.getLogger(TestaPing.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(TestaPing.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
