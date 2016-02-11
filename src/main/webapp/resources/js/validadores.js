PrimeFaces.locales['pt_BR'] = {
	messages : {
		'org.hibernate.validator.constraints.NotBlank.message' : '{0} não pode estar em branco',
		'br.com.maddytec.constraints.SKU.message' : '{0} favor preencher no formato XX9999'
	}
}

/*
 * Conversão corrigida no primefaces 5.2 
 * deixando de ser necessaria a conversão
 * PrimeFaces.converter['br.com.maddytec.Categoria'] = {
 * 
 * convert : function(element, value) { if (value === null || value === ''){
 * return null; } return parseInt(value); } };
 */

PrimeFaces.validator.NotBlank = {

	MESSAGE_ID : 'org.hibernate.validator.constraints.NotBlank.message',

	validate : function(element, value) {
		if (value === null || value === undefined || value.trim() === '') {
			var msg = element.data('msg-notblank');
			var label = element.data('p-label');
			var context = PrimeFaces.util.ValidationContext;
			var msgObj;

			if (!msg) {
				msgObj = context.getMessage(this.MESSAGE_ID, label);
			} else {
				var msgObj = {
					summary : msg,
					detail : msg
				}
			}

			throw msgObj;
		}
	}

};

PrimeFaces.validator.SKU = {

	pattern : /^([a-zA-Z]{2}\d{4,18})?$/,

	validate : function(element, value) {
		if (!this.pattern.test(value)) {
			var msg = element.data('msg-sku');
			var label = element.data('p-label');
			var context = PrimeFaces.util.ValidationContext;
			var msgObj;

			if (!msg) {
				msgObj = context.getMessage(this.MESSAGE_ID, label);
			} else {
				var msgObj = {
					summary : msg,
					detail : msg
				}
			}

			throw msgObj;
		}
	}

};