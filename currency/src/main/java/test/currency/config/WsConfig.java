package test.currency.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import test.currency.ws.BankCurrencyClient;

@Configuration
public class WsConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("ru.cbr.web");
        return marshaller;
    }

    @Bean
    public BankCurrencyClient weatherClient(Jaxb2Marshaller marshaller) {
        BankCurrencyClient client = new BankCurrencyClient();
        client.setDefaultUri("http://www.cbr.ru/DailyInfoWebServ");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
