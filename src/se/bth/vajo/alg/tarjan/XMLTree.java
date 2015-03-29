package se.bth.vajo.alg.tarjan;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

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

	private Document doc;

	/**
	 * Save an existing tree to file.
	 * 
	 * @param root
	 */
	public void generateFile(VNode root) {
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// Create document and attach root
			doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("tree");
			doc.appendChild(rootElement);

			// Attach nodes
			attachNodeElement(rootElement, root);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(CONSTANTS.FILENAME));
			transformer.transform(source, result);

			System.out.println("File '" + CONSTANTS.FILENAME + "'" + " saved.");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	/**
	 * Recursively build the dom.
	 * 
	 * @param parent
	 * @param currentNode
	 */
	private void attachNodeElement(Element parent, VNode currentNode) {
		Element node = doc.createElement("node");
		node.setAttribute("name", currentNode.getName());
		node.setAttribute("born", currentNode.getBorn() + "");
		for (VNode n : currentNode.getChildren()) {
			attachNodeElement(node, n);
		}
		parent.appendChild(node);
	}

	ArrayList<VNode> testNodes;

	/**
	 * Build tree from file.
	 * 
	 * @param filename
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public Tree buildTree(final String filename)
			throws ParserConfigurationException, SAXException, IOException {
		testNodes = new ArrayList<VNode>();
		Tree t = new Tree();
		VNode root = readFile(filename);
		return t.setRoot(root);
	}

	private String filename;

	/**
	 * Construct the tree from the xml file.
	 * 
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private VNode readFile(final String filename)
			throws ParserConfigurationException, SAXException, IOException {
		this.filename = filename;
		VNode root = null;
		File file = new File(filename);
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
									.getAttribute(CONSTANTS.NODE_BORN_ATTR)), 1);

					addChildren(root, rootElement);

				}
			}
		}
		return root;

	}

	/**
	 * Recursively reconstruct the children.
	 * 
	 * @param n
	 * @param p
	 */
	private void addChildren(VNode n, Element p) {
		NodeList nodeList = p.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element childElement = (Element) nodeList.item(i);
				VNode child = new VNode(
						childElement.getAttribute(CONSTANTS.NODE_NAME_ATTR), n,
						Integer.parseInt(childElement
								.getAttribute(CONSTANTS.NODE_BORN_ATTR)),
						n.getGeneration() + 1);
				if (isTestNode(child)) {
					this.testNodes.add(child);
				}
				n.addChild(child);
				addChildren(child, childElement);
			}
		}
	}

	public ArrayList<VNode> getTestNodes() {
		return this.testNodes;
	}

	private boolean isTestNode(VNode n) {
		// 100 nodes test case
		// 6. FERNANDA, born 1141.
		// 5. KAILEE, born 1112.
		// LCA should be 2. JANELLE, born 1029.
		// if (this.filename == CONSTANTS.CASE_20_CHILDREN) {

		if (this.filename == CONSTANTS.CASE_20_CHILDREN) {
			if (n.getBorn() == 1085 && n.getName().equals("LYRA")) {
				return true;
			} else if (n.getBorn() == 1085 && n.getName().equals("CECILIA")) {
				return true;
			} else
				return false;

		} else if (this.filename == CONSTANTS.CASE_100_CHILDREN) {
			if (n.getBorn() == 1141 && n.getName().equals("FERNANDA")) {
				return true;
			} else if (n.getBorn() == 1112 && n.getName().equals("KAILEE")) {
				return true;
			} else
				return false;

		} else if (this.filename == CONSTANTS.CASE_1000_CHILDREN) {
//			if (n.getBorn() == 1243 && n.getName().equals("JALYNN")) {
//				return true;
//			} else if (n.getBorn() == 1325 && n.getName().equals("JULIETTA")) {
//				return true;
//			} else
//				return false;
			
			if (n.getBorn() == 1406 && n.getName().equals("ALINE")) {
				return true;
			} else if (n.getBorn() == 1323 && n.getName().equals("ISABELLE")) {
				return true;
			} else
				return false;

		} else if (this.filename == CONSTANTS.CASE_1000_CHILDREN_B) {

//			10. MAISY:258, born 1261
//			3. PIPER:241, born 1048
//			LCA: 1. EVE:0, born 1000
//			if (n.getId() == 258) {
//				return true;
//			} else if (n.getId() == 241) {
//				return true; }
//			else
//				return false;

			
			
//			11. ASHLEE:221, born 1254
//			10. LIA:233, born 1255
//			LCA: 7. NADINE:157, born 1152
			if (n.getId() == 221) {
				return true;
			} else if (n.getId() == 233) {
				return true; }
			else
				return false;

	}
		
		else if (this.filename == CONSTANTS.CASE_ONE_MILLION_CHILDREN) {
//			if (n.getBorn() == 1243 && n.getName().equals("JALYNN")) {
//			return true;
//		} else if (n.getBorn() == 1325 && n.getName().equals("JULIETTA")) {
//			return true;
//		} else
//			return false; 
	

		if (n.getId() == 977905) {
			return true;
		} else if (n.getId() == 978302) {
			return true;
		} else
			return false;
	}else if (this.filename == CONSTANTS.CASE_ONE_MILLION_CHILDREN_1K_GENERATIONS) {
//		if (n.getBorn() == 1243 && n.getName().equals("JALYNN")) {
//		return true;
//	} else if (n.getBorn() == 1325 && n.getName().equals("JULIETTA")) {
//		return true;
//	} else
//		return false; 


	if (n.getId() == 885168) {
		return true;
	} else if (n.getId() == 13038) {
		return true;
	} else
		return false;
}
		
		else
			return false;

		// 1M nodes
//		22. PAOLA:977905, born 1557
//		18. AAHANA:978302, born 1440
//		LCA: 12. UMA:977840, born 1283
		
		
//		1000 nodes
//		ALINE, born 1406
//		ISABELLE, born 1323
//		LCA should be SAPPHIRE, born 1295
		
//		10. JALYNN, born 1243
//		JULIETTA, born 1325
//		LCA: CARYS, born 1174
		
		// 20 nodes
		// 4. LYRA, born 1085
		// 4. CECILIA, born 1085
		// LCA should be 2. RENA, born 1033

		// if (n.getBorn() == 1085 && n.getName().equals("LYRA")) {
		// return true;
		// } else if (n.getBorn() == 1085 && n.getName().equals("CECILIA")) {
		// return true;
		// } else {
		// return false;
		// }
		// if (n.getBorn() == 1081 && n.getName().equals("AILANI")) {
		// return true;
		// } else if (n.getBorn() == 1076 && n.getName().equals("ELLIOT")) {
		// return true;
		// } else {
		// return false;
		// }

	}

}
