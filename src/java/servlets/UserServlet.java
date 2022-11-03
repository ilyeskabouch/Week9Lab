package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.*;
import services.*;

public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String email = request.getParameter("email");
        UserService us = new UserService();
        if (action != null && action.equals("delete")) {
            try {
                us.delete(email);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("message", "Error deleting user");
            }
        } else if (action != null && action.equals("edit")) {
            try {
                User user = us.get(email);
                request.setAttribute("firstname", user.getFirstName());
                request.setAttribute("lastname", user.getLastName());
                request.setAttribute("role", user.getRole().getRoleName());
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        List<User> users = null;
        try {
            users = us.getAll();
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "Error when retrieving users");
        }

        request.setAttribute("users", users);
        request.setAttribute("action", action);
        request.setAttribute("email", email);

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String password = request.getParameter("password");
        String roleName = request.getParameter("role");
        Role role = new Role();
        UserService us = new UserService();
        if (email == null || email.equals("") || firstname == null || firstname.equals("") || lastname == null || lastname.equals("") || password == null || password.equals("")) {
            request.setAttribute("error", "All fields are required");
        } else {
            if (roleName.equals("admin")) {
                role = new Role(1, "system admin");
            } else if (roleName.equals("user")) {
                role = new Role(2, "regular user");
            }
            if (action != null && action.equals("add")) {
                try {
                    us.insert(email, firstname, lastname, password, role);
                } catch (Exception ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("message", "Error adding user");
                }
            }
            if (action != null && action.equals("edit")) {
                try {
                    us.update(email, firstname, lastname, password, role);
                } catch (Exception ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("message", "Error editing user");
                }
            }
        }
        List<User> users = null;
        try {
            users = us.getAll();
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "Error when retrieving users");
        }

        request.setAttribute("users", users);
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

}
