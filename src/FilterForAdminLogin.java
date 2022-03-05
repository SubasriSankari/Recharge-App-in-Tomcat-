import java.io.IOException;
import java.io.PrintWriter;
import java.lang.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class FilterForAdminLogin implements Filter {


	@Override
    	public void init(FilterConfig arg0) throws ServletException {}

    	@Override
    	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

	        PrintWriter out = response.getWriter();
	 	String name = request.getParameter("adminName");
		String password = request.getParameter("adminPassword");
		
	        if(name.equals("admin") && password.equals("admin@2000")){
			chain.doFilter(request, response);
	        } 
		else{
			out.println("<style>");
			out.println("input[type=button]{border-radius : 10px;background-image: linear-gradient(to right, pink , yellow);padding: 4px 8px;font-family: cursive;}");
			out.println("</style>");
			out.print("Entered username or password is incorrect!!");
       			out.print("<p><input type=\"button\" name=\"statusButton\" onclick=\"window.location.href='admin.html'\" value=\"Login\"/></p>");
		}
    	}

	@Override
	public void destroy() {}
}