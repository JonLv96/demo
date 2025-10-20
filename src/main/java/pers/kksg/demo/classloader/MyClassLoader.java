package pers.kksg.demo.classloader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @project: demo
 * @description: 自定义类加载器
 * @author: lvqiang
 * @create: 2025-10-13 16:56
 **/
public class MyClassLoader extends ClassLoader {

    private String classPath; // 自定义类加载器搜索类的路径

    public MyClassLoader(String classPath) {
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // 1. 根据类名构造类文件的路径 (e.g., "com.example.Foo" -> "/path/to/classes/com/example/Foo.class")
        String fileName = name.replace('.', '/') + ".class";
        File classFile = new File(classPath, fileName);

        try {
            // 2. 读取类文件的字节码
            byte[] classBytes = Files.readAllBytes(classFile.toPath());
            // 3. 调用defineClass将字节数组转换为Class对象
            return defineClass(name, classBytes, 0, classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Could not load class: " + name, e);
        }
    }
}