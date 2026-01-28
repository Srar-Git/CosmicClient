package cn.pixelwar.cosmic.client.keybind;

import cn.pixelwar.cosmic.client.CosmicClient;
import cn.pixelwar.cosmic.client.util.ModifierKey;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cosmic 客户端按键管理器
 * 管理从服务端接收的按键配置和本地按键绑定
 * 
 * @author CosmicCore Team
 */
public class CosmicKeybindManager {
    
    private final Map<Identifier, CosmicKeyBinding> customKeybinds = new ConcurrentHashMap<>();
    private final Set<Identifier> vanillaKeys = new HashSet<>();
    private final Map<Identifier, InputUtil.Key> pressedKeys = new ConcurrentHashMap<>();
    
    /**
     * 服务器连接时初始化
     */
    public void onServerConnect() {
        CosmicClient.LOGGER.info("Initializing keybind system for server connection");
        clear();
    }
    
    /**
     * 服务器断开时清理
     */
    public void onServerDisconnect() {
        CosmicClient.LOGGER.info("Cleaning up keybind system after server disconnect");
        clear();
    }
    
    /**
     * 添加按键配置（从服务端接收）
     * 
     * @param keyData 按键配置数据
     */
    public void addKeyConfiguration(KeyConfigData keyData) {
        Identifier id = keyData.getId();
        
        // 检查是否为原版按键
        if (id.getNamespace().equals(Identifier.DEFAULT_NAMESPACE)) {
            vanillaKeys.add(id);
            CosmicClient.LOGGER.debug("Added vanilla key: {}", id);
        } else {
            // 创建自定义按键绑定
            CosmicKeyBinding keyBinding = new CosmicKeyBinding(keyData);
            customKeybinds.put(id, keyBinding);
            CosmicClient.LOGGER.debug("Added custom keybind: {} -> {}", id, keyData.getDisplayName());
        }
    }
    
    /**
     * 处理按键输入事件
     * 
     * @param key 按键
     * @param pressed 是否按下
     */
    public void handleKeyInput(InputUtil.Key key, boolean pressed) {
        // 检查原版按键绑定
        checkVanillaKeybinds(key, pressed);
        
        // 检查自定义按键绑定
        checkCustomKeybinds(key, pressed);
    }
    
    /**
     * 检查原版按键绑定
     */
    private void checkVanillaKeybinds(InputUtil.Key key, boolean pressed) {
        // 获取与此按键关联的原版按键绑定
        Collection<KeyBinding> keyBindings = getKeyBindingsForKey(key);
        
        for (KeyBinding binding : keyBindings) {
            if (binding != null) {
                String translationKey = binding.getTranslationKey();
                String cleanKey = cleanTranslationKey(translationKey);
                Identifier id = Identifier.of(Identifier.DEFAULT_NAMESPACE, cleanKey);
                
                if (vanillaKeys.contains(id)) {
                    registerKeyPress(id, key, pressed);
                }
            }
        }
    }
    
    /**
     * 检查自定义按键绑定
     */
    private void checkCustomKeybinds(InputUtil.Key key, boolean pressed) {
        for (CosmicKeyBinding keybind : customKeybinds.values()) {
            if (key.equals(keybind.getBoundKey()) && keybind.testModifiers()) {
                registerKeyPress(keybind.getId(), key, pressed);
            }
        }
    }
    
    /**
     * 注册按键按下/释放事件
     */
    private void registerKeyPress(Identifier id, InputUtil.Key key, boolean pressed) {
        if (pressed) {
            boolean wasHeld = pressedKeys.containsKey(id);
            if (!wasHeld) {
                pressedKeys.put(id, key);
                sendKeyEvent(id, false); // false = 按下事件
                CosmicClient.LOGGER.debug("Key pressed: {}", id);
            }
        } else {
            if (pressedKeys.remove(id) != null) {
                sendKeyEvent(id, true); // true = 释放事件
                CosmicClient.LOGGER.debug("Key released: {}", id);
            }
        }
    }
    
    /**
     * 发送按键事件到服务端
     */
    private void sendKeyEvent(Identifier id, boolean isRelease) {
        CosmicClient.getInstance().getNetworkManager().sendKeyEvent(id, isRelease);
    }
    
    /**
     * 获取与指定按键关联的按键绑定
     */
    private Collection<KeyBinding> getKeyBindingsForKey(InputUtil.Key key) {
        // 使用 Mixin Accessor 获取按键绑定映射表
        Map<InputUtil.Key, KeyBinding> keyToBindings = cn.pixelwar.cosmic.client.mixin.KeyBindingAccessor.getKeyToBindings();
        KeyBinding binding = keyToBindings.get(key);
        if (binding != null) {
            return Collections.singletonList(binding);
        }
        return Collections.emptyList();
    }
    
    /**
     * 清理翻译键名称
     */
    private String cleanTranslationKey(String translationKey) {
        // 移除 "key." 前缀
        if (translationKey.startsWith("key.")) {
            return translationKey.substring(4);
        }
        return translationKey;
    }
    
    /**
     * 获取所有自定义按键绑定
     */
    public Collection<CosmicKeyBinding> getCustomKeybinds() {
        return Collections.unmodifiableCollection(customKeybinds.values());
    }
    
    /**
     * 获取所有原版按键
     */
    public Set<Identifier> getVanillaKeys() {
        return Collections.unmodifiableSet(vanillaKeys);
    }
    
    /**
     * 按分类排序的按键绑定
     */
    public List<CosmicKeyBinding> getCategorySortedKeybinds() {
        List<CosmicKeyBinding> sorted = new ArrayList<>(customKeybinds.values());
        sorted.sort(Comparator.comparing(CosmicKeyBinding::getCategory)
                .thenComparing(CosmicKeyBinding::getDisplayName));
        return sorted;
    }
    
    /**
     * 按修饰符排序的按键绑定
     */
    public List<CosmicKeyBinding> getModifierSortedKeybinds() {
        List<CosmicKeyBinding> sorted = new ArrayList<>(customKeybinds.values());
        sorted.sort((a, b) -> {
            int modCountA = a.getModifiers().size();
            int modCountB = b.getModifiers().size();
            if (modCountA != modCountB) {
                return Integer.compare(modCountB, modCountA); // 修饰符多的在前
            }
            return a.getDisplayName().compareTo(b.getDisplayName());
        });
        return sorted;
    }
    
    /**
     * 清空所有按键配置
     */
    public void clear() {
        customKeybinds.clear();
        vanillaKeys.clear();
        pressedKeys.clear();
        CosmicClient.LOGGER.debug("Cleared all keybind configurations");
    }
}