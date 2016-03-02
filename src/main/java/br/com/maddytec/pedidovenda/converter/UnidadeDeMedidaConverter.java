package br.com.maddytec.pedidovenda.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import br.com.maddytec.pedidovenda.model.UnidadeDeMedida;
import br.com.maddytec.pedidovenda.repository.UnidadeDeMedidas;

@FacesConverter(forClass = UnidadeDeMedida.class)
public class UnidadeDeMedidaConverter implements Converter, ClientConverter {

	@Inject
	private UnidadeDeMedidas unidadeDeMedidas;

	@Override
	public Object getAsObject(FacesContext context, UIComponent componet,
			String value) {
		UnidadeDeMedida retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			return retorno = unidadeDeMedidas.porId(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			UnidadeDeMedida unidadeDeMedida = (UnidadeDeMedida) value;
			return unidadeDeMedida.getId() == null ? null : unidadeDeMedida.getId()
					.toString();
		}

		return "";
	}

	@Override
	public String getConverterId() {
		return "br.com.maddytec.UnidadeDeMedida";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

}
