package Douban.BookRecive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DbConn
{
    public static final String url = "jdbc:mysql://120.24.187.99:3306/kinbook?useUnicode=true&characterEncoding=utf8";

    public static final String name = "com.mysql.jdbc.Driver";

    public static final String user = "root";

    public static final String password = "mysql";

    public Connection conn = null;

    public PreparedStatement pst = null;

    //判断book表是否存在，如果存在则删除
    public static final String init = "drop table if EXISTS book;";

    //创建book表
    public static final String book =
            "CREATE TABLE `book`( `max` float DEFAULT NULL,  `numRaters` float DEFAULT NULL,  `average` float DEFAULT NULL,  `min` float DEFAULT NULL,  `subtitle` varchar(255) DEFAULT NULL,  `author` varchar(255) DEFAULT NULL,  `tags` mediumtext,  `origin_title` varchar(255) DEFAULT NULL,  `binding` varchar(255) DEFAULT NULL,  `translator` varchar(255) DEFAULT NULL,  `catalog` varchar(255) DEFAULT NULL,  `pages` varchar(255) DEFAULT NULL,  `images` varchar(255) DEFAULT NULL,  `alt` varchar(255) DEFAULT NULL,  `id` int(11) NOT NULL,  `publisher` varchar(255) DEFAULT NULL,  `isbn10` varchar(255) DEFAULT NULL,  `isbn13` varchar(255) DEFAULT NULL,  `title` varchar(255) DEFAULT NULL,  `url` varchar(255) DEFAULT NULL,  `alt_title` varchar(255) DEFAULT NULL,  `author_intro` varchar(255) DEFAULT NULL,  `summary` varchar(255) DEFAULT NULL,  `price` float DEFAULT NULL,  `ebook` varchar(255) DEFAULT NULL,  PRIMARY KEY (`id`))";

    /**
     * 创建连接
     *
     * @Title: DdBuilder
     * @Description: DdBuilder
     * @author: yangshuai
     * @return Connection
     * @throws
     */
    public Connection DdBuilder()
    {
        try
        {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 执行sql
     *
     * @Title: excute
     * @Description: excute
     * @author: yangshuai
     * @param sql void
     * @throws
     */
    public void excute(final String sql)
    {
        try
        {
            if (this.conn != null)
            {
                pst = this.conn.prepareStatement(sql);//准备执行语句
                pst.execute(sql);
            }
            else
            {
                this.DdBuilder();
                excute(sql);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 断开连接
     *
     * @Title: close
     * @Description: close
     * @author: yangshuai void
     * @throws
     */
    public void close()
    {
        try
        {
            this.conn.close();
            this.pst.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 存储数据
     *
     * @Title: store
     * @Description: store
     * @author: yangshuai void
     * @throws
     */
    public void store(final List<Map<Object, Object>> book)
    {
        Long base = System.currentTimeMillis();
        this.DdBuilder();
        String dot = ",";
        for (Map<Object, Object> map : book)
        {
            String sql = "INSERT INTO book VALUES (";
            Map<Object, Object> rating = (Map<Object, Object>) map.get("rating");
            Map<Object, Object> images = (Map<Object, Object>) map.get("images");

            sql +=
                    rating.get("max").toString() + dot + rating.get("numRaters") + dot + rating.get("average") + dot
                            + rating.get("min") + dot + map.get("subtitle") + dot + map.get("author") + dot
                            + map.get("tags") + dot + map.get("origin_title") + dot + map.get("binding") + dot
                            + map.get("translator") + dot + map.get("catalog") + dot + map.get("pages") + dot
                            + images.get("small") + dot + images.get("medium") + dot + images.get("large") + dot
                            + map.get("alt") + dot + map.get("id") + dot + map.get("publisher") + dot
                            + map.get("isbn10") + dot + map.get("isbn13") + dot + map.get("title") + dot
                            + map.get("url") + dot + map.get("alt_title") + dot + map.get("author_intro") + dot
                            + map.get("summary") + dot + map.get("price") + dot + map.get("ebook") + ");";
            System.out.println(sql);
            excute(sql);
        }
        Long c20 = System.currentTimeMillis();
        System.out.println(c20 - base);
        this.close();
    }
}
