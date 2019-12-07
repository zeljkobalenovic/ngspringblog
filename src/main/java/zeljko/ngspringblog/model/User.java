package zeljko.ngspringblog.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * User
 */

@Entity
// problem je sto je u mysql user rezervisana rec pa sam tabeli dao drugo ime jer sam imao problema npr. u data.sql insert into user neradi
@Table(name = "user_table")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    private String email;
}