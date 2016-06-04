package br.com.delfos;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.StringTokenizer;

public class PingExample {
	public static void main(String[] args) {
		String host = "10.49.9.4";
		// Recomendo no minimo 3 segundos
		int timeOut = 3000;
		int port = 8080;
		while (true) {
			System.out.println(host + "=" + doPing(host, timeOut));
			System.out.println("isPortAvailable:" + isPortAvailable(port));
		}
	}

	public static boolean doPing(String host) {
		return doPing(host, 3000); // 3 segundos
	}

	/**
	 * Verifica se determinado host esta atingivel
	 */
	public static boolean doPing(String host, int timeOut) {
		try {
			return InetAddress.getByName(host).isReachable(timeOut);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Cria uma comunicacao com a porta desejada, se a porta estiver disponivel
	 * returna true, caso contr�rio uma exception ira ocorrer e retornara false
	 */
	public static boolean isPortAvailable(int port) {
		try {
			ServerSocket srv = new ServerSocket(port);
			srv.close();
			srv = null;
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Uma outra maneira de fazer o ping, dessa maneira � invocado o comando
	 * ping do sistemaOperacional e verificado na mensagem de retorno se houve
	 * faha.
	 */
	public static boolean runPing(String ipstr) {
		boolean retv = false;
		try {
			InputStream ins = Runtime.getRuntime().exec("ping -n 1 -w 2000 " + ipstr).getInputStream();
			Thread.sleep(3000);
			byte[] prsbuf = new byte[ins.available()];
			ins.read(prsbuf);
			String parsstr = new StringTokenizer(new String(prsbuf), "%").nextToken().trim();
			if (!parsstr.endsWith("100"))
				retv = true;
		} catch (Exception e) {
			retv = false;
		}
		return retv;
	}
}
