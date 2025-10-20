package pers.kksg.demo.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * LiteFlow风格的热加载实现演示
 *
 * LiteFlow的热加载核心机制：
 * 1. 配置文件监听 - 监听流程定义文件变化
 * 2. 规则解析缓存 - 解析并缓存流程规则
 * 3. 节点动态注册 - 动态注册和更新节点组件
 * 4. 流程重建 - 重新构建执行链
 * 5. Spring集成 - 与Spring容器深度集成
 */
public class LiteFlowStyleHotSwap {

    // 流程定义缓存
    private final Map<String, FlowDefinition> flowCache = new ConcurrentHashMap<>();

    // 节点组件缓存
    private final Map<String, NodeComponent> nodeCache = new ConcurrentHashMap<>();

    // 配置文件监听
    private WatchService watchService;
    private Thread watchThread;
    private volatile boolean isWatching = false;

    // 配置文件路径
    private final String configPath;

    // 版本控制
    private final AtomicLong versionCounter = new AtomicLong(0);

    /**
     * 流程定义
     */
    public static class FlowDefinition {
        private final String id;
        private final String chain;
        private final long version;
        private final List<NodeDefinition> nodes;

        public FlowDefinition(String id, String chain, long version, List<NodeDefinition> nodes) {
            this.id = id;
            this.chain = chain;
            this.version = version;
            this.nodes = nodes;
        }

        public String getId() { return id; }
        public String getChain() { return chain; }
        public long getVersion() { return version; }
        public List<NodeDefinition> getNodes() { return nodes; }
    }

    /**
     * 节点定义
     */
    public static class NodeDefinition {
        private final String id;
        private final String className;
        private final Map<String, Object> properties;

        public NodeDefinition(String id, String className) {
            this.id = id;
            this.className = className;
            this.properties = new HashMap<>();
        }

        public String getId() { return id; }
        public String getClassName() { return className; }
        public Map<String, Object> getProperties() { return properties; }
    }

    /**
     * 节点组件接口
     */
    public interface NodeComponent {
        void process();
        String getName();
        void setProperties(Map<String, Object> properties);
    }

    /**
     * 模拟LiteFlow的节点组件
     */
    public static abstract class AbstractNodeComponent implements NodeComponent {
        protected Map<String, Object> properties = new HashMap<>();

        @Override
        public void setProperties(Map<String, Object> properties) {
            this.properties = properties;
        }

        protected Object getProperty(String key) {
            return properties.get(key);
        }

        protected String getProperty(String key, String defaultValue) {
            Object value = properties.get(key);
            return value != null ? value.toString() : defaultValue;
        }
    }

    public LiteFlowStyleHotSwap(String configPath) {
        this.configPath = configPath;
    }

    /**
     * 启动热加载监控 - 核心方法1：配置文件监听
     */
    public void startHotSwap() throws Exception {
        if (isWatching) {
            System.out.println("热加载监控已在运行");
            return;
        }

        System.out.println("启动LiteFlow风格热加载监控...");
        System.out.println("配置文件路径: " + configPath);

        // 初始化WatchService
        watchService = FileSystems.getDefault().newWatchService();
        Path configDir = Paths.get(configPath).getParent();
        configDir.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

        // 加载初始配置
        loadConfiguration();

        // 启动监听线程
        isWatching = true;
        watchThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isWatching) {
                        WatchKey key = watchService.take();

                        for (WatchEvent<?> event : key.pollEvents()) {
                            if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                                String fileName = event.context().toString();
                                if (fileName.equals(Paths.get(configPath).getFileName().toString())) {
                                    System.out.println("检测到配置文件修改: " + fileName);
                                    reloadConfiguration();
                                }
                            }
                        }

                        key.reset();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    System.err.println("配置监听异常: " + e.getMessage());
                }
            }
        });

        watchThread.setDaemon(true);
        watchThread.start();

        System.out.println("✓ LiteFlow热加载监控启动成功");
    }

    /**
     * 加载配置文件 - 核心方法2：规则解析缓存
     */
    private void loadConfiguration() throws Exception {
        File configFile = new File(configPath);
        if (!configFile.exists()) {
            System.out.println("配置文件不存在，创建默认配置...");
            createDefaultConfiguration();
            // 创建默认配置后重新加载
            configFile = new File(configPath);
        }

        System.out.println("加载流程配置文件...");

        // 模拟解析XML配置文件
        String configContent = readFileContent(configFile);
        parseAndCacheFlow(configContent);

        System.out.println("✓ 流程配置加载完成，当前流程数: " + flowCache.size());
    }

    /**
     * 重新加载配置 - 核心方法3：流程重建
     */
    private void reloadConfiguration() {
        try {
            System.out.println("开始重新加载流程配置...");

            // 清除旧缓存
            long oldVersion = versionCounter.get();

            // 重新解析配置
            loadConfiguration();

            // 重新构建执行链
            rebuildExecutionChains();

            System.out.println("✓ 流程配置热更新完成");
            System.out.println("  旧版本: " + oldVersion + ", 新版本: " + versionCounter.get());

            // 验证热更新效果
            validateHotSwap();

        } catch (Exception e) {
            System.err.println("热加载失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 解析并缓存流程定义
     */
    private void parseAndCacheFlow(String configContent) {
        // 模拟LiteFlow的XML解析逻辑
        // 这里简化处理，实际LiteFlow使用复杂的DOM解析

        long currentVersion = versionCounter.incrementAndGet();

        // 示例：解析一个简单的流程链
        List<NodeDefinition> nodes = new ArrayList<>();
        nodes.add(new NodeDefinition("a", "pers.kksg.demo.classloader.LiteFlowStyleHotSwap$NodeA"));
        nodes.add(new NodeDefinition("b", "pers.kksg.demo.classloader.LiteFlowStyleHotSwap$NodeB"));
        nodes.add(new NodeDefinition("c", "pers.kksg.demo.classloader.LiteFlowStyleHotSwap$NodeC"));

        FlowDefinition flowDef = new FlowDefinition(
            "demoChain",
            "THEN(a, WHEN(b, c))",
            currentVersion,
            nodes
        );

        // 缓存流程定义
        flowCache.put("demoChain", flowDef);

        // 缓存并注册节点组件
        for (NodeDefinition nodeDef : nodes) {
            registerNodeComponent(nodeDef);
        }
    }

    /**
     * 动态注册节点组件 - 核心方法4：节点动态注册
     */
    private void registerNodeComponent(NodeDefinition nodeDef) {
        try {
            // 使用自定义类加载器加载节点类
            ClassLoader nodeClassLoader = createNodeClassLoader();
            Class<?> nodeClass = nodeClassLoader.loadClass(nodeDef.getClassName());

            // 创建节点实例
            Object nodeInstance = nodeClass.newInstance();

            if (nodeInstance instanceof NodeComponent) {
                NodeComponent nodeComponent = (NodeComponent) nodeInstance;
                nodeComponent.setProperties(nodeDef.getProperties());

                // 缓存节点组件
                nodeCache.put(nodeDef.getId(), nodeComponent);

                System.out.println("  ✓ 注册节点组件: " + nodeDef.getId() +
                                 " -> " + nodeDef.getClassName());
            }

        } catch (Exception e) {
            System.err.println("注册节点组件失败: " + nodeDef.getId() + ", " + e.getMessage());
        }
    }

    /**
     * 创建节点类加载器 - 类似LiteFlow的NodeClassLoader
     */
    private ClassLoader createNodeClassLoader() {
        String classPath = System.getProperty("user.dir") + "/target/classes";
        return new NodeClassLoader(classPath, getClass().getClassLoader());
    }

    /**
     * 重新构建执行链
     */
    private void rebuildExecutionChains() {
        System.out.println("重新构建执行链...");

        // 这里模拟LiteFlow的执行链构建逻辑
        // 实际LiteFlow会根据表达式解析出执行顺序

        for (FlowDefinition flowDef : flowCache.values()) {
            System.out.println("  构建流程: " + flowDef.getId());
            System.out.println("  执行链: " + flowDef.getChain());

            // 验证节点完整性
            for (NodeDefinition nodeDef : flowDef.getNodes()) {
                if (!nodeCache.containsKey(nodeDef.getId())) {
                    System.err.println("  ⚠ 节点组件缺失: " + nodeDef.getId());
                }
            }
        }
    }

    /**
     * 验证热更新效果
     */
    private void validateHotSwap() {
        System.out.println("验证热更新效果...");

        // 执行流程以验证热更新是否生效
        try {
            executeFlow("demoChain");
        } catch (Exception e) {
            System.err.println("流程执行验证失败: " + e.getMessage());
        }
    }

    /**
     * 执行流程
     */
    public String executeFlow(String chainId) throws Exception {
        FlowDefinition flowDef = flowCache.get(chainId);
        if (flowDef == null) {
            throw new RuntimeException("流程不存在: " + chainId);
        }

        System.out.println("执行流程: " + chainId + " (版本: " + flowDef.getVersion() + ")");

        // 模拟执行链: THEN(a, WHEN(b, c))
        StringBuilder result = new StringBuilder();

        // 执行节点a
        NodeComponent nodeA = nodeCache.get("a");
        if (nodeA != null) {
            nodeA.process();
            result.append("a->");
        }

        // 并行执行b和c (简化为串行)
        NodeComponent nodeB = nodeCache.get("b");
        if (nodeB != null) {
            nodeB.process();
            result.append("b->");
        }

        NodeComponent nodeC = nodeCache.get("c");
        if (nodeC != null) {
            nodeC.process();
            result.append("c");
        }

        System.out.println("流程执行结果: " + result.toString());
        return result.toString();
    }

    /**
     * 创建默认配置文件
     */
    private void createDefaultConfiguration() throws IOException {
        String defaultConfig = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                              "<flow>\n" +
                              "    <chain name=\"demoChain\">\n" +
                              "        <THEN value=\"a, WHEN(b, c)\"/>\n" +
                              "    </chain>\n" +
                              "</flow>";

        File configFile = new File(configPath);
        configFile.getParentFile().mkdirs();

        try (java.io.FileOutputStream fos = new java.io.FileOutputStream(configFile)) {
            fos.write(defaultConfig.getBytes("UTF-8"));
        }

        System.out.println("创建默认配置文件: " + configPath);
    }

    /**
     * 读取文件内容
     */
    private String readFileContent(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        try {
            java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            return bos.toString("UTF-8");
        } finally {
            fis.close();
        }
    }

    /**
     * 停止热加载监控
     */
    public void stopHotSwap() {
        isWatching = false;
        if (watchThread != null) {
            watchThread.interrupt();
        }
        System.out.println("LiteFlow热加载监控已停止");
    }

    /**
     * 获取当前状态信息
     */
    public void printStatus() {
        System.out.println("=== LiteFlow热加载状态 ===");
        System.out.println("监控状态: " + (isWatching ? "运行中" : "已停止"));
        System.out.println("配置文件: " + configPath);
        System.out.println("流程数量: " + flowCache.size());
        System.out.println("节点数量: " + nodeCache.size());
        System.out.println("当前版本: " + versionCounter.get());

        System.out.println("\n流程列表:");
        for (FlowDefinition flowDef : flowCache.values()) {
            System.out.println("  - " + flowDef.getId() + " (v" + flowDef.getVersion() + "): " + flowDef.getChain());
        }

        System.out.println("\n节点列表:");
        for (Map.Entry<String, NodeComponent> entry : nodeCache.entrySet()) {
            System.out.println("  - " + entry.getKey() + ": " + entry.getValue().getClass().getSimpleName());
        }
    }

    // 示例节点组件实现
    public static class NodeA extends AbstractNodeComponent {
        @Override
        public void process() {
            System.out.println("  [NodeA] 处理逻辑 - 版本: " + System.currentTimeMillis());
        }

        @Override
        public String getName() {
            return "NodeA";
        }
    }

    public static class NodeB extends AbstractNodeComponent {
        @Override
        public void process() {
            System.out.println("  [NodeB] 处理逻辑 - 版本: " + System.currentTimeMillis());
        }

        @Override
        public String getName() {
            return "NodeB";
        }
    }

    public static class NodeC extends AbstractNodeComponent {
        @Override
        public void process() {
            System.out.println("  [NodeC] 处理逻辑 - 版本: " + System.currentTimeMillis());
        }

        @Override
        public String getName() {
            return "NodeC";
        }
    }

    /**
     * LiteFlow风格的自定义类加载器
     */
    private static class NodeClassLoader extends ClassLoader {
        private final String classPath;

        public NodeClassLoader(String classPath, ClassLoader parent) {
            super(parent);
            this.classPath = classPath;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                String classFile = name.replace('.', '/') + ".class";
                String fullPath = classPath + "/" + classFile;

                File file = new File(fullPath);
                if (!file.exists()) {
                    return super.findClass(name);
                }

                FileInputStream fis = new FileInputStream(file);
                try {
                    java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        bos.write(buffer, 0, bytesRead);
                    }
                    byte[] classData = bos.toByteArray();
                    return defineClass(name, classData, 0, classData.length);
                } finally {
                    fis.close();
                }
            } catch (IOException e) {
                throw new ClassNotFoundException("Failed to load class: " + name, e);
            }
        }
    }
}