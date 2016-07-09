package br.com.delfos.model.pesquisa.resposta;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import br.com.delfos.model.pesquisa.pergunta.Alternativa;
import br.com.delfos.model.pesquisa.pergunta.TipoPergunta;

class QuestAlternativa extends Alternativa {

	public QuestAlternativa(TipoPergunta tipo) {
		super(tipo);
	}

	@Override
	public Resposta<?> createSimpleResposta() {
		return null;
	}

}

@Entity
@DiscriminatorValue("QUESTIONARIO")
public class RespostaQuestionario extends Resposta<QuestAlternativa> {

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RespostaQuestionario [getPergunta()=");
		builder.append(getPergunta());
		builder.append(", getExpert()=");
		builder.append(getExpert());
		builder.append(", getHoraResposta()=");
		builder.append(getHoraResposta());
		builder.append(", getQuestionario()=");
		builder.append(getQuestionario());
		builder.append(", hashCode()=");
		builder.append(hashCode());
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getClass()=");
		builder.append(getClass());
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}

}
