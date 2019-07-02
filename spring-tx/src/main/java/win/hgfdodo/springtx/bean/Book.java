package win.hgfdodo.springtx.bean;

import java.util.Objects;

public class Book extends Goods {

    public Book(String name, Double price) {
        super(name, price);
    }

    public Book(int id, String name, Double price) {
        super(id, name, price);
    }

    public Book(int id, String name, Double price, int num) {
        super(id, name, price, num);
    }
}
