package com.restaurantreview.restaurant_service.integration.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.restaurantreview.restaurant_service.model.Restaurant;
import com.restaurantreview.restaurant_service.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@ExtendWith(TestContainersInitializer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = TestContainersInitializer.class)
public class RestaurantRepositoryTest {
  @Autowired private RestaurantRepository restaurantRepository;

  @Test
  void testNameIsUppercase() {
    Restaurant restaurant = new Restaurant();
    restaurant.setName("Da Cracco");
    assertEquals("DA CRACCO", restaurant.getName());
  }

  @Test
  void testUniqueConstraintCaseInsensitive() {
    Restaurant r1 = new Restaurant();
    r1.setName("Da Cracco");
    restaurantRepository.saveAndFlush(r1);

    Restaurant r2 = new Restaurant();
    r2.setName("da cracco");

    assertEquals(r1.getName().toUpperCase(), r1.getName());
    assertEquals(r1.getName(), r2.getName());
    assertThrows(
        DataIntegrityViolationException.class,
        () -> {
          restaurantRepository.saveAndFlush(r2);
        });
  }
}
