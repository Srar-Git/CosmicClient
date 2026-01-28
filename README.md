# Cosmic Client - 客户端按键绑定模组

[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.4-brightgreen.svg)](https://www.minecraft.net/)
[![Fabric](https://img.shields.io/badge/Fabric-0.110.5-blue.svg)](https://fabricmc.net/)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)

Cosmic Client 是一个 Fabric 模组,为 Minecraft 客户端提供与服务端 CosmicCore 插件的按键绑定联动功能。通过 Plugin Message Channel 协议实现客户端-服务端通信,支持自定义按键绑定和原版按键监听。

## 📚 文档导航

- 🚀 **[快速开始](QUICK_START.md)** - 3 分钟上手指南 (推荐玩家阅读)
- 📖 **[完整文档](#)** - 当前页面,包含详细的安装和使用说明
- 🔧 **[兼容性说明](COMPATIBILITY.md)** - 技术文档,了解模组如何保证兼容性
- 🎮 **[玩家使用指南](#-玩家使用指南)** - 跳转到本页的玩家指南章节
- 🔌 **[服务端配置](#-服务端插件配置)** - 服务器管理员和开发者参考

## 📋 功能特性

- ✅ **服务端同步按键配置** - 自动从服务端获取按键绑定设置
- ✅ **原版按键支持** - 监听并上报 Minecraft 原版按键事件
- ✅ **自定义按键绑定** - 支持服务端定义的自定义按键
- ✅ **修饰键支持** - 支持 Ctrl、Shift、Alt 组合键
- ✅ **分类管理** - 按键按类别组织,便于管理
- ✅ **即时通信** - 按键事件实时发送到服务端
- ✅ **完全兼容** - 客户端/服务端单方安装不影响游戏运行

## 🔄 兼容性保证

### 向后兼容
本模组设计为**完全可选**,确保在各种安装情况下都不影响玩家正常游戏:

#### 情况 1: 只有客户端安装了模组
- ✅ **玩家可以正常连接任何服务器**
- ✅ 连接到没有 CosmicCore 插件的服务器时,模组会自动检测并禁用按键功能
- ✅ 只在日志中显示一条提示信息,不会影响游戏体验
- ℹ️ 日志提示: `Server does not have CosmicCore plugin installed - keybind features disabled`

#### 情况 2: 只有服务端安装了插件
- ✅ **没有客户端模组的玩家可以正常连接服务器**
- ✅ 服务端只向安装了模组的玩家发送按键配置
- ✅ 没有模组的玩家不会收到任何协议数据包
- ℹ️ 这些玩家可以正常游戏,只是无法使用自定义按键功能

#### 情况 3: 客户端和服务端都安装
- ✅ **完整功能体验**
- ✅ 自动握手并同步按键配置
- ✅ 按键事件实时传输到服务端
- ✅ 支持所有高级功能(自定义按键、修饰键等)

### 协议检测机制
- 客户端使用 `ClientPlayNetworking.canSend()` 检测服务端是否支持 Cosmic 协议
- 服务端通过握手消息识别安装了模组的玩家
- 只有明确握手的玩家才会被标记为"支持模组",并接收按键配置

## 🔧 环境要求

### 客户端要求
- **Minecraft 版本**: 1.21.4
- **Fabric Loader**: 0.16.9+
- **Fabric API**: 0.110.5+1.21.4
- **Java 版本**: 21 或更高

### 服务端要求
- **服务端**: Paper/Folia 1.21.4
- **插件**: CosmicCore (包含 KeybindManager)

## 📦 安装方法

### 1. 安装 Fabric Loader

如果还没有安装 Fabric,请先下载并安装 [Fabric Installer](https://fabricmc.net/use/installer/):

1. 下载 Fabric Installer
2. 运行安装程序,选择 Minecraft 1.21.4 版本
3. 安装 Fabric Loader

### 2. 安装 Fabric API

下载 [Fabric API 0.110.5+1.21.4](https://www.curseforge.com/minecraft/mc-mods/fabric-api) 并放入 `.minecraft/mods` 文件夹。

### 3. 安装 Cosmic Client 模组

1. 从 Releases 下载 `cosmiccore-client-1.0.0.jar`
2. 将 JAR 文件放入 `.minecraft/mods` 文件夹
3. 启动 Minecraft

```
.minecraft/
├── mods/
│   ├── fabric-api-0.110.5+1.21.4.jar
│   └── cosmiccore-client-1.0.0.jar
└── ...
```

## 🎮 玩家使用指南

### 快速上手 (3 步)

#### 1️⃣ 安装模组
将模组文件放入 `.minecraft/mods` 文件夹,启动游戏即可。

#### 2️⃣ 连接服务器  
连接安装了 CosmicCore 插件的服务器,模组会自动同步按键配置。

#### 3️⃣ 查看按键
在游戏中输入命令查看可用按键:
```
/keybind list
```

### 📋 查看已绑定的按键

有多种方式查看服务器配置了哪些按键:

#### 方法 1: 游戏内命令 (推荐) ⭐
在聊天框输入:
```
/keybind list
```

**显示效果:**
```
===== 已注册的按键 (5) =====
- cosmic:skill_menu (技能菜单) [启用]
- cosmic:quick_craft (快速制作) [启用]
- cosmic:pet_menu (宠物菜单) [启用]
- cosmic:guild_chat (公会聊天) [启用]
- minecraft:forward (前进) [启用]
```

#### 方法 2: 查看按键详情
想了解某个按键的详细信息,使用:
```
/keybind info cosmic:skill_menu
```

**显示内容:**
- 按键ID
- 显示名称
- 描述说明
- 默认绑定的键位
- 是否需要修饰键 (Shift/Ctrl/Alt)
- 冷却时间
- 所属分类

#### 方法 3: 控制设置中查看 (即将推出)
1. 按 `ESC` → "选项" → "控制"
2. 查找 "Cosmic 按键设置" 按钮
3. 点击查看所有自定义按键

#### 方法 4: 查看日志文件
如果想看技术细节,打开 `.minecraft/logs/latest.log`,搜索:
```
Received key configuration
```

### ⌨️ 如何使用按键

#### 基本使用
连接服务器后,按键会自动生效,直接按下即可!

**示例:**
```
服务器设置: K 键 = 打开技能菜单
你的操作: 在游戏中按下 K 键
游戏反应: 自动打开技能菜单界面
```

#### 修饰键组合
有些按键需要配合修饰键使用:

| 组合方式 | 操作方法 | 示例 |
|---------|---------|------|
| **Shift + 按键** | 按住 Shift,再按指定键 | Shift + K |
| **Ctrl + 按键** | 按住 Ctrl,再按指定键 | Ctrl + M |
| **Alt + 按键** | 按住 Alt,再按指定键 | Alt + V |
| **多重组合** | 同时按住多个修饰键 | Shift + Ctrl + K |

**查看是否需要修饰键:**
```
/keybind info <按键ID>
```
会显示该按键是否需要配合 Shift、Ctrl 或 Alt 使用。

#### 按键在什么时候生效?

✅ **生效时机:**
- 在游戏世界中 (正常游玩时)
- 第三人称/第一人称视角
- 飞行模式

❌ **不生效时机:**
- 打开任何 GUI 界面时 (背包、箱子、菜单等)
- 在聊天框输入时
- 使用告示牌/书本编辑时

### 🔧 按键冲突怎么办?

#### 情况 1: 与 Minecraft 原版冲突
**示例:** 服务器设置 `E` 键为某个功能,但 `E` 是打开背包的键。

**解决方案:**
- 服务器的自定义功能优先级更高
- 如果影响游戏体验,联系服务器管理员更换按键
- 管理员可以使用 `/keybind recommended` 查看推荐按键

#### 情况 2: 与其他模组冲突
**解决方案:**
1. 在 Minecraft 控制设置中重新绑定冲突的键
2. 优先保留 Cosmic 的按键设置
3. 调整其他模组的按键配置

#### 情况 3: 不知道哪个键冲突了
使用命令查看被占用的按键:
```
/keybind blacklist
```
会显示 Minecraft 默认占用的按键列表。

### 📊 按键状态指示

连接服务器后,日志会显示状态:

**成功连接并同步:**
```
[INFO] (cosmic-client) Connected to server, sending handshake...
[INFO] (cosmic-client) Sent handshake to server
[INFO] (cosmic-client) Server finished sending key configurations
[INFO] (cosmic-client) Cosmic keybind system ready!
```

**服务器没有插件:**
```
[INFO] (cosmic-client) Server does not have CosmicCore plugin installed
[INFO] (cosmic-client) Keybind features disabled
```

**按键事件发送 (开启调试模式后):**
```
[DEBUG] (cosmic-client) Sent key event: cosmic:skill_menu (release: false)
```

### 💡 常见问题 - 玩家版

**Q: 我按了按键但没反应?**  
A: 检查以下几点:
1. 是否在 GUI 界面?按键在界面中不会触发
2. 使用 `/keybind info <按键ID>` 确认是否需要修饰键
3. 查看日志确认按键事件是否发送成功
4. 向服务器管理员确认该功能是否可用

**Q: 如何取消某个按键?**  
A: 玩家无法取消按键,但可以:
1. 在 Minecraft 控制设置中重新绑定冲突的键
2. 联系服务器管理员禁用该按键

**Q: 按键功能是什么?**  
A: 使用命令查看:
```
/keybind info <按键ID>
```
会显示该按键的描述和用途。

**Q: 可以自定义按键吗?**  
A: 按键绑定由服务器统一配置,玩家暂时无法自定义。未来版本会支持玩家端个性化配置。

### 🎯 推荐设置

为了最佳体验,建议:
1. ✅ 定期使用 `/keybind list` 查看可用按键
2. ✅ 熟悉常用按键的位置和功能
3. ✅ 遇到冲突及时调整其他模组的按键
4. ✅ 向朋友推荐安装模组,获得更好的游戏体验

---

## 🎮 使用说明 - 技术细节

### 玩家快速开始

#### 第一步: 安装模组
1. 确保你已经安装了 Fabric Loader 和 Fabric API
2. 将 `cosmiccore-client-1.0.0.jar` 放入 `.minecraft/mods` 文件夹
3. 启动 Minecraft

#### 第二步: 连接服务器
1. 启动游戏后,连接到安装了 CosmicCore 插件的服务器
2. 模组会自动与服务器握手并同步按键配置
3. 查看聊天栏或日志,确认连接成功

**成功连接的标志:**
```
[Cosmic] 已连接到服务器并同步按键配置
[Cosmic] 共加载 5 个自定义按键
```

#### 第三步: 查看可用按键

**方法 1: 使用游戏内命令** (推荐)
```
/keybind list
```
这会显示服务器上所有可用的按键绑定,包括:
- 按键ID (如 `cosmic:skill_menu`)
- 按键名称 (如 "技能菜单")
- 启用状态

**方法 2: 查看控制设置**
1. 进入游戏后按 `ESC` 打开菜单
2. 点击 "选项" → "控制"
3. 查找 "Cosmic 按键设置" 按钮 (如果有自定义按键)
4. 点击查看所有 Cosmic 按键绑定

**方法 3: 查看日志文件**
- 打开 `.minecraft/logs/latest.log`
- 搜索 `Received key configuration` 查看接收到的按键

#### 第四步: 使用按键
按键配置完成后,直接按下对应的按键即可触发功能!

**示例场景:**
- 服务器设置了 `K` 键打开技能菜单
- 你在游戏中按下 `K` 键
- 客户端自动发送按键事件到服务器
- 服务器执行对应的功能(如打开菜单)

### 高级功能

#### 修饰键组合
某些按键可能需要配合修饰键使用:
- **Shift + 按键**: 按住 Shift 再按按键
- **Ctrl + 按键**: 按住 Ctrl 再按按键  
- **Alt + 按键**: 按住 Alt 再按按键
- **组合**: 可能需要同时按住多个修饰键

**查看按键详细信息:**
```
/keybind info <按键ID>
```
例如: `/keybind info cosmic:skill_menu`

#### 按键冲突处理
如果按键与其他模组或 Minecraft 原版功能冲突:
1. 优先使用服务器定义的功能
2. 可以在客户端按键设置中重新绑定冲突的键
3. 推荐使用不常用的按键 (如 `K`, `L`, `Z`, `X`, `C`, `V`)

#### 原版按键监听
模组也支持监听 Minecraft 原版按键,如:
- `key.keyboard.w` - 前进
- `key.keyboard.space` - 跳跃
- `key.mouse.left` - 左键
- 等等...

服务器可以监听这些按键来实现特殊功能,但不会影响原版功能的正常使用。

### 查看日志

客户端日志位于 `.minecraft/logs/latest.log`:

```log
[INFO] (cosmic-client) Initializing Cosmic Client...
[INFO] (cosmic-client) Registered Cosmic network payload types
[INFO] (cosmic-client) Initializing keybind system for server connection
[DEBUG] (cosmic-client) Received key configuration: cosmic:skill_menu -> 技能菜单
[DEBUG] (cosmic-client) Received key configuration: cosmic:quick_craft -> 快速制作
[INFO] (cosmic-client) Server finished sending key configurations, loading user keybinds...
[INFO] (cosmic-client) Cosmic keybind system ready!
```

**查看按键事件日志:**
开启调试模式后可以看到每次按键:
```log
[DEBUG] (cosmic-client) Sent key event: cosmic:skill_menu (release: false)
[DEBUG] (cosmic-client) Sent key event: cosmic:skill_menu (release: true)
```

## 🔌 服务端插件配置

### 管理员命令

服务端管理员可以使用以下命令管理按键系统:

#### 基本命令
```bash
# 查看所有已注册的按键
/keybind list

# 查看按键详细信息
/keybind info <按键ID>
# 示例: /keybind info cosmic:skill_menu

# 开启/关闭调试模式
/keybind debug on
/keybind debug off

# 测试按键事件 (模拟玩家按键)
/keybind test <按键ID>
# 示例: /keybind test cosmic:skill_menu
```

#### 高级命令
```bash
# 查看推荐使用的按键列表
/keybind recommended
# 显示不会与 Minecraft 常用操作冲突的按键

# 查看按键黑名单
/keybind blacklist  
# 显示不建议占用的 Minecraft 默认按键
```

#### 命令权限
所有命令需要权限: `cosmiccore.keybind.admin`

#### 命令别名
- `/keybind` = `/kb` = `/keys`

### 开发者配置示例

```java
// 注册自定义按键
KeybindInfo info = new KeybindInfo();
info.setKeyId(NamespacedKey.fromString("cosmic:skill_menu"));
info.setDisplayName("技能菜单");
info.setCategory("游戏功能");
info.setDefaultKey(GLFW.GLFW_KEY_K); // 默认绑定 K 键
info.setModifiers(ModifierKey.SHIFT); // 需要按住 Shift
info.setDescription("打开技能选择菜单");
info.setCooldownMs(1000); // 1秒冷却
info.setEnabled(true); // 启用此按键

KeybindRegistry.register(info);

// 监听按键事件
@EventHandler
public void onKeyPress(PlayerKeybindEvent event) {
    if (event.getKeyId().equals(NamespacedKey.fromString("cosmic:skill_menu"))) {
        Player player = event.getPlayer();
        
        // 检查是否为按下事件(非释放)
        if (event.isFirstPress()) {
            // 玩家按下了技能菜单键
            openSkillMenu(player);
        }
    }
}

// 检查玩家是否安装了客户端模组
KeybindManager manager = CosmicCore.get().getKeybindManager();
if (manager.getProtocolAdapter().hasClientMod(player)) {
    // 玩家有模组,可以使用自定义按键功能
    player.sendMessage("你可以按 K 键打开技能菜单!");
} else {
    // 玩家没有模组,提供替代方案
    player.sendMessage("使用 /skills 命令打开技能菜单");
}
```

### 配置最佳实践

1. **选择合适的按键**
   - 使用 `/keybind recommended` 查看推荐按键
   - 避免使用 `/keybind blacklist` 中的按键
   - 常用推荐键: `K`, `L`, `Z`, `X`, `C`, `V`, `B`, `N`, `M`

2. **设置合理的冷却时间**
   ```java
   info.setCooldownMs(1000); // 防止玩家频繁触发
   ```

3. **使用分类组织按键**
   ```java
   info.setCategory("游戏功能");  // 功能类按键
   info.setCategory("社交");      // 社交类按键
   info.setCategory("快捷操作");  // 快捷操作按键
   ```

4. **提供清晰的描述**
   ```java
   info.setDescription("Shift+K 打开技能选择菜单,查看和使用已解锁的技能");
   ```

5. **处理没有客户端模组的玩家**
   ```java
   // 总是提供命令替代方案
   if (!manager.getProtocolAdapter().hasClientMod(player)) {
       player.sendMessage("提示: 安装 Cosmic Client 模组可使用快捷键!");
   }
   ```

## 🛠 开发指南

### 构建项目

```bash
# 克隆仓库
cd /path/to/cosmic/CosmicClient

# 构建模组
./gradlew build

# 构建产物在 build/libs/ 目录
ls build/libs/cosmiccore-client-1.0.0.jar
```

### 开发调试

```bash
# 启动开发客户端
./gradlew runClient

# 这会启动一个包含模组的 Minecraft 客户端用于测试
```

### 开发环境

- **IDE**: IntelliJ IDEA 推荐
- **Gradle**: 8.12
- **Fabric Loom**: 1.8.13
- **Language**: Java 21

```bash
# 导入到 IDE
# IntelliJ IDEA: File -> Open -> 选择 build.gradle

# 刷新依赖
./gradlew --refresh-dependencies
```

## 📡 通信协议

### 四个核心通道

1. **cosmic:greeting** - 握手通道
   - 客户端连接时发送握手消息
   - 服务端验证并准备配置

2. **cosmic:addkey** - 按键配置通道
   - 服务端下发按键配置到客户端
   - 支持自定义按键和原版按键

3. **cosmic:keybind** - 按键事件通道
   - 客户端上报按键按下/释放事件
   - 包含按键ID、状态和修饰键

4. **cosmic:load** - 配置完成通道
   - 服务端通知配置下发完成
   - 客户端开始监听按键

### 数据包格式

所有数据包使用 VarInt + UTF-8 字符串格式,与 Minecraft 原版协议兼容。

```
Packet Structure:
[VarInt: String Length] [UTF-8: String Data]
```

## 🐛 常见问题

### Q: 我只在客户端安装了模组,能连接普通服务器吗?
**A**: 完全可以! 
- 模组会自动检测服务端是否支持 Cosmic 协议
- 如果服务端没有插件,模组会自动禁用按键功能
- 不会影响你连接和游戏,只是无法使用自定义按键功能
- 查看日志会显示: `Server does not have CosmicCore plugin installed`

### Q: 服务器安装了插件,但部分玩家没有客户端模组?
**A**: 没问题!
- 没有模组的玩家可以正常连接和游戏
- 服务端只向安装了模组的玩家发送按键配置
- 不会向普通玩家发送任何协议数据包
- 服务端通过握手消息识别哪些玩家有模组

### Q: 模组无法加载?
**A**: 检查以下内容:
- Minecraft 版本是否为 1.21.4
- Fabric Loader 是否正确安装
- Fabric API 版本是否匹配
- Java 版本是否为 21+

### Q: 连接服务器后没有反应?
**A**: 这是正常的,有两种情况:
- **服务端有插件**: 应该在日志中看到 `Sent handshake to server`,按键功能会自动启用
- **服务端无插件**: 会显示 `Server does not have CosmicCore plugin installed`,这是预期行为

### Q: 按键没有响应?
**A**: 检查:
- 服务端是否正确下发了按键配置
- 按键是否有冲突(被其他模组占用)
- 是否在 GUI 界面(GUI界面不触发按键)
- 查看日志确认按键事件是否发送

### Q: 如何查看已注册的按键?
**A**: 在服务器执行命令:
```
/keybind list
```

### Q: 会影响游戏性能吗?
**A**: 几乎没有影响:
- 只在按键按下时发送轻量级数据包
- 使用异步处理,不阻塞游戏主线程
- Mixin 注入优化,性能开销极小

## 📝 更新日志

### v1.0.0 (2026-01-28)
- ✨ 初始版本发布
- ✅ Plugin Message Channel 协议实现
- ✅ 自定义按键绑定支持
- ✅ 原版按键监听
- ✅ 修饰键(Ctrl/Shift/Alt)支持
- ✅ 服务端配置同步

## 🤝 贡献

欢迎提交 Issue 和 Pull Request!

## 📄 许可证

本项目是 Cosmic 服务器生态系统的一部分。

---

**注意**: 此模组需要配合服务端 CosmicCore 插件使用,单独安装无法发挥作用。

**Cosmic Core Team** - 为 Minecraft 服务器提供完整的按键绑定解决方案
