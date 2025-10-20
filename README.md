# Algorithmic Toolkit (Java)

A curated collection of classical algorithms and data structures implemented in Java, with emphasis on clarity, correctness, and efficiency.

## Contents

| Category | Examples | Complexity |
|-----------|-----------|-------------|
| Graph and Geometry | Kd-Tree (range and nearest queries), Collinear Points, Percolation | O(log N) average, O(N² log N), O(α(N)) |
| Search and Optimization | A* (8-Puzzle), Binary Search, BFS/DFS | O(E log V) |
| Core Data Structures | Stacks, Queues, Union-Find | Amortized ≈ α(N) |

## How to Run

Requires **JDK 17** or higher.  
Compile and execute any module:

```bash
javac -cp . src/<module>/<MainClass>.java && java <MainClass>
```

## Notes

These implementations currently pass most automated tests (above 80%).  
Further updates will refine code quality, add documentation, and expand coverage.
