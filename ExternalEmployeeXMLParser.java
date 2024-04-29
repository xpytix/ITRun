import java.util.List;

public class ExternalEmployeeXMLParser {

  private XMLParser xmlParser;

  public ExternalEmployeeXMLParser(XMLParser xmlParser) {
    this.xmlParser = xmlParser;
  }

  public List<Person> parse(String directoryPath) {
    return xmlParser.parse(directoryPath);
  }
}
