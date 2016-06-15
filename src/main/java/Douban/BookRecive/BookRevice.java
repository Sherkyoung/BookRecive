package Douban.BookRecive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 *
 * @author CallMeWhy
 *
 */
public class BookRevice
{
    /**
     * 根据传入参数获取豆瓣书籍资料json串
     *
     * @param index
     */
    public void getBook(final int index)
    {
        ClientResponse response = null;
        List<Map<Object, Object>> book = new ArrayList<Map<Object, Object>>();

        String url = "https://api.douban.com/v2/book/";
        try
        {
            Client client = Client.create();
            int ind = 0;
            Long base = System.currentTimeMillis();
            for (int i = index; i > 0; i++)
            {
                ind++;
                WebResource resource = client.resource(url + i);
                response = resource.get(ClientResponse.class);
                int status = response.getStatus();
                if (status != 404)
                {
                    String data = response.getEntity(String.class);
                    System.out.println("ID：" + i + ":");
                    System.out.println(data);
                    book.add(parseJSON2Map(data));
                }
                else
                {
                    System.out.println(i);
                }
                if (ind > 20)
                {
                    ind = 0;
                    Long c20 = System.currentTimeMillis();
                    System.out.println(c20 - base);
                    //将数据存储到数据库中
                    DbConn db = new DbConn();
                    db.store(book);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (response != null)
            {
                response.close();
            }
        }

    }

    /**
     * 将豆瓣获取json数据转存到Map中
     *
     * @param jsonStr
     * @return
     */
    public Map<Object, Object> parseJSON2Map(final String jsonStr)
    {
        Map<Object, Object> map = new HashMap<Object, Object>();
        //最外层解析
        JSONObject json = JSONObject.fromObject(jsonStr);
        for (Object k : json.keySet())
        {
            Object v = json.get(k);
            //如果内层还是数组的话，继续解析
            if (v instanceof JSONArray)
            {
                map.put(k.toString(), v);
            }
            else
            {
                map.put(k.toString(), v);
            }
        }
        return map;
    }
}
