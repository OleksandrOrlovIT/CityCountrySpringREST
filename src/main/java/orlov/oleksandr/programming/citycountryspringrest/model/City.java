package orlov.oleksandr.programming.citycountryspringrest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "City name cannot be empty")
    private String cityName;

    @ManyToOne
    @JoinColumn(name = "country_id")
    @NotNull(message = "City`s country cannot be null")
    private Country country;

    @NotNull(message = "City`s population cannot be null")
    @Min(value = 0, message = "City`s population cannot be less then 0")
    private Integer cityPopulation;

    @NotNull(message = "City`s area cannot be null")
    @DecimalMin(value = "0.0", message = "City`s area cannot be less then 0.0")
    private Double cityArea;

    @Past(message = "The founded year must be in the past")
    private Date foundedAt;

    @ElementCollection
    @NotEmpty(message = "City has to have at least one language of speaking")
    private List<String> languages;

    @Builder
    public City(Long id, String cityName, Country country, Integer cityPopulation, Double cityArea, Date foundedAt,
                List<String> languages) {
        this.id = id;
        this.cityName = cityName;
        this.country = country;
        this.cityPopulation = cityPopulation;
        this.cityArea = cityArea;
        this.foundedAt = foundedAt;
        this.languages = languages;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        City city = (City) o;
        return getId() != null && Objects.equals(getId(), city.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "cityName = " + cityName + ", " +
                "country = " + country + ", " +
                "cityPopulation = " + cityPopulation + ", " +
                "cityArea = " + cityArea + ", " +
                "foundedAt = " + foundedAt + ", " +
                "languages = " + languages + ")";
    }
}