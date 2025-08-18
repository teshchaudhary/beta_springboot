// package com.example.restaurantbooking.seeder;

// import com.example.restaurantbooking.model.Restaurant;
// import com.example.restaurantbooking.repository.RestaurantRepository;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

// import java.util.List;

// @Component
// public class RestaurantSeeder implements CommandLineRunner {

//     private final RestaurantRepository restaurantRepository;

//     public RestaurantSeeder(RestaurantRepository restaurantRepository) {
//         this.restaurantRepository = restaurantRepository;
//     }

//     @Override
//     public void run(String... args) {
//         if (restaurantRepository.count() == 0) { // seed only if empty
//             List<Restaurant> restaurants = List.of(
//                 Restaurant.builder()
//                         .name("The Spice House")
//                         .image("spice_house.jpg")
//                         .location("Bangalore, MG Road")
//                         .cuisine_type("Indian")
//                         .build(),
//                 Restaurant.builder()
//                         .name("Sushi World")
//                         .image("sushi_world.jpg")
//                         .location("Delhi, Sakura Street")
//                         .cuisine_type("Japanese")
//                         .build(),
//                 Restaurant.builder()
//                         .name("Pasta Paradise")
//                         .image("pasta_paradise.jpg")
//                         .location("Mumbai, Roma Lane")
//                         .cuisine_type("Italian")
//                         .build(),
//                 Restaurant.builder()
//                         .name("Tandoori Tales")
//                         .image("tandoori_tales.jpg")
//                         .location("Chennai, Food Street")
//                         .cuisine_type("Indian")
//                         .build(),
//                 Restaurant.builder()
//                         .name("Burger Hub")
//                         .image("burger_hub.jpg")
//                         .location("Hyderabad, Downtown Ave")
//                         .cuisine_type("American")
//                         .build(),
//                 Restaurant.builder()
//                         .name("Dragon’s Den")
//                         .image("dragons_den.jpg")
//                         .location("Kolkata, Park Street")
//                         .cuisine_type("Chinese")
//                         .build(),
//                 Restaurant.builder()
//                         .name("Taco Fiesta")
//                         .image("taco_fiesta.jpg")
//                         .location("Pune, FC Road")
//                         .cuisine_type("Mexican")
//                         .build(),
//                 Restaurant.builder()
//                         .name("Royal Biryani House")
//                         .image("royal_biryani.jpg")
//                         .location("Lucknow, Hazratganj")
//                         .cuisine_type("Mughlai")
//                         .build(),
//                 Restaurant.builder()
//                         .name("Le Petit Café")
//                         .image("le_petit_cafe.jpg")
//                         .location("Goa, Panjim")
//                         .cuisine_type("French")
//                         .build(),
//                 Restaurant.builder()
//                         .name("Green Leaf Vegan")
//                         .image("green_leaf_vegan.jpg")
//                         .location("Bangalore, Indiranagar")
//                         .cuisine_type("Vegan")
//                         .build()
//             );

//             restaurantRepository.saveAll(restaurants);
//             System.out.println("✅ Seeded 10 restaurants!");
//         }
//     }
// }
