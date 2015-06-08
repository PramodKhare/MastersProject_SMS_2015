package edu.neu.ccis.sms.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.CompositeConfiguration;

import edu.neu.ccis.sms.config.ConfigurationReader;

/**
 * Servlet implementation class Dashboard View
 * @author Swapnil Gupta
 *
 */
@WebServlet("/Dashboard")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/* Context for current web application instance*/
	private static ServletContext context;
	
	/* Configuration object containing the configuration parameters */
	private static CompositeConfiguration config;
	
	/* View mapping for dashboard */
	private static final String dashBoardView = "/pages/dashboard.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		context = getServletContext();
        config = (CompositeConfiguration) context.getAttribute("config");
		setConfigurationParamsInRequest(request);
        request.getRequestDispatcher(dashBoardView).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Set configurations parameters in Http request
	 * @param request request object for current servlet
	 */
	private void setConfigurationParamsInRequest(HttpServletRequest request) {
		request.setAttribute("conductorId", config.getString("roles.conductor"));
        request.setAttribute("evaluatorId", config.getString("roles.evaluator"));
        request.setAttribute("submitterId", config.getString("roles.submitter"));
        
//        String hierarchyId = config.getString("hierarchy.id");
//        List<String> hierarchyParamsList = ConfigurationReader.getCategoryParamsList(hierarchyId);
//		request.setAttribute("hierarchyParamsList", hierarchyParamsList);
//        request.setAttribute("hierarchyLevel1", config.getString("hierarchy.level1"));
	}

}
