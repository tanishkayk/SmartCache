# SmartCache - Project Statement

## Problem Statement

SmartCache is a Java-based caching server that stores frequently requested HTTP responses. It reduces repeated requests to origin servers by serving previously cached responses when available.

The system uses TTL (Time-To-Live) to expire cached responses and LRU (Least Recently Used) to manage limited cache capacity. It also supports multiple clients through multithreading and maintains cache statistics and logs.

## Scope

The project focuses on implementing a basic HTTP caching server for GET requests.

The system covers:
- Client request handling
- HTTP request parsing and validation
- In-memory response caching
- TTL-based cache expiration
- LRU-based cache eviction
- Communication with origin servers
- Concurrent client handling
- Cache statistics and logging

## Target Users

The system is primarily intended for:
- Students learning Java networking and caching concepts
- Developers demonstrating cache management techniques
- Academic projects involving Java, networking, collections, and multithreading

## High-Level Features

- HTTP GET request handling
- Cache hit and cache miss detection
- TTL-based expiration
- LRU cache eviction
- Origin server communication
- Multithreaded client handling
- Cache statistics
- Logging and error handling
- Modular object-oriented architecture

## Technologies Used

- Java
- Maven
- IntelliJ IDEA
- Git / GitHub
- Java Networking APIs
- Java Collections Framework
- Java Concurrency Utilities