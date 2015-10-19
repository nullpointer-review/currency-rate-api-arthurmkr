package test.currency.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cbr.web.GetCursOnDateXMLResponse;
import test.currency.dal.CurrencyRateDao;
import test.currency.entity.CurrencyRate;
import test.currency.exception.ApplicationException;
import test.currency.ws.BankCurrencyClient;
import test.currency.ws.GetCursOnDateResultParser;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class CurrencyRateService {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final Logger log = LoggerFactory.getLogger(CurrencyRateService.class);
    @Autowired
    private BankCurrencyClient currencyClient;

    @Autowired
    private CurrencyRateDao currencyRateDao;

    @Autowired
    private GetCursOnDateResultParser parser;

    public CurrencyRate getCurrencyRate(String code, String date) throws ApplicationException {
        Date parse;
        DateFormat format = new SimpleDateFormat(DATE_FORMAT);
        if (date != null) {
            try {
                parse = format.parse(date);
            } catch (ParseException e) {
                log.error("Parsing date error: ", e);
                throw new ApplicationException("Incorrect date [" + date + "]", e);
            }
        } else {
            Calendar tomorrow = Calendar.getInstance();
            tomorrow.add(Calendar.DAY_OF_YEAR, 1);
            parse = tomorrow.getTime();
            date = format.format(parse);
        }

        CurrencyRate existedRate = currencyRateDao.getByCodeAndDate(code, date);
        if (existedRate == null) {
            try {
                GetCursOnDateXMLResponse response = currencyClient.getCursOnDate(parse);
                GetCursOnDateResultParser.Currency value = parser.getValuteByValuteCh(code.toUpperCase(), response.getGetCursOnDateXMLResult());
                log.info("cursOnDate: {}", value);

                BigDecimal curs = value.getCurs();
                existedRate = new CurrencyRate(code, curs != null ? curs.toString() : "0", date);

                currencyRateDao.save(existedRate);
                log.info("{} is saved in storage", existedRate);
            } catch (Exception e) {
                log.error("WS error:", e);
                throw new ApplicationException("Internal error");
            }
        }

        return existedRate;
    }
}
