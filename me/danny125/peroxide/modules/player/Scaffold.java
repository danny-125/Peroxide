package me.danny125.peroxide.modules.player;

import org.lwjgl.input.Keyboard;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSnow;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import me.danny125.peroxide.Events.Event;
import me.danny125.peroxide.Events.EventUpdate;
import me.danny125.peroxide.Events.MotionEvent;
import me.danny125.peroxide.modules.Module;
import me.danny125.peroxide.settings.BooleanSetting;
import me.danny125.peroxide.utilities.movement.MovementUtil;

public class Scaffold extends Module {

	int lastItem;
	
	float yaw;
	float pitch = 90;
	
	public BooleanSetting tower = new BooleanSetting("Tower", true);
	
	public Scaffold() {
		super("Scaffold",Keyboard.KEY_NONE,Category.MOVEMENT);
		this.addSettings(tower);
	}
	
	private boolean canPlaceBlock(BlockPos pos) {
		Block block = mc.theWorld.getBlockState(pos).getBlock();
		return !mc.theWorld.isAirBlock(pos) && !(block instanceof BlockLiquid);
	}
	
	public void onEnable() {
		//lastItem = mc.thePlayer.inventory.currentItem;
	}
	
	public void onEvent(Event e) {
		if(e instanceof MotionEvent) {	
		if(e.isPre()) {
				
				MotionEvent event = (MotionEvent)e;
				
				if(tower.isToggled()) {
					if(Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()) && !MovementUtil.isMoving()) {
						mc.thePlayer.motionY = 0.2f;
					}
				}
				
				System.out.println("test");
					BlockPos playerBlock = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
					
					BlockData data = null;
					
					
					double posY;
					double yDif = 1.0D;
					for (posY = mc.thePlayer.posY - 1.0D; posY > 0.0D; posY--) {
				        BlockData newData = getBlockData(new BlockPos(mc.thePlayer.posX, posY, mc.thePlayer.posZ));
				        if (newData != null) {
				            yDif = mc.thePlayer.posY - posY;
				            if (yDif <= 3.0D) {
				            	if(data != null) {
				            		//lastblock = data;
				            	}
				                data = newData;
				                break;
				            }
				        }
				    }
					
					if(data == null)
						return;
					
					if(data.pos == new BlockPos(0, -1, 0)) {
						mc.thePlayer.motionX = 0;
						mc.thePlayer.motionZ = 0;
					}
					
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
						
						
						if((mc.thePlayer.getHeldItem() != null) && ((mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock))) {
							mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
							mc.playerController.func_178890_a(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), data.pos, data.face, new Vec3(data.pos.getX(), data.pos.getY(), data.pos.getZ()));
							
							if(data.face == EnumFacing.UP) {
								yaw = 90;
							}else if(data.face == EnumFacing.NORTH) {
								yaw = 360;
							}else if(data.face == EnumFacing.EAST) {
								yaw = 90;
							}else if(data.face == EnumFacing.SOUTH) {
								yaw = 180;
							}else if(data.face == EnumFacing.WEST) {
								yaw = 270;
							} else {
								yaw = 90;
							}
							
							
							//this method doesnt do anything lol
							//int ticks = 0;
							//ticks++;
							//if(ticks >= 1000) {
							//	ticks = 0;
							//}
							//faster place method
							event.setYaw(yaw);
							event.setPitch(pitch);
						}
						
						mc.thePlayer.inventory.currentItem = lastItem;
			}
		}
	}
	
	
	private boolean isPosSolid(BlockPos pos) {
        Block block = mc.theWorld.getBlockState(pos).getBlock();
        if ((block.getMaterial().isSolid() || !block.isTranslucent() || block.isSolidFullCube() || block instanceof BlockLadder || block instanceof BlockCarpet
                || block instanceof BlockSnow || block instanceof BlockSkull)
                && !block.getMaterial().isLiquid() && !(block instanceof BlockContainer)) {
            return true;
        }
        return false;
    }   
 
    private BlockData getBlockData(BlockPos pos) {
        	if (this.mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air) {
        		return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
            } else if (this.mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() != Blocks.air) {
            	return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
            } else if (this.mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() != Blocks.air) {
            	return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
            } else if (this.mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() != Blocks.air) {
            	return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
            } else if (this.mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() != Blocks.air) {
                return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
            } else {
                return null;
            }
    }
    
    public class BlockData {
        public final BlockPos pos;

        public final EnumFacing face;

        BlockData(BlockPos pos, EnumFacing face) {
            this.pos = pos;
            this.face = face;
        }
    }
    
}