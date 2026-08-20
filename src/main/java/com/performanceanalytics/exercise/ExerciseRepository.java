package com.performanceanalytics.exercise;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Just an interface with no implementation needed. Spring Data JPA generates
// a working implementation of this at runtime, based on the two generic
// parameters: <Exercise, Long> means "this repository manages Exercise
// entities, whose primary key type is Long" (matching the type of
// Exercise.id).
//
// Extending JpaRepository gives you findAll(), findById(), save(), delete(),
// and pagination/sorting support.
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {


    // Follows the same findBy naming convention as before. Spring parses
    // "findByName" and builds: SELECT * FROM exercise WHERE name = ?
    // Optional<> because a name might not exist — this makes the "not
    // found" case explicit in the return type, rather than returning null
    // and hoping every caller remembers to check for it.
    Optional<Exercise> findByName(String name);
}

// ------------------------------------------------------------------------------


// ── Inherited from CrudRepository (the base interface) ──────────────
//
// <S extends T> S save(S entity);
//     Insert a new row, or update an existing one if the entity's id
//     is already set and matches an existing row.
//
// <S extends T> List<S> saveAll(Iterable<S> entities);
//     Save a batch of entities in one call.
//
// Optional<T> findById(ID id);
//     Look up one row by primary key. Optional because it might not
//     exist, forces you to handle the "not found" case explicitly.
//
// boolean existsById(ID id);
//     Cheap check: does a row with this id exist? (no full row fetch)
//
// List<T> findAll();
//     Every row in the table. (JpaRepository overrides this to return
//     List instead of the more generic Iterable that CrudRepository
//     declares, convenient, since you can .stream() it directly.)
//
// List<T> findAllById(Iterable<ID> ids);
//     Fetch multiple specific rows by their ids in one call.
//
// long count();
//     Total number of rows in the table.
//
// void deleteById(ID id);
//     Delete one row by primary key.
//
// void delete(T entity);
//     Delete a specific entity instance.
//
// void deleteAllById(Iterable<? extends ID> ids);
//     Delete multiple rows by id in one call.
//
// void deleteAll(Iterable<? extends T> entities);
//     Delete a specific collection of entities.
//
// void deleteAll();
//     Delete every row in the table. Use with caution.


// ── Inherited from PagingAndSortingRepository ────────────────────────
//
// List<T> findAll(Sort sort);
//     Every row, sorted by the given field(s).
//
// Page<T> findAll(Pageable pageable);
//     One "page" of rows at a time (limit/offset), plus metadata like
//     total page count. Useful once a table has thousands of rows and
//     you don't want to return them all in one response.


// ── Inherited directly from JpaRepository ────────────────────────────
//
// void flush();
//     Force any pending changes to be written to the database right
//     now, instead of waiting for the transaction to naturally commit.
//
// <S extends T> S saveAndFlush(S entity);
//     save() + flush() combined into one call.
//
// void deleteAllInBatch();
//     Delete every row using a single, efficient bulk DELETE statement
//     (rather than one DELETE per row, which deleteAll() effectively
//     does).
//
// T getReferenceById(ID id);
//     Get a "lazy" placeholder for an entity without hitting the
//     database yet, only queries when you actually access a field
//     on it. Niche, rarely needed early on.


// ── NOT inherited — you must declare these yourself ──────────────────
// Anything referencing a SPECIFIC FIELD on the entity has to be
// written, as JpaRepository has no built-in knowledge of
// what columns Exercise actually has. Spring Data parses the method
// NAME at startup and builds the query from it, no method body
// needed, but the signature itself is to written.