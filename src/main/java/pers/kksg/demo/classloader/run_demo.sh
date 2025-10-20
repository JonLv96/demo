#!/bin/bash

# Java类热更新Demo运行脚本

echo "=== Java类热更新Demo运行脚本 ==="

# 设置变量
PROJECT_ROOT="$(cd "$(dirname "$0")/../.." && pwd)"
TARGET_DIR="$PROJECT_ROOT/target/classes"
JAR_FILE="$PROJECT_ROOT/target/demo.jar"

# 检查编译状态
if [ ! -d "$TARGET_DIR" ] || [ -z "$(ls -A "$TARGET_DIR" 2>/dev/null)" ]; then
    echo "⚠ 项目未编译，正在执行编译..."
    "$PROJECT_ROOT/src/main/java/pers/kksg/demo/classloader/build.sh"
fi

# 检查JAR文件
if [ ! -f "$JAR_FILE" ]; then
    echo "⚠ JAR文件不存在，正在创建..."
    cd "$TARGET_DIR"
    jar cfm "$JAR_FILE" META-INF/MANIFEST.MF pers/
fi

# 显示菜单
show_menu() {
    echo -e "\n请选择要运行的演示:"
    echo "1. ClassLoader热更新演示"
    echo "2. Instrumentation Agent热更新演示"
    echo "3. OSGi风格热更新演示"
    echo "4. 字节码操作热更新演示"
    echo "5. 全部方式对比演示"
    echo "6. 重新编译项目"
    echo "7. 查看项目信息"
    echo "0. 退出"
    echo -n "请输入选择: "
}

# 运行ClassLoader演示
run_classloader_demo() {
    echo -e "\n=== 运行ClassLoader热更新演示 ==="
    java -cp "$TARGET_DIR" pers.kksg.demo.classloader.ClassLoaderHotSwapDemo
}

# 运行Agent演示
run_agent_demo() {
    echo -e "\n=== 运行Instrumentation Agent热更新演示 ==="
    echo "使用Java Agent启动..."
    java -javaagent:"$JAR_FILE" -cp "$TARGET_DIR" pers.kksg.demo.classloader.AgentHotSwapDemo
}

# 运行对比演示
run_comparison_demo() {
    echo -e "\n=== 运行全部方式对比演示 ==="
    java -cp "$TARGET_DIR" pers.kksg.demo.classloader.HotSwapComparisonDemo
}

# 编译项目
build_project() {
    echo -e "\n=== 重新编译项目 ==="
    "$PROJECT_ROOT/src/main/java/pers/kksg/demo/classloader/build.sh"
}

# 显示项目信息
show_project_info() {
    echo -e "\n=== 项目信息 ==="
    echo "项目路径: $PROJECT_ROOT"
    echo "编译输出: $TARGET_DIR"
    echo "JAR文件: $JAR_FILE"
    echo ""
    echo "已编译的类文件:"
    find "$TARGET_DIR" -name "*.class" -type f | sort | head -10
    if [ $(find "$TARGET_DIR" -name "*.class" -type f | wc -l) -gt 10 ]; then
        echo "... 还有更多类文件"
    fi
    echo ""
    echo "Java版本:"
    java -version
    echo ""
    echo "类路径:"
    echo "$TARGET_DIR"
}

# 创建测试脚本
create_test_script() {
    local test_script="$PROJECT_ROOT/test_hot_swap.sh"
    cat > "$test_script" << 'EOF'
#!/bin/bash

# 热更新测试脚本
echo "=== 热更新功能测试 ==="

# 启动一个长期运行的服务
echo "1. 启动热更新监控服务..."
java -cp target/classes pers.kksg.demo.classloader.AgentHotSwapDemo &
SERVICE_PID=$!

echo "服务已启动，PID: $SERVICE_PID"
echo "等待服务初始化..."
sleep 3

echo -e "\n2. 现在可以修改 src/main/java/pers/kksg/demo/classloader/HotSwapDemoService.java"
echo "   然后重新编译: mvn compile"
echo "   观察控制台输出，应该能看到热更新日志"

echo -e "\n3. 按 Enter 键停止服务..."
read

echo "停止服务..."
kill $SERVICE_PID
echo "测试完成"
EOF

    chmod +x "$test_script"
    echo "✓ 测试脚本已创建: $test_script"
}

# 主循环
while true; do
    show_menu
    read choice

    case $choice in
        1)
            run_classloader_demo
            ;;
        2)
            run_agent_demo
            ;;
        3)
            run_comparison_demo
            ;;
        4)
            run_comparison_demo
            ;;
        5)
            run_comparison_demo
            ;;
        6)
            build_project
            ;;
        7)
            show_project_info
            ;;
        8)
            create_test_script
            ;;
        0)
            echo "退出演示"
            exit 0
            ;;
        *)
            echo "无效选择，请重新输入"
            ;;
    esac

    echo -e "\n按 Enter 继续..."
    read
done