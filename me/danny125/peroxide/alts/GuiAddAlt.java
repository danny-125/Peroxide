package me.danny125.peroxide.alts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Proxy;

import org.lwjgl.input.Keyboard;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import me.danny125.peroxide.InitClient;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;


public class GuiAddAlt
extends GuiScreen {
    private final GuiAltManager manager;
    private PasswordField password;
    private String status = (Object)((Object)EnumChatFormatting.GRAY) + "Idle...";
    private GuiTextField username;

    public GuiAddAlt(GuiAltManager manager) {
        this.manager = manager;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0: {
                AddAltThread login = new AddAltThread(this.username.getText(), this.password.getText());
                login.start();
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(this.manager);
            }
        }
    }

    @Override
    public void drawScreen(int i2, int j2, float f2) {
        this.drawDefaultBackground();
        this.username.drawTextBox();
        this.password.drawTextBox();
        this.drawCenteredString(this.fontRendererObj, "Add Alt", width / 2, 20, -1);
        if (this.username.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "Username / E-Mail", width / 2 - 96, 66, -7829368);
        }
        if (this.password.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "Password", width / 2 - 96, 106, -7829368);
        }
        this.drawCenteredString(this.fontRendererObj, this.status, width / 2, 30, -1);
        super.drawScreen(i2, j2, f2);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 92 + 12, "Login"));
        this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 116 + 12, "Back"));
        this.username = new GuiTextField(this.eventButton, this.mc.fontRendererObj, width / 2 - 100, 60, 200, 20);
        this.password = new PasswordField(this.mc.fontRendererObj, width / 2 - 100, 100, 200, 20);
        
        
        
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        this.username.textboxKeyTyped(par1, par2);
        this.password.textboxKeyTyped(par1, par2);
        if (par1 == '\t' && (this.username.isFocused() || this.password.isFocused())) {
            this.username.setFocused(!this.username.isFocused());
            this.password.setFocused(!this.password.isFocused());
        }
        if (par1 == '\r') {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
        try {
            super.mouseClicked(par1, par2, par3);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.username.mouseClicked(par1, par2, par3);
        this.password.mouseClicked(par1, par2, par3);
    }

    static void access$0(GuiAddAlt guiAddAlt, String status) {
        guiAddAlt.status = status;
    }

    public static class AddAltThread
    extends Thread {
        private final String password;
        private final String username;

        public AddAltThread(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public static void checkAndAddAlt(String username, String password, boolean initialCheck) throws IOException {
            YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
            auth.setUsername(username);
            auth.setPassword(password);
            try {
                auth.logIn();
                AltManager altManager = InitClient.altManager;
                AltManager.registry.add(new Alt(username, password, auth.getSelectedProfile().getName()));
                
                if(!initialCheck) {
                FileWriter fw = null;
                BufferedWriter bw = null;
                PrintWriter pw = null;
                
                fw = new FileWriter("PeroxideAlts.txt", true);
                bw = new BufferedWriter(fw); 
                pw = new PrintWriter(bw);
                
                pw.println(username + ":" + password);
                pw.close();
                }
            }
            catch (AuthenticationException e) {
                e.printStackTrace();
            }	
        }
        

        @Override
        public void run() {
            if (this.password.equals("")) {
                AltManager altManager = InitClient.altManager;
                AltManager.registry.add(new Alt(this.username, ""));
                return;
            }
            try {
                this.checkAndAddAlt(this.username, this.password,false);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

