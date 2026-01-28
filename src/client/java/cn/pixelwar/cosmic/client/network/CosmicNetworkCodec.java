package cn.pixelwar.cosmic.client.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.nio.charset.StandardCharsets;

/**
 * Cosmic 网络编解码器
 * 处理客户端和服务端之间的数据包编解码
 * 
 * @author CosmicCore Team
 */
public class CosmicNetworkCodec {
    
    /**
     * 握手数据包
     */
    public record HandshakePayload() implements CustomPayload {
        public static final CustomPayload.Id<HandshakePayload> ID = new CustomPayload.Id<>(CosmicChannels.HANDSHAKE);
        public static final PacketCodec<PacketByteBuf, HandshakePayload> CODEC = PacketCodec.of(
            (value, buf) -> {
                // 写入 Forge 兼容性字节
                buf.writeByte(0);
            },
            buf -> {
                // 读取 Forge 兼容性字节
                buf.readByte();
                return new HandshakePayload();
            }
        );
        
        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }
    
    /**
     * 按键事件数据包
     */
    public record KeyPressPayload(String namespace, String key, boolean isRelease) implements CustomPayload {
        public static final CustomPayload.Id<KeyPressPayload> ID = new CustomPayload.Id<>(CosmicChannels.KEY_PRESS);
        public static final PacketCodec<PacketByteBuf, KeyPressPayload> CODEC = PacketCodec.of(
            (value, buf) -> {
                // 写入 Forge 兼容性字节
                buf.writeByte(0);
                // 写入按键信息
                buf.writeString(value.namespace);
                buf.writeString(value.key);
                buf.writeBoolean(value.isRelease);
            },
            buf -> {
                // 读取 Forge 兼容性字节
                buf.readByte();
                // 读取按键信息
                String namespace = buf.readString();
                String key = buf.readString();
                boolean isRelease = buf.readBoolean();
                return new KeyPressPayload(namespace, key, isRelease);
            }
        );
        
        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }
    
    /**
     * 按键配置数据包
     */
    public record AddKeyPayload(
            String namespace,
            String key,
            int defaultKey,
            String displayName,
            String category,
            int[] modifiers
    ) implements CustomPayload {
        public static final CustomPayload.Id<AddKeyPayload> ID = new CustomPayload.Id<>(CosmicChannels.ADD_KEY);
        public static final PacketCodec<PacketByteBuf, AddKeyPayload> CODEC = PacketCodec.of(
            (value, buf) -> {
                // 这个方向不需要实现，客户端不发送此类型数据包
                throw new UnsupportedOperationException("Client should not send AddKeyPayload");
            },
            buf -> {
                // 读取 Forge 兼容性字节
                buf.readByte();
                // 读取按键配置
                String namespace = buf.readString();
                String key = buf.readString();
                int defaultKey = buf.readInt();
                String displayName = buf.readString();
                String category = buf.readString();
                int[] modifiers = buf.readIntArray();
                return new AddKeyPayload(namespace, key, defaultKey, displayName, category, modifiers);
            }
        );
        
        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }
    
    /**
     * 加载完成数据包
     */
    public record LoadKeysPayload() implements CustomPayload {
        public static final CustomPayload.Id<LoadKeysPayload> ID = new CustomPayload.Id<>(CosmicChannels.LOAD_KEYS);
        public static final PacketCodec<PacketByteBuf, LoadKeysPayload> CODEC = PacketCodec.of(
            (value, buf) -> {
                // 这个方向不需要实现，客户端不发送此类型数据包
                throw new UnsupportedOperationException("Client should not send LoadKeysPayload");
            },
            buf -> {
                // 读取 Forge 兼容性字节
                buf.readByte();
                return new LoadKeysPayload();
            }
        );
        
        @Override
        public Id<? extends CustomPayload> getId() {
            return ID;
        }
    }
}