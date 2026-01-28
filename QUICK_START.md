# Cosmic Client 快速开始 ⚡

> 3 分钟上手指南 - 适合玩家阅读

## 🚀 安装 (1 分钟)

### 需要准备
- ✅ Minecraft 1.21 - 1.21.4
- ✅ Fabric Loader (0.16.0+)
- ✅ Fabric API (对应版本)

### 安装步骤
1. 下载 `cosmiccore-client-1.0.0.jar`
2. 放入 `.minecraft/mods` 文件夹
3. 启动游戏

**就这么简单！** 🎉

---

## 🎮 使用 (2 分钟)

### 连接服务器
连接任何 Minecraft 服务器都能正常游戏！
- 有 CosmicCore 插件的服务器 → 完整功能
- 普通服务器 → 自动禁用,不影响游戏

### 查看可用按键
在游戏中输入:
```
/keybind list
```

会显示类似这样的列表:
```
===== 已注册的按键 (5) =====
- cosmic:skill_menu (技能菜单) [启用]
- cosmic:quick_craft (快速制作) [启用]  
- cosmic:pet_menu (宠物菜单) [启用]
```

### 查看按键详情
想知道按键怎么用:
```
/keybind info cosmic:skill_menu
```

会显示:
- 📛 名称: 技能菜单
- 🔑 默认键: K
- 📝 说明: 打开技能选择菜单
- ⚡ 修饰键: 需要按 Shift
- ⏱️ 冷却: 1000ms

### 使用按键
按下对应的键就行了！例如:
- 按 `K` → 打开技能菜单
- `Shift + K` → 快速传送
- `Ctrl + M` → 打开地图

---

## ⌨️ 按键使用技巧

### 什么时候能用?
✅ 在游戏世界中正常移动时  
✅ 第一人称/第三人称视角  
✅ 飞行模式

### 什么时候不能用?
❌ 打开背包/箱子时  
❌ 在聊天框输入时  
❌ 打开任何 GUI 菜单时

### 修饰键是什么?
就是组合键:
- `Shift + K` = 按住 Shift 再按 K
- `Ctrl + M` = 按住 Ctrl 再按 M
- `Alt + V` = 按住 Alt 再按 V

---

## 🔍 常见问题

### Q: 按键没反应?
**A:** 检查这些:
1. 是不是在 GUI 界面?退出界面再试
2. 需不需要按修饰键? 用 `/keybind info` 查看
3. 服务器有没有启用这个功能?

### Q: 和其他键冲突了?
**A:** 
- 在 Minecraft 设置里重新绑定冲突的键
- 或者联系服务器管理员换个按键

### Q: 没看到按键列表?
**A:** 可能是:
- 服务器没有安装 CosmicCore 插件
- 服务器没有配置自定义按键
- 使用 `/keybind list` 查看是否显示内容

### Q: 能自己改按键吗?
**A:** 目前按键由服务器统一配置,玩家暂时不能自定义。未来版本会支持个性化配置。

---

## 📊 实用命令

| 命令 | 功能 | 示例 |
|------|------|------|
| `/keybind list` | 查看所有按键 | - |
| `/keybind info <ID>` | 查看按键详情 | `/keybind info cosmic:skill_menu` |
| `/keybind recommended` | 查看推荐按键列表 | - |
| `/keybind blacklist` | 查看不建议用的按键 | - |

---

## 💡 推荐做法

1. ✅ 连接服务器后先用 `/keybind list` 看看有什么按键
2. ✅ 用 `/keybind info` 了解按键功能和用法  
3. ✅ 熟悉常用按键的位置
4. ✅ 遇到问题先查看日志 `.minecraft/logs/latest.log`
5. ✅ 向朋友推荐这个模组,一起获得更好的体验!

---

## 🔗 更多信息

- 📖 完整文档: [README.md](README.md)
- 🔧 兼容性说明: [COMPATIBILITY.md](COMPATIBILITY.md)
- 🐛 遇到问题? 查看 [常见问题](README.md#-常见问题)

---

**安装后可以连接任何服务器,不用担心冲突或错误！** ✨

**Cosmic Core Team** - 为 Minecraft 带来更好的游戏体验
