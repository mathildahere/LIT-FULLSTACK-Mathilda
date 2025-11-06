# How to Run This Assignment

## Quick Start

### Method 1: Run Tests (Recommended)

```bash
# Step 1: Navigate to this assignment directory
cd session-16-multithreading/assignment-01-basic-threads

# Step 2: Run tests (will fail initially - that's expected!)
mvn test

# Step 3: Watch mode (auto-reloads on file changes)
mvn test -Dtest.solution=false
```

### Method 2: Run from Project Root

```bash
# From project root, run this specific assignment
mvn test -pl session-16-multithreading/assignment-01-basic-threads
```

### Method 3: Test Solution Mode

```bash
# Test that solution works (should pass)
mvn test -Dtest.solution=true
```

## What You'll See

Initially, all tests will **FAIL** because `TradingThread` is not fully implemented. That's the whole point of TDD!

### Step 1: Red 🔴
```bash
cd session-16-multithreading/assignment-01-basic-threads
mvn test
# Output: Tests failing ❌
```

### Step 2: Green ✅
Implement the class in `src/main/java/com/kulkultech/threads/TradingThread.java`:
- Complete the constructor
- Implement the `run()` method
- Handle `InterruptedException`

### Step 3: Test Again
```bash
mvn test
# Output: Tests passing ✅
```

### Step 4: Verify Solution
```bash
# Test with solution mode
mvn test -Dtest.solution=true
# Output: All tests passing ✅
```

## QA Checklist

- [ ] Tests fail when using `TradingThread.java` (problem file)
- [ ] Tests pass when using `TradingThreadSolution.java` (solution file)
- [ ] Multiple threads can run concurrently
- [ ] Thread state transitions correctly (NEW → RUNNABLE → TERMINATED)

## Need Help?

Compare with the solution:
```bash
cat src/main/java/com/kulkultech/threads/TradingThreadSolution.java
```

Happy coding! 🎯
