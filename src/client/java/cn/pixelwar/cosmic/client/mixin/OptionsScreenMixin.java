package cn.pixelwar.cosmic.client.mixin;

import cn.pixelwar.cosmic.client.CosmicClient;
import cn.pixelwar.cosmic.client.keybind.CosmicKeyBinding;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 控制选项界面 Mixin
 * 在控制设置界面添加 Cosmic 按键配置按钮
 * 
 * @author CosmicCore Team
 */
@Mixin(ControlsOptionsScreen.class)
public abstract class OptionsScreenMixin extends GameOptionsScreen {
    
    public OptionsScreenMixin(net.minecraft.client.gui.screen.Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }
    
    /**
     * 在控制选项界面初始化时添加 Cosmic 按键按钮
     */
    @Inject(method = "init()V", at = @At("TAIL"))
    private void addCosmicKeybindButton(CallbackInfo ci) {
        try {
            // 检查 Cosmic 客户端是否已初始化
            CosmicClient cosmicClient = CosmicClient.getInstance();
            if (cosmicClient == null || cosmicClient.getKeybindManager() == null) {
                return;
            }
            
            // 检查是否有自定义按键
            if (cosmicClient.getKeybindManager().getCustomKeybinds().isEmpty()) {
                return;
            }
            
            // 添加 Cosmic 按键配置按钮
            int buttonY = this.height / 6 + 120; // 在现有按钮下方
            ButtonWidget cosmicButton = ButtonWidget.builder(
                Text.literal("Cosmic 按键设置"),
                button -> openCosmicKeybindScreen()
            ).dimensions(this.width / 2 - 155, buttonY, 150, 20).build();
            
            this.addDrawableChild(cosmicButton);
            
            CosmicClient.LOGGER.debug("Added Cosmic keybind button to controls screen");
            
        } catch (Exception e) {
            CosmicClient.LOGGER.error("Error adding Cosmic keybind button", e);
        }
    }
    
    /**
     * 打开 Cosmic 按键配置界面
     */
    private void openCosmicKeybindScreen() {
        try {
            CosmicClient cosmicClient = CosmicClient.getInstance();
            if (cosmicClient == null) {
                return;
            }
            
            // 显示按键信息（临时实现，后续可以创建专门的GUI）
            StringBuilder info = new StringBuilder("Cosmic 按键配置:\\n\\n");
            
            for (CosmicKeyBinding keybind : cosmicClient.getKeybindManager().getCategorySortedKeybinds()) {
                info.append(String.format("[%s] %s: %s\\n", 
                    keybind.getCategory(),
                    keybind.getDisplayName(),
                    keybind.getFullDescription()
                ));
            }
            
            CosmicClient.LOGGER.info("Cosmic Keybinds:\\n{}", info.toString());
            
            // TODO: 创建专门的 Cosmic 按键配置 GUI 界面
            
        } catch (Exception e) {
            CosmicClient.LOGGER.error("Error opening Cosmic keybind screen", e);
        }
    }
}