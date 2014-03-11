package ter.twitter.suicide.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ter.twitter.suicide.hibernate.config.HibernateConfig;
import ter.twitter.suicide.model.hibernate.jpa.Thematics;
import ter.twitter.suicide.model.hibernate.jpa.Tweets;
import ter.twitter.suicide.model.hibernate.queryData.SelectSingle;

import com.google.gson.Gson;

/**
 * Servlet implementation class ThematicsServlet
 */
@WebServlet("/ThematicsServlet")
public class ThematicsServlet extends HttpServlet {
	// -> Variables
	private static final long serialVersionUID = 1L;
    // -> Constructor(s)
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ThematicsServlet() {
        super();
    }
    // -> Methods
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("posted");
		String category = request.getParameter("category");
		String subcategory = request.getParameter("subcategory");

		System.out.println(category + " : "+ subcategory);
		SelectSingle select = new SelectSingle();
		HibernateConfig.init();

		List<Thematics> thems = select.selectThematicByCategory(category, subcategory);

		for(Thematics them : thems)
		{
			them.setTweets(new ArrayList<Tweets>());
		}
		String test = new Gson().toJson(thems);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		
		out.print(test);
		out.flush();
		
		List<String> headers = new ArrayList<String>();
		headers.add("Terme");
		headers.add("Category");
		headers.add("Subcategory");
	}

}
