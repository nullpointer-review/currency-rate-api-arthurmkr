package test.currency.dal.impl;

import org.junit.Test;
import test.currency.entity.CurrencyRate;
import static org.junit.Assert.*;

public class CurrencyRateDaoInMemoryTest {
    @Test
    public void testSaveAndGet() throws Exception {
        CurrencyRateDaoInMemory dao = new CurrencyRateDaoInMemory();

        CurrencyRate expected = new CurrencyRate("USD", "1", "2015-01-25");
        dao.save(expected);

        assertEquals(expected, dao.getByCodeAndDate("USD", "2015-01-25"));

        assertNull(dao.getByCodeAndDate("USD", "2015-01-24"));
    }
}
