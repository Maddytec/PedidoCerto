package br.com.maddytec.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.maddytec.pedidovenda.model.Orcamento;
import br.com.maddytec.pedidovenda.repository.Orcamentos;

@FacesConverter(forClass = Orcamento.class)
public class OrcamentoConverter implements Converter {

	@Inject
	private Orcamentos orcamentos;

	@Override
	public Object getAsObject(FacesContext context, UIComponent componet,
			String value) {
		Orcamento retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			return retorno = orcamentos.porId(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent componet,
			Object value) {
		if (value != null) {
			Orcamento orcamento = (Orcamento) value;
			return orcamento.getId() == null ? null : orcamento.getId().toString();
		}
		return "";
	}

}
