package test.currency.ws;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import ru.cbr.web.GetCursOnDateXML;
import ru.cbr.web.GetCursOnDateXMLResponse;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class BankCurrencyClient extends WebServiceGatewaySupport {
    private static final Logger log = LoggerFactory.getLogger(BankCurrencyClient.class);

    private static final String WSDL_URL = "http://www.cbr.ru/DailyInfoWebServ/DailyInfo.asmx";
    public static final String WSDL_METHOD_PREFIX = "http://web.cbr.ru/";

    public GetCursOnDateXMLResponse getCursOnDate(Date date) {
        Calendar instance = GregorianCalendar.getInstance();
        instance.setTime(date);

        GetCursOnDateXML request = new GetCursOnDateXML();
        request.setOnDate(new XMLGregorianCalendarImpl((GregorianCalendar) instance));

        return (GetCursOnDateXMLResponse) getWebServiceTemplate()
                .marshalSendAndReceive(
                        WSDL_URL,
                        request,
                        new SoapActionCallback(getCallbackUrl("GetCursOnDateXML")));
    }

    private String getCallbackUrl(String method) {
        return WSDL_METHOD_PREFIX + method;
    }

}
