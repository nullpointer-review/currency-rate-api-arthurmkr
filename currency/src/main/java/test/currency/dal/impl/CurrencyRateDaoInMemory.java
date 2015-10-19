package test.currency.dal.impl;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import test.currency.dal.CurrencyRateDao;
import test.currency.entity.CurrencyRate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyRateDaoInMemory implements CurrencyRateDao {
    private Map<String, Map<String, CurrencyRate>> storage = new HashMap<String, Map<String, CurrencyRate>>();

    public void save(CurrencyRate currencyRate) {
        Map<String, CurrencyRate> storageByCode = storage.getOrDefault(currencyRate.getCode(), new HashMap<String, CurrencyRate>());
        storageByCode.putIfAbsent(currencyRate.getDate(), currencyRate);

        storage.put(currencyRate.getCode(), storageByCode);
    }

    public CurrencyRate getByCodeAndDate(String code, String date) {
        Map<String, CurrencyRate> storageByCode = storage.getOrDefault(code, new HashMap<String, CurrencyRate>());
        return storageByCode.get(date);
    }
}
