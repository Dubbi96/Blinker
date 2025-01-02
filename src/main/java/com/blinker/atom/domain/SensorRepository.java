package com.blinker.atom.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor,Long> {
}
