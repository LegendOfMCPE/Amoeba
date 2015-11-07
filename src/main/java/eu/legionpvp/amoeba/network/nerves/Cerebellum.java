package eu.legionpvp.amoeba.network.nerves;

/*
 * This file is part of Amoeba.
 *
 * Amoeba is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Amoeba is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Amoeba.  If not, see <http://www.gnu.org/licenses/>.
 */

import eu.legionpvp.amoeba.network.PacketHandler;
import eu.legionpvp.amoeba.network.nerves.protocol.NerveImpulse;
import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Cerebellum extends PacketHandler<NerveImpulse>{
	private static Cerebellum instance;
	public final static String PASS_KEY;

	static{
		try{
			InputStream is = new FileInputStream(new File(".", "passkey"));
			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			is.close();
			String str = new String(buffer);
			PASS_KEY = str.replace("\n", "").replace("\r", "").replace(" ", "").replace("\t", "");
		}catch(IOException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static Cerebellum getInstance(){
		return instance;
	}

	private ServerSocket serverSocket;
	private Map<SocketAddress, Nerve> nerves = new HashMap<>();

	public Cerebellum(int port){
		try{
			serverSocket = new ServerSocket(port, 20);
		}catch(IOException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void tick(){
		long start = System.nanoTime();
		long left;
		while((left = System.nanoTime() - start) < 1e+7){
			try{
				serverSocket.setSoTimeout((int) left);
			}catch(SocketException e){
				e.printStackTrace();
			}
			try{
				Socket conn = serverSocket.accept();
				Nerve nerve = new Nerve(conn);
				nerves.put(conn.getRemoteSocketAddress(), nerve);
			}catch(SocketTimeoutException e){
				break;
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		for(Nerve nerve : nerves.values()){
			nerve.tick();
		}
	}

	@Override
	protected void finalize() throws Throwable{
		super.finalize();
		serverSocket.close();
	}
}
