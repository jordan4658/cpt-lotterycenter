package com.caipiao.live.common.util;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Map Xml 转换工具类
 */
public class MapXmlUtils {

    private static final Logger logger = LoggerFactory.getLogger(MapXmlUtils.class);

    // Map转换成XML String
    public static String toString(Map<String, Object> map, String rootName) throws IOException {
        if (StringUtils.isEmpty(rootName)) {
            rootName = "xml";
        }
        if (map == null || map.size() == 0) {
            return String.format("<%s></%s>", rootName, rootName);
        }

        ByteArrayOutputStream outputStream = null;
        XMLWriter writer = null;
        String xml = null;
        try {
            outputStream = new ByteArrayOutputStream();
            writer = new XMLWriter(outputStream);

            Element root = DocumentHelper.createElement(rootName);
            map.forEach((key, val) -> {
                Element childElm = null;
                if (StringUtils.isNotEmpty(key) && val != null) {
                    childElm = DocumentHelper.createElement(key);
                    childElm.setText(val.toString());
                    root.add(childElm);
                }
            });

            Document document = DocumentHelper.createDocument(root);
            writer.write(document);
            writer.flush();
        } catch (Exception ex) {
            logger.error("paylog Map Xml 转换工具类 Exception MapXmlUtils toString", ex);
        } finally {
            if (writer != null) {
                writer.close();
            }

            if (outputStream != null) {
                xml = outputStream.toString();
                outputStream.close();
            }
        }
        return xml;
    }

    // XML String转换成Map
    public static Map<String, Object> toMap(String xml) throws ParserConfigurationException,
            IOException, SAXException, DocumentException {
        if (StringUtils.isEmpty(xml)) {
            return new HashMap<String, Object>();
        }

        SAXReader reader = new SAXReader();
        Document document = DocumentHelper.parseText(xml);
        Element root = document.getRootElement();

        Map<String, Object> resultMap = new HashMap<String, Object>();
        for (Iterator it = root.elementIterator(); it.hasNext(); ) {
            Element element = (Element) it.next();
            String name = element.getName();
            String value = element.getTextTrim();
            if (StringUtils.isNoneEmpty(name, value)) {
                resultMap.put(name, value);
            }
        }
        return resultMap;
    }
}
