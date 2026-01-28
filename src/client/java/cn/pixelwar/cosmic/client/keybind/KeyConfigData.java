package cn.pixelwar.cosmic.client.keybind;

import cn.pixelwar.cosmic.client.util.ModifierKey;
import net.minecraft.util.Identifier;

import java.util.Set;

/**
 * 按键配置数据
 * 从服务端接收的按键配置信息
 * 
 * @author CosmicCore Team
 */
public class KeyConfigData {
    
    private final Identifier id;
    private final String displayName;
    private final String category;
    private final int defaultKey;
    private final Set<ModifierKey> modifiers;
    
    public KeyConfigData(Identifier id, String displayName, String category, int defaultKey, Set<ModifierKey> modifiers) {
        this.id = id;
        this.displayName = displayName;
        this.category = category;
        this.defaultKey = defaultKey;
        this.modifiers = modifiers;
    }
    
    public Identifier getId() {
        return id;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getCategory() {
        return category;
    }
    
    public int getDefaultKey() {
        return defaultKey;
    }
    
    public Set<ModifierKey> getModifiers() {
        return modifiers;
    }
    
    @Override
    public String toString() {
        return "KeyConfigData{" +
                "id=" + id +
                ", displayName='" + displayName + '\'' +
                ", category='" + category + '\'' +
                ", defaultKey=" + defaultKey +
                ", modifiers=" + modifiers +
                '}';
    }
}