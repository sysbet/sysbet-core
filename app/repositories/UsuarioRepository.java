package repositories;

import models.seguranca.Papel;
import models.seguranca.Usuario;
import models.vo.Confirmacao;
import models.vo.Tenant;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class UsuarioRepository implements Repository<Long,Usuario>{

    JPAApi jpaApi;

    @Inject
    public UsuarioRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Override
    public List<Usuario> todos(Tenant tenant) {
        return null;
    }

    @Override
    public Optional<Usuario> buscar(Tenant tenant, Long id) {
        return null;
    }

    @Override
    public CompletableFuture<Usuario> atualizar(Tenant tenant, Long id, Usuario updetable) {
        return null;
    }

    @Override
    public CompletableFuture<Usuario> inserir(Tenant tenant, Usuario novo) {
        EntityManager em = jpaApi.em();

        Papel admin = em.find(Papel.class, 1L);

        novo.setIdTenant(tenant.get());
        novo.setPapel(admin);
        jpaApi.em()
                .persist(novo);
        return CompletableFuture.completedFuture(novo);
    }

    @Override
    public CompletableFuture<Confirmacao> excluir(Tenant tenant, Long id) {
        return null;
    }



    public Optional<Usuario> registro(Tenant sysbet, String login, String senha) {
        try {
            return jpaApi.withTransaction((em) -> {

                TypedQuery<Usuario> query = em.createNamedQuery("validarLoginSenha", Usuario.class);
                query.setParameter("login", login);
                query.setParameter("senha", senha);

                return Optional.of(query.getSingleResult());
            });
        } catch (NoResultException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
