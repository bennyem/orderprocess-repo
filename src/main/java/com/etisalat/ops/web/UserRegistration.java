package com.etisalat.ops.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.etisalat.ops.domain.User;
import com.etisalat.ops.excemption.UserException;
import com.etisalat.ops.service.UserService;
import com.etisalat.ops.service.UserServiceImpl;

@WebServlet("/register")
public class UserRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService service=new UserServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		try {
			User user=new User();
			populateUser(request,user);
			int n= service.addNewUser(user);
			out.println("<html><body><h1>"+"User:"+n+" added to database");
			out.println("</h1></body></html>");			
		}catch(UserException e) {
			e.printStackTrace();
		}
		
	}

	private void populateUser(HttpServletRequest request, User user) {
		user.setUserName(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		user.setEmail(request.getParameter("email"));
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
