package it.uniroma3.siw.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message = "{ingredient.name.notblank}")
	private String name;
	
	//private Float quantita;
	
	@OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeIngredient> recipeIngredients = new HashSet<>();
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@OneToOne
	@JoinColumn(name = "image_id", referencedColumnName = "id")
	private Image imageI;
	
	/*@ManyToMany(mappedBy="ingredients")
	private Set<Recipe> recipes;*/
	
	public Ingredient() {
		//this.recipes = new HashSet<>();
	}

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

	/*public Float getQuantita() {
		return quantita;
	}

	public void setQuantita(Float quantita) {
		this.quantita = quantita;
	}*/
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Image getImageI() {
		return imageI;
	}

	public void setImageI(Image imageI) {
		this.imageI = imageI;
	}	

	/*public Set<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(Set<Recipe> recipes) {
		this.recipes = recipes;
	}*/

	public Set<RecipeIngredient> getRecipeIngredients() {
		return recipeIngredients;
	}

	public void setRecipeIngredients(Set<RecipeIngredient> recipeIngredients) {
		this.recipeIngredients = recipeIngredients;
	}
	
	//Aggiungo i metodi Equals() e HashCode() 
	//Due oggetti Recipe sono uguali se hanno lo stesso id, lo stesso nome e la stessa ricetta

	@Override
	public int hashCode() {
		return Objects.hash( name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingredient other = (Ingredient) obj;
		return Objects.equals(name, other.name);
	}
}
