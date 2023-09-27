var app = {
	
	init : function(){	
		app.initDatatable('#productosTable');
	},
	initDatatable : function(id){
		$(id).DataTable({
			
			ajax : {
				url : '/productos/mostrar',
				dataSrc : function(json){
					return json;
				}
			},
			columns : [
				{data: "nombre"},
				{data: "codigo"},
				{data: "precioCompra"},
				{data: "precioVenta"},
				{data: "stock"}			
			]
			
		});
	}
};
$(document).ready(function(){
	app.init();
});