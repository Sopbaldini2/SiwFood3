package it.uniroma3.siw.model;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	@Column(columnDefinition = "TEXT")
	private String description;
	@Column(columnDefinition = "TEXT")
	private String preparation;
	private String image;
	@ManyToOne
	@JoinColumn(name = "cooke_id", nullable = false)
	private User cooke;
	@ManyToMany
	private Set<Ingredient> ingredients;
    
	//Aggiungo i metodi Getter e Setter
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPreparation() {
		return preparation;
	}
	public void setPreparation(String preparation) {
		this.preparation = preparation;
	}
	public User getCooke() {
		return cooke;
	}
	public void setCooke(User cooke) {
		this.cooke = cooke;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Set<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	//Aggiungo i metodi Equals() e HashCode() 
	//Due oggetti Recipe sono uguali se hanno lo stesso id,lo stesso nome e lo stesso cuoco
	
	@Override
	public int hashCode() {
		return Objects.hash(cooke, id, name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recipe other = (Recipe) obj;
		return Objects.equals(cooke, other.cooke) && Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}
	
}

