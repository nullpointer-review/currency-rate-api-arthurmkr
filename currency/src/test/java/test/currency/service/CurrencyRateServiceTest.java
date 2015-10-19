package test.currency.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.cbr.web.GetCursOnDateXMLResponse;
import test.currency.dal.CurrencyRateDao;
import test.currency.entity.CurrencyRate;
import test.currency.exception.ApplicationException;
import test.currency.ws.BankCurrencyClient;
import test.currency.ws.GetCursOnDateResultParser;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.Mockito.*;

public class CurrencyRateServiceTest {
    @InjectMocks
    private CurrencyRateService service;
    @Mock
    private CurrencyRateDao currencyRateDao;
    @Mock
    private BankCurrencyClient bankCurrencyClient;
    @Mock
    private GetCursOnDateResultParser parser;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = ApplicationException.class)
    public void testIncorrectDate() throws Exception {
        service.getCurrencyRate("USD", "2015-sdf-23");
    }

    @Test(expected = ApplicationException.class)
    public void testUnavailableBankClient() throws Exception {
        when(bankCurrencyClient.getCursOnDate(any(Date.class))).thenThrow(ApplicationException.class);
        service.getCurrencyRate("USD", "2015-01-23");
    }


    @Test
    public void testSuccess() throws Exception {
        String expectedCode = "USD";
        String expectedRate = "65.12";

        GetCursOnDateXMLResponse response = mock(GetCursOnDateXMLResponse.class);
        GetCursOnDateXMLResponse.GetCursOnDateXMLResult value = mock(GetCursOnDateXMLResponse.GetCursOnDateXMLResult.class);

        when(response.getGetCursOnDateXMLResult()).thenReturn(value);
        GetCursOnDateResultParser.Currency currency = new GetCursOnDateResultParser.Currency();

        currency.setCurs(new BigDecimal(expectedRate));
        when(parser.getValuteByValuteCh(expectedCode, value)).thenReturn(currency);
        when(bankCurrencyClient.getCursOnDate(any(Date.class))).thenReturn(response);

        CurrencyRate currencyRate = service.getCurrencyRate(expectedCode, "2015-01-23");

        assertEquals(expectedCode, currencyRate.getCode());
        assertEquals(expectedRate, currencyRate.getRate());
    }
}
