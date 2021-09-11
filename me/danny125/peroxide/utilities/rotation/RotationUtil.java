package me.danny125.peroxide.utilities.rotation;

import java.util.ArrayList;
import java.util.HashSet;

import me.danny125.peroxide.Events.MotionEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class RotationUtil {
static Minecraft mc = Minecraft.getMinecraft();
	
	public static float Setpitch = 0;
	public static float Setyaw = 0;
	
	public static float[] getRotations(Entity e) {
		double deltaX = e.posX + (e.posX - e.lastTickPosX) - mc.thePlayer.posX,
		deltaY = e.posY - 3.5 + e.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight(),
		deltaZ = e.posZ + (e.posZ - e.lastTickPosZ) - mc.thePlayer.posZ,
		distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));
		
		float yaw = (float) Math.toDegrees(-Math.atan(deltaX - deltaZ)), 
				pitch = (float) -Math.toDegrees(Math.atan(deltaY / distance));
		
		if(deltaX < 0 &&  deltaZ < 0) {
			yaw = (float) (90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
		}else if(deltaX > 0 && deltaZ < 0) {
			yaw = (float) (-90 + Math.toDegrees(Math.atan(deltaZ / deltaX)));
		}
		
		return new float[] {yaw, pitch };
		
		
	}
	
	public static float[] getRotationFromPosition(double x, double z, double y) {
        double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
        double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
        double yDiff = y - Minecraft.getMinecraft().thePlayer.posY - 1.2;

        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) -(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D);
        return new float[]{yaw, pitch};
    }
	
	public static float[] getPredictedRotations(EntityLivingBase ent) {
        double x = ent.posX + (ent.posX - ent.lastTickPosX) + randomNumber(0.03, -0.03);
        double z = ent.posZ + (ent.posZ - ent.lastTickPosZ) + randomNumber(0.03, -0.03);
        double y = ent.posY + ent.getEyeHeight() / 2.0F;
        return getRotationFromPosition(x, z, y);
    }
	
	public static float[] getRotationss(double posX, double posY, double posZ) {
        
		Minecraft mc = Minecraft.getMinecraft();
		//random numbers so hypixel does'nt flag for pattern
        double x = posX - mc.thePlayer.posX + randomNumber(0.03, -0.03);
        double y = posY - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double z = posZ - mc.thePlayer.posZ + randomNumber(0.03, -0.03);

        double dist = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) (-(Math.atan2(y, dist) * 180.0D / Math.PI));
        return new float[]{yaw, pitch};
    }
	
	
	public static void RotatePlayer(MotionEvent event, double pitch, double yaw, double speed) {
	
		//broken
		
		if(Setpitch == 0) {
			Setpitch = mc.thePlayer.rotationPitch;
		}
		
		if(Setyaw == 0) {
			Setpitch = mc.thePlayer.rotationYaw;
		}
		
		if(Setpitch > pitch + speed || Setpitch < pitch - speed) {
			if(Setpitch > pitch) {
				Setpitch -= speed;
			}
			
			if(Setpitch < pitch) {
				Setpitch += speed;
			}
		}
		
		if(Setyaw > yaw - speed || Setyaw < yaw + speed) {
			if(Setyaw > yaw) {
				Setyaw -= speed;
			}
			
			if(Setyaw < yaw) {
				Setyaw += speed;
			}
		}
		
		//event.setPitch(Setpitch);
		//event.setYaw(Setyaw);
		
	}
	
	 public static float[] getRotationsEntity(final EntityLivingBase entity) {
	        // Hypixel typically flags your rotations making it so you cannot hit people for a bit if they flag their pattern check.
	        
		 Minecraft mc = Minecraft.getMinecraft();
		 
		 if (mc.thePlayer.isSprinting()) {
	            return getRotationss(entity.posX + randomNumber(0.03, -0.03), entity.posY + entity.getEyeHeight() - 0.4D + randomNumber(0.07, -0.07), entity.posZ + randomNumber(0.03, -0.03));
	        }
	        return getRotationss(entity.posX, entity.posY + entity.getEyeHeight() - 0.4D, entity.posZ);
	    }
	 
	 private static MovingObjectPosition tracePath(final World world, final float x, final float y, final float z, final float tx, final float ty, final float tz, final float borderSize, final HashSet<Entity> excluded) {
	        Vec3 startVec = new Vec3(x, y, z);
	        Vec3 endVec = new Vec3(tx, ty, tz);
	        final float minX = (x < tx) ? x : tx;
	        final float minY = (y < ty) ? y : ty;
	        final float minZ = (z < tz) ? z : tz;
	        final float maxX = (x > tx) ? x : tx;
	        final float maxY = (y > ty) ? y : ty;
	        final float maxZ = (z > tz) ? z : tz;
	        final AxisAlignedBB bb = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ).expand(borderSize, borderSize, borderSize);
	        final ArrayList<Entity> allEntities = (ArrayList<Entity>) world.getEntitiesWithinAABBExcludingEntity(null, bb);
	        MovingObjectPosition blockHit = world.rayTraceBlocks(startVec, endVec);
	        startVec = new Vec3(x, y, z);
	        endVec = new Vec3(tx, ty, tz);
	        Entity closestHitEntity = null;
	        float closestHit = Float.POSITIVE_INFINITY;
	        float currentHit;
	        for (final Entity ent : allEntities) {
	            if (ent.canBeCollidedWith() && !excluded.contains(ent)) {
	                final float entBorder = ent.getCollisionBorderSize();
	                AxisAlignedBB entityBb = ent.getEntityBoundingBox();
	                if (entityBb == null) {
	                    continue;
	                }
	                entityBb = entityBb.expand(entBorder, entBorder, entBorder);
	                final MovingObjectPosition intercept = entityBb.calculateIntercept(startVec, endVec);
	                if (intercept == null) {
	                    continue;
	                }
	                currentHit = (float) intercept.hitVec.distanceTo(startVec);
	                if (currentHit >= closestHit && currentHit != 0.0f) {
	                    continue;
	                }
	                closestHit = currentHit;
	                closestHitEntity = ent;
	            }
	        }
	        if (closestHitEntity != null) {
	            blockHit = new MovingObjectPosition(closestHitEntity);
	        }
	        return blockHit;
	    }

	 private static MovingObjectPosition tracePathD(final World w, final double posX, final double posY, final double posZ, final double v, final double v1, final double v2, final float borderSize, final HashSet<Entity> exclude) {
	        return tracePath(w, (float) posX, (float) posY, (float) posZ, (float) v, (float) v1, (float) v2, borderSize, exclude);
	    }

	    public static MovingObjectPosition rayCast(final EntityPlayerSP player, final double x, final double y, final double z) {
	        final HashSet<Entity> excluded = new HashSet<>();
	        excluded.add(player);
	        return tracePathD(player.worldObj, player.posX, player.posY + player.getEyeHeight(), player.posZ, x, y, z, 1.0f, excluded);
	    }
	 
	 public static int randomNumber(double d, double e) {
	        int ii = (int) (-e + (int) (Math.random() * ((d - (-e)) + 1)));
	        return ii;
	    }
	
}
