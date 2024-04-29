package orlov.oleksandr.programming.citycountryspringrest.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.time.Year;
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

    private String cityName;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    private int cityPopulation;

    private double cityArea;

    private Year foundedAt;

    @ElementCollection
    private List<String> languages;

    @Builder
    public City(Long id, String cityName, Country country, int cityPopulation, double cityArea, Year foundedAt,
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