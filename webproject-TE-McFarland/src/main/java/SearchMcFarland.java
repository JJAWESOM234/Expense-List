import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchMcFarland")
public class SearchMcFarland extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SearchMcFarland() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nameKeyword = request.getParameter("nameKeyword");
		String dateKeyword = request.getParameter("dateKeyword");
		search(nameKeyword, dateKeyword, response);
	}

	void search(String nameKeyword, String dateKeyword, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String title = "Database Result";
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
				"transitional//en\">\n"; //
		out.println(docType + //
				"<html>\n" + //
				"<head><title>" + title + "</title></head>\n" + //
				"<body bgcolor=\"#f0f0f0\">\n" + //
				"<h1 align=\"center\">" + title + "</h1>\n");

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			DBConnectionMcFarland.getDBConnection();
			connection = DBConnectionMcFarland.connection;

			if (nameKeyword.isEmpty() && dateKeyword.isEmpty()) {
				String selectSQL = "SELECT * FROM expenseHeader";
				preparedStatement = connection.prepareStatement(selectSQL);
			} else if (dateKeyword.isEmpty()) {
				String selectSQL = "SELECT * FROM expenseHeader WHERE NAME LIKE ?";
				String name = nameKeyword + "%";
				preparedStatement = connection.prepareStatement(selectSQL);
				preparedStatement.setString(1, name);
			} else if (nameKeyword.isEmpty()) {
				String selectSQL = "SELECT * FROM expenseHeader WHERE DATE LIKE ?";
				String date = dateKeyword + "%";
				preparedStatement = connection.prepareStatement(selectSQL);
				preparedStatement.setString(1, date);
			} else {
				String selectSQL = "SELECT * FROM expenseHeader WHERE NAME LIKE ? AND DATE LIKE ?";
				String name = nameKeyword + "%";
				String date = dateKeyword + "%";
				preparedStatement = connection.prepareStatement(selectSQL);
				preparedStatement.setString(1, name);
				preparedStatement.setString(2, date);
			} 
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("NAME").trim();
				String cost = rs.getString("COST").trim();
				String desc = rs.getString("EXP_DESC").trim();
				String date = rs.getString("DATE").trim();

				if ((nameKeyword.isEmpty() && dateKeyword.isEmpty()) || name.contains(nameKeyword) || date.contains(dateKeyword)) {
					out.println("ID: " + id + ", ");
					out.println("Expense Name: " + name + ", ");
					out.println("Cost: " + cost + ", ");
					out.println("Description: " + desc + ", ");
					out.println("Date: " + date + "<br>");
				}
				
			}
			out.println("<a href=/webproject-TE-McFarland/search_mcfarland.html>Search Data</a> <br>");
			out.println("</body></html>");
			rs.close();
			preparedStatement.close();
			connection.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException se2) {
			}
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
