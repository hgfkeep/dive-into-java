package win.hgfdodo.springtx.bean;

import java.util.Objects;

public class Goods {

    private int id;
    private String name;
    private Double price;
    private int num;

    public Goods(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public Goods(int id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Goods(int id, String name, Double price, int num) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.num = num;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods goods = (Goods) o;
        return id == goods.id &&
                num == goods.num &&
                Objects.equals(name, goods.name) &&
                Objects.equals(price, goods.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, num);
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                "price='" + price + '\'' +
                "num='" + num + '\'' +
                '}';
    }
}
