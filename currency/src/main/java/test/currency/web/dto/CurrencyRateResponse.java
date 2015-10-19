package test.currency.web.dto;

import test.currency.entity.CurrencyRate;

public class CurrencyRateResponse {
    private String code;
    private String rate;
    private String date;

    public CurrencyRateResponse(CurrencyRate currencyRate) {
        this.code = currencyRate.getCode();
        this.rate = currencyRate.getRate();
        this.date = currencyRate.getDate();
    }

    public String getCode() {
        return code;
    }

    public String getRate() {
        return rate;
    }

    public String getDate() {
        return date;
    }
}
