package pojo;

import java.sql.Blob;

/**
 * @ClassName customer
 * @Description TODO
 * @Author xpower
 * @Date 2022/9/27 10:37
 * @Version 1.0
 */
public class customer {
    int id;
    String name;
    int age;
    Blob img;

    public customer() {

    }

    @Override
    public String toString() {
        return "customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", img=" + img +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Blob getBlob() {
        return img;
    }

    public void setBlob(Blob blob) {
        this.img = blob;
    }
}
