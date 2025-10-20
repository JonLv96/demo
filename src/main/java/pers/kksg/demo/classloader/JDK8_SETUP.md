# JDK8环境下的Java热更新Demo使用指南

## 📋 前置条件

确保你的环境满足以下要求：
- **JDK 1.8**（推荐1.8.0_202或更高版本）
- 操作系统：Windows、macOS或Linux
- 基本的命令行操作能力

## 🔧 环境验证

### 1. 验证Java版本

```bash
java -version
```

预期输出应该包含类似：
```
java version "1.8.0_xxx"
Java(TM) SE Runtime Environment (build 1.8.0_xxx)
Java HotSpot(TM) 64-Bit Server VM (build 25.xxx, mixed mode)
```

### 2. 验证编译器

```bash
javac -version
```

应该输出：
```
javac 1.8.xxxx
```

## 🚀 快速开始

### 1. 下载并编译

```bash
# 进入项目目录
cd /path/to/your/project

# 使用提供的构建脚本
./src/main/java/pers/kksg/demo/classloader/build.sh

# 或手动编译
mkdir -p target/classes
javac -d target/classes src/main/java/pers/kksg/demo/classloader/*.java
```

### 2. 运行兼容性测试

```bash
java -cp target/classes pers.kksg.demo.classloader.JDK8CompatibilityTest
```

成功运行的输出应该包含：
```
=== JDK8兼容性测试 ===
Java版本: 1.8.0_xxx
=== 所有JDK8兼容性测试通过！ ===
```

### 3. 运行演示程序

```bash
# 运行交互式对比演示（推荐）
java -cp target/classes pers.kksg.demo.classloader.HotSwapComparisonDemo

# 或使用便捷脚本
./src/main/java/pers/kksg/demo/classloader/run_demo.sh
```

## 📦 各个演示程序说明

### 1. ClassLoader热更新演示

```bash
java -cp target/classes pers.kksg.demo.classloader.ClassLoaderHotSwapDemo
```

**特点**：
- 使用自定义类加载器
- 打破双亲委派机制
- 通过创建新的类加载器实例实现热更新

### 2. Instrumentation Agent演示

```bash
# 首先需要创建JAR包
cd target/classes
jar cfm ../demo.jar META-INF/MANIFEST.MF pers/
cd ..

# 使用Agent启动
java -javaagent:target/demo.jar -cp target/classes pers.kksg.demo.classloader.AgentHotSwapDemo
```

**注意**：Agent方式需要在MANIFEST.MF中正确配置Agent类。

### 3. OSGi风格模块化演示

```bash
java -cp target/classes pers.kksg.demo.classloader.HotSwapComparisonDemo
# 选择选项3
```

### 4. 字节码操作演示

```bash
java -cp target/classes pers.kksg.demo.classloader.HotSwapComparisonDemo
# 选择选项4
```

## 🔍 JDK8特定注意事项

### 1. 编译器设置

如果使用IDE，请确保：
- 项目JDK版本设置为1.8
- 编译器合规级别设置为1.8
- 不使用任何Java 9+的特性

### 2. 常见问题解决

**问题1：编译错误 "lambda expressions are not supported in -source 1.8"**
- 解决：代码已将所有Lambda表达式替换为匿名内部类

**问题2：找不到符号 "java.util.function.Function"**
- 解决：代码已使用自定义的函数式接口

**问题3：NoSuchElementException: No line found**
- 解决：这是Scanner在非交互环境下的正常现象，使用JDK8CompatibilityTest进行测试

### 3. 内存设置

对于Agent热更新演示，建议增加内存：

```bash
java -Xmx512m -javaagent:target/demo.jar -cp target/classes pers.kksg.demo.classloader.AgentHotSwapDemo
```

## 🧪 测试热更新功能

### 手动测试步骤

1. **启动监控程序**
   ```bash
   java -javaagent:target/demo.jar -cp target/classes pers.kksg.demo.classloader.AgentHotSwapDemo
   ```

2. **修改源代码**
   编辑 `src/main/java/pers/kksg/demo/classloader/HotSwapDemoService.java`
   例如修改version字段或某个方法的实现

3. **重新编译**
   ```bash
   javac -d target/classes src/main/java/pers/kksg/demo/classloader/HotSwapDemoService.java
   ```

4. **观察热更新效果**
   控制台应该显示检测到类文件修改并执行热更新的日志

## 📚 学习路径建议

1. **初学者**：先运行JDK8CompatibilityTest，然后尝试ClassLoader演示
2. **进阶学习**：研究HotSwapAgent的实现，理解Instrumentation API
3. **高级应用**：尝试OSGi风格的模块化热更新
4. **深度研究**：了解字节码操作和动态代理技术

## 🆘 故障排除

### 常见错误及解决方案

1. **ClassNotFoundException**
   - 确保classpath设置正确
   - 检查target/classes目录下是否存在对应的class文件

2. **NoClassDefFoundError**
   - 重新编译整个项目
   - 清理target目录后重新构建

3. **Agent初始化失败**
   - 检查MANIFEST.MF文件是否正确
   - 确保使用`-javaagent`参数而不是`-classpath`

4. **权限错误**
   - 确保对项目目录有读写权限
   - 在某些系统上可能需要管理员权限

### 获取帮助

如果遇到问题，可以：
1. 检查Java版本是否为1.8
2. 运行JDK8CompatibilityTest确认基本功能
3. 查看控制台输出的错误信息
4. 参考README.md中的详细说明

## 🎉 成功验证

当你看到以下输出时，说明JDK8环境下的热更新Demo已经成功运行：

```
=== JDK8兼容性测试 ===
Java版本: 1.8.0_xxx
1. 测试ClassLoader热更新...
✓ ClassLoader热更新测试通过
2. 测试基本类加载功能...
✓ 基本类加载功能测试通过
3. 测试Agent相关功能...
✓ Agent功能测试通过
4. 测试字节码操作功能...
✓ 字节码操作功能测试通过
5. 测试OSGi风格模块化...
✓ OSGi风格测试通过
=== 所有JDK8兼容性测试通过！ ===
```

恭喜！你已经在JDK8环境下成功运行了Java类热更新Demo！