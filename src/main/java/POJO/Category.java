package POJO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Category   {
  @JsonProperty("id")
  private Integer id = null;
  @JsonProperty("name")
  private String name = null;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Category category = (Category) o;
    return Objects.equals(this.id, category.id) &&
            Objects.equals(this.name, category.name);
  }

}

