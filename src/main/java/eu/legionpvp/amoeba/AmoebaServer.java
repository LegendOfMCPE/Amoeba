package eu.legionpvp.amoeba;

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

import eu.legionpvp.amoeba.network.client.ClientManager;
import lombok.Getter;
import lombok.Setter;

@Getter
public class AmoebaServer{
	private static AmoebaServer instance;
	private static int clientCount = 0;

	private ClientManager clientManager;
	@Setter
	private int port;

	public static void init(){
		instance = new AmoebaServer();
	}

	public static AmoebaServer getInstance(){
		return instance;
	}

	private AmoebaServer(){
	}

	public void start(){
		clientManager = new ClientManager(port);
	}

	public static int getClientCount(){
		return clientCount;
	}

	public static int getNextClientId(){
		return clientCount++;
	}
}
