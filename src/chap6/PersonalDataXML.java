package chap6;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 * Responsible for converting a PersonalData object into an XML
 * representation using DOM.
 */
public class PersonalDataXML {

    /**
     * @param personalData the data to convert to XML.
     * @param includeErrors if true, an extra field will be included in
     * the XML, indicating that the browser should warn the user about
     * required fields that are missing.
     * @return a DOM Document that contains the web page.
     */
    public Document produceDOMDocument(PersonalData personalData,
            boolean includeErrors) throws ParserConfigurationException {

        // use Sun's JAXP to create the DOM Document
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
        DocumentBuilder docBuilder = dbf.newDocumentBuilder( );
        Document doc =  docBuilder.newDocument( );

        // create <page>, the root of the document
        Element pageElem = doc.createElement("page");
        doc.appendChild(pageElem);

        // if needed, append <requiredFieldsMissing/>
        if (includeErrors && !personalData.isValid( )) {
            pageElem.appendChild(doc.createElement(
                    "requiredFieldsMissing"));
        }

        Element personalDataElem = doc.createElement("personalData");
        pageElem.appendChild(personalDataElem);

        // use a private helper function to avoid some of DOM's
        // tedious code
        addElem(doc, personalDataElem, "firstName",
                personalData.getFirstName( ), true);
        addElem(doc, personalDataElem, "lastName",
                personalData.getLastName( ), true);
        addElem(doc, personalDataElem, "daytimePhone",
                personalData.getDaytimePhone( ), true);
        addElem(doc, personalDataElem, "eveningPhone",
                personalData.getEveningPhone( ), false);
        addElem(doc, personalDataElem, "email",
                personalData.getEmail( ), true);

        return doc;
    }

    /**
     * A helper method that simplifies this class.
     *
     * @param doc the DOM Document, used as a factory for
     *        creating Elements.
     * @param parent the DOM Element to add the child to.
     * @param elemName the name of the XML element to create.
     * @param elemValue the text content of the new XML element.
     * @param required if true, insert 'required="true"' attribute.
     */
    private void addElem(Document doc, Element parent, String elemName,
            String elemValue, boolean required) {
        Element elem = doc.createElement(elemName);
        elem.appendChild(doc.createTextNode(elemValue));
        if (required) {
            elem.setAttribute("required", "true");
        }
        parent.appendChild(elem);
    }
}