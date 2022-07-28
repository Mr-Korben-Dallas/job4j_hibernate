package ru.job4j.hql.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "car_brands")
public class CarBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarModel> cars = new ArrayList<>();

    public CarBrand() {
    }

    public CarBrand(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CarModel> getCars() {
        return cars;
    }

    public void setCars(List<CarModel> cars) {
        this.cars = cars;
    }

    public void addCar(CarModel car) {
        this.cars.add(car);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarBrand carBrand = (CarBrand) o;
        return Objects.equals(id, carBrand.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
