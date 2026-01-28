package cn.pixelwar.cosmic.client.util;

import org.lwjgl.glfw.GLFW;

/**
 * 修饰符按键枚举
 * 与服务端的 ModifierKey 保持一致
 * 
 * @author CosmicCore Team
 */
public enum ModifierKey {
    
    NONE(-1, "None", -1),
    LEFT_CTRL(4, "Left Ctrl", GLFW.GLFW_KEY_LEFT_CONTROL),
    LEFT_SHIFT(0, "Left Shift", GLFW.GLFW_KEY_LEFT_SHIFT),
    LEFT_ALT(2, "Left Alt", GLFW.GLFW_KEY_LEFT_ALT),
    RIGHT_CTRL(5, "Right Ctrl", GLFW.GLFW_KEY_RIGHT_CONTROL),
    RIGHT_SHIFT(1, "Right Shift", GLFW.GLFW_KEY_RIGHT_SHIFT),
    RIGHT_ALT(3, "Right Alt", GLFW.GLFW_KEY_RIGHT_ALT);
    
    private final int id;
    private final String displayName;
    private final int glfwKey;
    
    ModifierKey(int id, String displayName, int glfwKey) {
        this.id = id;
        this.displayName = displayName;
        this.glfwKey = glfwKey;
    }
    
    /**
     * 获取修饰符ID（用于网络传输）
     */
    public int getId() {
        return id;
    }
    
    /**
     * 获取显示名称
     */
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * 获取 GLFW 按键码
     */
    public int getGlfwKey() {
        return glfwKey;
    }
    
    /**
     * 检查修饰符是否被按下
     * 
     * @param windowHandle GLFW 窗口句柄
     * @return 如果按键被按下返回 true
     */
    public boolean isPressed(long windowHandle) {
        if (this == NONE || glfwKey == -1) {
            return true;
        }
        
        return GLFW.glfwGetKey(windowHandle, glfwKey) == GLFW.GLFW_PRESS;
    }
    
    /**
     * 根据ID获取修饰符
     * 
     * @param id 修饰符ID
     * @return 对应的修饰符，如果未找到则返回 NONE
     */
    public static ModifierKey fromId(int id) {
        for (ModifierKey modifier : values()) {
            if (modifier.id == id) {
                return modifier;
            }
        }
        return NONE;
    }
    
    /**
     * 检查是否为有效的修饰符（非 NONE）
     */
    public boolean isValid() {
        return this != NONE;
    }
    
    /**
     * 获取修饰符的简短名称
     */
    public String getShortName() {
        switch (this) {
            case LEFT_CTRL:
            case RIGHT_CTRL:
                return "Ctrl";
            case LEFT_SHIFT:
            case RIGHT_SHIFT:
                return "Shift";
            case LEFT_ALT:
            case RIGHT_ALT:
                return "Alt";
            default:
                return "None";
        }
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}