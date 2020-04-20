package com.learnreact.Spring.React.Demo.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** @author a0r00rf */
@Document // ==@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
  @Id private String id;
  private String description;
  private Double price;
}
