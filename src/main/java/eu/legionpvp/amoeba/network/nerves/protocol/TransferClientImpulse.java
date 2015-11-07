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
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

@PacketType(Neurotransmitter.TRANSFER_CLIENT)
@Getter
@Setter
public class TransferClientImpulse extends NerveImpulse{
	private long clientId;
	private String ip;
	private short port;

	@Override
	public void parse(ByteBuffer bb){
		clientId = bb.getLong();
		ip = getString(bb);
		port = bb.getShort();
	}
}
