# reservation-service

Microservicio para gestionar reservas temporales de copias.

Maneja:
- usuario que reserva
- copia reservada
- fecha de reserva
- fecha de expiracion
- estado de la reserva

No consulta book-service directamente y no administra inventario. Para reservar o liberar una copia se comunica con copy-service.
