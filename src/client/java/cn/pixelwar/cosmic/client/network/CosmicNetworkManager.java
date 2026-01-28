package cn.pixelwar.cosmic.client.network;

import cn.pixelwar.cosmic.client.CosmicClient;
import cn.pixelwar.cosmic.client.keybind.CosmicKeybindManager;
import cn.pixelwar.cosmic.client.keybind.KeyConfigData;
import cn.pixelwar.cosmic.client.util.ModifierKey;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Set;

/**
 * Cosmic 网络管理器
 * 处理与服务端的网络通信
 * 
 * @author CosmicCore Team
 */
public class CosmicNetworkManager {
    
    private final CosmicKeybindManager keybindManager;
    
    public CosmicNetworkManager(CosmicKeybindManager keybindManager) {
        this.keybindManager = keybindManager;
        registerPayloadTypes();
        registerPacketHandlers();
    }
    
    /**
     * 注册数据包类型
     */
    private void registerPayloadTypes() {
        // 注册客户端发送的数据包类型
        PayloadTypeRegistry.playC2S().register(
            CosmicNetworkCodec.HandshakePayload.ID,
            CosmicNetworkCodec.HandshakePayload.CODEC
        );
        PayloadTypeRegistry.playC2S().register(
            CosmicNetworkCodec.KeyPressPayload.ID,
            CosmicNetworkCodec.KeyPressPayload.CODEC
        );
        
        // 注册服务端发送的数据包类型
        PayloadTypeRegistry.playS2C().register(
            CosmicNetworkCodec.AddKeyPayload.ID,
            CosmicNetworkCodec.AddKeyPayload.CODEC
        );
        PayloadTypeRegistry.playS2C().register(
            CosmicNetworkCodec.LoadKeysPayload.ID,
            CosmicNetworkCodec.LoadKeysPayload.CODEC
        );
        
        CosmicClient.LOGGER.info("Registered Cosmic network payload types");
    }
    
    /**
     * 注册数据包处理器
     */
    private void registerPacketHandlers() {
        // 处理服务端发送的按键配置
        ClientPlayNetworking.registerGlobalReceiver(
            CosmicNetworkCodec.AddKeyPayload.ID,
            (payload, context) -> {
                context.client().execute(() -> handleAddKeyPayload(payload));
            }
        );
        
        // 处理服务端发送的加载完成信号
        ClientPlayNetworking.registerGlobalReceiver(
            CosmicNetworkCodec.LoadKeysPayload.ID,
            (payload, context) -> {
                context.client().execute(() -> handleLoadKeysPayload(payload));
            }
        );
        
        CosmicClient.LOGGER.info("Registered Cosmic network packet handlers");
    }
    
    /**
     * 处理按键配置数据包
     */
    private void handleAddKeyPayload(CosmicNetworkCodec.AddKeyPayload payload) {
        try {
            // 构建按键ID
            Identifier keyId = Identifier.of(payload.namespace(), payload.key());
            
            // 解析修饰符
            Set<ModifierKey> modifiers = new HashSet<>();
            for (int modifierId : payload.modifiers()) {
                ModifierKey modifier = ModifierKey.fromId(modifierId);
                if (modifier.isValid()) {
                    modifiers.add(modifier);
                }
            }
            
            // 创建按键配置数据
            KeyConfigData keyData = new KeyConfigData(
                keyId,
                payload.displayName(),
                payload.category(),
                payload.defaultKey(),
                modifiers
            );
            
            // 添加到按键管理器
            keybindManager.addKeyConfiguration(keyData);
            
            CosmicClient.LOGGER.debug("Received key configuration: {} -> {}", keyId, payload.displayName());
            
        } catch (Exception e) {
            CosmicClient.LOGGER.error("Failed to handle AddKey payload", e);
        }
    }
    
    /**
     * 处理加载完成数据包
     */
    private void handleLoadKeysPayload(CosmicNetworkCodec.LoadKeysPayload payload) {
        CosmicClient.LOGGER.info("Server finished sending key configurations, loading user keybinds...");
        
        // TODO: 加载用户自定义的按键绑定配置
        // 这里可以从本地文件加载用户的按键绑定设置
        
        CosmicClient.LOGGER.info("Cosmic keybind system ready!");
    }
    
    /**
     * 发送握手数据包到服务端
     */
    public void sendHandshake() {
        if (ClientPlayNetworking.canSend(CosmicNetworkCodec.HandshakePayload.ID)) {
            ClientPlayNetworking.send(new CosmicNetworkCodec.HandshakePayload());
            CosmicClient.LOGGER.info("Sent handshake to server");
        } else {
            CosmicClient.LOGGER.info("Server does not have CosmicCore plugin installed - keybind features disabled");
            CosmicClient.LOGGER.info("Client will work normally, but custom keybinds will not be available");
        }
    }
    
    /**
     * 发送按键事件到服务端
     * 
     * @param keyId 按键ID
     * @param isRelease 是否为释放事件
     */
    public void sendKeyEvent(Identifier keyId, boolean isRelease) {
        if (ClientPlayNetworking.canSend(CosmicNetworkCodec.KeyPressPayload.ID)) {
            CosmicNetworkCodec.KeyPressPayload payload = new CosmicNetworkCodec.KeyPressPayload(
                keyId.getNamespace(),
                keyId.getPath(),
                isRelease
            );
            ClientPlayNetworking.send(payload);
            
            CosmicClient.LOGGER.debug("Sent key event: {} (release: {})", keyId, isRelease);
        } else {
            // 静默处理 - 服务端没有插件时不发送，也不打印警告避免刷屏
            CosmicClient.LOGGER.debug("Key event not sent - server does not support Cosmic protocol: {}", keyId);
        }
    }
    
    /**
     * 服务器连接时初始化
     */
    public void onServerConnect() {
        CosmicClient.LOGGER.info("Connected to server, sending handshake...");
        
        // 延迟发送握手，确保连接稳定
        new Thread(() -> {
            try {
                Thread.sleep(1000); // 等待1秒
                sendHandshake();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
    
    /**
     * 服务器断开时清理
     */
    public void onServerDisconnect() {
        CosmicClient.LOGGER.info("Disconnected from server, cleaning up network state");
        // 清理网络相关状态
    }
}