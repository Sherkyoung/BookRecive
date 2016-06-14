package Douban.BookRecive;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import org.codehaus.jackson.map.ObjectMapper;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author CallMeWhy
 * 
 */
public class BookRevice {
	/**
	 * 根据传入参数获取豆瓣书籍资料json串
	 * @param index
	 */
	public void getBook(int index){
		ClientResponse response = null;
		List<Map<Object,Object>> book = new ArrayList<Map<Object,Object>>() ;
		
        String url = "https://api.douban.com/v2/book/" ;
        try {
            Client client = Client.create();
            for(int i=index;i>0;i++){
            	WebResource resource = client.resource(url + i);
				response = resource.get(ClientResponse.class);
				int status = response.getStatus();
				if(status!=404){
					String data = response.getEntity(String.class);
					System.out.println("ID："+i+":");
					System.out.println(data);
					book.add(parseJSON2Map(data)) ;
				}else{
					System.out.println(i);
				}
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        //将数据存储到数据库中
        DbConn db = new DbConn() ;
        db.store();
	}
	
	/**
	 * 将豆瓣获取json数据转存到Map中
	 * @param jsonStr
	 * @return
	 */
	public Map<Object, Object> parseJSON2Map(String jsonStr){
        Map<Object, Object> map = new HashMap<Object, Object>();
        //最外层解析
        JSONObject json = JSONObject.fromObject(jsonStr);
        for(Object k : json.keySet()){
            Object v = json.get(k);
            //如果内层还是数组的话，继续解析
            if(v instanceof JSONArray){
                map.put(k.toString(), v);
            } else {
                map.put(k.toString(), v);
            }
        }
        return map;
    }
}
