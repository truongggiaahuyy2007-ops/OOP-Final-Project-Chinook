/*
 * Food Nutrition Management System
 *
 * Developed by:
 * Truong Gia Huy
Student ID: 87482503626
 * Nguyen Ngoc Nhu Y
 *Student ID: 87482503633
 * Course: Object Oriented Programming
 */
package com.siu.oop.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
    boolean save(T entity);
    boolean update(T entity);
    boolean delete(ID id);
}