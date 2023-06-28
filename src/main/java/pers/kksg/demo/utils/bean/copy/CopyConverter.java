package pers.kksg.demo.utils.bean.copy;

/**
 * @project demo
 * @description CopyConverter
 * @author lvqiang
 * @date 2023/6/25 17:21:10
 * @version 1.0
 */
@FunctionalInterface
public interface CopyConverter {
    /**
     * @param sourceFiled       源属性
     * @param targetFiledClass  目标属性的class
     * @param targetFiledSetter 目标属性的setter方法的方法名
     * @param targetFiled       目标属性
     * @return 设置目标的属性
     */
    Object convert(Object sourceFiled, Class<?> targetFiledClass, Object targetFiledSetter, Object targetFiled);
}
