package dominio.validadores.bilhete;

import dominio.validadores.Validador;
import dominio.validadores.exceptions.ValidadorExcpetion;
import models.bilhetes.Bilhete;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class HabilitadoRevendedorApostasValidador extends Validador<Bilhete> {

    public HabilitadoRevendedorApostasValidador() {
    }

    public HabilitadoRevendedorApostasValidador(Long idTenant, String regra, Long valorInteiro, Boolean valorLogico, BigDecimal valorDecimal, String valorTexto) {
        super(idTenant, regra, valorInteiro, valorLogico, valorDecimal, valorTexto);
    }

    @Override
    public void validate(Bilhete entity) throws ValidadorExcpetion {
        if(!getValorLogico()) {
            throw new ValidadorExcpetion("Revendedor não habilitado para fazer apostas.");
        }
    }
}
