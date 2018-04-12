package io.websockets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * servlet implementation class UserNameServlet
 */
@WebServlet("/UserNameServlet")
public class UserNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		response.setContentType("text/html");
 		PrintWriter printwriter=response.getWriter();
 		HttpSession httpsession=request.getSession(true);
 		String username=request.getParameter("username");
 		httpsession.setAttribute("username",username);
 		if(username!=null)
 		{
 			printwriter.println("<html>");
 			
 			printwriter.println(	"<head><meta charset=\"utf-8\">\r\n" + 
 					"<title>welcome to chandusoft</title></head>");
 			
 			printwriter.println("<body bgcolor=\"#33b2ff\">");
 			printwriter.println(	"<mark>username: "+username+"</mark><br>");
 			printwriter.println(	"<textarea id =\"messagesTextArea\" readonly=\"readonly\" rows=\"30\" cols=\"75\"></textarea><br>");
 			printwriter.print("<input type=\"text\"  id =\"messageText\"	placeholder=\"enter your message\"  size=\"50\">");
 			printwriter.println("<input type=\"button\" value =\"send\" onclick=\"sendMessage();\">");
 			printwriter.println("<script type=\"text/javascript\">");
 			printwriter.println( "var websocket =new WebSocket(\"ws://192.168.1.26:8080/jaiganeshji2/chatroomServerEndpoint\");");
 			printwriter.println("websocket.onmessage= function processMessage(message){");
 			printwriter.println("var jsonData =JSON.parse(message.data);");
 			printwriter.println("if (jsonData.message !=null) messagesTextArea.value += jsonData.message + \"\\n\";");
 			printwriter.println("}");
 			printwriter.println("function sendMessage(){");
 			printwriter.println("websocket.send(messageText.value);");
 			printwriter.println("messageText.value= \"\";");
 			printwriter.println("}");
 			printwriter.println("</script>");
 			printwriter.println("</body>");
 			printwriter.println("</html>");

 			
 		}
		
		
		
		
 	}

}
