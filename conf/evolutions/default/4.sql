
CREATE SEQUENCE public.eventos_apostas_id_seq;

CREATE TABLE public.evento_apostas
(
  evento_aposta_id INTEGER NOT NULL DEFAULT nextval('public.eventos_apostas_id_seq'),
  permitir boolean,
  tenant_id INTEGER NOT NULL,
  evento_id INTEGER NOT NULL,
  CONSTRAINT evento_apostas_pkey PRIMARY KEY (evento_aposta_id)
)
WITH (
   OIDS=FALSE
);

ALTER TABLE public.evento_apostas
  OWNER TO postgres;
COMMENT ON TABLE public.evento_apostas IS 'Tabela com eventos apostáveis';


CREATE SEQUENCE public.taxas_taxa_id_seq;

CREATE TABLE public.taxas
(
  taxa_id INTEGER NOT NULL DEFAULT nextval('public.taxas_taxa_id_seq'),
  odd_id INTEGER NOT NULL,
  evento_aposta_id INTEGER NOT NULL,
  tenant_id INTEGER NOT NULL,
  alterado_em TIMESTAMP NOT NULL,
  criado_em TIMESTAMP NOT NULL,
  linha DECIMAL(5,2) NOT NULL,
  taxa DECIMAL(5,2) NOT NULL,
  CONSTRAINT taxas_pkey PRIMARY KEY (taxa_id)
)
  WITH (
    OIDS=FALSE
);

ALTER TABLE public.taxas
  OWNER TO postgres;
COMMENT ON TABLE public.taxas IS 'Tabela com taxas relacionadas a cada ODD e cobrada no momento da aposta';


CREATE SEQUENCE public.odds_odd_id_seq;

CREATE TABLE public.odds
(
  odd_id INTEGER NOT NULL DEFAULT nextval('public.odds_odd_id_seq'),
  abreviacao VARCHAR(255) NOT NULL,
  descricao VARCHAR(255) NOT NULL,
  mercado VARCHAR(255) NOT NULL,
  nome VARCHAR(255) NOT NULL,
  posicao INTEGER NOT NULL,
  prioridade INTEGER NOT NULL,
  tenant_id INTEGER NOT NULL,
  tipo_linha CHAR(1) NOT NULL,
  CONSTRAINT odds_pkey PRIMARY KEY (odds_odd_id)
)
WITH (
  OIDS=FALSE
);

ALTER TABLE public.odds
  OWNER TO postgres;
COMMENT ON TABLE public.odds IS 'Tabela de odds';

ALTER TABLE public.evento_apostas ADD CONSTRAINT eventos_evento_apostas_fk
FOREIGN KEY (evento_id)
REFERENCES public.eventos (evento_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.taxas ADD CONSTRAINT evento_apostas_taxas_fk
FOREIGN KEY (evento_aposta_id)
REFERENCES public.evento_apostas (evento_aposta_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.taxas ADD CONSTRAINT odds_taxas_fk
FOREIGN KEY (odd_id)
REFERENCES public.odds (odd_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

# --- !Downs

DROP TABLE IF EXISTS evento_apostas CASCADE;

DROP TABLE IF EXISTS taxas CASCADE;

DROP TABLE IF EXISTS odds CASCADE;

DROP SEQUENCE IF EXISTS public.eventos_apostas_id_seq CASCADE;

DROP SEQUENCE IF EXISTS public.taxas_taxa_id_seq CASCADE;

DROP SEQUENCE IF EXISTS public.odds_odd_id_seq CASCADE;