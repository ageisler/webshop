package de.szut.webshop.exchangerateservice;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class ExchangeRateService {
    private final RestTemplate restTemplate;

    private String url = "https://api.exchangerate.host/";

    public ExchangeRateService(){
        this.restTemplate = new RestTemplate();
    }

    public RateDto convert(String from, String to, Double amount){
        RateDto response = this.restTemplate.getForObject(url + "convert?from=" + from + "&to=" + to, RateDto.class);
        RateDto dto  = new RateDto();
        BigDecimal result = BigDecimal.valueOf(response.getResult()).multiply(BigDecimal.valueOf(amount));

        // Runde das Ergebnis auf 2 Nachkommastellen
        BigDecimal roundedResult = result.setScale(2, RoundingMode.HALF_UP);
        dto.setResult(roundedResult.doubleValue());
        return dto;
    }
}
