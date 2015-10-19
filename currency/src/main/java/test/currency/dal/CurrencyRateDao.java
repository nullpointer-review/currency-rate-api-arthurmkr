package test.currency.dal;

import test.currency.entity.CurrencyRate;

public interface CurrencyRateDao {
    void save(CurrencyRate currencyRate);

    CurrencyRate getByCodeAndDate(String code, String date);
}
