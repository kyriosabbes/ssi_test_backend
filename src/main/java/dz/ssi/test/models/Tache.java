package dz.ssi.test.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "taches")
public class Tache {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "etat")
	private EtatTache etat;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "tache_user_id")
	private User tacheUser;

	public Tache() {
	}

	public Tache(String title, String description, EtatTache etat) {
		this.title = title;
		this.description = description;
		this.etat = etat;
	}

}
