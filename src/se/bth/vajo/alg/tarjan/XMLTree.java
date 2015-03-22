package se.bth.vajo.alg.tarjan;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLTree {

	Document doc;

	private void attachNode(Element parent, VNode currentNode) {
		Element node = doc.createElement("node");
		node.setAttribute("name", currentNode.name);
		node.setAttribute("born", currentNode.born + "");
		for (VNode n : currentNode.children) {
			attachNode(node, n);
		}
		parent.appendChild(node);
	}

	public void generateFile(VNode root) {
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("tree");
			doc.appendChild(rootElement);

			// staff elements
			attachNode(rootElement, root);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(CONSTANTS.FILENAME));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	public Tree buildTree() throws ParserConfigurationException, SAXException,
			IOException {
//		VNode root = new VNode("EVE", null, 1000, 1);
		Tree t = new Tree();
		VNode root = readFile();
		return t.setRoot(root);
	}

	private VNode readFile() throws ParserConfigurationException, SAXException,
			IOException {
		VNode root = null;
		File file = new File(CONSTANTS.FILENAME);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();
		Document document = documentBuilder.parse(file);
		NodeList nodeList = document.getChildNodes();
		if (nodeList != null && nodeList.getLength() > 0) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element node = (Element) nodeList.item(i);
					NodeList l = node.getChildNodes();
					Element rootElement = (Element) l.item(0);

					root = new VNode(
							rootElement.getAttribute(CONSTANTS.NODE_NAME_ATTR),
							null, Integer.parseInt(rootElement
									.getAttribute(CONSTANTS.NODE_BORN_ATTR)), 1
									);
					
					addChildren(root, rootElement);

				}
			}
		}
		return root;

	}
	
	private void addChildren(VNode n, Element p) {
		NodeList nodeList = p.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element childElement = (Element) nodeList.item(i);
				VNode child = new VNode(childElement.getAttribute(CONSTANTS.NODE_NAME_ATTR), n, Integer.parseInt(childElement
						.getAttribute(CONSTANTS.NODE_BORN_ATTR)), n.generation + 1);
				n.addChild(child);
				addChildren(child, childElement);
			}
		}
	}
	



}
