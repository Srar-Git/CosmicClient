package cn.pixelwar.cosmic.client.mixin;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

/**
 * KeyBinding 访问器接口
 * 提供对 KeyBinding 私有字段的访问
 * 
 * @author CosmicCore Team
 */
@Mixin(KeyBinding.class)
public interface KeyBindingAccessor {
    
    /**
     * 获取 KEY_TO_BINDINGS 映射表
     */
    @Accessor("KEY_TO_BINDINGS")
    static Map<InputUtil.Key, KeyBinding> getKeyToBindings() {
        throw new AssertionError();
    }
}
