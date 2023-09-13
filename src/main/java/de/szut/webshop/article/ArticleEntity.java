package de.szut.webshop.article;

import de.szut.webshop.supplier.SupplierEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.*;;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="article")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aid;

    @NotBlank(message="Designation is mandatory")
    private String designation;

    @NotNull
    private Double price;

    @Column(name="create_date", nullable = false)
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name= "last_update_date", nullable = false)
    private LocalDateTime lastUpdateDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private SupplierEntity supplier;
}
