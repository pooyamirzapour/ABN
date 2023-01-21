package com.abn.recipe.repository.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * User Entity keeps the data related to users security
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */

@Entity
@Table(name = "User")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @NotNull
    @Column(unique = true)
    private String username;

    @Column
    @JsonIgnore
    @NotBlank
    @NotNull
    private String password;

    @Column
    private LocalDateTime tokenExpireDate;



}