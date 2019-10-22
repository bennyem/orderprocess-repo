package com.etisalat.ops.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.etisalat.ops.domain.User;
import com.etisalat.ops.service.UserService;
import com.etisalat.ops.service.UserServiceImpl;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService service=new UserServiceImpl();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		try {					
			String userName=request.getParameter("username");
			String password=request.getParameter("password");
			User user= service.getUserDetails(userName, password);
			if(user!=null) {				
				HttpSession session=request.getSession();
				session.setAttribute("username", userName);
				request.getRequestDispatcher("views/main_menu.jsp")
				.forward(request,response);
			}else {
				/*response.sendRedirect("http://localhost:9090/WebLoginApplication/login.html");*/
				out.println("<html><body><h1><font color='red'>");
				out.println("Invalid Credentials! Re-enter");
				out.println("</font></h1></body></html>");
				request.getRequestDispatcher("login.jsp").include(request, response);
			}
			
		}catch(Exception e) {
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,e.getMessage());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
