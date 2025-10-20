#!/bin/bash

# Java类热更新Demo构建脚本

echo "=== Java类热更新Demo构建脚本 ==="

# 设置变量
PROJECT_ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
SRC_DIR="$PROJECT_ROOT/src/main/java"
TARGET_DIR="$PROJECT_ROOT/target/classes"
RESOURCES_DIR="$PROJECT_ROOT/src/main/resources"
JAR_FILE="$PROJECT_ROOT/target/demo.jar"

echo "项目根目录: $PROJECT_ROOT"
echo "源码目录: $SRC_DIR"
echo "输出目录: $TARGET_DIR"

# 创建目标目录
echo -e "\n1. 创建输出目录..."
mkdir -p "$TARGET_DIR"
mkdir -p "$TARGET_DIR/META-INF"

# 复制Manifest文件
echo -e "\n2. 复制Manifest文件..."
if [ -f "$RESOURCES_DIR/META-INF/MANIFEST.MF" ]; then
    cp "$RESOURCES_DIR/META-INF/MANIFEST.MF" "$TARGET_DIR/META-INF/"
    echo "✓ Manifest文件复制成功"
else
    echo "⚠ Manifest文件不存在，将创建默认文件"
    cat > "$TARGET_DIR/META-INF/MANIFEST.MF" << EOF
Manifest-Version: 1.0
Premain-Class: pers.kksg.demo.classloader.HotSwapAgent
Agent-Class: pers.kksg.demo.classloader.HotSwapAgent
Can-Redefine-Classes: true
Can-Retransform-Classes: true
EOF
fi

# 编译Java文件
echo -e "\n3. 编译Java文件..."
find "$SRC_DIR" -name "*.java" -type f | while read java_file; do
    echo "编译: $(basename "$java_file")"
    javac -cp "$TARGET_DIR" -d "$TARGET_DIR" "$java_file"
done

echo "✓ Java文件编译完成"

# 创建JAR文件
echo -e "\n4. 创建JAR文件..."
cd "$TARGET_DIR"
jar cfm "$JAR_FILE" META-INF/MANIFEST.MF pers/
echo "✓ JAR文件创建完成: $JAR_FILE"

# 设置执行权限
echo -e "\n5. 设置执行权限..."
chmod +x "$PROJECT_ROOT/src/main/java/pers/kksg/demo/classloader/build.sh"

# 显示编译结果
echo -e "\n=== 编译结果 ==="
echo "编译的类文件:"
find "$TARGET_DIR" -name "*.class" -type f | sort

echo -e "\n可运行的演示类:"
echo "1. ClassLoader热更新:"
echo "   java -cp $TARGET_DIR pers.kksg.demo.classloader.ClassLoaderHotSwapDemo"
echo ""
echo "2. Agent热更新:"
echo "   java -javaagent:$JAR_FILE -cp $TARGET_DIR pers.kksg.demo.classloader.AgentHotSwapDemo"
echo ""
echo "3. 对比演示:"
echo "   java -cp $TARGET_DIR pers.kksg.demo.classloader.HotSwapComparisonDemo"

echo -e "\n=== 构建完成 ==="
echo "可以使用以下命令运行演示:"
echo "cd $PROJECT_ROOT"
echo "./src/main/java/pers/kksg/demo/classloader/run_demo.sh"