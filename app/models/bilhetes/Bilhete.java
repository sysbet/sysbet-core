package models.bilhetes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import models.serializacoes.CalendarDeserializer;
import models.serializacoes.CalendarSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "bilhetes")
public class Bilhete implements Serializable{


    public enum Status {

        /**
         * Situacao em que o bilhete ainda não foi atualizado após o fim da partida
         */
        ABERTO("ABERTO"),
        /**
         * Todos os palpites do bilhete estão corretos
         */
        PREMIADO("PREMIADO"),
        /**
         * Pelo menos um palpite do bilhete está errado
         */
        ERRADO("ERRADO"),
        /**
         * O prêmio do bilhete foi pago
         */
        PAGO("PAGO"),
        /**
         * Desistiram do bilhete antes do início (?) da partida
         */
        CANCELADO("CANCELADO");

        private String string;

        Status(String string) {

            this.string = string;
        }
    }
    @Id
    @SequenceGenerator(name="bilhetes_bilhete_id_seq", sequenceName = "bilhetes_bilhete_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bilhetes_bilhete_id_seq")
    @Column(name = "bilhete_id",updatable = false)
    private Long id;

    @Column(name = "tenant_id")
    private Long tenant;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "status")
    private Status status;

    @Column(name = "cliente")
    private String cliente;

    @Column(name = "valor_aposta")
    private BigDecimal valorAposta;

    @Column(name = "valor_premio")
    private BigDecimal valorPremio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "criado_em")
    private Calendar criadoEm;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "alterado_em")
    private Calendar alteradoEm;

/*
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "bilhete_id")
    private List<Palpite> palpites;
*/

    public Bilhete() {

    }

    public Bilhete(Long tenant, String codigo, Status status, String cliente, BigDecimal valorAposta,
                   BigDecimal valorPremio, Calendar criadoEm, Calendar alteradoEm, List<Palpite> palpites) {

        this.tenant = tenant;
        this.codigo = codigo;
        this.status = status;
        this.cliente = cliente;
        this.valorAposta = valorAposta;
        this.valorPremio = valorPremio;
        this.criadoEm = criadoEm;
        this.alteradoEm = alteradoEm;
//        this.palpites = palpites;
    }

    public Long getId() {
        return id;
    }

    public Long getTenant() {
        return tenant;
    }

    public void setTenant(Long tenant) {
        this.tenant = tenant;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getValorAposta() {
        return valorAposta;
    }

    public void setValorAposta(BigDecimal valorAposta) {
        this.valorAposta = valorAposta;
    }

    public BigDecimal getValorPremio() {
        return valorPremio;
    }

    public void setValorPremio(BigDecimal valorPremio) {
        this.valorPremio = valorPremio;
    }

    @JsonDeserialize(using= CalendarDeserializer.class)
    public Calendar getCriadoEm() {
        return criadoEm;
    }

    @JsonSerialize(using= CalendarSerializer.class)
    public void setCriadoEm(Calendar criadoEm) {
        this.criadoEm = criadoEm;
    }

    @JsonDeserialize(using= CalendarDeserializer.class)
    public Calendar getAlteradoEm() {
        return alteradoEm;
    }

    @JsonSerialize(using= CalendarSerializer.class)
    public void setAlteradoEm(Calendar alteradoEm) {
        this.alteradoEm = alteradoEm;
    }

/*
    public List<Palpite> getPalpites() {
        return palpites;
    }

    public void setPalpites(List<Palpite> palpites) {
        this.palpites = palpites;
    }
*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bilhete bilhete = (Bilhete) o;

        if (id != null ? !id.equals(bilhete.id) : bilhete.id != null) return false;
        return tenant != null ? tenant.equals(bilhete.tenant) : bilhete.tenant == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (tenant != null ? tenant.hashCode() : 0);
        return result;
    }
}
