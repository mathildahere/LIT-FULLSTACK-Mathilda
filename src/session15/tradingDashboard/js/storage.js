const Storage = {
  load(key) {
    try {
      const raw = localStorage.getItem(key);
      return raw ? JSON.parse(raw) : null;
    } catch (e) {
      console.error('Storage load failed', e);
      return null;
    }
  },
  save(key, value) {
    try {
      localStorage.setItem(key, JSON.stringify(value));
    } catch (e) {
      console.error('Storage save failed', e);
    }
  },
  clear(key) {
    try {
      localStorage.removeItem(key);
    } catch (e) {
      console.error('Storage clear failed', e);
    }
  }
};

export default Storage;
