package net.minecraft.client.network;

import com.google.common.base.Objects;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldSettings;

public class NetworkPlayerInfo
{
    private final GameProfile field_178867_a;
    private WorldSettings.GameType gameType;

    /** Player response time to server in milliseconds */
    private int responseTime;
    private boolean field_178864_d = false;
    private ResourceLocation field_178865_e;
    private ResourceLocation field_178862_f;
    private String field_178863_g;
    private IChatComponent field_178872_h;
    private int field_178873_i = 0;
    private int field_178870_j = 0;
    private long field_178871_k = 0L;
    private long field_178868_l = 0L;
    private long field_178869_m = 0L;
    private static final String __OBFID = "CL_00000888";

    public NetworkPlayerInfo(GameProfile p_i46294_1_)
    {
        this.field_178867_a = p_i46294_1_;
    }

    public NetworkPlayerInfo(S38PacketPlayerListItem.AddPlayerData p_i46295_1_)
    {
        this.field_178867_a = p_i46295_1_.func_179962_a();
        this.gameType = p_i46295_1_.func_179960_c();
        this.responseTime = p_i46295_1_.func_179963_b();
    }

    public GameProfile func_178845_a()
    {
        return this.field_178867_a;
    }

    public WorldSettings.GameType getGameType()
    {
        return this.gameType;
    }

    public int getResponseTime()
    {
        return this.responseTime;
    }

    protected void func_178839_a(WorldSettings.GameType p_178839_1_)
    {
        this.gameType = p_178839_1_;
    }

    protected void func_178838_a(int p_178838_1_)
    {
        this.responseTime = p_178838_1_;
    }

    public boolean func_178856_e()
    {
        return this.field_178865_e != null;
    }

    public String func_178851_f()
    {
        return this.field_178863_g == null ? DefaultPlayerSkin.func_177332_b(this.field_178867_a.getId()) : this.field_178863_g;
    }

    public ResourceLocation func_178837_g()
    {
        if (this.field_178865_e == null)
        {
            this.func_178841_j();
        }

        return (ResourceLocation)Objects.firstNonNull(this.field_178865_e, DefaultPlayerSkin.func_177334_a(this.field_178867_a.getId()));
    }

    public ResourceLocation func_178861_h()
    {
        if (this.field_178862_f == null)
        {
            this.func_178841_j();
        }

        return this.field_178862_f;
    }

    public ScorePlayerTeam func_178850_i()
    {
        return Minecraft.getMinecraft().theWorld.getScoreboard().getPlayersTeam(this.func_178845_a().getName());
    }

    protected void func_178841_j()
    {
        synchronized (this)
        {
            if (!this.field_178864_d)
            {
                this.field_178864_d = true;
                Minecraft.getMinecraft().getSkinManager().func_152790_a(this.field_178867_a, new SkinManager.SkinAvailableCallback()
                {
                    private static final String __OBFID = "CL_00002619";
                    public void func_180521_a(Type p_180521_1_, ResourceLocation p_180521_2_, MinecraftProfileTexture p_180521_3_)
                    {
                        switch (NetworkPlayerInfo.SwitchType.field_178875_a[p_180521_1_.ordinal()])
                        {
                            case 1:
                                NetworkPlayerInfo.this.field_178865_e = p_180521_2_;
                                NetworkPlayerInfo.this.field_178863_g = p_180521_3_.getMetadata("model");

                                if (NetworkPlayerInfo.this.field_178863_g == null)
                                {
                                    NetworkPlayerInfo.this.field_178863_g = "default";
                                }

                                break;

                            case 2:
                                NetworkPlayerInfo.this.field_178862_f = p_180521_2_;
                        }
                    }
                }, true);
            }
        }
    }

    public void func_178859_a(IChatComponent p_178859_1_)
    {
        this.field_178872_h = p_178859_1_;
    }

    public IChatComponent func_178854_k()
    {
        return this.field_178872_h;
    }

    public int func_178835_l()
    {
        return this.field_178873_i;
    }

    public void func_178836_b(int p_178836_1_)
    {
        this.field_178873_i = p_178836_1_;
    }

    public int func_178860_m()
    {
        return this.field_178870_j;
    }

    public void func_178857_c(int p_178857_1_)
    {
        this.field_178870_j = p_178857_1_;
    }

    public long func_178847_n()
    {
        return this.field_178871_k;
    }

    public void func_178846_a(long p_178846_1_)
    {
        this.field_178871_k = p_178846_1_;
    }

    public long func_178858_o()
    {
        return this.field_178868_l;
    }

    public void func_178844_b(long p_178844_1_)
    {
        this.field_178868_l = p_178844_1_;
    }

    public long func_178855_p()
    {
        return this.field_178869_m;
    }

    public void func_178843_c(long p_178843_1_)
    {
        this.field_178869_m = p_178843_1_;
    }

    static final class SwitchType
    {
        static final int[] field_178875_a = new int[Type.values().length];
        private static final String __OBFID = "CL_00002618";

        static
        {
            try
            {
                field_178875_a[Type.SKIN.ordinal()] = 1;
            }
            catch (NoSuchFieldError var2)
            {
                ;
            }

            try
            {
                field_178875_a[Type.CAPE.ordinal()] = 2;
            }
            catch (NoSuchFieldError var1)
            {
                ;
            }
        }
    }
}
