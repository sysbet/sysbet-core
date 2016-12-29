package api.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import controllers.ApplicationController;
import dominio.processadores.eventos.ResultadoInserirProcessador;
import models.eventos.Evento;
import models.eventos.Resultado;
import models.vo.Tenant;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.java.Secure;
import org.pac4j.play.store.PlaySessionStore;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import repositories.EventoRepository;
import repositories.ResultadoRepository;
import repositories.ValidadorRepository;
import dominio.validadores.Validador;
import dominio.validadores.exceptions.ValidadorExcpetion;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ResultadoController extends ApplicationController {

    ResultadoRepository resultadoRepository;
    ResultadoInserirProcessador inserirProcessador;
    ValidadorRepository validadorRepository;
    EventoRepository eventoRepository;

    @Inject
    public ResultadoController(ResultadoRepository resultadoRepository, PlaySessionStore playSessionStore,
                               ResultadoInserirProcessador inserirProcessador,
                               ValidadorRepository validadorRepository, EventoRepository eventoRepository) {
        super(playSessionStore);
        this.resultadoRepository = resultadoRepository;
        this.inserirProcessador = inserirProcessador;
        this.validadorRepository = validadorRepository;
        this.eventoRepository = eventoRepository;

    }

    @Secure(clients = "headerClient")
    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result inserir(Long idEvento) throws IOException {

        JsonNode json = Controller.request()
                .body()
                .asJson();
        ObjectMapper mapper = new ObjectMapper();

        Resultado[] resultado = mapper.readValue(json.toString(), Resultado[].class);

        //Melhorar
        if(resultado.length == 0)
        {
            return notFound("Lista de resultados não pode ser vazia!");
        }

        List<Validador> validadores = validadorRepository.todos(getTenant(), ResultadoInserirProcessador.REGRA);
        inserirProcessador = new ResultadoInserirProcessador(resultadoRepository);

        Optional<Evento> evento = eventoRepository.buscar(getTenant(), idEvento);
        if(!evento.isPresent()){
            return notFound("Evento não encontrado");
        }
        try {
            inserirProcessador.executar(getTenant(), resultado, evento.get(), validadores);
        } catch (ValidadorExcpetion validadorExcpetion) {
            return status(Http.Status.UNPROCESSABLE_ENTITY, validadorExcpetion.getMessage());
        }

        return created(Json.toJson(resultado));
    }

    @Secure(clients = "headerClient")
    @Transactional
    public Result todos() {

        List todos = resultadoRepository.todos(getTenant());
        return ok(Json.toJson(todos));
    }

    @Secure(clients = "headerClient")
    @Transactional
    public Result buscar(Long id) {

        Optional<Resultado> todos = (Optional<Resultado>) resultadoRepository.buscar(getTenant(), id);

        if (!todos.isPresent()) {
            return notFound("Resultado não encontrado!");
        }
        return ok(Json.toJson(todos));
    }

    @Secure(clients = "headerClient")
    @Transactional
    public Result excluir(Long id) {

        try {
            resultadoRepository.excluir(getTenant(), id);
            return noContent();
        } catch (NoResultException e) {
            return notFound(e.getMessage());
        }
    }
}