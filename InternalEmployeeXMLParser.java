import java.util.List;

public class InternalEmployeeXMLParser {

  private XMLParser xmlParser;

  public InternalEmployeeXMLParser(XMLParser xmlParser) {
    this.xmlParser = xmlParser;
  }

  public List<Person> parse(String directoryPath) {
    return xmlParser.parse(directoryPath);
  }
}
