# How to Run Quiz 13 - OpenAI Clone

## Quick Start

### Method 1: Simple (Recommended)

```bash
# Navigate to quiz directory
cd 13-html-css-js

# Open index.html in your browser
# On Mac:
open index.html

# On Windows:
start index.html

# On Linux:
xdg-open index.html
```

### Method 2: With Live Server

```bash
# Install serve globally (optional)
npm install -g serve

# Run local server
cd 13-html-css-js
npx serve . --port 3000
```

Then open: http://localhost:3000

### Method 3: Using pnpm

```bash
cd 13-html-css-js
pnpm install
pnpm start
```

## Viewing Your Work

1. **Open index.html** in a browser
2. **Inspect your code** with browser DevTools (F12)
3. **Compare** with `public/snapshot.png`
4. **Test animations** by hovering over buttons and sidebar

## Viewing the Solution

1. **Open index.solution.html** to see the complete clone
2. **Study** the HTML, CSS, and JavaScript
3. **Compare** with your implementation
4. **Learn** from the differences

## Development Workflow

### Step 1: HTML (Structure)
```bash
# Edit index.html
# Build the HTML structure
# Add semantic tags
```

### Step 2: CSS (Styling)
```bash
# Edit styles.css
# Add colors, layout, spacing
# Match the reference design
```

### Step 3: JavaScript (Interactivity)
```bash
# Edit script.js
# Add hover animations
# Implement transitions
```

## Testing Checklist

- [ ] Header with logo and login button
- [ ] Sidebar navigation on the left
- [ ] Hero section with blue gradient
- [ ] Buttons with hover effects
- [ ] Featured content cards
- [ ] News section
- [ ] Footer with links
- [ ] Animations work smoothly
- [ ] Matches reference design

## Key Features to Implement

### 1. Sidebar Hover Animation
```javascript
// Links should animate on hover
sidebarLink.addEventListener('mouseenter', function() {
  // Add animation
});
```

### 2. Button Hover Animation
```javascript
// Buttons should lift and glow on hover
button.addEventListener('mouseenter', function() {
  // Add transform and shadow
});
```

### 3. Card Hover Animation
```javascript
// Cards should lift slightly on hover
card.addEventListener('mouseenter', function() {
  // Add translateY
});
```

## Need Help?

Compare with the solution files:
```bash
# View solution
cat index.solution.html
cat styles.solution.css  
cat script.solution.js
```

## Browser DevTools

Press `F12` to open DevTools:
- **Elements** tab - Inspect HTML/CSS
- **Console** tab - Debug JavaScript
- **Network** tab - Check resources

Good luck building your clone! 🎨✨
