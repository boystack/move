package com.servlet.sql;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class addsuggest
 */
@WebServlet("/addsuggest")
public class addsuggest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addsuggest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	PrintWriter out = response.getWriter();
    	String moveid =request.getParameter("moveid");
    	String score =request.getParameter("score");
		Connection conn = null;
        Statement stmt = null;
        HttpSession session = request.getSession();
        Object account = session.getAttribute("account");
        if(account!=null){
        	try{
    			Class.forName(contentsql.JDBC_DRIVER);
    			conn = DriverManager.getConnection(contentsql.DB_URL,contentsql.USER,contentsql.PASS);
    			stmt = conn.createStatement();
    			String sql;
    			String sq ="select moveId from move_suggest where userId = 123457 and moveId ="+moveid;
    			ResultSet rs = stmt.executeQuery(sq);
    			if(rs.next()){
    				sql = "update move_suggest set moveScore="+score+" where userId = 123457 and moveId ="+moveid;
    				stmt.executeUpdate(sql);
    			}else{
    				sql = "insert into move_suggest(userId,moveId,moveScore) value(123457,"+moveid+","+score+")";
    				stmt.executeUpdate(sql);
    			}
    			// 完成后关闭
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
        }else{
        	System.out.print("session已过期或者未登录");
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
