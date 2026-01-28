package cn.pixelwar.cosmic.client.network;

import net.minecraft.util.Identifier;

/**
 * Cosmic 网络通道定义
 * 与服务端保持一致的通道标识符
 * 
 * @author CosmicCore Team
 */
public class CosmicChannels {
    
    /** 握手协议 - 客户端连接时发送 */
    public static final Identifier HANDSHAKE = Identifier.of("cosmic", "greeting");
    
    /** 添加按键配置 - 服务端发送按键定义到客户端 */
    public static final Identifier ADD_KEY = Identifier.of("cosmic", "addkey");
    
    /** 按键事件传输 - 客户端发送按键事件到服务端 */
    public static final Identifier KEY_PRESS = Identifier.of("cosmic", "keybind");
    
    /** 加载配置完成 - 服务端通知客户端加载完成 */
    public static final Identifier LOAD_KEYS = Identifier.of("cosmic", "load");
    
    private CosmicChannels() {
        // 工具类，禁止实例化
    }
}