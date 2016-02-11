package br.com.maddytec.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.maddytec.pedidovenda.model.Grupo;
import br.com.maddytec.pedidovenda.repository.Grupos;

@FacesConverter(forClass = Grupo.class)
public class GrupoConverter implements Converter {
	
	@Inject
	private Grupos grupos;
		
	@Override
	public Object getAsObject(FacesContext context, UIComponent componet, String value) {
		Grupo retorno = null; 
		
		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			return retorno = grupos.porId(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent componet, Object value) {
		if(value != null){
			Grupo grupo = (Grupo) value; 
			return grupo.getId() == null ? null : grupo.getId().toString();
		}
		return "";
	}

	
}
 