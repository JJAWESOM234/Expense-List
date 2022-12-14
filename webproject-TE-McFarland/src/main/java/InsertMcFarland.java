
/**
 * @file InsertMcFarland.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertMcFarland")
public class InsertMcFarland extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InsertMcFarland() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String name = request.getParameter("name");
      String cost = request.getParameter("cost");
      String desc = request.getParameter("desc");
      String date = request.getParameter("date");

      Connection connection = null;
      String insertSql = " INSERT INTO expenseHeader (id, NAME, COST, EXP_DESC, DATE) values (default, ?, ?, ?, ?)";

      try {
         DBConnectionMcFarland.getDBConnection();
         connection = DBConnectionMcFarland.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, name);
         preparedStmt.setString(2, cost);
         preparedStmt.setString(3, desc);
         preparedStmt.setString(4, date);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Data to DB table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Expense Name</b>: " + name + "\n" + //
            "  <li><b>Cost</b>: " + cost + "\n" + //
            "  <li><b>Description</b>: " + desc + "\n" + //
            "  <li><b>Date</b>: " + date + "\n" + //

            "</ul>\n");

      out.println("<a href=/webproject-TE-McFarland/search_mcfarland.html>Search Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
