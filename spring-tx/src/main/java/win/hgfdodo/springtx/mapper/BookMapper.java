package win.hgfdodo.springtx.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import win.hgfdodo.springtx.bean.Book;

import java.util.List;

@Mapper
public interface BookMapper {
    @Select("select * from book where id = #{id}")
    public Book get(@Param("id") int id);

    @Update("update book set num = num-#{num} where id = #{id}")
    public void storeDecr(@Param("id") int id, @Param("num") int num);

    @Select("select id, name, price, num from book")
    List<Book> getAll();
}
