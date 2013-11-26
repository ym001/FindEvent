package WsSem.model;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the style database table.
 * 
 */
@Entity
@Table(name="style")
@NamedQuery(name="Style.findAll", query="SELECT s FROM Style s")
public class Style implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="Id")
	private int id;

	private String label;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="styles")
	@JsonIgnore
	private List<User> users;

	public Style() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@JsonIgnore
	public List<User> getUsers() {
		return this.users;
	}

	@JsonIgnore
	public void setUsers(List<User> users) {
		this.users = users;
	}

}