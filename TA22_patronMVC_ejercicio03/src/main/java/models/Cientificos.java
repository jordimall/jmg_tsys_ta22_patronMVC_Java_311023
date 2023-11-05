/**
 * 
 */
package models;

/**
 * 
 */
public class Cientificos {

	private String DNI;
	private String NomAples;

	/**
	 * @return the dNI
	 */
	public String getDNI() {
		return DNI;
	}

	/**
	 * @return the nomApels
	 */
	public String getNomApels() {
		return NomAples;
	}

	/**
	 * @param dNI the dNI to set
	 */
	public void setDNI(String dNI) {
		DNI = dNI;
	}

	/**
	 * @param nomApels the nomApels to set
	 */
	public void setNomApels(String nomApels) {
		NomAples = nomApels;
	}

	
	public String toStringAdd() {
		return "\"" + DNI + "\", \"" + NomAples + "\"";
	}
	
	public String toStringUpdate() {
		return "DNI=\"" + DNI + "\", NomAples=\"" + NomAples + "\"";
	}

}
