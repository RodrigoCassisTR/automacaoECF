package ECF.automacaoECF.laboratorio;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Demo {

	public static void main(String[] args) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(new File("c:/temp/xmltest.xml"));
		NodeList nodeList = document.getElementsByTagName("MESSAGE");
		for (int x = 0, size = nodeList.getLength(); x < size; x++) {
			System.out.println(nodeList.item(x).getAttributes().getNamedItem("description").getNodeValue());
		}
	}
}
