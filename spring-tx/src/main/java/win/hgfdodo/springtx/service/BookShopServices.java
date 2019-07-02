package win.hgfdodo.springtx.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import win.hgfdodo.springtx.bean.Account;
import win.hgfdodo.springtx.bean.Goods;
import win.hgfdodo.springtx.exception.NotCreditAccountException;
import win.hgfdodo.springtx.mapper.AccountMapper;
import win.hgfdodo.springtx.mapper.BookMapper;

@Service
public class BookShopServices implements ShopService {
    private final static Logger log = LoggerFactory.getLogger(BookShopServices.class);

    private final BookMapper bookMapper;
    private final AccountMapper accountMapper;

    public BookShopServices(BookMapper bookMapper, AccountMapper accountMapper) {
        this.bookMapper = bookMapper;
        this.accountMapper = accountMapper;
    }

    @Transactional
    @Override
    public void purchase(Goods goods, int num, Account account) {
        log.debug("Request to purchase {} of {} by {}", num, goods, account);
        if (account.getAccountBalance() > goods.getPrice() * num) {
            accountMapper.decr(account.getId(), goods.getPrice() * num);
        } else {
            throw new NotCreditAccountException("not a credit account," +
                    " the balance of account cannot be negative!");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void storeDecr(Goods goods, int num) {
        log.debug("Request to decrement {} of {} in store", num, goods);
        bookMapper.storeDecr(goods.getId(), num);
    }
}