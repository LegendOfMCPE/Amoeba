package io.github.legendofmcpe.amoeba.network.nerves.protocol;

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

public interface Neurotransmitter{
	public final static byte OPEN_CONNECTION = 0x00;
	public final static byte ADD_CLIENT = 0x10;
	public final static byte REMOVE_CLIENT = 0x11;
	public final static byte TRANSFER_CLIENT = 0x12;
	public final static byte CLIENT_BATCH = 0x13;
}
