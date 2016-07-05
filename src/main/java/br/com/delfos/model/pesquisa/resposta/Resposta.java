package br.com.delfos.model.pesquisa.resposta;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Convert;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import br.com.delfos.converter.date.LocalDateTimePersistenceConverter;
import br.com.delfos.model.basic.Pessoa;
import br.com.delfos.model.generic.AbstractModel;
import br.com.delfos.model.pesquisa.Questionario;
import br.com.delfos.model.pesquisa.pergunta.Alternativa;
import br.com.delfos.model.pesquisa.pergunta.Pergunta;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoResposta")
public class Resposta<S extends Alternativa> extends AbstractModel<Resposta<?>> {

	public Resposta() {
		this.horaResposta = LocalDateTime.now();
	}

	public Resposta(Pessoa expert) {
		this();
		this.setExpert(Optional.ofNullable(expert));
	}

	@ManyToOne
	private Pessoa expert;

	@OneToOne
	private Pergunta<S> pergunta;

	@OneToOne
	private Questionario questionario;

	@Convert(converter = LocalDateTimePersistenceConverter.class)
	private LocalDateTime horaResposta;

	public void setExpert(Optional<Pessoa> optionalExpert) {
		optionalExpert.ifPresent(expert -> {
			if (expert.isEspecialista()) {
				this.expert = expert;
			} else
				throw new IllegalArgumentException("Essa pessoa não é um especialista válido.");
		});
	}

	public void setPergunta(Pergunta<S> pergunta) {
		Set<Pergunta<?>> perguntas = questionario.getPerguntas().orElseThrow(() -> new IllegalArgumentException(
				"é necessário que seja informado o questionario para ter acesso à pergunta."));

		if (perguntas.contains(pergunta)) {
			this.pergunta = pergunta;
		} else {
			throw new IllegalArgumentException(
					String.format("A pergunta %s não pertence ao questionário %s[código: %d]", pergunta.getNome(),
							this.questionario.getNome(), this.questionario.getId()));
		}
	}

	public Pergunta<S> getPergunta() {
		return pergunta;
	}

	public void setQuestionario(Questionario questionario) {
		this.questionario = questionario;
	}

	public Pessoa getExpert() {
		return expert;
	}

	public LocalDateTime getHoraResposta() {
		return horaResposta;
	}

}