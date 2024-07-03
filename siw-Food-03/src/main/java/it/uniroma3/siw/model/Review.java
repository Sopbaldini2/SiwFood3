package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(columnDefinition = "TEXT")
	@NotBlank(message = "{review.comment.notblank}")
	private String comment;
	
	/*@Column
    private Integer like;*/
	
	@ManyToOne
	private Recipe recipe;
	
	@ManyToOne
	private User cooke;
	
	
	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	

	public User getUser() {
		return cooke;
	}

	public void setUser(User cooke) {
		this.cooke = cooke;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/*public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }*/
	
	@Override
	public int hashCode() {
		return Objects.hash(comment, id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Review other = (Review) obj;
		return Objects.equals(comment, other.comment) && Objects.equals(id, other.id);
	}
}
