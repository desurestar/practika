package ru.inversion.jinvfc.controller;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.xml.sax.SAXException;
import ru.inversion.jinvfc.entity.Pparam;
import ru.inversion.jinvfc.entity.Pproc;

/**
 *
 * @author psh
 */
public class PlsqlDefLoader 
{
//
// PlsqlDefLoader
//    
    public PlsqlDefLoader ( URL def ) 
    {
        if (def == null)
            throw new RuntimeException (getClass ().getName () + ": def is null");
        
        try 
        {
            urlDef = def;
            
            DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance ();            
            DBF.setNamespaceAware (true);
            DBF.setIgnoringComments(true);
            DBF.setIgnoringElementContentWhitespace(true);
            
            DocumentBuilder DB = DBF.newDocumentBuilder ();
            
            xmlDef = DB.parse (urlDef.openStream ());
        } 
        catch (ParserConfigurationException | IOException | SAXException ex) 
        {
            throw new RuntimeException (def.toString (), ex);
        }
        
        xPath = XPathFactory.newInstance ().newXPath ();
    }   
//
// getProcList
//
    public List <Pproc> getProcList ()
    {
        List <Pproc> lp = new ArrayList <> ();
        
        NodeList el = xmlDef.getDocumentElement ().getElementsByTagName ("expression");
        for (int i=0; i< el.getLength (); i++)
        {
            Element e = (Element) el.item (i);
            
            Pproc proc = new Pproc ();
            proc.setName (e.getAttribute ("name"));
            proc.setLang (e.getAttribute ("lang"));
            proc.setFile (e.getAttribute ("file"));
            
            NodeList sl = e.getElementsByTagName ("script");
            if (sl.getLength () > 0)
            {    
                NodeList ch = sl.item (0).getChildNodes ();
                for (int k = 0; k < ch.getLength (); k++)
                {
                    Node n = ch.item (k);
                    if (n.getNodeType () == Node.CDATA_SECTION_NODE) 
                        proc.setScript (n.getNodeValue ());
                }     
            }
            
            ArrayList <Pparam> l = new ArrayList ();
            NodeList pl = e.getElementsByTagName ("parameter");
            for (int k = 0; k < pl.getLength (); k++)
            {
                Element par = (Element) pl.item (k);
                
                Pparam param = new Pparam ();
                param.setName (par.getAttribute ("name"));
                param.setPosition (par.hasAttribute ("position") ? Long.parseLong (par.getAttribute ("position")) : null);
                param.setJavaType (par.getAttribute ("javaType"));
                param.setSqlType (par.getAttribute ("sqlType"));
                param.setElementType (par.getAttribute ("elementType"));
                param.setDefaultValue (par.getAttribute ("defaultValue"));
                param.setMode (par.getAttribute("mode"));
                
                l.add (param);
            }     
            
            proc.setParam (l);
            lp.add (proc);
        }
        
        return lp;
    }        
//
// selectElement
//
    private Element selectElement ( String xp )
    {   
        try {
            return (Element) xPath.evaluate (xp, xmlDef, XPathConstants.NODE);
        }
        catch (XPathExpressionException e)
        {
            throw new RuntimeException (xp, e);
        }
    }
//
// sqlFromFile
//
    private String sqlFromFile ( String fname )     
    {
        char [] cbuf = new char [2000];
        int cur; 
        StringBuilder sb = new StringBuilder ();
        
        try 
        { 
            URL ufile = new URL (urlDef, fname);
            try (InputStreamReader is = new InputStreamReader (ufile.openStream ())) {
                do {
                    cur = is.read (cbuf);
                    
                    if (cur > 0)
                        sb.append (cbuf, 0, cur);
                } while (cur == 2000);
            }
        } 
        catch (Exception ex) {
            throw new RuntimeException (String.format ("%1$s: def=%2$s file=%3$s", getClass ().getName (), urlDef, fname), ex);
        }
               
        return sb.toString ();        
    }    
//
// getDef
//    
    public PlsqlDef getDef ( String procName )
    {
        Element procDef = selectElement ("/root/proc[@name=\"" + procName + "\"]");
        String sql = null;
        
        if (procDef == null)
            throw new RuntimeException (String.format ("%1$s no proc %2$s def=%3$s", getClass ().getName (), procName, urlDef));
            
        NodeList param = procDef.getElementsByTagName ("parameter");
           
        if (procDef.hasAttribute ("file"))
            sql = sqlFromFile (procDef.getAttribute ("file"));
        else
        {    
            NodeList ch = procDef.getChildNodes ();
            for (int i = 0; i < ch.getLength (); i++)
            {
                Node n = ch.item (i);
                if (n.getNodeType () == Node.CDATA_SECTION_NODE) 
                    sql = n.getNodeValue () + "/*" + urlDef + "*/";
            }
        }
            
        return new PlsqlDef (sql, param);
    }  
//
// PlSqlDef
//    
    public class PlsqlDef
    {    

        public PlsqlDef (String sql, NodeList param) 
        {
            this.sql = sql;
            this.param = param;
        }

        public String getSql () {
            return sql;
        }

        public NodeList getParam () {
            return param;
        }
        
        private final String sql;
        private final NodeList param;
    }
//
//
//    
    private final URL urlDef;
    private final Document xmlDef;        
    private final XPath xPath;    
//
//
//    
}
