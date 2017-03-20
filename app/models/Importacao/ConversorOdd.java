package models.Importacao;

import com.google.common.collect.ImmutableMap;
import models.apostas.Odd;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ConversorOdd {

    List<Odd> odds;

    Map<String, String> map = ImmutableMap.of("oddCasa", "nomeOddReal")
            .of("v", "a");

    Map<String, BigDecimal> mapLinha = ImmutableMap.of("oddAcima25", BigDecimal.valueOf(2.5));

    public ConversorOdd(List<Odd> odds) {
        this.odds = odds;
    }

    public Optional<Odd> from(String nome) {
        String nomeOddSistema = map.get(nome);
        return odds.stream().filter( odd -> odd.getNome().equals(nomeOddSistema))
                .findFirst();
    }

    public BigDecimal linha(String oddName) {
        if(!mapLinha.containsKey(oddName))
            return BigDecimal.ZERO;
        return null;
    }
}
