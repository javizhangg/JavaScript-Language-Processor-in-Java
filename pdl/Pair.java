package pdl123.pdl;

class Pair {
	private Integer estado; // El estado puede ser null en caso de error
	private Object accion; // La acción puede ser un Character o un Integer

	// Constructor para estado y acción (Character)
	Pair(Integer estado, Character accion) {
		this.estado = estado;
		this.accion = accion;
	}

	// Constructor para error (null estado, Integer acción)
	Pair(Integer error) {
		this.estado = null; // Estado es null en caso de error
		this.accion = error;
	}

	public Integer getEstado() {
		return estado;
	}

	public Object getAccion() {
		return accion;
	}

}