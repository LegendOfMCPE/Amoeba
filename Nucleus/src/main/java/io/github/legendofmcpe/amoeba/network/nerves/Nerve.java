package io.github.legendofmcpe.amoeba.network.nerves;

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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

@Getter
public class Nerve{
	private final Socket socket;
	private final InputStream in;
	private final OutputStream out;
	private final long creation;

	private boolean authenticated;

	public Nerve(Socket socket) throws IOException{
		this.socket = socket;
		in = socket.getInputStream();
		out = socket.getOutputStream();
		creation = System.currentTimeMillis();
	}

	public void tick(){

	}
}
