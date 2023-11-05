/**
 * 
 */
package models;

/**
 * 
 */
public class Proyecto {
	private String Id;
	private String Nombre;
	private int Horas;

	/**
	 * @return the id
	 */
	public String getId() {
		return Id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return Nombre;
	}

	/**
	 * @return the horas
	 */
	public int getHoras() {
		return Horas;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		Id = id;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	/**
	 * @param horas the horas to set
	 */
	public void setHoras(int horas) {
		Horas = horas;
	}

	public String toStringAdd() {
		return "\"" + Id + "\", \"" + Nombre + "\", " + Horas;
	}

	public String toStringUpdate() {
		return "Id=\"" + Id + "\", Nombre=\"" + Nombre + "\", Horas=" + Horas;
	}

}
