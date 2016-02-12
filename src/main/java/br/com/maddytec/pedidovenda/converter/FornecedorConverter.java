package br.com.maddytec.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.maddytec.pedidovenda.model.Fornecedor;
import br.com.maddytec.pedidovenda.repository.Fornecedores;

@FacesConverter(forClass = Fornecedor.class)
public class FornecedorConverter implements Converter {

	@Inject
	private Fornecedores fornecedores;

	@Override
	public Object getAsObject(FacesContext context, UIComponent componet,
			String value) {
		Fornecedor retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			return retorno = fornecedores.porId(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			Fornecedor fornecedor = (Fornecedor) value;
			return fornecedor.getId() == null ? null : fornecedor.getId().toString();
		}

		return "";
	}

}
