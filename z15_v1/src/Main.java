import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class Main
{
    private static final String XML_FILE = "main.xml";
    private static final String SCHEMA_FILE = "Orangery.xsd";
    private static final String HTML_FILE = "Report.html";
    private static final String XSL_FILE = "transform.xsl";

    private static boolean isValid() throws IOException, SAXException, ParserConfigurationException
    {
        DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = parser.parse(new File(XML_FILE));
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaFile = new StreamSource(new File(SCHEMA_FILE));
        Schema schema = factory.newSchema(schemaFile);
        Validator validator = schema.newValidator();
        try
        {
            validator.validate(new DOMSource(document));
            return true;
        } catch (SAXException e)
        {
            return false;
        }
    }

    private static List<Flower> DOMParse() throws IOException, SAXException, ParserConfigurationException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(XML_FILE));
        NodeList flowerElements = document.getDocumentElement().getElementsByTagName(Flower.FLOWER);
        ArrayList<Flower> flowers = new ArrayList<>();
        for (int i = 0; i < flowerElements.getLength(); i++)
        {
            Flower flower = new Flower();
            Node flowerNode = flowerElements.item(i);
            NodeList elements = flowerNode.getChildNodes();
            VisualParameters parameters = new VisualParameters();
            GrowingTips tips = new GrowingTips();
            for (int j = 0; j < elements.getLength(); j++)
            {
                Node currentNode = elements.item(j);
                if (currentNode.getNodeType() != Node.ELEMENT_NODE)
                    continue;

                switch (((Element) currentNode).getTagName())
                {
                    case VisualParameters.VISUAL_PARAMETERS -> {
                        NodeList parametersElements = currentNode.getChildNodes();
                        for (int k = 0; k < parametersElements.getLength(); k++)
                        {
                            Node node = parametersElements.item(k);
                            switch (node.getNodeName())
                            {
                                case VisualParameters.STEM_COLOR -> parameters.setStemColor(node.getTextContent());
                                case VisualParameters.LEAF_COLOR -> parameters.setLeafColor(node.getTextContent());
                                case VisualParameters.AVERAGE_SIZE -> parameters.setAverageSize(Double.parseDouble(node.getTextContent()));
                            }
                        }
                        flower.setParameters(parameters);
                    }
                    case GrowingTips.GROWING_TIPS -> {
                        NodeList parametersElements = currentNode.getChildNodes();
                        for (int k = 0; k < parametersElements.getLength(); k++)
                        {
                            Node node = parametersElements.item(k);
                            switch (node.getNodeName())
                            {
                                case GrowingTips.TEMPERATURE -> tips.setTemperature(Double.parseDouble(node.getTextContent()));
                                case GrowingTips.LIGHT_LOVING -> tips.setLightLoving(node.getTextContent().equals(GrowingTips.YES));
                                case GrowingTips.WATERING -> tips.setWatering(Double.parseDouble(node.getTextContent()));
                                case GrowingTips.MULTIPLYING -> tips.setMultiplying(node.getTextContent());
                            }
                        }
                        flower.setTips(tips);
                    }
                    case Flower.NAME -> flower.setName(currentNode.getTextContent());
                    case Flower.SOIL -> flower.setSoil(currentNode.getTextContent());
                    case Flower.ORIGIN -> flower.setOrigin(currentNode.getTextContent());
                }
            }
            flowers.add(flower);
        }
        return flowers;
    }

    private static class Handler extends DefaultHandler
    {
        public List<Flower> flowers = new ArrayList<>();
        private String lastElementName;
        private Flower flower = new Flower();
        private VisualParameters parameters = new VisualParameters();
        private GrowingTips tips = new GrowingTips();

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
        {
            lastElementName = qName;
        }

        @Override
        public void characters(char[] ch, int start, int length)
        {
            String information = new String(ch, start, length).replace("\n", "").trim();
            if (information.isEmpty())
                return;
            switch (lastElementName)
            {
                case Flower.NAME -> flower.setName(information);
                case Flower.ORIGIN -> flower.setOrigin(information);
                case Flower.SOIL -> flower.setSoil(information);
                case VisualParameters.LEAF_COLOR -> parameters.setLeafColor(information);
                case VisualParameters.STEM_COLOR -> parameters.setStemColor(information);
                case VisualParameters.AVERAGE_SIZE -> parameters.setAverageSize(Double.parseDouble(information));
                case GrowingTips.LIGHT_LOVING -> tips.setLightLoving(information.equals(GrowingTips.YES));
                case GrowingTips.MULTIPLYING -> tips.setMultiplying(information);
                case GrowingTips.TEMPERATURE -> tips.setTemperature(Double.parseDouble(information));
                case GrowingTips.WATERING -> tips.setWatering(Double.parseDouble(information));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName)
        {
            if (!qName.equals(Flower.FLOWER))
                return;
            flower.setParameters(parameters);
            flower.setTips(tips);
            flowers.add(flower);
            flower = new Flower();
            tips = new GrowingTips();
            parameters = new VisualParameters();
        }
    }

    interface Settable
    {
        void set();
    }

    private static String getText(XMLEventReader reader) throws XMLStreamException
    {
        return reader.nextEvent().asCharacters().getData();
    }

    private static List<Flower> StAXParse() throws FileNotFoundException, XMLStreamException
    {
        List<Flower> flowers = new ArrayList<>();

        AtomicReference<GrowingTips> tips = new AtomicReference<>();
        AtomicReference<VisualParameters> parameters = new AtomicReference<>();
        AtomicReference<Flower> flower = new AtomicReference<>();

        Settable setFlower = () ->
        {
            tips.set(new GrowingTips());
            parameters.set(new VisualParameters());
            flower.set( new Flower());
            flower.get().setParameters(parameters.get());
            flower.get().setTips(tips.get());
        };

        setFlower.set();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(XML_FILE));

        while (reader.hasNext())
        {
            XMLEvent xmlEvent = reader.nextEvent();
            if (xmlEvent.isStartElement())
            {
                StartElement startElement = xmlEvent.asStartElement();
                switch (startElement.getName().getLocalPart())
                {
                    case Flower.NAME -> flower.get().setName(getText(reader));
                    case Flower.ORIGIN -> flower.get().setOrigin(getText(reader));
                    case Flower.SOIL -> flower.get().setSoil(getText(reader));
                    case VisualParameters.LEAF_COLOR -> parameters.get().setLeafColor(getText(reader));
                    case VisualParameters.STEM_COLOR -> parameters.get().setStemColor(getText(reader));
                    case VisualParameters.AVERAGE_SIZE -> parameters.get().setAverageSize(Double.parseDouble(getText(reader)));
                    case GrowingTips.LIGHT_LOVING -> tips.get().setLightLoving(getText(reader).equals(GrowingTips.YES));
                    case GrowingTips.MULTIPLYING -> tips.get().setMultiplying(getText(reader));
                    case GrowingTips.TEMPERATURE -> tips.get().setTemperature(Double.parseDouble(getText(reader)));
                    case GrowingTips.WATERING -> tips.get().setWatering(Double.parseDouble(getText(reader)));
                }
            }
            if (xmlEvent.isEndElement())
            {
                if (xmlEvent.asEndElement().getName().getLocalPart().equals(Flower.FLOWER))
                {
                    flowers.add(flower.get());
                    setFlower.set();
                }
            }
        }
        return flowers;
    }

        private static List<Flower> SAXParse () throws ParserConfigurationException, SAXException, IOException
        {
            Handler handler = new Handler();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(XML_FILE, handler);
            return handler.flowers;
        }
        private static void transformXMLtoHTML() throws TransformerException, IOException
        {
            TransformerFactory factory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(new File(XSL_FILE));
            Transformer transformer = factory.newTransformer(xslt);
            Source xml = new StreamSource(new File(XML_FILE));
            transformer.transform(xml, new StreamResult(new File(HTML_FILE)));
            Desktop.getDesktop().open(new File(HTML_FILE));
        }
        public static void main (String[]args) throws IOException, ParserConfigurationException, SAXException, XMLStreamException, TransformerException
        {
            System.out.println("Документ валиден " + isValid());
            List<Flower> flowers = DOMParse();
            flowers.sort(Comparator.comparing(Flower::getName));
            System.out.println(flowers);
            System.out.println(SAXParse());
            System.out.println(StAXParse());
            transformXMLtoHTML();
        }
    }
