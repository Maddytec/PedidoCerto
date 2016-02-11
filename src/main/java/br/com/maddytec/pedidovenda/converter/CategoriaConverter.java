package br.com.maddytec.pedidovenda.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.convert.ClientConverter;

import br.com.maddytec.pedidovenda.model.Categoria;
import br.com.maddytec.pedidovenda.repository.Categorias;

@FacesConverter(forClass = Categoria.class)
public class CategoriaConverter implements Converter, ClientConverter {

	@Inject
	private Categorias categorias;

	@Override
	public Object getAsObject(FacesContext context, UIComponent componet,
			String value) {
		Categoria retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			return retorno = categorias.porId(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			Categoria categoria = (Categoria) value;
			return categoria.getId() == null ? null : categoria.getId()
					.toString();
		}

		return "";
	}

	@Override
	public String getConverterId() {
		return "br.com.maddytec.Categoria";
	}

	@Override
	public Map<String, Object> getMetadata() {
		return null;
	}

}
