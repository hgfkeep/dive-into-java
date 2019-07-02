package win.hgfdodo.springtx.service;

import win.hgfdodo.springtx.bean.Account;
import win.hgfdodo.springtx.bean.Goods;

public interface ShopService {
    public void purchase(Goods goods, int num, Account account);

    public void storeDecr(Goods goods, int num);
}
