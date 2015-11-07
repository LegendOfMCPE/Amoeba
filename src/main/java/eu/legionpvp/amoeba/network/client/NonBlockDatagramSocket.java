package eu.legionpvp.amoeba.network.client;

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

import lombok.Getter;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class NonBlockDatagramSocket extends Thread{
	private int port;
	private DatagramSocket socket;
	@Getter private boolean stopped;
	@Getter private final List<AddressedPacket> receiveBuffer = new ArrayList<>(), sendBuffer = new ArrayList<>();

	public NonBlockDatagramSocket(int port){
		this.port = port;
	}

	@Override
	public void run(){
		setName("NonBlockDatagramSocket");
		try{
			socket = new DatagramSocket(port);
			socket.setBroadcast(true);
			socket.setSendBufferSize(1024 * 1024 * 8); // from PocketMine
			socket.setReceiveBufferSize(1024 * 1024); // from PocketMine
		}catch(SocketException e){
			e.printStackTrace();
			return;
		}
		while(!stopped){
			while(!sendBuffer.isEmpty()){
				AddressedPacket pk;
				synchronized(sendBuffer){
					pk = sendBuffer.remove(0);
				}
				try{
					socket.send(new DatagramPacket(pk.getBuffer(), pk.getBuffer().length, pk.getAddress()));
				}catch(IOException e){
					e.printStackTrace();
				}
			}
			List<AddressedPacket> received = new ArrayList<>();
			long start = System.nanoTime();
			long left;
			while((left = System.nanoTime() - start) < 1e+7){
				try{
					socket.setSoTimeout((int) (left / 1e+6));
				}catch(SocketException e){
					e.printStackTrace();
					break;
				}
				DatagramPacket pk = new DatagramPacket(new byte[1 << 20], 1 << 20);
				try{
					socket.receive(pk);
				}catch(SocketTimeoutException e){
					break;
				}catch(IOException e){
					e.printStackTrace();
					break;
				}
				received.add(new AddressedPacket(pk.getData(), new InetSocketAddress(pk.getAddress(), pk.getPort())));
			}
		}
	}

	public void setStopped(){
		stopped = true;
	}

	public void send(byte[] buffer, InetSocketAddress address){
		synchronized(sendBuffer){
			sendBuffer.add(new AddressedPacket(buffer, address));
		}
	}

	@Override
	protected void finalize() throws Throwable{
		super.finalize();
		socket.close();
	}
}
