package it.uniroma3.siw.model;

import java.util.HashSet;
import java.util.List;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message = "{recipe.name.notblank}")
	private String name;
	@Column(columnDefinition = "TEXT")
	private String description;
	@Column(columnDefinition = "TEXT")
	private String preparation;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id", referencedColumnName = "id")
	private Image imageR;
	@ManyToOne
	@JoinColumn(name = "cooke_id", nullable = false)
	private User cooke;
	/*@ManyToMany
	private Set<Ingredient> ingredients;*/
	
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeIngredient> recipeIngredients = new HashSet<>();
	
	@OneToMany(mappedBy="recipe", cascade = CascadeType.ALL)
	private List<Review> reviews;
    
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
	public Image getImageR() {
		return imageR;
	}
	public void setImageR(Image imageR) {
		this.imageR = imageR;
	}
	/*public Set<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}*/
	
	public List<Review> getReviews() {
		return reviews;
	}
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	
	public Set<RecipeIngredient> getRecipeIngredients() {
		return recipeIngredients;
	}
	public void setRecipeIngredients(Set<RecipeIngredient> recipeIngredients) {
		this.recipeIngredients = recipeIngredients;
	}
	
	//Aggiungo i metodi Equals() e HashCode() 
	//Due oggetti Recipe sono uguali se hanno lo stesso id,lo stesso nome e lo stesso cuoco
	
	
	@Override
	public int hashCode() {
		return Objects.hash(cooke, id, name, reviews);
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
		return Objects.equals(cooke, other.cooke) && Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(reviews, other.reviews);
	}
	
}

