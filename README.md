# Introduction

This is a clone of https://github.com/ministryofjustice/hmpps-template-kotlin/tree/main to provide a base project for learning Kotlin in a real-world-ish environment.

It has a few example endpoints to run locally and view in Swagger UI at http://localhost:8080/swagger-ui.html

Note that there may be redundant files from the template that have not been removed.

# How to Run

You can run it with `./gradlew bootRun --args='--spring.profiles.active=local'`

# Tech

It is a SpringMVC application due to the uk.gov.justice.service.hmpps:hmpps-kotlin-spring-boot-starter dependency.

It is set up with JDK 25 as the default.

# Exercises

Change as much or as litle as you like to complete the exercises.
They are designed to be relatively open-ended to support exploratory learning.
If you are familiar with Java and Springboot, the exercises will be easier, but should be pushing you quickly towards areas where Kotlin and Java differ.

Make sure you write tests as you go!

The "main" branch is the base template, the "example-completed-exercises" branch contains possible solutions for the exercises, with each commit a different exercise.

# The Future

The Scala Sandbox has gone through many iterations, and is now a way of adding a measure of Scala experience to your Consultant Profile.
These exercises may be the basis for the same in Kotlin.

# Contributing

Feel free to raise a Pull Request or commit directly into the 'main' branch for new exercises or base code for exercises.
Feel free to add/modify the example solutions in the 'example-completed-exercises' branch.

## Exercise 1 - Create an order endpoint

Implement:
- POST /orders
- GET /orders/{id}

Requirements:
1. Order input (CreateOrderRequest):
   1. customerId,
   1. productId,
   1. quantity,
   1. address (line1, city, postcode).
1. Order to store in DB (Order):
   1. orderId,
   1. customerId,
   1. productId,
   1. quantity,
   1. address (line1, city, postcode),
   1. trackingId,
1. Order to return in response (OrderDto):
   1. orderId,
   1. trackingUrl,
1. Tracking information isn't always available immediately after an order is placed.
   1. Update the code to correctly handle nullable tracking IDs.
   1. No !! double-bang operators remain in the codebase.
   1. API returns a default value for tracking URL when appropriate.
1. Use extension functions to map from CreateOrderRequest to Order and from Order to OrderDto.
1. Handle error cases e.g. no items requested.
   1. Prefer using sealed interfaces for error handling.
   1. Convert these to appropriate HTTP status codes in the controller.

## Exercise 2 - Searching Orders

Customer support needs a search endpoint.

Requirements:
1. Implement GET /orders
1. Optional query parameters:
    1. customerId
    1. orderStatus
1. Any combination of query parameters should work.
1. OrderStatus is new - a refactor may be required. Allowed values:
    1. CREATED,
    1. PROCESSING,
    1. SHIPPED,
    1. DELIVERED,
1. Stretch Goal: Additional query parameters:
    1. postcode
    1. minimumQuantity

## Exercise 3 - Business Rules

Implement PATCH /orders/{id}/status

Requirements:
1. CREATED → PROCESSING
1. PROCESSING → SHIPPED
1. SHIPPED → DELIVERED
1. Illegal transitions should be rejected.

Avoid nested if/else if possible.

## Exercise 4 - Warehouse Allocation
Each warehouse stocks different products.

Warehouse A (Unit 2, The Kiln, Hoults Yard, Walker Road, Newcastle-upon-Tyne, NE6 1AB)
- Product 1
- Product 2

Warehouse B (Castle Park - Welsh Back, The Pithay, Bristol BS1 2NB)
- Product 2
- Product 5

Requirements:
1. When an order is created, allocate it to the nearest warehouse that stocks every requested item.
1. Optionally use https://api.postcodes.io/docs/postcode/lookup#endpoint to look up a postcode.
   1. Then probably just ask AI to calculate the distance between two coordinates ;)

## Exercise 5 - Add an exercise...

Don't forget to publish the exercise and example solution in this repo!