/**
 * 
 */
package models;

/**
 * 
 */
public class Videos {

	private int id;
	private String title;
	private String director;
	private int cli_id;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the director
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * @return the cli_id
	 */
	public int getCli_id() {
		return cli_id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param director the director to set
	 */
	public void setDirector(String director) {
		this.director = director;
	}

	/**
	 * @param cli_id the cli_id to set
	 */
	public void setCli_id(int cli_id) {
		this.cli_id = cli_id;
	}

	public String toStringAdd() {
		return title + ", " + director + ", " + cli_id;
	}

	public String toStringUpdate() {
		return "title=\"" + title + "\", director=\"" + director + "\", cli_id=" + cli_id;
	}

}
