package server.AlwaysCare.domain.pet.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import server.AlwaysCare.domain.user.entity.UserAccount;
import server.AlwaysCare.global.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static lombok.AccessLevel.PROTECTED;

@DynamicInsert
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode()
@ToString
public class PetAccount extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserAccount user;

    private String name;
    private String imageURL;
    private int age;
    private int type;
    private String species;


    public PetAccount(UserAccount user, String name, String imageURL, int age, int type, String species) {
        this.user = user;
        this.name = name;
        this.imageURL = imageURL;
        this.age = age;
        this.type = type;
        this.species = species;
    }
}

