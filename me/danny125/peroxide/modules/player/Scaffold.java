package me.danny125.peroxide.modules.player;

import org.lwjgl.input.Keyboard;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.Events.MotionEvent;
import me.danny125.peroxide.modules.Module;

public class Scaffold extends Module {

	int lastItem;
	
	float yaw;
	float pitch = 90;
	
	public Scaffold() {
		super("Scaffold",Keyboard.KEY_N,Category.MOVEMENT);
	}
	
	private boolean canPlaceBlock(BlockPos pos) {
		Block block = mc.theWorld.getBlockState(pos).getBlock();
		return !mc.theWorld.isAirBlock(pos) && !(block instanceof BlockLiquid);
	}
	
	private void place(BlockPos pos, EnumFacing face) {
		
		
		
		
		if(face == EnumFacing.UP) {
			pos = pos.add(0,-1,0);
			yaw = 90;
		}else if(face == EnumFacing.NORTH) {
			pos = pos.add(0,0,1);
			yaw = 360;
		}else if(face == EnumFacing.EAST) {
			pos = pos.add(-1,0,0);
			yaw = 90;
		}else if(face == EnumFacing.SOUTH) {
			pos = pos.add(0,0,-1);
			yaw = 180;
		}else if(face == EnumFacing.WEST) {
			pos = pos.add(1,0,0);
			yaw = 270;
		} else {
			yaw = 90;
		}
		
		if((mc.thePlayer.getHeldItem() != null) && ((mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock))) {
			mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
			mc.playerController.func_178890_a(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), pos, face, new Vec3(pos.getX(), pos.getY(), pos.getZ()));
			double var4 = pos.getX() + 0.25D - mc.thePlayer.posX;
			double var6 = pos.getZ() + 0.25D - mc.thePlayer.posZ;
			double var8 = pos.getY() + 0.25D - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
			double var14 = MathHelper.sqrt_double(var4 * var4 + var6 * var6);
			//float yaw = (float)(Math.atan2(var6, var4) * 180.0D / 3.141592653689793D) - 90.0f;
		//	float pitch = (float)(Math.atan2(var8, var14) * 180.0D / 3.141592653689793D) - 90.0f;
			
			
			//this method doesnt do anything lol
			//int ticks = 0;
			//ticks++;
			//if(ticks >= 1000) {
			//	ticks = 0;
			//}
			//faster place method
			mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, yaw, pitch, mc.thePlayer.onGround));
		}
	}
	
	public void onEnable() {
		//lastItem = mc.thePlayer.inventory.currentItem;
	}
	
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			if(e.isPre()) {
				
				
				
					BlockPos playerBlock = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
					if(mc.theWorld.isAirBlock(playerBlock.add(0,-1,0))) {
						
						for (int i = 0; i < 9; i++) {
					        ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);
					        if (itemStack != null) {
					            int stackSize = itemStack.stackSize;
					            if (itemStack.getItem() instanceof ItemBlock) {
					            	lastItem = mc.thePlayer.inventory.currentItem;
					                mc.thePlayer.inventory.currentItem = i;
					            }
					        }
					    }
						
						if(canPlaceBlock(playerBlock.add(0,-2,0))) {
							place(playerBlock.add(0,-1,0), EnumFacing.UP);
						}
						if(canPlaceBlock(playerBlock.add(-1,-1,0))) {
							place(playerBlock.add(0,-1,0), EnumFacing.EAST);
						}
						if(canPlaceBlock(playerBlock.add(1,-1,0))) {
							place(playerBlock.add(0,-1,0), EnumFacing.WEST);
						}
						if(canPlaceBlock(playerBlock.add(0,-1,-1))) {
							place(playerBlock.add(0,-1,0), EnumFacing.SOUTH);
						}
						if(canPlaceBlock(playerBlock.add(0,-1,1))) {
							place(playerBlock.add(0,-1,0), EnumFacing.NORTH);
							//finished with basic directions
							
							
							
							
						}else if(canPlaceBlock(playerBlock.add(1,-1,1))) {
							if(canPlaceBlock(playerBlock.add(0,-1,1))){
								place(playerBlock.add(0,-1,1), EnumFacing.NORTH);
							}
							place(playerBlock.add(1,-1,1), EnumFacing.EAST);
							
						}else if(canPlaceBlock(playerBlock.add(-1,-1,1))) {
							if(canPlaceBlock(playerBlock.add(-1,-1,0))){
								place(playerBlock.add(0,-1,1), EnumFacing.WEST);
							}
							place(playerBlock.add(-1,-1,1), EnumFacing.SOUTH);
							
						}else if(canPlaceBlock(playerBlock.add(1,-1,-1))) {
							if(canPlaceBlock(playerBlock.add(0,-1,-1))){
								place(playerBlock.add(0,-1,-1), EnumFacing.SOUTH);
							}
							place(playerBlock.add(-1,-1,1), EnumFacing.WEST);
							
						}else if(canPlaceBlock(playerBlock.add(1,-1,-1))) {
							if(canPlaceBlock(playerBlock.add(1,-1,0))){
								place(playerBlock.add(1,-1,0), EnumFacing.EAST);
							}
							place(playerBlock.add(1,-1,-1), EnumFacing.NORTH);
						}
						mc.thePlayer.inventory.currentItem = lastItem;
					}
			}
		}
	}
}
