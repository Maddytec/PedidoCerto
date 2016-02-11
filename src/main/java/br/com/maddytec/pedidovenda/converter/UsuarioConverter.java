package br.com.maddytec.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.maddytec.pedidovenda.model.Usuario;
import br.com.maddytec.pedidovenda.repository.Usuarios;

@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter {

	@Inject
	private Usuarios usuarios;

	@Override
	public Object getAsObject(FacesContext context, UIComponent componet,
			String value) {
		Usuario retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			return retorno = usuarios.porId(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			Usuario usuario = (Usuario) value;
			return usuario.getId() == null ? null : usuario.getId().toString();
		}

		return "";
	}

}
