package ru.job4j.hql.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "car_models")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public CarModel() {
    }

    public CarModel(String name) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarModel carModel = (CarModel) o;
        return Objects.equals(id, carModel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
