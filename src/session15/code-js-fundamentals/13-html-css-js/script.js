/**
 * OpenAI Landing Page Clone - Animation Script
 *
 * TODO: Add JavaScript for animations
 *
 * Requirements:
 * 1. Sidebar menu toggle functionality
 * 2. Button hover animations
 * 3. Smooth transitions
 *
 * Hints:
 * - Add click event listener to sidebar toggle button
 * - Add mouseenter/mouseleave events to buttons
 * - Use CSS classes to control animations
 */

document.addEventListener('DOMContentLoaded', function() {
  const sidebar = document.getElementById('sidebar');
  const toggle = document.querySelector('.sidebar-toggle');
  const buttons = document.querySelectorAll('.btn');
  const links = document.querySelectorAll('a[href^="#"]');
  // TODO: Implement sidebar toggle animation
  // When clicking a menu button, show/hide sidebar with smooth transition
  if (toggle) {
    toggle.addEventListener('click', () => {
      if (window.innerWidth <= 768) {
        // Mode mobile
        sidebar.classList.toggle('active');
      } else {
        // Mode desktop
        sidebar.classList.toggle('hidden');
      }
    });
  }


  // TODO: Implement button hover animations
  // Add event listeners to buttons
  // Apply CSS classes on hover for smooth transitions
  buttons.forEach(b => {
    b.addEventListener('mouseenter', () => b.classList.add('hover'));
    b.addEventListener('mouseleave', () => b.classList.remove('hover'));
  });

  // TODO: Add smooth scroll behavior

  // Your code here
  links.forEach(link => {
    link.addEventListener('click', (e) => {
      const href = link.getAttribute('href');
      if (href.startsWith('#')) {
        const target = document.querySelector(href);
        if (target) {
          e.preventDefault();
          target.scrollIntoView({ behavior: 'smooth', block: 'start' });
          // close sidebar on mobile after click
          if (sidebar.classList.contains('active')) sidebar.classList.remove('active');
        }
      }
    });
  });

});
