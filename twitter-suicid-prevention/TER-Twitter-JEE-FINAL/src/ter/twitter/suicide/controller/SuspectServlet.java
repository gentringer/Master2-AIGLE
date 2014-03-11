package ter.twitter.suicide.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ter.twitter.suicide.model.csv.suspect.SuspectCSVParser;
import ter.twitter.suicide.model.csv.suspect.SuspectClass;
import com.google.gson.Gson;

/**
 * Servlet implementation class ThematicsServlet
 */
@WebServlet("/SuspectServlet")
public class SuspectServlet extends HttpServlet {
	// -> Variables
	private static final long serialVersionUID = 1L;
    // -> Constructor(s)  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SuspectServlet() {
        super();
    }
    // -> Methods
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String path = "/WEB-INF/CSVData/result_d_db.csv";
		
		InputStream inputstream = this.getServletConfig().getServletContext().getResourceAsStream(path);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));

		List<SuspectClass> suspectclass = new SuspectCSVParser().readList(reader);

		String suspects = new Gson().toJson(suspectclass);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		
		out.print(suspects);
		out.flush();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		

	}

}
