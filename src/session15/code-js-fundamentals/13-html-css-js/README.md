# Quiz 13: OpenAI Landing Page Clone

## Learning Objectives
- Build complete HTML structure
- Apply CSS styling and layouts
- Implement JavaScript animations
- Create responsive web pages
- Combine HTML, CSS, and JavaScript in one project

## The Challenge

Clone the OpenAI landing page based on the reference image. This quiz is special because you'll be building an actual website!

### What You Need to Build

Create a working clone of the OpenAI landing page with:
1. **HTML** - Page structure and layout
2. **CSS** - Styling, colors, and layout
3. **JavaScript** - Animations and interactions

### Reference Image

Check `public/snapshot.png` for the complete design reference.

## Page Structure

### 1. Header (Top Bar)
- OpenAI logo
- "Log in" button on the right

### 2. Sidebar (Left Navigation)
- Fixed sidebar with navigation links:
  - Research
  - Safety
  - For developers
  - ChatGPT
  - Blog
  - About
  - Careers
  - Contact

### 3. Hero Section
- Large blue gradient background
- Title: "The OpenAI Foundation"
- Description text
- Two buttons:
  - "Watch livestream" (primary)
  - "Learn more" (secondary)

### 4. Featured Content
- Grid of cards with icons and descriptions
- Chat, DevDay, Company Knowledge cards

### 5. Latest News Section
- News cards with thumbnails
- Titles and metadata

### 6. Footer
- Multiple columns with links
- Social media icons
- Copyright notice

## Design Requirements

### Colors
- **Background**: Black (#000000)
- **Text**: White
- **Accents**: Blue gradients (#667eea, #764ba2)

### Typography
- Clean, modern sans-serif font
- Multiple font sizes for hierarchy

### Animations Required

**Buttons:**
- Hover effect: Slight lift and glow
- Smooth transitions

**Sidebar Links:**
- Hover effect: Background change and slight translation
- Smooth color transitions

**Cards:**
- Hover effect: Lift slightly
- Shadow effect

## File Structure

```
13-html-css-js/
├── index.html          # Your implementation
├── styles.css          # Your CSS
├── script.js            # Your JavaScript
├── index.solution.html  # Solution HTML
├── styles.solution.css  # Solution CSS
├── script.solution.js   # Solution JavaScript
└── public/
    └── snapshot.png     # Reference image
```

## Running the Quiz

### Method 1: Open Directly
```bash
cd 13-html-css-js
# Open index.html in your browser
open index.html
```

### Method 2: Serve with local server
```bash
cd 13-html-css-js
npx serve . --port 3000
# Open http://localhost:3000
```

### Method 3: Use pnpm
```bash
cd 13-html-css-js
pnpm start
```

## What You'll Implement

### 1. HTML Structure
Build semantic HTML:
- `<header>` for top bar
- `<aside>` for sidebar
- `<main>` for content sections
- `<footer>` for footer
- Use semantic tags: `<nav>`, `<article>`, `<section>`

### 2. CSS Styling
Create dark theme with:
- Black background
- White text
- Blue gradients for accents
- Modern button styles
- Grid layouts

### 3. JavaScript Animations
Add interactivity:
- Button hover effects
- Sidebar link animations
- Smooth transitions
- Card hover effects

## Key CSS Techniques

```css
/* Gradients */
background: linear-gradient(135deg, #667eea, #764ba2);

/* Transitions */
transition: all 0.3s ease;

/* Transform */
transform: translateY(-2px);

/* Box Shadow */
box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
```

## Key JavaScript Patterns

```javascript
// Query Selector
const button = document.querySelector('.btn');

// Event Listener
button.addEventListener('mouseenter', function() {
  // Add hover effect
});

// Class Manipulation
element.classList.add('active');
element.classList.remove('active');
```

## Evaluation Criteria

Your clone will be evaluated on:
- ✅ HTML structure matches the layout
- ✅ CSS styling matches the design
- ✅ JavaScript animations work smoothly
- ✅ Button hover effects implemented
- ✅ Sidebar animations implemented
- ✅ Responsive layout
- ✅ Overall visual similarity

## Tips

1. **Start with HTML** - Build the structure first
2. **Add CSS** - Style section by section
3. **Add JavaScript last** - Animate the completed page
4. **Use the reference image** - Compare constantly
5. **Test in browser** - See it come to life!

## Resources

- [MDN: HTML](https://developer.mozilla.org/en-US/docs/Web/HTML)
- [MDN: CSS](https://developer.mozilla.org/en-US/docs/Web/CSS)
- [MDN: JavaScript Events](https://developer.mozilla.org/en-US/docs/Web/Events)
- [CSS-Tricks: Flexbox](https://css-tricks.com/snippets/css/a-guide-to-flexbox/)
- [CSS-Tricks: Grid](https://css-tricks.com/snippets/css/complete-guide-grid/)

## Bonus Challenges

1. Make it responsive (mobile-friendly)
2. Add smooth scrolling
3. Add parallax effects
4. Create a working sidebar toggle
5. Add more interactive elements

Happy cloning! 🎨
