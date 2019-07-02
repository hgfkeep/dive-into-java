package win.hgfdodo.springtx.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import win.hgfdodo.springtx.bean.Account;

@Mapper
public interface AccountMapper {
    @Select("select id,name,account_balance as accountBalance from account where id=#{id}")
    public Account get(@Param("id") int id);

    @Update("update account set account_balance = account_balance-#{money} where id = #{id}")
    public void decr(@Param("id") int id, @Param("money") double money);
}
