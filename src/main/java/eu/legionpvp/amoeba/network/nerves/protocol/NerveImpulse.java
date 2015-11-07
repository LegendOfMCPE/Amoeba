package eu.legionpvp.amoeba.network.nerves.protocol;

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

import eu.legionpvp.amoeba.network.PacketType;

import java.nio.ByteBuffer;

public abstract class NerveImpulse implements Neurotransmitter{
	public void parse(ByteBuffer bb){
		throw new UnsupportedOperationException("This packet cannot be parsed");
	}

	public final void parse(byte[] buffer){
		parse(ByteBuffer.wrap(buffer));
	}

	public ByteBuffer emit(){
		throw new UnsupportedOperationException("This packet cannot be emitted");
	}

	public final byte[] emitArray(){
		return emit().array();
	}

	public byte pid(){
		return getClass().getAnnotation(PacketType.class).value();
	}

	protected String getString(ByteBuffer bb){
		byte[] buffer = new byte[bb.getShort()];
		bb.get(buffer);
		return new String(buffer);
	}
}
