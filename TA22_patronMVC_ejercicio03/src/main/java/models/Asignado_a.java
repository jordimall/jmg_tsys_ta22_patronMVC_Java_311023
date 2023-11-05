/**
 * 
 */
package models;

/**
 * 
 */
public class Asignado_a {
	private String Cientifico;
	private String Proyecto;
	/**
	 * @return the cientifico
	 */
	public String getCientifico() {
		return Cientifico;
	}
	/**
	 * @return the proyecto
	 */
	public String getProyecto() {
		return Proyecto;
	}
	/**
	 * @param cientifico the cientifico to set
	 */
	public void setCientifico(String cientifico) {
		Cientifico = cientifico;
	}
	/**
	 * @param proyecto the proyecto to set
	 */
	public void setProyecto(String proyecto) {
		Proyecto = proyecto;
	}
	
	public String toStringAdd() {
		return "\"" + Cientifico + "\", \"" + Proyecto + "\"";
	}
	
	
	public String toString() {
		return "Cientifico=\"" + Cientifico + "\", Proyecto=\"" + Proyecto + "\"";
	}
	
	
}
