
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

/**
 * Servlet implementation class HomePageMcFarland
 */
@WebServlet("/HomePageMcFarland")
public class HomePageMcFarland extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HomePageMcFarland() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String title = "Expense Home Page";
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
				"transitional//en\">\n"; //
		out.println(docType + //
				"<html>"
				+ "<head>"
				+ "<style>"
				+ "nav {"
				+ "	line-height: 30px;"
				+ "	background-color: #eeeeee;"
				+ "	height: 300px;"
				+ "	width: 150px;"
				+ "	float: left;"
				+ "	padding: 5px;"
				+ "}"
				+ ""
				+ "section {"
				+ "	width: 350px;"
				+ "	float: left;"
				+ "	padding: 10px;"
				+ "}"
				+ "</style>"
				+ "</head>"
				+ ""
				+ "<body>"
				+ "	<header>"
				+ "		<h1 align=\"center\">"+ title + "</h1>"
				+ "	</header>"
				+ "<nav>\r\n"
				+ "		<a href=\"/webproject-TE-McFarland/search_mcfarland.html\">Search Data</a> <br> \r\n"
				+ "		<a href=\"/webproject-TE-McFarland/insert_mcfarland.html\">Insert Data</a> <br>\r\n"
				+ "	</nav>");

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			DBConnectionMcFarland.getDBConnection();
			connection = DBConnectionMcFarland.connection;

			String selectSQL = "SELECT * FROM expenseHeader";
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("NAME").trim();
				String cost = rs.getString("COST").trim();
				String desc = rs.getString("EXP_DESC").trim();
				String date = rs.getString("DATE").trim();
				
				out.printf("&emsp;%d&emsp;%s&emsp;%s&emsp;%s&emsp;%s<br>", id, name, cost, desc, date);
				
			}
//			out.println("<br>");
//			out.println("<a href=/webproject-TE-McFarland/search_mcfarland.html>Search Data</a> <br>");
//			out.println("<a href=/webproject-TE-McFarland/insert_mcfarland.html>Insert Data</a> <br>");
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
