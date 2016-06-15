package Douban.BookRecive;

public class StartUp
{
    public static void main(final String[] args)
    {
        BookRevice br = new BookRevice();
        br.getBook(1000000);
        //		 DbConn db = new DbConn() ;
        //	     db.store();
    }
}
