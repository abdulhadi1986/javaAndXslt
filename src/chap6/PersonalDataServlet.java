package chap6;
import java.io.*;
import java.net.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

/**
 * A demonstration servlet that produces two pages. In the first page,
 * the user is prompted to enter "personal information", including
 * name, phone number, and Email. In the second page, a summary of this
 * information is displayed. XSLT is used for all HTML rendering,
 * so this servlet does not enforce any particular look and feel.
 */
public class PersonalDataServlet extends HttpServlet {
    private PersonalDataXML personalDataXML = new PersonalDataXML( );
    private Templates editTemplates;
    private Templates thanksTemplates;

    /**
     * One-time initialization of this Servlet.
     */
    public void init( ) throws UnavailableException {
        TransformerFactory transFact = TransformerFactory.newInstance( );
        String curName = null;
        try {
            curName = "/WEB-INF/xslt/editPersonalData.xslt";
            URL xsltURL = getServletContext( ).getResource(curName);
            String xsltSystemID = xsltURL.toExternalForm( );
            this.editTemplates = transFact.newTemplates(
                    new StreamSource(xsltSystemID));

            curName = "/WEB-INF/xslt/confirmPersonalData.xslt";
            xsltURL = getServletContext( ).getResource(curName);
            xsltSystemID = xsltURL.toExternalForm( );
            this.thanksTemplates = transFact.newTemplates(
                    new StreamSource(xsltSystemID));
        } catch (TransformerConfigurationException tce) {
            log("Unable to compile stylesheet", tce);
            throw new UnavailableException("Unable to compile stylesheet");
        } catch (MalformedURLException mue) {
            log("Unable to locate XSLT file: " + curName);
            throw new UnavailableException(
                    "Unable to locate XSLT file: " + curName);
        }
    }

    /**
     * Handles HTTP GET requests, such as when the user types in
     * a URL into his or her browser or clicks on a hyperlink.
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException,
            ServletException {
        PersonalData personalData = getPersonalData(request);
        // the third parameter, 'false', indicates that error
        // messages should not be displayed when showing the page.
        showPage(response, personalData, false, this.editTemplates);
    }

    /**
     * Handles HTTP POST requests, such as when the user clicks on
     * a Submit button to update his or her personal data.
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws IOException,
            ServletException {

        // locate the personal data object and update it with
        // the information the user just submitted.
        PersonalData pd = getPersonalData(request);
        pd.setFirstName(request.getParameter("firstName"));
        pd.setLastName(request.getParameter("lastName"));
        pd.setDaytimePhone(request.getParameter("daytimePhone"));
        pd.setEveningPhone(request.getParameter("eveningPhone"));
        pd.setEmail(request.getParameter("email"));

        if (!pd.isValid( )) {
            // show the 'Edit' page with an error message
            showPage(response, pd, true, this.editTemplates);
        } else {
            // show a confirmation page
            showPage(response, pd, false, this.thanksTemplates);
        }
    }

    /**
     * A helper method that sends the personal data to the client
     * browser as HTML. It does this by applying an XSLT stylesheet
     * to the DOM tree.
     */
    private void showPage(HttpServletResponse response,
            PersonalData personalData, boolean includeErrors,
            Templates stylesheet) throws IOException, ServletException {
        try {
            org.w3c.dom.Document domDoc =
                    this.personalDataXML.produceDOMDocument(
                    personalData, includeErrors);

            Transformer trans = stylesheet.newTransformer( );

            response.setContentType("text/html");
            PrintWriter writer = response.getWriter( );

            trans.transform(new DOMSource(domDoc), new StreamResult(writer));
        } catch (Exception ex) {
            showErrorPage(response, ex);
        }
    }

    /**
     * If any exceptions occur, this method can be called to display
     * the stack trace in the browser window.
     */
    private void showErrorPage(HttpServletResponse response,
            Throwable throwable) throws IOException {
        PrintWriter pw = response.getWriter( );
        pw.println("<html><body><h1>An Error Has Occurred</h1><pre>");
        throwable.printStackTrace(pw);
        pw.println("</pre></body></html>");
    }

    /**
     * A helper method that retrieves the PersonalData object from
     * the HttpSession.
     */
    private PersonalData getPersonalData(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        PersonalData pd = (PersonalData) session.getAttribute(
                "chap6.PersonalData");
        if (pd == null) {
            pd = new PersonalData( );
            session.setAttribute("chap6.PersonalData", pd);
        }
        return pd;
    }
}