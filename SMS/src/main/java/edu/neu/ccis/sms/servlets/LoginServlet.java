package edu.neu.ccis.sms.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.neu.ccis.sms.constants.SessionKeys;
import edu.neu.ccis.sms.dao.users.UserDao;
import edu.neu.ccis.sms.dao.users.UserDaoImpl;
import edu.neu.ccis.sms.entity.users.User;

/**
 * Servlet implementation class LoginServlet
 * 
 * @author Pramod R. Khare
 * @date 18-May-2015
 * @lastUpdate 3-June-2015
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("Username - " + username);
        System.out.println("Password - " + password);

        UserDao userDao = new UserDaoImpl();
        User one = userDao.findUserByUsernameAndPassword(username, password);
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            session = null;
        }

        if (one != null) {
            System.out.println("Login successful!!");
            session = request.getSession(true);
            
            // Save important information about user into session
            session.setAttribute(SessionKeys.keyUserObj, one);
            session.setAttribute(SessionKeys.keyUserId, one.getId());
            session.setAttribute(SessionKeys.keyUserName, one.getUsername());

            //TODO Fetch the Role Mappings and save them into the SessionKeys
            
            response.sendRedirect("pages/dashboard.jsp");
        } else {
            System.out.println("Login failed!!");
            response.sendRedirect("pages/login.jsp");
        }
    }
}
