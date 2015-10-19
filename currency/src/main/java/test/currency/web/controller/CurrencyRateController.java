package test.currency.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.currency.exception.ApplicationException;
import test.currency.service.CurrencyRateService;
import test.currency.web.dto.CurrencyRateResponse;

@RestController
@RequestMapping("/api/rate")
public class CurrencyRateController {
    private static final Logger log = LoggerFactory.getLogger(CurrencyRateController.class);

    @Autowired
    private CurrencyRateService service;

    @RequestMapping("/{code}/{date}")
    public CurrencyRateResponse rate(@PathVariable("code") String code, @PathVariable("date") String date) throws ApplicationException {
        log.info("Rate for code[{}], date[{}]", code, date);
        return new CurrencyRateResponse(service.getCurrencyRate(code, date));
    }

    @RequestMapping("/{code}")
    public CurrencyRateResponse rate(@PathVariable("code") String code) throws ApplicationException {
        return rate(code, null);
    }
}
