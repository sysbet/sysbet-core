package models.financeiro;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class Saldo {

    private BigDecimal saldo = BigDecimal.ZERO;
    private BigDecimal emprestimo = BigDecimal.ZERO;

    public Saldo() {
    }

    public Saldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(BigDecimal emprestimo) {
        this.emprestimo = emprestimo;
    }

    public BigDecimal getFechamento(){
        return saldo.subtract(emprestimo);
    }
}
