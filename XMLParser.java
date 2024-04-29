import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

  public List<Person> parse(String directoryPath) {
    List<Person> employees = new ArrayList<>();

    try {
      File directory = new File(directoryPath);
      if (!directory.exists() || !directory.isDirectory()) {
        System.err.println("Podana ścieżka nie istnieje lub nie jest katalogiem.");
        return employees;
      }

      File[] files = directory.listFiles();
      if (files != null) {
        for (File file : files) {
          if (file.isFile() && file.getName().endsWith(".xml")) {
            Person employee = parseEmployeeFromFile(file);
            if (employee != null) {
              employees.add(employee);
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return employees;
  }

  private Person parseEmployeeFromFile(File file) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(file);

      Element root = doc.getDocumentElement();
      String personId = getTextContent(root, "PersonalId");
      String firstName = getTextContent(root, "FirstName");
      String lastName = getTextContent(root, "LastName");
      String mobile = getTextContent(root, "Mobile");
      String email = getTextContent(root, "Email");
      String pesel = getTextContent(root, "PESEL");

      return new Person(personId, firstName, lastName, mobile, email, pesel);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private String getTextContent(Element element, String tagName) {
    NodeList nodeList = element.getElementsByTagName(tagName);
    if (nodeList != null && nodeList.getLength() > 0) {
      return nodeList.item(0).getTextContent();
    }
    return "";
  }
}
