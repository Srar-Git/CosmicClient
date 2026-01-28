package cn.pixelwar.cosmic.client.mixin;

import cn.pixelwar.cosmic.client.CosmicClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

/**
 * 键盘输入 Mixin
 * 注入键盘事件监听，拦截按键输入并发送到 Cosmic 按键系统
 * 
 * @author CosmicCore Team
 */
@Mixin(KeyBinding.class)
public class KeyboardMixin {
    
    @Shadow
    private static Map<InputUtil.Key, KeyBinding> KEY_TO_BINDINGS;
    
    /**
     * 注入按键状态设置方法
     * 拦截所有按键输入事件
     */
    @Inject(method = "setKeyPressed", at = @At("HEAD"))
    private static void onSetKeyPressed(InputUtil.Key key, boolean pressed, CallbackInfo ci) {
        try {
            // 检查是否在游戏中（不在GUI界面）
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.currentScreen != null) {
                return; // 在GUI界面时不处理按键
            }
            
            // 检查 Cosmic 客户端是否已初始化
            CosmicClient cosmicClient = CosmicClient.getInstance();
            if (cosmicClient == null || cosmicClient.getKeybindManager() == null) {
                return;
            }
            
            // 将按键事件传递给 Cosmic 按键管理器
            cosmicClient.getKeybindManager().handleKeyInput(key, pressed);
            
        } catch (Exception e) {
            // 静默处理异常，避免影响游戏正常运行
            CosmicClient.LOGGER.error("Error in KeyboardMixin.onSetKeyPressed", e);
        }
    }
    
    /**
     * 私有辅助方法获取按键绑定
     */
    private static KeyBinding getKeyBinding(InputUtil.Key key) {
        return KEY_TO_BINDINGS.get(key);
    }
}