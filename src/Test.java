import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Test{
public static void main(String[] args) {  
        ArrayList<Publication> list= new ArrayList<Publication>();  
        list = getParserAuthor();  
        for(int i=0;i<list.size();i++)  
        {             
            if (list.get(i)!=null)  
            writeToMysql(list.get(i));  
      
        }
}

private static void writeToMysql(Publication publication) {
	// TODO Auto-generated method stub
	System.out.println(publication);  
    //1.加载driver驱动  
    try {  
        // 加载MySql的驱动类  
        Class.forName("com.mysql.jdbc.Driver");  
    } catch (ClassNotFoundException e) {  
        System.out.println("找不到驱动程序类 ，加载驱动失败！");  
        e.printStackTrace();  
    }  
    //2.建立连接  
    Statement st = null;  
    //调用DriverManager对象的getConnection()方法，获得一个Connection对象  
    Connection con  =null;  
    try {  
    	 String sqlStr = "jdbc:mysql://localhost:3306/huo";  
    	 String rootName = "root";//数据库名  
    	 String rootPwd = "1234";//数据库密码  
		//建立数据库连接  
        con = DriverManager.getConnection(sqlStr, rootName,rootPwd);  
        //INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)  
        String paperId= publication.getId();  
        String authors = publication.getAuthors();  
        //插入语句格式  
        String sql = "insert into publication1(paperId,Authors) values(\""+paperId+"\",\""+authors+"\")";  
        System.out.println(sql);  
        st =  con.createStatement(); //创建一个Statement对象  
        st.executeUpdate(sql);//提交数据更新  
    } catch (SQLException e) {  
        e.printStackTrace();  
    }finally{  
        try {  
            st.close();  
            con.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
	
}

private static ArrayList<Publication> getParserAuthor() {
	// TODO Auto-generated method stub
	
	 ArrayList<Publication> list= new ArrayList<Publication>();  
     //获取DOM解析器  
     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
     DocumentBuilder builder;  
     try {  
            builder = factory.newDocumentBuilder();  
            Document doc;  
            doc = builder.parse(new File("Myxml.xml"));  
                //得到一个element根元素,获得根节点  
             Element root = doc.getDocumentElement();          
             System.out.println("根元素："+root.getNodeName());  
               
             //子节点  
             NodeList personNodes = root.getElementsByTagName("publication");  
             for(int i = 0; i<personNodes.getLength();i++){  
                 Element personElement = (Element) personNodes.item(i);  
                 Publication publication = new Publication();  
                 NodeList childNodes = personElement.getChildNodes();  
                 //System.out.println("*****"+childNodes.getLength());    
                   
                 for (int j = 0; j < childNodes.getLength(); j++) {  
                 if(childNodes.item(j).getNodeType()==Node.ELEMENT_NODE){  
                     if("authors".equals(childNodes.item(j).getNodeName())){  
                         publication.setAuthors(childNodes.item(j).getFirstChild().getNodeValue());  
                     }else if("id".equals(childNodes.item(j).getNodeName())){  
                         publication.setId(childNodes.item(j).getFirstChild().getNodeValue());  
                     }  
                 }  
             }  
                 list.add(publication);  
             }  
             for(Publication publication2 : list){  //为了查看数据是否正确，进行打印结果  
                  System.out.println(publication2.toString());    
              }   
     } catch (SAXException e) {  
         e.printStackTrace();  
     } catch (IOException e) {  
         e.printStackTrace();          
     } catch (ParserConfigurationException e) {  
         e.printStackTrace();  
     }  
     return list;
}
}
	
	
    