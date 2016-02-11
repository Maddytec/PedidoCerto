package br.com.maddytec.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.maddytec.pedidovenda.model.Pedido;
import br.com.maddytec.pedidovenda.repository.Pedidos;

@FacesConverter(forClass = Pedido.class)
public class PedidoConverter implements Converter {

	@Inject
	private Pedidos pedidos;

	@Override
	public Object getAsObject(FacesContext context, UIComponent componet,
			String value) {
		Pedido retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			return retorno = pedidos.porId(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent componet,
			Object value) {
		if (value != null) {
			Pedido pedido = (Pedido) value;
			return pedido.getId() == null ? null : pedido.getId().toString();
		}
		return "";
	}

}
