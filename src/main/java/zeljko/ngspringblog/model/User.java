package zeljko.ngspringblog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * User
 */

 // zbog koriscenja lomboka kod je puno lepsi 

@Entity
// problem je sto je u mysql user rezervisana rec pa sam tabeli dao drugo ime jer sam imao problema npr. u data.sql insert into user neradi
@Table(name = "user_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    private String email;
}