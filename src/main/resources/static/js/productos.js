$(document).ready( function () {
	 var table = $('#productosTabla').DataTable({
			"sAjaxSource": "/listado",
			"sAjaxDataProp": "",
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			  
		    { "mData": "codigo" },				  
			{ "mData": "nombre" },
			{ "mData": "precioCompra" },
			{ "mData": "precioVenta" },
			{ "mData": "stock" }
				 
			]
	 })
});