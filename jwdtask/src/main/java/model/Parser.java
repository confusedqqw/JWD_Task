package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private static final String TAG_NAME_MAIN ="name";
    private static final String TAG_EMPLOYEES ="employees";
    private static final String TAG_ELEMENT ="element";
    private static final String TAG_NAME ="name";
    private static final String TAG_SALARY ="salary";
    private static final String TAG_POSITION ="position";


    public Root parse() {
        Root root = new Root();

        Document document = null;
        try {
            document = buildDocument();
        } catch (Exception e) {
            System.out.println("Open parsing error" + e.toString());
            return null;
        }

        Node rootNode = document.getFirstChild();
        NodeList rootChilds = rootNode.getChildNodes();

        String mainName = null;
        Node employeesNode = null;
        for (int i = 0; i < rootChilds.getLength(); i++) { //inside root
            if (rootChilds.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            switch (rootChilds.item(i).getNodeName()) {
                case TAG_NAME_MAIN: {
                    mainName = rootChilds.item(i).getTextContent();
                    break;
                }

                case TAG_EMPLOYEES: {
                    employeesNode = rootChilds.item(i);
                    break;
                }
            }
        }
        if (employeesNode == null) {
            return null;
        }
        List<Employees> employeesList = parsEmployeesList(employeesNode);
        root.setName(mainName);
        root.setEmployees(employeesList);
        root.projectPrice();
        root.positionAmmount();
        System.out.println("Root " + root.toString());


        try {
            Document doc2 = buildDocument();
            WriteXML(doc2,root);
        } catch (Exception e){
            System.out.println("Open parsing error" + e.toString());
            return null;
        }

        return root;
    }
    
    private  Document buildDocument() throws Exception{
        File file = new File("itcompany.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        return dbf.newDocumentBuilder().parse(file);

    }

    private  List<Employees> parsEmployeesList(Node employeesNode) {
        List<Employees> employeesList = new ArrayList<>();
        NodeList employeesChilds = employeesNode.getChildNodes();
        for (int i = 0; i < employeesChilds.getLength(); i++) {  //inside employees
            if (employeesChilds.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            if (!employeesChilds.item(i).getNodeName().equals(TAG_ELEMENT)) {
                continue;
            }

            Employees employees = parsElement(employeesChilds.item(i));
            employeesList.add(employees);
        }
        return employeesList;
    }

    private  Employees parsElement(Node elementNode){
        NodeList elementChilds = elementNode.getChildNodes();
        String name = "";
        int salary = 0;
        String position = "";
        for (int j = 0; j < elementChilds.getLength(); j++) { //inside element
            if (elementChilds.item(j).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            switch (elementChilds.item(j).getNodeName()) {
                case TAG_NAME: {
                    name = elementChilds.item(j).getTextContent();
                    break;
                }
                case TAG_SALARY: {
                    salary = Integer.valueOf(elementChilds.item(j).getTextContent());
                    break;
                }
                case TAG_POSITION: {
                    position = elementChilds.item(j).getTextContent();
                    break;
                }
            }
        }
        Employees employees = new Employees(name, salary, position);
        return employees;
    }

    private static void WriteXML(Document doc,Root root) throws TransformerException {
        Element project = doc.createElement("ProjectTeam");
        doc.appendChild(project);

        makeNode(doc,project,"name",root.getName());
        makeNode(doc,project,"projectPrice",String.valueOf(root.getTotalPrice()));
        makeNode(doc,project,"developersAmount",String.valueOf(root.getDev()));
        makeNode(doc,project,"testersAmount",String.valueOf(root.getTester()));
        makeNode(doc,project,"qaEngineersAmount",String.valueOf(root.getQa()));
        makeNode(doc,project,"projectManagersAmount",String.valueOf(root.getProjectManager()));

        File file2 = new File("project.xml");
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        transformer.transform(new DOMSource(doc),new StreamResult(file2));

    }

    public static void makeNode(Document doc,Element parentsNode,String name,String data){
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(data));
        parentsNode.appendChild(node);
    }
}
