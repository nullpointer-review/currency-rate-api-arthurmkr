package test.currency.ws;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import com.sun.org.apache.xerces.internal.dom.TextImpl;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.cbr.web.GetCursOnDateXMLResponse;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetCursOnDateResultParser {
    public Currency getValuteByValuteCh(String valuteCh, GetCursOnDateXMLResponse.GetCursOnDateXMLResult result) throws Exception {
        List<Object> list = result.getContent();
        ElementNSImpl e = (ElementNSImpl) list.get(0);
        NodeList chCodeList = e.getElementsByTagName("VchCode");

        Map<String, Object> props = getObjectProperties(valuteCh, result, chCodeList);
        return new ObjectMapper().convertValue(props, Currency.class);
    }

    private Map<String, Object> getObjectProperties(String valuteCh, GetCursOnDateXMLResponse.GetCursOnDateXMLResult result, NodeList chCodeList) {
        Map<String, Object> props = new HashMap<String, Object>();

        int length = chCodeList.getLength();

        boolean isFound = false;
        for (int i = 0; i < length; i++) {
            if (isFound) break;

            Node valuteChNode = chCodeList.item(i);
            TextImpl textimpl = (TextImpl) valuteChNode.getFirstChild();
            String chVal = textimpl.getData();

            if (chVal.equalsIgnoreCase(valuteCh)) {
                isFound = true;
                Node parent = valuteChNode.getParentNode();
                NodeList nodeList = parent.getChildNodes();
                int paramLength = nodeList.getLength();

                for (int j = 0; j < paramLength; j++) {
                    Node currentNode = nodeList.item(j);

                    String name = currentNode.getNodeName();
                    Node currentValue = currentNode.getFirstChild();
                    String value = currentValue.getNodeValue();

                    props.put(transformName(name), value);
                }
            }
        }
        return props;
    }

    private String transformName(String name) {
        return name.substring(1, name.length());
    }

    public static class Currency {
        private String name;
        private String chCode;
        private int code;
        private BigDecimal nom;
        private BigDecimal curs;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getChCode() {
            return chCode;
        }

        public void setChCode(String chCode) {
            this.chCode = chCode;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public BigDecimal getNom() {
            return nom;
        }

        public void setNom(BigDecimal nom) {
            this.nom = nom;
        }

        public BigDecimal getCurs() {
            return curs;
        }

        public void setCurs(BigDecimal curs) {
            this.curs = curs;
        }

        @Override
        public String toString() {
            return "Currency{" +
                    "name='" + name + '\'' +
                    ", chCode='" + chCode + '\'' +
                    ", code=" + code +
                    ", nom=" + nom +
                    ", curs=" + curs +
                    '}';
        }
    }

}
