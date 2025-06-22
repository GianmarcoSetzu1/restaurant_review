package com.restaurantreview.restaurant_service.integration.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.restaurantreview.restaurant_service.model.Restaurant;
import com.restaurantreview.restaurant_service.repository.RestaurantRepository;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        DataIntegrityViolationException.class, () -> restaurantRepository.saveAndFlush(r2));
  }

  @Test
  void testFindPartialName() {
    Restaurant restaurant1 = new Restaurant();
    restaurant1.setName("Da Cracco");
    restaurantRepository.saveAndFlush(restaurant1);
    Page<Restaurant> partialName =
        restaurantRepository.findByNameContainingIgnoreCase("Cra", Pageable.ofSize(5));
    assertEquals(restaurant1, partialName.get().toList().getFirst());
    Page<Restaurant> space =
        restaurantRepository.findByNameContainingIgnoreCase("", Pageable.ofSize(5));
    assertEquals(restaurant1, space.get().toList().getFirst());
    Page<Restaurant> lowerCase =
        restaurantRepository.findByNameContainingIgnoreCase("cra", Pageable.ofSize(5));
    assertEquals(restaurant1, lowerCase.get().toList().getFirst());
    Page<Restaurant> withSpace =
        restaurantRepository.findByNameContainingIgnoreCase(" cr", Pageable.ofSize(5));
    assertEquals(restaurant1, withSpace.get().toList().getFirst());
    assertThrows(
        NoSuchElementException.class,
        () ->
            restaurantRepository
                .findByNameContainingIgnoreCase("k", Pageable.ofSize(5))
                .get()
                .toList()
                .getFirst());

    Restaurant restaurant2 = new Restaurant();
    restaurant2.setName("Da CR7");
    restaurantRepository.saveAndFlush(restaurant2);
    Page<Restaurant> result =
        restaurantRepository.findByNameContainingIgnoreCase("cr", Pageable.ofSize(5));
    ArrayList<Restaurant> expectedList = new ArrayList<>();
    expectedList.add(restaurant1);
    expectedList.add(restaurant2);
    assertEquals(expectedList, result.get().toList());
  }
}
