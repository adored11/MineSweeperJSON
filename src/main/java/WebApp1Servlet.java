/**
 * Created by john on 21/11/2016.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@WebServlet("/WebApp1Servlet")
public class WebApp1Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public WebApp1Servlet() {
         super();
        //MineSweeperJSON mineSweeperJSON = new MineSweeperJSON();
         // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        MineSweeperJSON mineSweeperJSON = new MineSweeperJSON();

        //String gameNum = (String)displayObj.get("Field #");

        //String initialGridLayout = request.getParameter(mineSweeperJSON.initialGridLayout.get(0).toString()) ;


        JSONObject test  = mineSweeperJSON.object;
        PrintWriter out = response.getWriter();

            out.println(test.toJSONString());

        out.flush();
        /*out.println("<html>");
        out.println("<body>");
        out.println("<h1>Hello Jetty Runner Servlet Web App 1</h1>");
        out.println("<h2>Test: " + test + "</h2>");
        out.println("</html>");
        out.println("</body>");*/
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

}
