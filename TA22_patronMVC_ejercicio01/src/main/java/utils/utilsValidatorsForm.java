/**
 * 
 */
package utils;

/**
 * 
 */
public class utilsValidatorsForm {
	
	public boolean validarMaxCaracteres(String text, int maxCaracteres) {
		boolean result = true;
		if(maxCaracteres < text.length()) {
			result = !result;
		}
		return result;
	}
	
	public boolean validarNumero(String text) {
		try {
			Integer.parseInt(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean validarNoCadenaVacia(String text) {
		return !text.isEmpty();
	}

}
