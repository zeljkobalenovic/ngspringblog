package zeljko.ngspringblog.model;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Post
 */

 // lombok anotacije pomazu da nepisemo kod koji je lako napraviti automatski , u pojo klasama kao sto je ova koristimo tri
 // @data ce za sva polja napraviti getere i setere kao da smo ih kucali , kad se stavi na klasu , ako stavimo na polje npr @geter za to
 // polje ce napraviti geter 
 // 2 tipa konstruktora , bez argumenata i sa svim poljima 
 // mogu se dodati i druge anotacije npr @ToString i sl. - vrlo mocna stvar , kod je lepsi , upoznati se po potrebi sa svim mogucnostima 
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
 public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    // @NotBlank , @NotEmpty , @NotNull   - na primeru stringova (notnull i notempty vaze i za druge tipove polja)
    // not null znaci da polju mora biti dodeljena vrednost , a dozvoljeno je i prazan string npr title="" je ok 
    // not empty obavezuje i not null i plus da string mora imati vrednost , ali je npr title="    " ok dok title="" nije ok
    // not blank je samo za stringove i validira trimovanu vrednost tj. bez blankova , pa gornja dva primera nisu ok title="   a" je samo a sto se 
    // ove validacije tice i ok je.
    // sva tri se ticu samo validacije i sprovode se pre perzistencije
    // !!! NECE namestiti takve konstraite u bazi ako je pravimo iz spring boot ako hocemo da tako napravimo bazu moramo kao dole
    @Column(nullable=false)     // ovo ce u mysql postaviti not null konstrait , ali samo ako se pravi baza , postojecu koja recimo nema
    // not null konstrait nece postaviti - u prod se nikad baza nepravi iz jpa pa ovo kao zanimljivost dok se validacije (sve tri koriste)
    private String title;
    @Lob   // nece napraviti obican varchar u bazi nego lob (large objekt u bazi) , a u @column mozemo birati clob (charakter) ili blob (binary)
           // konkretno mysql napravi longtext tj. stane do 4 GB teksta sto je dosta za knjigu a ne blog 
    @NotEmpty
    private String content;
    private Instant createdOn;
//  ovako bi se postavila default vrednost ovog polja u mysql bazi pa ako se bilo kako updejtuje post automatski ce biti upisano tekuce vreme
    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant updatedOn;
    @NotBlank
    private String userName;
}