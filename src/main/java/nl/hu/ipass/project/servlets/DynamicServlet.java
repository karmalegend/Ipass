package nl.hu.ipass.project.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DynamicServlet extends HttpServlet
{protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String name = request.getParameter("username");
    PrintWriter out = response.getWriter();
    response.setContentType("text/html");
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println(" <title>Dynamic Example</title>");
    out.println("</head>");out.println("<body>");
    out.println(" <h2>Dynamic webapplication example</h2>");
    out.println(" <h2>Hello " + name + "!</h2>");
    out.println("</body>");
    out.println("</html>");
    }
}