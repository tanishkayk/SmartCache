# SmartCache

## Overview

SmartCache is a Java-based caching server that acts as an intermediary between clients and origin servers.

It stores frequently requested HTTP responses and serves them from the cache when they are requested again. This reduces repeated requests to the origin server and can improve response time.

The system uses TTL (Time-To-Live) for cache expiration and LRU (Least Recently Used) for cache eviction. It also supports multiple clients through Java multithreading and maintains cache statistics and logs.

## Features

- HTTP GET request handling
- Cache hit and cache miss detection
- TTL-based cache expiration
- LRU cache eviction
- Communication with origin servers
- Multithreaded client handling
- Cache statistics
- Logging and error handling
- Modular object-oriented design

## Technologies Used

- Java
- Java Networking (`Socket`, `ServerSocket`, `HttpClient`)
- Java Collections (`LinkedHashMap`)
- Java Concurrency (`ExecutorService`)
- Maven
- IntelliJ IDEA
- Git / GitHub

## Project Structure

```text
SmartCache/
├── src/
│   └── main/
│       └── java/
│           └── smartcache/
│               ├── CachingServer.java
│               ├── ClientHandler.java
│               ├── HttpRequest.java
│               ├── HttpResponse.java
│               ├── CacheEntry.java
│               ├── CacheManager.java
│               ├── LRUCache.java
│               ├── OriginServerClient.java
│               ├── CacheStatistics.java
│               └── Logger.java
├── src/
│   └── test/
├── README.md
├── statement.md
└── pom.xml