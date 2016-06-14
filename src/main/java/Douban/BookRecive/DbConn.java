package Douban.BookRecive;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.SQLException;  

public class DbConn {
	public static final String url = "jdbc:mysql://120.24.187.99:3306/kinbook?useUnicode=true&characterEncoding=utf8";  
    public static final String name = "com.mysql.jdbc.Driver";  
    public static final String user = "root";  
    public static final String password = "mysql";  
  
    public Connection conn = null;  
    public PreparedStatement pst = null;  
    
    //判断book表是否存在，如果存在则删除
    public static final String init = "drop table if EXISTS book;";
    //创建book表
    public static final String book = "create table book (name varchar(10),sex varchar(2));";

    public void DBHelper(String sql) {  
        try {  
            Class.forName(name);//指定连接类型  
            conn = DriverManager.getConnection(url, user, password);//获取连接  
            pst = conn.prepareStatement(sql);//准备执行语句  
            pst.execute(sql) ;
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public void close() {  
        try {  
            this.conn.close();  
            this.pst.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    } 
    
    public void store(){
    	String sql = "create table table3 ( name varchar(10),sex varchar(2));";
    	DBHelper(sql) ;
    }
}
