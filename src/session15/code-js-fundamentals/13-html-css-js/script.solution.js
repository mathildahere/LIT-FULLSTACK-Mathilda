/**
 * OpenAI Landing Page Clone - Solution
 * 
 * This file contains the solution for animations.
 * Try not to look at this until you've attempted the challenge!
 */

document.addEventListener('DOMContentLoaded', function() {
  
  // Sidebar Toggle Animation
  const sidebarLinks = document.querySelectorAll('.sidebar-link');
  
  sidebarLinks.forEach(link => {
    link.addEventListener('mouseenter', function() {
      this.style.transform = 'translateX(5px)';
      this.style.transition = 'all 0.3s ease';
    });
    
    link.addEventListener('mouseleave', function() {
      this.style.transform = 'translateX(0)';
    });
  });
  
  // Button Hover Animations
  const buttons = document.querySelectorAll('.btn');
  
  buttons.forEach(button => {
    button.addEventListener('mouseenter', function() {
      // Add a subtle scale effect
      this.style.transform = 'translateY(-2px) scale(1.02)';
      this.style.transition = 'all 0.3s ease';
      
      // Add glow effect for primary buttons
      if (this.classList.contains('btn-primary')) {
        this.style.boxShadow = '0 8px 24px rgba(255, 255, 255, 0.3)';
      }
    });
    
    button.addEventListener('mouseleave', function() {
      this.style.transform = 'translateY(0) scale(1)';
      this.style.boxShadow = '';
    });
  });
  
  // Featured Cards Animation
  const cards = document.querySelectorAll('.featured-card, .news-card');
  
  cards.forEach(card => {
    card.addEventListener('mouseenter', function() {
      this.style.transform = 'translateY(-4px) scale(1.01)';
      this.style.transition = 'all 0.3s ease';
    });
    
    card.addEventListener('mouseleave', function() {
      this.style.transform = 'translateY(0) scale(1)';
    });
  });
  
  // Smooth Scroll
  document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
      e.preventDefault();
      const target = document.querySelector(this.getAttribute('href'));
      if (target) {
        target.scrollIntoView({
          behavior: 'smooth',
          block: 'start'
        });
      }
    });
  });
  
  // Parallax Effect for Hero Section (optional enhancement)
  const hero = document.querySelector('.hero');
  if (hero) {
    window.addEventListener('scroll', () => {
      const scrolled = window.pageYOffset;
      hero.style.transform = `translateY(${scrolled * 0.3}px)`;
    });
  }
  
});

/**
 * KEY LEARNINGS:
 * 
 * 1. Event Listeners:
 *    - addEventListener('event', function)
 *    - mouseenter/mouseleave for hover
 *    - click for interactions
 * 
 * 2. DOM Manipulation:
 *    - querySelector() to select elements
 *    - querySelectorAll() for multiple elements
 *    - Add/remove classes or styles
 * 
 * 3. CSS Transitions:
 *    - Use transition property in CSS
 *    - Smooth property changes on hover/click
 * 
 * 4. Transform Properties:
 *    - translateY() for vertical movement
 *    - translateX() for horizontal movement
 *    - scale() for size changes
 * 
 * 5. Box Shadow:
 *    - Add depth to hover effects
 *    - Simulate elevation changes
 */
