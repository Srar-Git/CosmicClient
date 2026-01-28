# Cosmic Client 兼容性说明

## 🎯 设计原则

Cosmic Client 模组和 CosmicCore 插件遵循**完全可选、互不强制**的设计原则,确保在任何安装情况下都不会影响玩家正常游戏。

## ✅ 三种安装场景

### 场景 1: 只安装客户端模组 (无服务端插件)

```
客户端: CosmicClient ✅
服务端: 普通服务器 ❌
```

**行为:**
- ✅ 玩家可以正常连接任何服务器
- ✅ 连接时模组自动检测服务端是否支持 Cosmic 协议
- ✅ 检测到服务端无插件时,自动禁用按键功能
- ✅ 游戏体验完全不受影响

**日志输出:**
```log
[INFO] (cosmic-client) Connected to server, sending handshake...
[INFO] (cosmic-client) Server does not have CosmicCore plugin installed - keybind features disabled
[INFO] (cosmic-client) Client will work normally, but custom keybinds will not be available
```

**技术实现:**
- 使用 `ClientPlayNetworking.canSend()` 检测服务端通道注册状态
- 未注册时不发送任何数据包,避免错误
- 按键事件监听器仍然运行,但不发送到服务端

---

### 场景 2: 只安装服务端插件 (无客户端模组)

```
客户端: 原版 Minecraft ❌
服务端: CosmicCore ✅
```

**行为:**
- ✅ 没有模组的玩家可以正常连接服务器
- ✅ 服务端不会向这些玩家发送任何协议数据包
- ✅ 这些玩家正常游戏,只是无法使用自定义按键功能
- ✅ 服务端只向发送了握手消息的玩家发送按键配置

**服务端日志:**
```log
[INFO] [Keybind] Player joined without Cosmic Client mod (no handshake received)
[DEBUG] [Keybind] Player Alice does not have client mod, skipping key configuration sync
```

**技术实现:**
- 服务端维护 `Set<UUID> playersWithMod` 追踪安装模组的玩家
- 只有收到握手消息的玩家才被添加到该集合
- 玩家退出时自动从集合中移除
- 按键配置只发送给集合中的玩家

**代码示例:**
```java
// CosmicProtocolAdapter.java
private final Set<UUID> playersWithMod = ConcurrentHashMap.newKeySet();

public void handleHandshake(Player player) {
    // 收到握手,标记该玩家有模组
    playersWithMod.add(player.getUniqueId());
    sendAllKeyConfigurations(player);
}

public void handlePlayerQuit(Player player) {
    // 玩家退出,清理追踪
    playersWithMod.remove(player.getUniqueId());
}

public boolean hasClientMod(Player player) {
    return playersWithMod.contains(player.getUniqueId());
}
```

---

### 场景 3: 客户端和服务端都安装 ✨

```
客户端: CosmicClient ✅
服务端: CosmicCore ✅
```

**行为:**
- ✅ 完整功能体验
- ✅ 自动握手并同步按键配置
- ✅ 按键事件实时传输
- ✅ 支持自定义按键、修饰键、原版按键监听等所有功能

**通信流程:**
1. 客户端连接服务器
2. 客户端发送握手: `cosmic:greeting`
3. 服务端标记该玩家有模组
4. 服务端发送按键配置: `cosmic:addkey` (多个)
5. 服务端发送加载完成: `cosmic:load`
6. 客户端按下按键
7. 客户端发送按键事件: `cosmic:keybind`
8. 服务端处理按键事件

**日志输出 (客户端):**
```log
[INFO] (cosmic-client) Connected to server, sending handshake...
[INFO] (cosmic-client) Sent handshake to server
[DEBUG] (cosmic-client) Received key configuration: cosmic:skill_menu -> 技能菜单
[INFO] (cosmic-client) Server finished sending key configurations, loading user keybinds...
[INFO] (cosmic-client) Cosmic keybind system ready!
[DEBUG] (cosmic-client) Sent key event: cosmic:skill_menu (release: false)
```

**日志输出 (服务端):**
```log
[DEBUG] [CosmicProtocolAdapter] Received handshake from player: Steve
[INFO] [CosmicProtocolAdapter] Sending all key configurations to player: Steve
[INFO] [CosmicProtocolAdapter] Sent 5 key configurations to Steve
[DEBUG] [CosmicProtocolAdapter] Received key event from Steve: cosmic:skill_menu (pressed: true)
[INFO] [Keybind] Player Steve pressed keybind: cosmic:skill_menu
```

---

## 🔍 协议检测机制

### 客户端检测
使用 Fabric 的 `ClientPlayNetworking` API:

```java
if (ClientPlayNetworking.canSend(payloadType)) {
    // 服务端支持该协议,可以发送
    ClientPlayNetworking.send(payload);
} else {
    // 服务端不支持,不发送数据包
    LOGGER.info("Server does not support Cosmic protocol");
}
```

### 服务端检测
通过握手消息识别:

```java
// 只有主动发送握手的玩家才有模组
@Override
public void onPluginMessageReceived(String channel, Player player, byte[] message) {
    if (channel.equals(CosmicChannels.HANDSHAKE)) {
        // 收到握手,标记该玩家
        protocolAdapter.handleHandshake(player);
    }
}
```

---

## 📊 兼容性矩阵

| 客户端 | 服务端 | 能否连接 | 按键功能 | 影响 |
|--------|--------|---------|---------|------|
| ❌ 无   | ❌ 无   | ✅ 是    | ❌ 无    | 无影响 |
| ✅ 有   | ❌ 无   | ✅ 是    | ❌ 无    | 无影响,自动禁用 |
| ❌ 无   | ✅ 有   | ✅ 是    | ❌ 无    | 无影响,服务端不发送 |
| ✅ 有   | ✅ 有   | ✅ 是    | ✅ 有    | 完整功能 |

---

## 🛡️ 安全保证

1. **不强制要求** - 客户端和服务端都可以单独安装或不安装
2. **优雅降级** - 检测到对方不支持时自动禁用功能
3. **无错误抛出** - 任何情况下都不会因为协议问题导致崩溃或错误
4. **零性能影响** - 不发送数据包时没有任何性能开销
5. **向后兼容** - 未来版本会保持协议向后兼容

---

## 💡 最佳实践

### 对于服务器管理员
- **推荐**: 同时安装服务端插件和推广客户端模组,获得完整功能
- **可选**: 只安装服务端插件,没有模组的玩家正常游戏
- **检查**: 查看服务端日志确认有多少玩家使用了客户端模组

### 对于玩家
- **推荐**: 安装客户端模组,在支持的服务器上获得更好体验
- **安心**: 安装后可以连接任何服务器,不会有任何问题
- **可选**: 如果不想用,随时可以删除模组

---

## 🔧 故障排查

### 客户端模组未生效
1. 检查服务端是否安装了 CosmicCore 插件
2. 查看客户端日志是否显示 "Sent handshake to server"
3. 确认服务端日志是否收到握手消息

### 服务端插件未检测到客户端
1. 确认客户端安装了 CosmicClient 模组和 Fabric API
2. 检查客户端是否成功发送握手
3. 确认服务端 Plugin Message Channel 已正确注册

---

**结论**: Cosmic 按键系统的客户端模组和服务端插件都是**完全可选**的,可以放心安装使用,不会影响任何现有游戏体验! 🎉
