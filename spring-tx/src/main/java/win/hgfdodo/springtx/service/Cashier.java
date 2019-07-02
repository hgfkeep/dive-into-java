package win.hgfdodo.springtx.service;

import win.hgfdodo.springtx.bean.Account;
import win.hgfdodo.springtx.bean.Goods;

import java.util.Map;

public interface Cashier {
    public void checkout(Map<? extends Goods, Integer> goods, Account account);
}
