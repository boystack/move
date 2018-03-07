package com.servlet.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class userLoginer
 */
@WebServlet("/userLoginer")
public class userLoginer extends HttpServlet {
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
    public userLoginer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
    	PrintWriter out = response.getWriter();
		String account =request.getParameter("loginAccount");
		String passwd =request.getParameter("loginPassword");
		Connection conn = null;
        Statement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT password,permision FROM user where account='"+account+"'";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
            String Spasswd = rs.getString("password");
            String Spermision = rs.getString("permision");
            if(Spasswd.equals(passwd)){
            	out.println(0);
            	// 如果不存在 session 会话，则创建一个 session 对象
                HttpSession session = request.getSession(true);
                session.setAttribute("account",account);
                session.setAttribute("permision",Spermision);
                
            }else{
            	out.println(1);
            }
			}
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
