package cn.pixelwar.cosmic.client;

import cn.pixelwar.cosmic.client.keybind.CosmicKeybindManager;
import cn.pixelwar.cosmic.client.network.CosmicNetworkManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cosmic Client 模组主类
 * 处理客户端初始化和生命周期管理
 * 
 * @author CosmicCore Team
 */
public class CosmicClient implements ClientModInitializer {
    
    public static final String MOD_ID = "cosmic-client";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    private static CosmicClient instance;
    private CosmicKeybindManager keybindManager;
    private CosmicNetworkManager networkManager;
    
    @Override
    public void onInitializeClient() {
        instance = this;
        
        LOGGER.info("Initializing Cosmic Client...");
        
        // 初始化按键管理器
        this.keybindManager = new CosmicKeybindManager();
        
        // 初始化网络管理器
        this.networkManager = new CosmicNetworkManager(keybindManager);
        
        // 注册连接事件
        registerConnectionEvents();
        
        LOGGER.info("Cosmic Client initialized successfully!");
    }
    
    /**
     * 注册连接相关事件
     */
    private void registerConnectionEvents() {
        // 玩家连接到服务器时
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            LOGGER.info("Connected to server, initializing Cosmic systems...");
            networkManager.onServerConnect();
            keybindManager.onServerConnect();
        });
        
        // 玩家断开连接时
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            LOGGER.info("Disconnected from server, cleaning up Cosmic systems...");
            keybindManager.onServerDisconnect();
            networkManager.onServerDisconnect();
        });
    }
    
    /**
     * 获取模组实例
     * 
     * @return 模组实例
     */
    public static CosmicClient getInstance() {
        return instance;
    }
    
    /**
     * 获取按键管理器
     * 
     * @return 按键管理器
     */
    public CosmicKeybindManager getKeybindManager() {
        return keybindManager;
    }
    
    /**
     * 获取网络管理器
     * 
     * @return 网络管理器
     */
    public CosmicNetworkManager getNetworkManager() {
        return networkManager;
    }
}