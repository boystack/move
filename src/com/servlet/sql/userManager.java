package com.servlet.sql;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class userManager
 */
@WebServlet("/userManager")
public class userManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://60.205.189.40:3306/bigdata?characterEncoding=utf8&useSSL=false";
    
    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "bigdata";
    static final String PASS = "Hadoop1234++";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public userManager() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
    	PrintWriter out = response.getWriter();
    	Connection conn = null;
        Statement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT id,account,name,sex,permision FROM user";
			ResultSet rs = stmt.executeQuery(sql);
			List<String> jsons = new ArrayList<String>();
			Gson gson = new Gson();
			while(rs.next()){
				int  Sid = Integer.valueOf(rs.getString("id"));
	            String Saccount = rs.getString("account");
	            String Sname = rs.getString("name");
	            String Ssex = rs.getString("sex");
	            int  Spermision = Integer.valueOf(rs.getString("permision"));
	            Map<String,Object> map=new HashMap<String,Object>();
	    		map.put("id", Sid);
	    		map.put("account", Saccount);
	    		map.put("name", Sname);
	    		map.put("sex", Ssex);
	    		map.put("permision", Spermision);
	    		String json = gson.toJson(map);
	    		
	    		jsons.add(json);
			}
			out.print(jsons);
			// 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException e){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
