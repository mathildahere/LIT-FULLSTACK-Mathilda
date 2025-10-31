# JavaScript Fundamentals - TDD Quizzes

A collection of Test-Driven Development quizzes based on video transcripts covering JavaScript fundamentals.

## Getting Started

### Install Dependencies

First, install all dependencies from the root:

```bash
pnpm install
```

### Run a Specific Quiz

You have **three ways** to run an individual quiz:

#### Option 1: From Quiz Directory (Recommended)
```bash
# Navigate to the quiz folder
cd 01-random-generator-love-calculator

# Run tests
pnpm test

# Run in watch mode (auto-reloads on changes)
pnpm test:watch

# Run from another quiz
cd ../02-control-flow-love-messages
pnpm test
```

#### Option 2: From Root Directory Using pnpm Filter
```bash
# Run a specific quiz from root
pnpm --filter "quiz-01-love-calculator" test

# Or using the quiz folder pattern
pnpm --filter "./01-random-generator-love-calculator" test
```

#### Option 3: Run All Quizzes
```bash
# From the root directory
pnpm test
```

This runs tests for **all quizzes** in the workspace.

## Quiz Structure

Each quiz folder contains:
- `*.test.js` - Test file with failing tests
- `*.js` - Production code file (to implement)
- `*.solution.js` - Solution (don't peek!)
- `README.md` - Learning objectives and hints
- `package.json` - Quiz configuration

## Available Quizzes

1. **01-random-generator-love-calculator** - Learn Math.random() and generating random numbers
2. **02-control-flow-love-messages** - Learn if-else statements and control flow
3. **03-comparator-equality-love-ranges** - Learn comparison operators (===, >, <, <=, >=)
4. **04-logical-operators-love-conditions** - Learn logical operators (&&, ||, !)
5. **05-bmi-calculator-advanced** - Learn function parameters, BMI calculation, and formatted returns
6. **06-leap-year-challenge** - Learn modulo operator, nested conditionals, and complex logic
7. **07-arrays-guest-list** - Learn arrays, indexing, and array methods
8. **08-fizzbuzz-arrays** - Learn FizzBuzz classic interview problem with arrays
9. **09-random-lunch-selector** - Learn random array selection and string formatting
10. **10-while-loops-bottles-of-beer** - Learn while loops and avoiding infinite loops
11. **11-for-loops-fizzbuzz** - Learn for loops and when to use for vs while
12. **12-fibonacci-generator** - Learn Fibonacci sequence and dynamic array building
13. **13-html-css-js** - Clone OpenAI landing page (HTML, CSS, JavaScript integration)

More quizzes coming soon...

## How to Use

1. **Read the test file** to understand what you need to build
2. **Implement the function** in the main JS file
3. **Run tests** to verify your solution
4. **Compare with solution** if you get stuck (or if all tests pass!)

## Learning Philosophy

- Start with failing tests (Red)
- Implement minimal code to pass (Green)
- Refactor for clarity (Refactor)
- Repeat!

Happy coding! 🎯
