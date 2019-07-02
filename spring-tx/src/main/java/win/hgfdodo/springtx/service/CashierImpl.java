package win.hgfdodo.springtx.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import win.hgfdodo.springtx.bean.Account;
import win.hgfdodo.springtx.bean.Goods;

import java.util.List;
import java.util.Map;

@Service
public class CashierImpl implements Cashier {
    private final static Logger log = LoggerFactory.getLogger(CashierImpl.class);

    private final ShopService shopService;

    public CashierImpl(ShopService shopService) {
        this.shopService = shopService;
    }

    @Transactional
    @Override
    public void checkout(Map<? extends Goods, Integer> goods, Account account) {
        log.debug("Request to checkout {} by {}", goods, account);
        for (Goods one : goods.keySet()) {
            shopService.storeDecr(one, goods.get(one));
            shopService.purchase(one, goods.get(one), account);
        }
    }
}
