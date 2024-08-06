<script>
	import '$lib/styles/global.css';
	import TableOfContent from '$lib/components/TableOfContent.svelte';
</script>

<div class="container">
	<TableOfContent />
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
		transition: all 0.3s ease; /* Smooth transition */
	}
</style>
