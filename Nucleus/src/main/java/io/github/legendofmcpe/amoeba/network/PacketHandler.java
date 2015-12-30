package io.github.legendofmcpe.amoeba.network;

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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class PacketHandler<P>{
	@Getter Map<Byte, Constructor<? extends P>> types = new HashMap<>();

	public void register(Class<? extends P> clazz){
		PacketType type = clazz.getAnnotation(PacketType.class);
		if(type == null){
			throw new RuntimeException("Class does not have @PacketType annotation");
		}
		byte id = type.value();
		if(types.containsKey(id)){
			throw new RuntimeException("Attempt to declare packet type class " + clazz.getName() + " of " +
					"ID " + id + " while ID is already declared for " + types.get(id).getDeclaringClass().getName());
		}
		try{
			Constructor<? extends P> c = clazz.getConstructor();
			if(!Modifier.isPublic(c.getModifiers())){
				throw new RuntimeException(clazz.getName() + " does not have a public empty constructor");
			}
			types.put(id, c);
		}catch(NoSuchMethodException e){
			throw new RuntimeException(clazz.getName() + " does not have an empty constructor");
		}
	}

	public P get(byte id){
		try{
			return types.get(id).newInstance();
		}catch(InstantiationException e){
			e.printStackTrace();
			return null;
		}catch(IllegalAccessException e){
			e.printStackTrace();
			return null;
		}catch(InvocationTargetException e){
			throw new RuntimeException(e.getTargetException());
		}
	}
}
