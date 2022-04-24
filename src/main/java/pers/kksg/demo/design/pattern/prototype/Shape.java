package pers.kksg.demo.design.pattern.prototype;

/**
 * Created with IntelliJ IDEA.
 * 形状接口
 * @Auther: lvqiang
 * @Date: 2022/03/23/18:42
 * @Description: 形状接口
 */
public abstract class Shape implements Cloneable  {

    private String id;
    protected String type;

    public abstract void draw();
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected Object clone(){
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}
