package com.abn.recipe.repository.recipe;

import com.abn.recipe.repository.ingredient.IngredientEntity;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "RECIPE")
@Getter
@Setter
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(cascade =  {CascadeType.MERGE})
    @JoinTable(
            name = "recipe_ingredient",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<IngredientEntity> ingredients= new HashSet<>();


    @Column(nullable = false)
    private String instruction;

    @Column(nullable = false)
    private String type;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime insertDate;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @Column
    private int numberOfServings;

}
