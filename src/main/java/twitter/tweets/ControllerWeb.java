package twitter.tweets;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ControllerWeb {
//    @GetMapping("/getsentiments")
//    public Map<String, Double> getfromamount(String fromCurrency, String toCurrency, double amount, double convertedamount){
//        Map<String, Double> response = new HashMap<>();
//        CurrencyRequest currencyRequestFrom = new CurrencyRequest(fromCurrency, amount);
//        CurrencyRequest currencyRequestTo = new CurrencyRequest(toCurrency, convertedamount);
//        for (Currency currency : currenciesList) {
//            if(currency.getAbbreviation().equals(currencyRequestTo.getCurrency())){
//                currencyRequestTo.setId(currency.getId());
//                currencyRequestTo.setAmountToBYN(currency.getAmount());
//            }
//            if(currency.getAbbreviation().equals(currencyRequestFrom.getCurrency())){
//                currencyRequestFrom.setId(currency.getId());
//                currencyRequestFrom.setAmountToBYN(currency.getAmount());
//            }
//        }
//        currencyRequestFrom.setRateToBYN(getRequestedCurrencyRate(currencyRequestFrom.getId()));
//        currencyRequestTo.setRateToBYN(getRequestedCurrencyRate(currencyRequestTo.getId()));
//        double toRate=(currencyRequestFrom.getRateToBYN()*currencyRequestTo.getAmountToBYN())/
//                (currencyRequestTo.getRateToBYN()*currencyRequestFrom.getAmountToBYN());
//        convertedamount=amount*toRate;
//        response.put("amount", convertedamount);
//        return response;
//    }
}
