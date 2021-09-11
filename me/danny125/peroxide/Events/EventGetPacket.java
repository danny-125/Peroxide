package me.danny125.peroxide.Events;

import net.minecraft.network.Packet;

public class EventGetPacket extends Event<EventGetPacket> {
	
	Packet packet;
	
	public EventGetPacket(Packet packet) {
		this.packet = packet;
	}

	public Packet getPacket() {
		return packet;
	}

}
