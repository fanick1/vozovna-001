/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.vozovna;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jostri
 */
@WebServlet(name = "PostMessage", urlPatterns = {"/PostMessage"})
public class PostMessage extends HttpServlet {

	@Resource(mappedName="jms/UserFactory")
    private  ConnectionFactory connectionFactory;

    @Resource(mappedName="jms/User")
    private  Queue queue;
	
	/**
	 * Processes requests for both HTTP
	 * <code>GET</code> and
	 * <code>POST</code> methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, JMSException {
		response.setContentType("text/html;charset=UTF-8");
		
		String firstName=request.getParameter("firstName");
		String lastName=request.getParameter("lastName");
		if ((firstName!=null) && (lastName!=null)) {
			try {
				Connection connection = connectionFactory.createConnection();
				Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				MessageProducer messageProducer;
				messageProducer = session.createProducer(queue);

				ObjectMessage message = session.createObjectMessage();
				// here we create NewsEntity, that will be sent in JMS message
				User e = new User();
				e.setFirstName(firstName);
				e.setLastName(lastName);

				message.setObject(e);                
				messageProducer.send(message);
				messageProducer.close();
				connection.close();
				response.sendRedirect("ListNews.jsp");

			} catch (JMSException ex) {
			}
		}
		
		PrintWriter out = response.getWriter();
		try {
			/* TODO output your page here. You may use following sample code. */
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet PostMessage</title>");			
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet PostMessage at " + request.getContextPath() + "</h1>");
			
			out.println("<form>");
			out.println("First name: <input type='text' name='firstName'><br/>");
			out.println("Last name: <input type='text' name='lastName'><br/>");
			out.println("<input type='submit' value='Add'><br/>");
			out.println("</form>");
			
			out.println("</body>");
			out.println("</html>");
		} finally {			
			out.close();
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP
	 * <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (JMSException ex) {
			Logger.getLogger(PostMessage.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Handles the HTTP
	 * <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (JMSException ex) {
			Logger.getLogger(PostMessage.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>
}
