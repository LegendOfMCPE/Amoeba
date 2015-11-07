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

import java.util.ArrayList;
import java.util.List;

@Getter
public class ClientManager{
	private static ClientManager instance;

	private final NonBlockDatagramSocket socket;
	private final List<Client> clients = new ArrayList<>();

	public ClientManager(int port){
		instance = this;
		socket = new NonBlockDatagramSocket(port);
	}

	public void tickProcess(){

	}

	public static ClientManager getInstance(){
		return instance;
	}
}
