package win.hgfdodo.springtx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import win.hgfdodo.springtx.bean.Account;
import win.hgfdodo.springtx.bean.Book;
import win.hgfdodo.springtx.mapper.AccountMapper;
import win.hgfdodo.springtx.mapper.BookMapper;
import win.hgfdodo.springtx.service.Cashier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication

public class SpringTxApplication implements CommandLineRunner {

    @Autowired
    Cashier cashier;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    AccountMapper accountMapper;


    public static void main(String[] args) {
        SpringApplication.run(SpringTxApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Account account = accountMapper.get(1);
        List<Book> books = bookMapper.getAll();
        assert books != null && books.size() == 2;
        Map<Book, Integer> shoppingList = new HashMap<>();

//        //异常情况1：购买商品a 20件，客户账户有足够的钱付款，可是库存不足20件，会造成买单失败，事务回滚。既不会扣账户钱，也不会减库存。
//        shoppingList.put(books.get(0), 20);

//        //异常情况2：购买商品b 2件，库存足够，但是账户余额不足，买单失败，事务回滚。既不会扣账户钱，也不会减库存。
//        shoppingList.put(books.get(1), 2);

//        //正常情况：购买商品a 2件，库存足够，账户余额足够，买单成功，事务提交。扣账户钱，减库存。
//        shoppingList.put(books.get(0), 2);

//        shoppingList.put(books.get(0), 11);
        shoppingList.put(books.get(1), 3);

        cashier.checkout(shoppingList, account);
    }
}
