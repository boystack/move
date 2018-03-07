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
 * Servlet implementation class movepersonal
 */
@WebServlet("/movepersonal")
public class movepersonal extends HttpServlet {
	private static final long serialVersionUID = 1L;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public movepersonal() {
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
    	String id =request.getParameter("id");
    	Connection conn = null;
        Statement stmt = null;
		try{
			Class.forName(contentsql.JDBC_DRIVER);
			conn = DriverManager.getConnection(contentsql.DB_URL,contentsql.USER,contentsql.PASS);
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM movie_info mi RIGHT JOIN user1_CF uc ON mi.MOVIE_ID=uc.movie ORDER BY uc.CF DESC LIMIT 30";
			ResultSet rs = stmt.executeQuery(sql);
			List<String> jsons = new ArrayList<String>();
			Gson gson = new Gson();
			while(rs.next()){
				int  Smovie_id = Integer.valueOf(rs.getString("MOVIE_ID"));
				int  Sid = Integer.valueOf(rs.getString("id"));
	            String Sname = rs.getString("name");
	            String Sfiletype = rs.getString("filetype");
	            String Sfilecountry = rs.getString("filecountry");
	            String Sscore = rs.getString("score");
	            String Sphoto = rs.getString("photo");
	            Map<String,Object> map=new HashMap<String,Object>();
	            map.put("MOVIE_ID", Smovie_id);
	            map.put("id", Sid);
	    		map.put("name", Sname);
	    		map.put("filetype", Sfiletype);
	    		map.put("filecountry", Sfilecountry);
	    		map.put("score", Sscore);
	    		map.put("photo", Sphoto);
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
