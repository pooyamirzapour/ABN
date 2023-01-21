package com.abn.recipe.repository.ingredient;

import com.abn.recipe.repository.recipe.RecipeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@DynamicUpdate
@Table(name = "ingredients")
@Getter
@Setter
@NoArgsConstructor
public class IngredientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String ingredient;

    @ManyToMany(mappedBy = "ingredients", cascade = { CascadeType.MERGE })
    private Set<RecipeEntity> recipes = new HashSet<>();

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime insertDate;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedDate;

    public IngredientEntity(Integer id, String ingredient) {
        this.id = id;
        this.ingredient = ingredient;
    }
}
