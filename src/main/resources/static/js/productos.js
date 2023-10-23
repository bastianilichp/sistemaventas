   $('#userTable').DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": "/productos/mostrar",
        columns: [
            {
                data: 'nombre',
            },
            {
                data: 'codigo',
            },
            {
                data: 'precioCompra',
                
            },
            {
                data: 'precioVenta',
                
            },
            {
                data: 'stock',
               
            },
        ]
    });