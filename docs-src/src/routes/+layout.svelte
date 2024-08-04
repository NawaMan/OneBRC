<script>
  import '$lib/styles/global.css';
</script>

<div class="container">
  <slot />
</div>

<style>
  .container {
    width: 1280px;
    height: 720px;
    margin: 0 auto;
    display: flex;
    justify-content: center;
    align-items: center;
    border: 5px solid #ccc; /* Optional: adds a border to visualize the container */
    background: #fff;
    font-family: 'Playfair Display Bold', 'Cormorant Garamond', serif;
  }
</style>