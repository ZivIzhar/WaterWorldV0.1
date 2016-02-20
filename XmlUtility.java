import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;




public class XmlUtility {

	public static void main(String [ ] args) {
		initSystem();
	}

	public static void initSystem() {
		Integer maxID = 0;
		List<Integer> allIDs = new ArrayList<Integer>();
		DB<Ride> ridedb = createRidesDB(maxID,allIDs);
		Inspector inspector = new Inspector(ridedb,maxID,allIDs);
		DB<Child> childdb = new DB<Child>();
		WaterWorld ww = new WaterWorld(childdb,ridedb);
		
		createRidesXmlFile(ridedb,inspector);
	}
	
	/*
	 * Function returns a ride database based on the input xml file "initRides.xml" which
	 * supposed to be in the main project folder
	 * @param maxID - it's an output parameter, in the end of the function it'll contain 
	 * the maximum id of a ride, it's used for the inspector which adds new rides with id's
	 * larger than this id
	 * @param allIDs - another output parameter which contains all the ids, used for the inspector
	 */
	public static DB<Ride> createRidesDB(Integer maxID,List<Integer> allIDs) {
		DB<Ride> db = new DB<Ride>();
		try {
			File fXmlFile = new File("initRides.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("Ride");
			
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
						
				//System.out.println("\nCurrent Element :" + nNode.getNodeName());
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					String id = eElement.getElementsByTagName("id").item(0).getTextContent();
					Integer currID = Integer.valueOf(id);
					allIDs.add(currID);
					if(currID > maxID)
						maxID = currID;
					String minAge =  eElement.getElementsByTagName("MinAge").item(0).getTextContent();
					String minHeight = eElement.getElementsByTagName("MinHeight").item(0).getTextContent();
					String capacity = eElement.getElementsByTagName("Capacity").item(0).getTextContent();
					String extreme = eElement.getElementsByTagName("Extreme").item(0).getTextContent();
					if(Boolean.parseBoolean(extreme) == true) {
						Ride newRide = new ExtremeRide(Integer.parseInt(id),Integer.parseInt(capacity),Double.parseDouble(minHeight),
								Integer.parseInt(minAge));
						db.addElement(newRide);
					}
					else { 
						Ride newRide = new RegularRide(Integer.parseInt(id),Integer.parseInt(capacity),Double.parseDouble(minHeight),
								Integer.parseInt(minAge));
						db.addElement(newRide);
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return db;
	}
	
	/*
	 * Function writes the Rides data base to a xml file.
	 * it should be called when the current running is done so the new rides which
	 * the inspector added, will be saved
	 * @param db - the rides data base
	 * @param insp - Inspector object, needed for the rides which he added (the ids)
	 */
	public static void createRidesXmlFile(DB<Ride> db,Inspector insp) {
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Rides");
			doc.appendChild(rootElement);
			
			List<Integer> allRides = insp.getMyRides();
			for(Integer rideID: allRides) {
				Ride ride = db.getElement(rideID);
				
				Element rideElement = doc.createElement("Ride");
				rootElement.appendChild(rideElement);
				
				Element id = doc.createElement("id");
				id.appendChild(doc.createTextNode(Integer.toString(ride.getID())));
				rideElement.appendChild(id);
				
				Element minAge = doc.createElement("MinAge");
				minAge.appendChild(doc.createTextNode(Integer.toString(ride.getMinAge())));
				rideElement.appendChild(minAge);
				
				Element minHeight = doc.createElement("MinHeight");
				minHeight.appendChild(doc.createTextNode(Double.toString(ride.getMinHeight())));
				rideElement.appendChild(minHeight);
				
				Element capacity = doc.createElement("Capacity");
				capacity.appendChild(doc.createTextNode(Integer.toString(ride.getCapacity())));
				rideElement.appendChild(capacity);
				
				Element extreme = doc.createElement("Extreme");
				if(ride instanceof RegularRide) {
					extreme.appendChild(doc.createTextNode("false"));
				}
				else {
					extreme.appendChild(doc.createTextNode("true"));
				}
				rideElement.appendChild(extreme);
				
			}
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("initRides2.xml"));

			transformer.transform(source, result);

		  }
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
