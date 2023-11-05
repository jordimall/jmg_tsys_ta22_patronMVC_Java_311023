/**
 * 
 */
package models;

/**
 * 
 */
public class Cientificos {

	private String DNI;
	private String NomApels;

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
		return NomApels;
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
		NomApels = nomApels;
	}

	
	public String toStringAdd() {
		return "\"" + DNI + "\", \"" + NomApels + "\"";
	}
	
	public String toStringUpdate() {
		return "DNI=\"" + DNI + "\", NomApels=\"" + NomApels + "\"";
	}

}
