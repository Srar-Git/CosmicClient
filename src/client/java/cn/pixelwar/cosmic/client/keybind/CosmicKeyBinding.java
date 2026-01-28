package cn.pixelwar.cosmic.client.keybind;

import cn.pixelwar.cosmic.client.util.ModifierKey;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

import java.util.Set;

/**
 * Cosmic 自定义按键绑定
 * 表示一个从服务端接收的按键配置
 * 
 * @author CosmicCore Team
 */
public class CosmicKeyBinding {
    
    private final Identifier id;
    private final String displayName;
    private final String category;
    private final int defaultKeyCode;
    private final Set<ModifierKey> modifiers;
    
    private InputUtil.Key boundKey;
    
    public CosmicKeyBinding(KeyConfigData data) {
        this.id = data.getId();
        this.displayName = data.getDisplayName();
        this.category = data.getCategory();
        this.defaultKeyCode = data.getDefaultKey();
        this.modifiers = data.getModifiers();
        
        // 设置默认绑定的按键
        this.boundKey = InputUtil.Type.KEYSYM.createFromCode(defaultKeyCode);
    }
    
    /**
     * 获取按键ID
     */
    public Identifier getId() {
        return id;
    }
    
    /**
     * 获取显示名称
     */
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * 获取分类
     */
    public String getCategory() {
        return category;
    }
    
    /**
     * 获取默认按键码
     */
    public int getDefaultKeyCode() {
        return defaultKeyCode;
    }
    
    /**
     * 获取修饰符集合
     */
    public Set<ModifierKey> getModifiers() {
        return modifiers;
    }
    
    /**
     * 获取当前绑定的按键
     */
    public InputUtil.Key getBoundKey() {
        return boundKey;
    }
    
    /**
     * 设置绑定的按键
     */
    public void setBoundKey(InputUtil.Key key) {
        this.boundKey = key;
    }
    
    /**
     * 测试修饰符是否满足条件
     * 检查当前是否按下了所有需要的修饰符
     */
    public boolean testModifiers() {
        if (modifiers.isEmpty()) {
            return true;
        }
        
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getWindow() == null) {
            return false;
        }
        
        long windowHandle = client.getWindow().getHandle();
        
        for (ModifierKey modifier : modifiers) {
            if (!modifier.isPressed(windowHandle)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * 获取按键的本地化名称
     */
    public String getLocalizedName() {
        return boundKey.getLocalizedText().getString();
    }
    
    /**
     * 获取完整的按键描述（包含修饰符）
     */
    public String getFullDescription() {
        StringBuilder sb = new StringBuilder();
        
        // 添加修饰符
        for (ModifierKey modifier : modifiers) {
            sb.append(modifier.getDisplayName()).append(" + ");
        }
        
        // 添加主按键
        sb.append(getLocalizedName());
        
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return "CosmicKeyBinding{" +
                "id=" + id +
                ", displayName='" + displayName + '\'' +
                ", category='" + category + '\'' +
                ", boundKey=" + boundKey +
                ", modifiers=" + modifiers +
                '}';
    }
}